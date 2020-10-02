package messenger.iti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import messenger.iti.adapters.RecyclerViewAdapter2;
import messenger.iti.helpers.DataBaseHelper;

public class Messageinfo extends AppCompatActivity {

private DataBaseHelper dataBaseHelper;
private RecyclerViewAdapter2 recyclerViewAdapter2;
  private   Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_info);
        Intent intent=getIntent();
        dataBaseHelper=new DataBaseHelper(this);
        final String name=intent.getStringExtra("hi");
        final String message=intent.getStringExtra("hayi");
        button=findViewById(R.id.btnCall);
        final TextView textView=findViewById(R.id.ms11);
        final TextView textView2=findViewById(R.id.ms33);
        textView.setText(name);
        textView2.setText(message);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                dataBaseHelper.deleteMessage(message);
                }catch (Exception e){
                    Toast.makeText(Messageinfo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });






    }
}
