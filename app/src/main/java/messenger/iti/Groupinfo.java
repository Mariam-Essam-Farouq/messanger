package messenger.iti;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import messenger.iti.adapters.GroupsRecyclerAdapter;
import messenger.iti.helpers.GroupHelper;

public class Groupinfo extends AppCompatActivity {

    private GroupHelper groupHelper;
    private Button buttenDelete,buttonSendMessage,buttonEdit;
    private String []splitName;
    private String []splitNumber;
    private String []Names;
    private String []Numbers;
    boolean [] checkedItems;
    boolean [] checkedItems2;
    final ArrayList<Integer> groupitems =new ArrayList<>();
    final ArrayList<Integer> groupitems2 =new ArrayList<>();

    public ArrayList<String> allNumbers=new ArrayList<>();
    public ArrayList<String> allNames=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_message_info);
        Intent intent=getIntent();


        groupHelper=new GroupHelper(this);
        getLists();


        final String title=intent.getStringExtra("title");
        final String name=intent.getStringExtra("name");
        final String number=intent.getStringExtra("number");
        splitName=name.split(",");
        splitNumber=number.split(",");

        buttenDelete=findViewById(R.id.btnDelete);
        buttonSendMessage=findViewById(R.id.btnSend);
        buttonEdit=findViewById(R.id.btnEdit);
        Names = allNames.toArray(new String[allNames.size()]);
        Numbers = allNumbers.toArray(new String[allNumbers.size()]);
        checkedItems = new boolean[splitName.length];
        checkedItems = new boolean[Names.length];
        final TextView textView=findViewById(R.id.ms11);
        final TextView textView1=findViewById(R.id.ms22);

        textView.setText(title);
        textView1.setText(name);
        buttenDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    groupHelper.deleteGroup(title);
                }catch (Exception e){
                    Toast.makeText(Groupinfo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Groupinfo.this,GroupSendMessage.class);
                intent1.putExtra("NAME",name);
                intent1.putExtra("NUMBER",number);
                startActivity(intent1);
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Groupinfo.this);

                mBuilder.setTitle("Group Members");
                mBuilder.setMultiChoiceItems(splitName, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked){
                            groupitems.add(position);


                        }else if (groupitems.contains(position)) {
                            groupitems.remove(Integer.valueOf(position));
                        }
                        checkedItems[position] = true;
                        String currentItem = splitName[position];
                        Toast.makeText(getApplicationContext(),
                                currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Edit Existing Group Members", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which ) {
                        String contact = "";
                        String contactNumber = "";
                        for (int i = 0; i < groupitems.size(); i++) {

                            contact = contact + splitName[groupitems.get(i)] + " ,";
                            contactNumber = contactNumber + splitNumber[groupitems.get(i)] + " ,";



                            textView1.setText(contact);
                             groupHelper.insertInRow(title,contact,contactNumber);

                        }





                    }


                });


                mBuilder.setNegativeButton("Add New Members", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Groupinfo.this);

                        mBuilder.setTitle("Contacts");
                        mBuilder.setMultiChoiceItems(Names, checkedItems2, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                                if (isChecked){
                                    groupitems2.add(position);


                                }else if (groupitems2.contains(position)) {
                                    groupitems2.remove(Integer.valueOf(position));
                                }
                                checkedItems[position] = true;
                                String currentItem = Names[position];
                                Toast.makeText(getApplicationContext(),
                                        currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                            }
                        });

                        mBuilder.setCancelable(false);
                        mBuilder.setPositiveButton("Add The Members", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which ) {
                                String contact = "";
                                String contactNumber = "";
                                for (int i = 0; i < groupitems2.size(); i++) {

                                    contact = contact + Names[groupitems2.get(i)] + " ,";
                                    contactNumber = contactNumber + Numbers[groupitems2.get(i)] + " ,";

                                    textView1.setText(contact);
                                    groupHelper.insertInRow(title,contact,contactNumber);

                                }

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
                });

                mBuilder.setNeutralButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });



    }

    public void getLists(){
        if(groupHelper.getAllData2()==null) {
            Toast.makeText(Groupinfo.this, "No data", Toast.LENGTH_LONG).show();
        } else {
            Cursor res = groupHelper.getAllData2();
            if (res.getCount() == 0) {
                Toast.makeText(Groupinfo.this, "No data", Toast.LENGTH_LONG).show();
                return;
            }
            StringBuffer buffer ;
            StringBuffer buffer2 ;
            while (res.moveToNext()) {
                buffer=new StringBuffer();
                buffer2=new StringBuffer();
                buffer.append(res.getString(0));
                buffer2.append(res.getString(1));
                allNames.add(buffer.toString());
                allNumbers.add(buffer2.toString());
            }
        }
    }

}
