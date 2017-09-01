package com.example.dell.blitzschlag.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.blitzschlag.Classes.Schedule;
import com.example.dell.blitzschlag.R;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder>{
    private List<Schedule> eventslist;
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title,venue,start,date;

        public MyViewHolder(View view){
            super(view);
            title=(TextView)view.findViewById(R.id.schedule_title);
            venue=(TextView)view.findViewById(R.id.schedule_venue);
            start=(TextView)view.findViewById(R.id.schedule_start_time);
        }

    }

    public ScheduleAdapter(List<Schedule> eventslist){
        this.eventslist=eventslist;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_list_row,parent,false);
        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(MyViewHolder holder,int position){

        String text;
        final Schedule movie=eventslist.get(position);
        holder.title.setText(movie.getTitle());
        text="Venue: "+movie.getVenue();
        holder.venue.setText(text);
        if(movie.getStart()/100<=12){
            text=String.format("%02d", movie.getStart()/100)+":"+movie.getStart()%100+" AM";
        }
        else{
            text=String.format("%02d",((movie.getStart()/100)-12))+":"+movie.getStart()%100+" PM";
        }
        holder.start.setText(text);
    }

    public int getItemCount(){
        return eventslist.size();
    }
}
