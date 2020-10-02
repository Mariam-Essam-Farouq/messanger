package messenger.iti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import messenger.iti.fragments.ContactsFragment;
import messenger.iti.helpers.DataBaseHelper;

public class ContactInfoActivity extends AppCompatActivity  {
private TextView nameTextView,numberTextView;
private Button buttenCall,buttonSendMessage;
public static final String NUMBER_KEY="number key";
public static final String NAME_KEY="name key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        initComponents();
        Intent intent=getIntent();
        String name=intent.getStringExtra(ContactsFragment.KEY_NAME);
        final String number=intent.getStringExtra(ContactsFragment.KEY_NUMBER);
        nameTextView.setText(name);
        numberTextView.setText(number);
        buttenCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL,
                        Uri.fromParts("tel", numberTextView.getText().toString(), null)));
            }
        });
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ContactInfoActivity.this,SendMessageActivity.class);
                intent1.putExtra(NAME_KEY,nameTextView.getText().toString());
                intent1.putExtra(NUMBER_KEY,numberTextView.getText().toString());
                startActivity(intent1);
            }
        });
    }

    public void initComponents(){
        nameTextView=findViewById(R.id.ms11);
        numberTextView=findViewById(R.id.ms22);
        buttenCall=findViewById(R.id.btnCall);
        buttonSendMessage=findViewById(R.id.btnSendMessage);
    }
}