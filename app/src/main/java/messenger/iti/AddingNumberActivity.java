package messenger.iti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import messenger.iti.helpers.DataBaseHelper;

public class AddingNumberActivity extends AppCompatActivity {
  private Button saveButton;
  private EditText nameEditText,numberEditText;
  private DataBaseHelper dataBaseHelper;
  private String name,number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_number);
        initComponents();
        dataBaseHelper=new DataBaseHelper(this);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 name  = nameEditText.getText().toString();
                 number  = numberEditText.getText().toString();
                boolean isInserted = dataBaseHelper.insertData(name,number);
                if(isInserted) {
                    Toast.makeText(AddingNumberActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }else
                    Toast.makeText(AddingNumberActivity.this,"Data not Inserted",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void initComponents(){
        saveButton=findViewById(R.id.saveContactButton);
        nameEditText=findViewById(R.id.editTextTextPersonName);
        numberEditText=findViewById(R.id.editTextPhone);
    }
}