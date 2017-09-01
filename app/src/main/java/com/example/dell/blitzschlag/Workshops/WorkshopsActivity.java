package com.example.dell.blitzschlag.Workshops;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.dell.blitzschlag.Adapters.WorkshopAdapter;
import com.example.dell.blitzschlag.Classes.Workshop;
import com.example.dell.blitzschlag.Individual.Individual2;
import com.example.dell.blitzschlag.R;

import android.app.ProgressDialog;
import android.content.Context;

import android.util.Log;
import android.view.GestureDetector;

import android.view.MotionEvent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
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

public class WorkshopsActivity extends AppCompatActivity {
    private static final String TAG = WorkshopsActivity.class.getSimpleName();
    private static final String url = "https://jsonblob.com/api/jsonBlob/574c1224e4b01190df703656";

    public List<Workshop> workshopList = new ArrayList<>();
    public RecyclerView recyclerView;
    public WorkshopAdapter mAdapter;
    public ProgressBar progressBar;
    public DBHelper mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshops);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=(RecyclerView)findViewById(R.id.workshop_recycler_view);
        mAdapter= new WorkshopAdapter(workshopList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mydb=new DBHelper(getApplicationContext());

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                final Workshop movie = workshopList.get(position);
                //Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Individual2.class);
                intent.putExtra("title", movie.getTitle());
                intent.putExtra("image",movie.getImage());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //pDialog = new ProgressDialog(getContext());
        //pDialog.setMessage("Loading...");
        //pDialog.show();

        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
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
                                Workshop event = new Workshop();

                                    event.setTitle(obj.getString("title"));
                                    event.setVenue(obj.getString("venue"));
                                    event.setStart(obj.getInt("start"));
                                    event.setDate(obj.getInt("day"));
                                    event.setEnd(obj.getInt("end"));
                                    event.setImage(obj.getString("image"));
                                    event.setDuration(obj.getString("duration"));
                                    event.setFees(obj.getString("fees"));
                                if(mydb.isEventRemembered(obj.getString("title"))){
                                    event.setRemember();
                                }
                                    // adding movie to movies array
                                    workshopList.add(event);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        Collections.sort(workshopList, Workshop.titleComparator);
                        Collections.sort(workshopList,Workshop.startComparator);
                        Collections.sort(workshopList, Workshop.dateComparator);

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();

                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //hidePDialog();
                progressBar.setVisibility(View.GONE);

            }
        });

        // Adding request to request queue
        AppController.getmInstance().addToRequestQueue(movieReq);

    }

    public interface ClickListener{
        void onClick(View view,int position);
        void onLongClick(View view,int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector gestureDetector;
        private WorkshopsActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final WorkshopsActivity.ClickListener clickListener) {
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        return super.onSupportNavigateUp();
    }
}
