package com.example.dell.blitzschlag.Events;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.blitzschlag.Individual.Individual;
import com.example.dell.blitzschlag.R;

import com.example.dell.blitzschlag.Adapters.EventsAdapter;
import com.example.dell.blitzschlag.Classes.Event;

import android.app.ProgressDialog;
import android.content.Context;

import android.util.Log;
import android.view.GestureDetector;

import android.view.MotionEvent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.dell.blitzschlag.Wishlist.DBHelper;
import com.example.dell.blitzschlag.app.AppController;

public class RoboticsTab extends Fragment {

    private static final String TAG = EventsActivity.class.getSimpleName();
    private static final String url = "https://jsonblob.com/api/jsonBlob/574abc47e4b01190df6fd287";
    private ProgressDialog pDialog;

    public List<Event> eventList = new ArrayList<>();
    public RecyclerView recyclerView;
    public EventsAdapter mAdapter;
    public ProgressBar progressBar;
    public DBHelper mydb;


    public RoboticsTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.robotics_tab, container, false);
        // Inflate the layout for this fragment
        recyclerView=(RecyclerView)v.findViewById(R.id.event_recycler_view);
        mAdapter= new EventsAdapter(eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mydb=new DBHelper(getContext());

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                final Event movie = eventList.get(position);
                //Toast.makeText(getContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), Individual.class);
                intent.putExtra("title", movie.getTitle());
                intent.putExtra("image",movie.getImage());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        //pDialog.show();

        progressBar=(ProgressBar)v.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        //hidePDialog();
                        progressBar.setVisibility(View.GONE);

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Event event = new Event();

                                if(obj.getString("category").equals("robotics")){
                                    event.setTitle(obj.getString("title"));
                                    event.setVenue(obj.getString("venue"));
                                    event.setStart(obj.getInt("start"));
                                    event.setDate(obj.getInt("day"));
                                    event.setImage(obj.getString("image"));
                                    if(mydb.isEventRemembered(obj.getString("title"))){
                                        event.setRemember();
                                    }
                                    // adding movie to movies array
                                    eventList.add(event);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        Collections.sort(eventList,Event.titleComparator);
                        Collections.sort(eventList,Event.startComparator);
                        Collections.sort(eventList, Event.dateComparator);
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();

                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //hidePDialog();
                progressBar.setVisibility(View.GONE);

            }
        });

        // Adding request to request queue
        AppController.getmInstance().addToRequestQueue(movieReq);
        return v;
    }

    public interface ClickListener{
        void onClick(View view,int position);
        void onLongClick(View view,int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector gestureDetector;
        private RoboticsTab.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final RoboticsTab.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
