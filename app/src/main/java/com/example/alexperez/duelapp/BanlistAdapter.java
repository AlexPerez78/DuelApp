package com.example.alexperez.duelapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        //Base Color for Text
        holder.textViewCardType.setTextColor(Color.parseColor("#000000"));
        holder.textViewHead.setTextColor(Color.parseColor("#000000"));
        holder.textViewDesc.setTextColor(Color.parseColor("#000000"));

        switch(listItem.getCardType()){
            case("Monster"):
                holder.banlist_layout.setBackgroundColor(Color.parseColor("#F7DC6F"));
                break;

            case("Monster/Effect"):
                holder.banlist_layout.setBackgroundColor(Color.parseColor("#FFA500"));
                break;

            case("Monster/Pendulum"):
                holder.banlist_layout.setBackgroundColor(Color.parseColor("#FFA500"));
                break;

            case("Monster/Fusion"):
                holder.banlist_layout.setBackgroundColor(Color.parseColor("#9370DB"));
                break;

            case("Monster/Ritual"):
                holder.banlist_layout.setBackgroundColor(Color.parseColor("#ADD8E6"));
                break;

            case("Monster/Synchro"):
                holder.banlist_layout.setBackgroundColor(Color.parseColor("#D3D3D3"));
                break;

            case("Monster/Xyz"):
                holder.banlist_layout.setBackgroundColor(Color.parseColor("#000000"));
                holder.textViewCardType.setTextColor(Color.parseColor("#FDFEFE"));
                holder.textViewHead.setTextColor(Color.parseColor("#FDFEFE"));
                holder.textViewDesc.setTextColor(Color.parseColor("#FDFEFE"));
                break;

            case("Spell"):
                holder.banlist_layout.setBackgroundColor(Color.parseColor("#20B2AA"));
                break;

            case("Trap"):
                holder.banlist_layout.setBackgroundColor(Color.parseColor("#EE82EE"));
                break;


        }

        holder.textViewCardType.setText(listItem.getCardType());
        holder.textViewHead.setText(listItem.getHead());
        holder.textViewDesc.setText(listItem.getDesc());
    }

    @Override
    public int getItemCount() {

        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewCardType;
        public TextView textViewHead;
        public TextView textViewDesc;
        //public TextView textViewUserFormat;

        public LinearLayout banlist_layout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewCardType = (TextView) itemView.findViewById(R.id.textViewCardType);
            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            //textViewUserFormat = (TextView) itemView.findViewById(R.id.);

            banlist_layout = (LinearLayout) itemView.findViewById(R.id.banlist_layout);
        }
    }
}
