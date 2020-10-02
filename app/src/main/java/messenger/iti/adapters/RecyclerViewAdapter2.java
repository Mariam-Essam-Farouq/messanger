package messenger.iti.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import messenger.iti.R;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter{
    private ArrayList<String> nameList;
    private ArrayList <String>numberList;
    private ArrayList <String>massegesList;
    private final RecyclerViewAdapter2.ListItemClickListener listItemClickListener;

    public RecyclerViewAdapter2(ArrayList<String> nameList, ArrayList<String> numberList,ArrayList<String> massegesList, RecyclerViewAdapter2.ListItemClickListener listItemClickListener) {
        this.nameList=nameList;
        this.numberList=numberList;
        this.massegesList=massegesList;
        this.listItemClickListener =listItemClickListener ;
    }
    public interface ListItemClickListener{
        void onItemClick(View itemView, int position);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View row=layoutInflater.inflate(R.layout.row_messages,parent,false);
        RecyclerViewAdapter2.ViewHolder viewHolder=new RecyclerViewAdapter2.ViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((RecyclerViewAdapter2.ViewHolder) holder).nameTextView.setText(nameList.get(position));
        ((RecyclerViewAdapter2.ViewHolder) holder).massegeTextView.setText(massegesList.get(position));
        ((RecyclerViewAdapter2.ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
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
        public TextView nameTextView,massegeTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView=itemView.findViewById(R.id.nameTextView);
            massegeTextView=itemView.findViewById(R.id.massegeTextView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            listItemClickListener.onItemClick(itemView, this.getPosition());
        }


    }
    public  void  deleteMethod(int position) {
        nameList.remove(position);
        numberList.remove(position);
        massegesList.remove(position);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }
}
