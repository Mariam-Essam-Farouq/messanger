package messenger.iti;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import messenger.iti.helpers.DataBaseHelper;
import messenger.iti.helpers.GroupHelper;

public class GroupSendMessageActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText msgEditText;
    private Button sendNowButton,sendLaterButton;
    private DataBaseHelper dataBaseHelper;
    public ArrayList<String> nameList=new ArrayList<>();
    public ArrayList<String>numberList=new ArrayList<>();
    String contact = "";
    String contactNumber = "";
    private Calendar calendar;
    private int year, month, day;
    private int passyear , passmonth , passday , passhour , passminute;
    private TimePickerDialog picker;
    private CheckBox simpleCheckBox;
    private CheckBox simpleCheckBox2;
    private CheckBox simpleCheckBox3;
    boolean [] checkedItems;
    String [] namearray;
    String [] numberarray;
    private String []splitNumber;
    GroupHelper groupHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_sendmessage);

        msgEditText=findViewById(R.id.msg);
        sendLaterButton=findViewById(R.id.later);
        sendNowButton=findViewById(R.id.now);
        Intent intent=getIntent();
        groupHelper= new GroupHelper(this);
        dataBaseHelper = new DataBaseHelper(this);
        getData();
        getData2();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        simpleCheckBox = (CheckBox) findViewById(R.id.checkBox);
        simpleCheckBox2 = (CheckBox) findViewById(R.id.checkBox2);
        simpleCheckBox3 = (CheckBox) findViewById(R.id.checkBox3);

        simpleCheckBox.setOnClickListener(this);
        simpleCheckBox2.setOnClickListener(this);
        simpleCheckBox3.setOnClickListener(this);

        namearray = nameList.toArray(new String[nameList.size()]);
        numberarray = numberList.toArray(new String[numberList.size()]);

        checkedItems = new boolean[namearray.length];



        if (ContextCompat.checkSelfPermission(GroupSendMessageActivity.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(GroupSendMessageActivity.this,
                    Manifest.permission.SEND_SMS)){
                ActivityCompat.requestPermissions(GroupSendMessageActivity.this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);
            }else{
                ActivityCompat.requestPermissions(GroupSendMessageActivity.this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        }
        sendNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    for (int i = 0; i <splitNumber.length ; i++) {
                        SmsManager smsManager=SmsManager.getDefault();
                        smsManager.sendTextMessage(splitNumber[i],null,msgEditText.getText().toString(),null,null);
                        Toast.makeText(GroupSendMessageActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                    }
                    Boolean isInserted = dataBaseHelper.insertData2(contact,contactNumber,msgEditText.getText().toString());

                    if(isInserted) {
                        Toast.makeText(GroupSendMessageActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }else
                        Toast.makeText(GroupSendMessageActivity.this,"Data not Inserted",Toast.LENGTH_SHORT).show();


                }catch (Exception e){
                    Toast.makeText(GroupSendMessageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        sendLaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isInserted = dataBaseHelper.insertData3(contact,contactNumber,msgEditText.getText().toString(),passyear,passmonth,passday,passhour,passminute);

                if(isInserted) {
                    Toast.makeText(GroupSendMessageActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }else
                    Toast.makeText(GroupSendMessageActivity.this,"Data not Inserted",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
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

        else if (simpleCheckBox3.isChecked()){

            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(GroupSendMessageActivity.this);
            final ArrayList<Integer> groupitems =new ArrayList<>();

            mBuilder.setTitle("Choose contacts/groups to message");
            mBuilder.setMultiChoiceItems(namearray, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                    if (isChecked){
                        groupitems.add(position);


                    }else if (groupitems.contains(position)) {
                        groupitems.remove(Integer.valueOf(position));
                    }

                }
            });

            mBuilder.setCancelable(false);
            mBuilder.setPositiveButton("Choose", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which ) {

                    for (int i = 0; i < groupitems.size(); i++) {

                        contact = contact + namearray[groupitems.get(i)] +" ,";
                        contactNumber = contactNumber + numberarray[groupitems.get(i)] + " ,";


                    }
                    splitNumber=contactNumber.split(",");

                }


            });


            mBuilder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });


            AlertDialog mDialog = mBuilder.create();
            mDialog.show();

        }

    }

    public void getData(){
        if(groupHelper.getAllData()==null) {
            Toast.makeText(GroupSendMessageActivity.this, "No data", Toast.LENGTH_LONG).show();
        } else {
            Cursor res = groupHelper.getAllData();
            if (res.getCount() == 0) {
                Toast.makeText(GroupSendMessageActivity.this, "No data", Toast.LENGTH_LONG).show();
                return;
            }
            StringBuffer buffer ;
            StringBuffer buffer2 ;
            while (res.moveToNext()) {
                buffer=new StringBuffer();
                buffer2=new StringBuffer();
                buffer.append(res.getString(0));
                buffer2.append(res.getString(2));
                nameList.add(buffer.toString());
                numberList.add(buffer2.toString());
            }
        }
    }

    public void getData2(){
        if(groupHelper.getAllData2()==null) {
            Toast.makeText(GroupSendMessageActivity.this, "No data", Toast.LENGTH_LONG).show();
        } else {
            Cursor res = groupHelper.getAllData2();
            if (res.getCount() == 0) {
                Toast.makeText(GroupSendMessageActivity.this, "No data", Toast.LENGTH_LONG).show();
                return;
            }
            StringBuffer buffer ;
            StringBuffer buffer2 ;
            while (res.moveToNext()) {
                buffer=new StringBuffer();
                buffer2=new StringBuffer();
                buffer.append(res.getString(0));
                buffer2.append(res.getString(1));
                nameList.add(buffer.toString());
                numberList.add(buffer2.toString());
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(GroupSendMessageActivity.this,
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }}
