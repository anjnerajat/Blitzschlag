package com.example.dell.blitzschlag.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.example.dell.blitzschlag.Classes.Event;
import com.example.dell.blitzschlag.Classes.FeedImageView;
import com.example.dell.blitzschlag.R;
import com.example.dell.blitzschlag.Wishlist.DBHelper;
import com.example.dell.blitzschlag.app.AppController;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder>{
    private List<Event> eventslist;
    ImageLoader imageLoader = AppController.getmInstance().getImageLoader();

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title,venue,start,end,date,remembered;
        public ImageView borderedStar,fullStar;
        public FeedImageView feedImageView;

        public MyViewHolder(View view){
            super(view);
            title=(TextView)view.findViewById(R.id.event_title);
            venue=(TextView)view.findViewById(R.id.event_venue);
            start=(TextView)view.findViewById(R.id.event_start_time);
            date=(TextView)view.findViewById(R.id.event_date);
            remembered=(TextView)view.findViewById(R.id.event_remembered);
            borderedStar=(ImageView)view.findViewById(R.id.remember_star);
            fullStar=(ImageView)view.findViewById(R.id.remembered_star);
            feedImageView = (FeedImageView) view.findViewById(R.id.event_image);
        }

    }

    public EventsAdapter(List<Event> eventslist){
        this.eventslist=eventslist;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_row,parent,false);
        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(final MyViewHolder holder,int position){

        String text;
        final Event movie=eventslist.get(position);
        holder.title.setText(movie.getTitle());
        text="Venue: "+movie.getVenue();
        holder.venue.setText(text);
        if(movie.getStart()/100<=12){
            text="Time: "+String.format("%02d", movie.getStart()/100)+":"+movie.getStart()%100+" AM";
        }
        else{
            text="Time: "+String.format("%02d",((movie.getStart()/100)-12))+":"+movie.getStart()%100+" PM";
        }
        holder.start.setText(text);
        if(movie.getDate()==1){
            text="April 26";
        }
        else if(movie.getDate()==2){
            text= "April 27";
        }
        else if(movie.getDate()==3){
            text= "April 28";
        }
        holder.date.setText(text);
        /*holder.date.setOnClickListener(
                new TextView.OnClickListener(){
                    public void onClick(View view){
                        holder.date.setText("Well Done!!");
                    }
                }
        );*/
        if(movie.getRemember()){
            holder.borderedStar.setVisibility(View.GONE);
            holder.fullStar.setVisibility(View.VISIBLE);
        }
        else {
            holder.borderedStar.setVisibility(View.VISIBLE);
            holder.fullStar.setVisibility(View.GONE);
        }
        if (movie.getImage() != null) {
            holder.feedImageView.setImageUrl(movie.getImage(), imageLoader);
            holder.feedImageView.setVisibility(View.VISIBLE);
            holder.feedImageView.setResponseObserver(new FeedImageView.ResponseObserver() {
                @Override
                public void onError() {
                }

                @Override
                public void onSuccess() {
                }
            });
        } else {
            holder.feedImageView.setVisibility(View.GONE);
        }

    }

    public int getItemCount(){
        return eventslist.size();
    }
}
