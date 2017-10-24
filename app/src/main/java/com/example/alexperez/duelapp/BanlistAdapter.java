package com.example.alexperez.duelapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BanlistAdapter extends RecyclerView.Adapter<BanlistAdapter.ViewHolder>{

    private List<ListItem> listItems;
    private Context context;

    public BanlistAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    //Inlfate the activity with data from the parent context in which case is the banlist xml activity
    @Override
    public BanlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.banlist_items,parent,false);
        return new ViewHolder(v);
    }

    //we will be grabbing the Data positon within the data, and grabbing the Head and Desc
    @Override
    public void onBindViewHolder(BanlistAdapter.ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);

        holder.textViewHead.setText(listItem.getHead());
        holder.textViewDesc.setText(listItem.getDesc());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
        }
    }
}
