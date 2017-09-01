package com.example.dell.blitzschlag.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.blitzschlag.Classes.Wish;
import com.example.dell.blitzschlag.R;
import java.util.List;

public class WishAdapter extends RecyclerView.Adapter<WishAdapter.MyViewHolder>{
    private List<Wish> eventslist;
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title,venue,start,date,type;

        public MyViewHolder(View view){
            super(view);
            title=(TextView)view.findViewById(R.id.schedule_title);
            venue=(TextView)view.findViewById(R.id.schedule_venue);
            start=(TextView)view.findViewById(R.id.schedule_start_time);
            date=(TextView)view.findViewById(R.id.schedule_date);
            type=(TextView)view.findViewById(R.id.schedul_category);
        }

    }

    public WishAdapter(List<Wish> eventslist){
        this.eventslist=eventslist;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list_row,parent,false);
        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(MyViewHolder holder,int position){

        String text;
        final Wish movie=eventslist.get(position);
        holder.title.setText(movie.getTitle());
        text="Venue: "+movie.getVenue();
        holder.venue.setText(text);
        holder.start.setText(movie.getStart());
        holder.date.setText(movie.getDate());
        holder.type.setText("("+movie.getType()+")");
    }

    public int getItemCount(){
        return eventslist.size();
    }
}
