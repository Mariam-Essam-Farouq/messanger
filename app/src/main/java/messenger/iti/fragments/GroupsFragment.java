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

import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import messenger.iti.Groupinfo;
import messenger.iti.R;
import messenger.iti.adapters.GroupsRecyclerAdapter;
import messenger.iti.helpers.GroupHelper;

public class GroupsFragment extends Fragment  implements GroupsRecyclerAdapter.ListItemClickListener {

    private RecyclerView groupsRecyclerView;
    private GroupsRecyclerAdapter recyclerViewAdapter;
    private GroupHelper groupHelper;
    public ArrayList<String> nameList=new ArrayList<>();
    public ArrayList<String>numberList=new ArrayList<>();
    public ArrayList<String>titleList=new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_groups, container, false);
        groupsRecyclerView=view.findViewById(R.id.groups_recycler_view);
        groupHelper=new GroupHelper(getContext());

        showGroups();
        groupsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        groupsRecyclerView.setHasFixedSize(true);
        groupsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        groupsRecyclerView.setAdapter(new GroupsRecyclerAdapter(titleList , nameList, (GroupsRecyclerAdapter.ListItemClickListener) this));
        recyclerViewAdapter = new GroupsRecyclerAdapter(titleList , nameList, (GroupsRecyclerAdapter.ListItemClickListener) this);
        groupsRecyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    public void showGroups(){
        if(groupHelper.getAllData()==null) {
            Toast.makeText(getContext(), "No data", Toast.LENGTH_LONG).show();
        } else {
            Cursor res = groupHelper.getAllData();
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
                titleList.add(buffer.toString());
                nameList.add(buffer2.toString());
                numberList.add(buffer3.toString());
            }
        }
    }



    @Override
    public void onItemClick(View itemView, int position) {
        Intent intent=new Intent (getContext(), Groupinfo.class);

        intent.putExtra("title",titleList.get(position));
        intent.putExtra("name",nameList.get(position));
        intent.putExtra("number",numberList.get(position));

        startActivity(intent);

    }
}