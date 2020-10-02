package messenger.iti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import messenger.iti.helpers.DataBaseHelper;

public class SendMessageActivity extends AppCompatActivity implements View.OnClickListener{

  private String name,number;
  private TextView nameTextView;
  private EditText msgEditText;
  private Button sendNowButton,sendLaterButton;
    private DataBaseHelper dataBaseHelper;

    private Calendar calendar;
    private int year, month, day;
    private int passyear , passmonth , passday , passhour , passminute;

    private TimePickerDialog picker;
    private CheckBox simpleCheckBox;
    private CheckBox simpleCheckBox2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
       nameTextView=findViewById(R.id.name_TextView);
       msgEditText=findViewById(R.id.msg);
       sendLaterButton=findViewById(R.id.later);
       sendNowButton=findViewById(R.id.now);
        Intent intent=getIntent();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        simpleCheckBox = (CheckBox) findViewById(R.id.checkBox);
        simpleCheckBox2 = (CheckBox) findViewById(R.id.checkBox2);
        simpleCheckBox.setOnClickListener(this);
        simpleCheckBox2.setOnClickListener(this);
        name=intent.getStringExtra(ContactInfoActivity.NAME_KEY);
        number=intent.getStringExtra(ContactInfoActivity.NUMBER_KEY);
        nameTextView.setText(name);
        dataBaseHelper=new DataBaseHelper(this);
        if (ContextCompat.checkSelfPermission(SendMessageActivity.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(SendMessageActivity.this,
                    Manifest.permission.SEND_SMS)){
                ActivityCompat.requestPermissions(SendMessageActivity.this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);
            }else{
                ActivityCompat.requestPermissions(SendMessageActivity.this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        }
        sendNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                SmsManager smsManager=SmsManager.getDefault();
                   smsManager.sendTextMessage(number,null,msgEditText.getText().toString(),null,null);
                    Toast.makeText(SendMessageActivity.this, "Message sent", Toast.LENGTH_SHORT).show();

                     Boolean isInserted = dataBaseHelper.insertData2(name,number,msgEditText.getText().toString());

                    if(isInserted) {
                        Toast.makeText(SendMessageActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }else
                        Toast.makeText(SendMessageActivity.this,"Data not Inserted",Toast.LENGTH_SHORT).show();


                }catch (Exception e){
                    Toast.makeText(SendMessageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        sendLaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isInserted = dataBaseHelper.insertData3(name,number,msgEditText.getText().toString(),passyear,passmonth,passday,passhour,passminute);
                Intent intent1=new Intent(getBaseContext(),Myservice.class);
                intent1.putExtra("z",number);
                intent1.putExtra("o",msgEditText.getText().toString());
                PendingIntent pendingIntent = PendingIntent.getService(SendMessageActivity.this, 0, intent1, 0);

                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());




                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,pendingIntent);

                if(isInserted) {
                    Toast.makeText(SendMessageActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }else
                    Toast.makeText(SendMessageActivity.this,"Data not Inserted",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {

        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    passyear = arg1;
                    passmonth = arg2 ;
                    passday = arg3 ;
                }
            };


    @Override
    public void onClick(View view) {
                if (simpleCheckBox.isChecked())
                    showDialog(999);

                else if (simpleCheckBox2.isChecked()){
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minutes = calendar.get(Calendar.MINUTE);
                    // time picker dialog
                    picker = new TimePickerDialog(this,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                    passhour = sHour ;
                                    passminute = sMinute ;
                                }
                            }, hour, minutes, true);
                    picker.show();
                }

                }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(SendMessageActivity.this,
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}