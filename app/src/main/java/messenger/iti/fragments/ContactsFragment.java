package messenger.iti.fragments;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import android.provider.ContactsContract;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import messenger.iti.ContactInfoActivity;
import messenger.iti.R;
import messenger.iti.adapters.RecyclerViewAdapter;
import messenger.iti.helpers.DataBaseHelper;
import messenger.iti.helpers.GroupHelper;


public class ContactsFragment extends Fragment implements RecyclerViewAdapter.ListItemClickListener , Serializable {
    private ConstraintLayout constraintLayout;
    private RecyclerView contactsRecyclerView;
    private Intent intent;
    private RecyclerViewAdapter recyclerViewAdapter;
    public ArrayList<String> nameList = new ArrayList<>();
    public ArrayList<String> numberList = new ArrayList<>();
    public static final String KEY_NAME = "name";
    public static final String KEY_NUMBER = "number";
    FloatingActionButton fab;
    boolean [] checkedItems;
    String [] namearray;
    String [] numberarray;
    String groupName;
    DataBaseHelper dataBaseHelper;
    GroupHelper groupHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        dataBaseHelper = new DataBaseHelper(getContext());
        constraintLayout = view.findViewById(R.id.addContactConstraintLayout);
        contactsRecyclerView = view.findViewById(R.id.numbers_recycler_view);
        final ArrayList<Integer> groupitems =new ArrayList<>();
        groupHelper = new GroupHelper(getContext());


        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    getContext().startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        try {
            getContacts();
            Bundle bundle = new Bundle();
            bundle.putSerializable("names", nameList);
            bundle.putSerializable("numbers", numberList);
            contactsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            contactsRecyclerView.setHasFixedSize(true);
            contactsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            contactsRecyclerView.setAdapter(new RecyclerViewAdapter(nameList, numberList, this));
            recyclerViewAdapter = new RecyclerViewAdapter(nameList, numberList, this);
            contactsRecyclerView.setAdapter(recyclerViewAdapter);
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }



        namearray = nameList.toArray(new String[nameList.size()]);fab =  view.findViewById(R.id.fab);
        numberarray = numberList.toArray(new String[numberList.size()]);

        checkedItems = new boolean[namearray.length];
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText taskEditText = new EditText(getContext());





                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());

                mBuilder.setTitle("Choose contacts to add to the group");
                mBuilder.setMultiChoiceItems(namearray, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked){
                            groupitems.add(position);


                        }else if (groupitems.contains(position)) {

                            groupitems.remove(Integer.valueOf(position));
                        }

                        checkedItems[position] = true;
                        String currentItem = nameList.get(position);

                        Toast.makeText(getContext(),
                                "is" + currentItem + " in the group ? " + isChecked, Toast.LENGTH_SHORT).show();
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Add contacts", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which ) {
                        String contact = "";
                        String contactNumber = "";
                            for (int i = 0; i < groupitems.size(); i++) {

                                contact = contact + namearray[groupitems.get(i)] +" ,";
                                contactNumber = contactNumber + numberarray[groupitems.get(i)] + " ,";


                            }

                        Toast.makeText(getContext(),"New group added. \n members : " + contact, Toast.LENGTH_SHORT).show();



                        Boolean isInserted = groupHelper.insertData(groupName,contact,contactNumber);

                        if(isInserted) {
                            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();

                        }else
                            Toast.makeText(getContext(),"Data not Inserted",Toast.LENGTH_SHORT).show();


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

                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("Write group name")
                        .setMessage("What do you want to name it as ?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                groupName = String.valueOf(taskEditText.getText());

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }

        });



        return view;
    }

    public void getContacts() {
        ContentResolver cr = getContext().getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor cursor1 = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (cursor1.moveToNext()) {
                        String contactNumber = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String contactName = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        numberList.add(contactNumber);
                        nameList.add(contactName);
                        groupHelper.insertData2(contactName,contactNumber);
                        cursor1.close();
                        break;
                    }

                }

            } while (cursor.moveToNext());

        }

    }



    @Override
    public void onItemClick(View itemView, int position) {
        Intent intent = new Intent(getContext(), ContactInfoActivity.class);
        intent.putExtra(KEY_NAME, nameList.get(position));
        intent.putExtra(KEY_NUMBER, numberList.get(position));
        startActivity(intent);


    }
}


