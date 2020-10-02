package messenger.iti.fragments;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import messenger.iti.GroupSendMessageActivity;
import messenger.iti.Messageinfo;
import messenger.iti.AddingNumberActivity;
import messenger.iti.ContactInfoActivity;
import messenger.iti.R;
import messenger.iti.adapters.RecyclerViewAdapter2;
import messenger.iti.helpers.DataBaseHelper;


public class MessageFragment extends Fragment implements RecyclerViewAdapter2.ListItemClickListener {

    private RecyclerView massegesRecyclerView;
    private RecyclerViewAdapter2 recyclerViewAdapter;
    private DataBaseHelper dataBaseHelper;
    public ArrayList<String> nameList=new ArrayList<>();
    public ArrayList<String>numberList=new ArrayList<>();
    public ArrayList<String>massegeList=new ArrayList<>();
    public static final String KEY_NAME2="name2";
    public static final String KEY_NUMBER2="number2";
    public static final String KEY_MESSAGE="massege";
    FloatingActionButton fab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_message, container, false);
        massegesRecyclerView=view.findViewById(R.id.masseges_recycler_view);
        dataBaseHelper=new DataBaseHelper(getContext());
        showMasseges();
        massegesRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        massegesRecyclerView.setHasFixedSize(true);
        massegesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        massegesRecyclerView.setAdapter(new RecyclerViewAdapter2(nameList, numberList,massegeList, (RecyclerViewAdapter2.ListItemClickListener) this));
        recyclerViewAdapter = new RecyclerViewAdapter2(nameList, numberList,massegeList, (RecyclerViewAdapter2.ListItemClickListener) this);
        massegesRecyclerView.setAdapter(recyclerViewAdapter);
        fab =  view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent (getContext(), GroupSendMessageActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
    public void showMasseges(){
        if(dataBaseHelper.getAllData2()==null) {
            Toast.makeText(getContext(), "No data", Toast.LENGTH_LONG).show();
        } else {
            Cursor res = dataBaseHelper.getAllData2();
            if (res.getCount() == 0) {
                Toast.makeText(getContext(), "No data", Toast.LENGTH_LONG).show();
                return;
            }
            StringBuffer buffer ;
            StringBuffer buffer2 ;
            StringBuffer buffer3 ;
            while (res.moveToNext()) {
                buffer=new StringBuffer();
                buffer2=new StringBuffer();
                buffer3=new StringBuffer();
                buffer.append(res.getString(0));
                buffer2.append(res.getString(1));
                buffer3.append(res.getString(2));
                nameList.add(buffer.toString());
                numberList.add(buffer2.toString());
                massegeList.add(buffer3.toString());
            }
        }
    }
    @Override
    public void onItemClick(View itemView, int position) {
        Intent intent=new Intent (getContext(),Messageinfo.class);
        intent.putExtra("hi",nameList.get(position));
        intent.putExtra("hay",numberList.get(position));
        intent.putExtra("hayi",massegeList.get(position));
        startActivity(intent);

    }
}