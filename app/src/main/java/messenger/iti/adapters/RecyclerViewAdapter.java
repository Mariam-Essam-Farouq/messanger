package messenger.iti.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

import messenger.iti.ContactInfoActivity;
import messenger.iti.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private ArrayList<String> nameList;
    private ArrayList <String>numberList;
    private final RecyclerViewAdapter.ListItemClickListener listItemClickListener;

    public RecyclerViewAdapter(ArrayList<String> nameList, ArrayList<String> numberList, ListItemClickListener listItemClickListener) {
        this.nameList=nameList;
        this.numberList=numberList;
        this.listItemClickListener =listItemClickListener ;
    }
    public interface ListItemClickListener{
        void onItemClick(View itemView, int position);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View row=layoutInflater.inflate(R.layout.row_contacts,parent,false);
        RecyclerViewAdapter.ViewHolder viewHolder=new RecyclerViewAdapter.ViewHolder(row);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).nameTextView.setText(nameList.get(position));
        ((ViewHolder) holder).numberTextView.setText(numberList.get(position));
        ((RecyclerViewAdapter.ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemClickListener.onItemClick(v, position);
            }
        });
    }
    @Override
    public int getItemCount()
    {
        return numberList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public TextView nameTextView,numberTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView=itemView.findViewById(R.id.nameTextView);
            numberTextView=itemView.findViewById(R.id.numberTextView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            listItemClickListener.onItemClick(itemView, this.getPosition());
        }
}
}
