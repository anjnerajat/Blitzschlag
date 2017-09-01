package com.example.dell.blitzschlag.Schedule;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.dell.blitzschlag.Adapters.ScheduleAdapter;
import com.example.dell.blitzschlag.Classes.DividerItemDecoration;
import com.example.dell.blitzschlag.Classes.Schedule;
import com.example.dell.blitzschlag.Individual.Individual;
import com.example.dell.blitzschlag.R;
import com.example.dell.blitzschlag.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Day1 extends Fragment {
    private static final String TAG = ScheduleActivity.class.getSimpleName();
    private static String url = "https://jsonblob.com/api/jsonBlob/574abc47e4b01190df6fd287";
    private boolean url1=false,url2=false,url3=false;

    public List<Schedule> eventList = new ArrayList<>();
    public RecyclerView recyclerView;
    public ScheduleAdapter mAdapter;
    public ProgressBar progressBar;

    public Day1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_day1, container, false);
        // Inflate the layout for this fragment
        recyclerView=(RecyclerView)v.findViewById(R.id.day1_recycler_view);
        mAdapter= new ScheduleAdapter(eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                final Schedule movie = eventList.get(position);
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
                                Schedule event = new Schedule();

                                if(obj.getInt("day")==1){
                                    event.setTitle(obj.getString("title"));
                                    event.setVenue(obj.getString("venue"));
                                    event.setStart(obj.getInt("start"));
                                    event.setDate(obj.getInt("day"));
                                    event.setImage(obj.getString("image"));
                                    // adding movie to movies array
                                    eventList.add(event);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        Collections.sort(eventList, Schedule.titleComparator);
                        Collections.sort(eventList,Schedule.startComparator);
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

        AppController.getmInstance().addToRequestQueue(movieReq);

        return v;
    }

    public interface ClickListener{
        void onClick(View view,int position);
        void onLongClick(View view,int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector gestureDetector;
        private Day1.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Day1.ClickListener clickListener) {
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
    }

}
