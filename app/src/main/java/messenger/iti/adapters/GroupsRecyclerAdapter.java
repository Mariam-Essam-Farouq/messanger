package messenger.iti.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import messenger.iti.R;

public class GroupsRecyclerAdapter extends RecyclerView.Adapter {
    private ArrayList <String>titleList;
    private ArrayList<String> nameList;
    private final GroupsRecyclerAdapter.ListItemClickListener listItemClickListener;

    public GroupsRecyclerAdapter(ArrayList<String> titleList , ArrayList<String> nameList, GroupsRecyclerAdapter.ListItemClickListener listItemClickListener) {
        this.titleList=titleList;
        this.nameList=nameList;
        this.listItemClickListener =listItemClickListener ;
    }
    public interface ListItemClickListener{
        void onItemClick(View itemView, int position);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View row=layoutInflater.inflate(R.layout.row_group,parent,false);
        GroupsRecyclerAdapter.ViewHolder viewHolder=new GroupsRecyclerAdapter.ViewHolder(row);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((GroupsRecyclerAdapter.ViewHolder) holder).titleTextView.setText(titleList.get(position));
        ((GroupsRecyclerAdapter.ViewHolder) holder).nameTextView.setText(nameList.get(position));
        ((GroupsRecyclerAdapter.ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemClickListener.onItemClick(v, position);
            }
        });
    }
    @Override
    public int getItemCount()
    {
        return titleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public TextView titleTextView,nameTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView=itemView.findViewById(R.id.titleTextView);
            nameTextView=itemView.findViewById(R.id.nameTextView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            listItemClickListener.onItemClick(itemView, this.getPosition());
        }
    }
}
