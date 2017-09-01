package com.example.dell.blitzschlag.Individual;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.widget.NestedScrollView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dell.blitzschlag.R;
import com.example.dell.blitzschlag.Registration.Registration;
import com.example.dell.blitzschlag.Wishlist.DBHelper;
import com.example.dell.blitzschlag.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Individual2 extends AppCompatActivity {
    String eventTitle;
    private static final String TAG = Individual.class.getSimpleName();
    private static final String url = "https://jsonblob.com/api/jsonBlob/574c1224e4b01190df703656";
    public String callno,registration_url,venueValue,StartValue,dateValue;
    public ProgressBar progressBar;
    public TextView venue,time,date,details,rules,organiser,fees,duration;
    public LinearLayout linearLayout;
    public NestedScrollView nestedScrollView;
    public DBHelper mydb;

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2,fab3;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    public ImageView imageView;
    String imageFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual2);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventTitle = getIntent().getExtras().getString("title");
        setTitle(eventTitle);
        imageFileName=getIntent().getExtras().getString("image");
        collapsingToolbarLayout.setTitle(eventTitle);

        imageView=(ImageView)findViewById(R.id.image_collapse);
        if (imageFileName != null) {
            Glide.with(getApplicationContext()).load(imageFileName)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);

        } else {
            imageView.setVisibility(View.GONE);
        }

        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        linearLayout=(LinearLayout)findViewById(R.id.linear_layout);
        nestedScrollView=(NestedScrollView)findViewById(R.id.nested_scroll_view);
        venue=(TextView)findViewById(R.id.venue_value);
        time=(TextView)findViewById(R.id.time_value);
        date=(TextView)findViewById(R.id.date);
        details=(TextView)findViewById(R.id.details_value);
        rules=(TextView)findViewById(R.id.rules_value);
        organiser=(TextView)findViewById(R.id.organiser_value);
        fees=(TextView)findViewById(R.id.fees_value);
        duration=(TextView)findViewById(R.id.duration_value);

        mydb=new DBHelper((getApplicationContext()));

        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);

        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                progressBar.setVisibility(View.GONE);
                                linearLayout.setVisibility(View.VISIBLE);
                                JSONObject obj = response.getJSONObject(i);
                                if(obj.getString("title").equals(eventTitle)){
                                    String text;
                                    if(obj.getInt("start")/100<=12){
                                        StartValue=String.format("%02d",obj.getInt("start")/100)+":"+obj.getInt("start")%100+" AM";
                                        //StartValue=obj.getInt("start")/100+":"+obj.getInt("start")%100+" AM";
                                    }
                                    else{
                                        StartValue=String.format("%02d",(obj.getInt("start")/100)-12)+":"+obj.getInt("start")%100+" PM";
                                        //StartValue=((obj.getInt("start")/100)-12)+":"+obj.getInt("start")%100+" PM";
                                    }
                                    time.setText(StartValue);
                                    venueValue=obj.getString("venue");
                                    venue.setText(venueValue);
                                    details.setText(obj.getString("details"));
                                    rules.setText(obj.getString("rules"));
                                    if((obj.getInt("day")==1)){
                                        dateValue="April 26";
                                    }
                                    else if((obj.getInt("day")==2)){
                                        dateValue= "April 27";
                                    }
                                    else if((obj.getInt("day")==3)){
                                        dateValue= "April 28";
                                    }
                                    date.setText(dateValue);
                                    organiser.setText(obj.getString("organiser"));
                                    fees.setText(obj.getString("fees"));
                                    duration.setText(obj.getString("duration"));
                                    callno=obj.getString("contact");
                                    registration_url=obj.getString("registration");
                                    break;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();

                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        AppController.getmInstance().addToRequestQueue(movieReq);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab3 = (FloatingActionButton)findViewById(R.id.fab3);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                animateFAB();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                //startActivity(new Intent(Individual.this, WishlistActivity.class));
                if(!mydb.isEventRemembered(eventTitle)){
                    mydb.insertEvent(eventTitle,venueValue,StartValue,dateValue,"Workshop");
                    Toast.makeText(getApplicationContext(),eventTitle + " added to Wishlist", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),eventTitle + " already in Wishlist", Toast.LENGTH_SHORT).show();
                }
                fab.startAnimation(rotate_backward);
                fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                fab3.startAnimation(fab_close);
                fab1.setClickable(false);
                fab2.setClickable(false);
                fab3.setClickable(false);
                isFabOpen = false;
                linearLayout.setAlpha(1.0F);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent in=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + callno));
                try{
                    startActivity(in);
                }

                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(getApplicationContext(),"Cannot make the call!",Toast.LENGTH_SHORT).show();
                }
                fab.startAnimation(rotate_backward);
                fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                fab3.startAnimation(fab_close);
                fab1.setClickable(false);
                fab2.setClickable(false);
                fab3.setClickable(false);
                isFabOpen = false;
                linearLayout.setAlpha(1.0F);
                //startActivity(new Intent(Individual.this, ContactActivity.class));
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(Individual2.this, Registration.class);
                intent.putExtra("registration", registration_url);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                fab.startAnimation(rotate_backward);
                fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                fab3.startAnimation(fab_close);
                fab1.setClickable(false);
                fab2.setClickable(false);
                fab3.setClickable(false);
                isFabOpen = false;
                linearLayout.setAlpha(1.0F);
            }
        });
    }

    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;
            linearLayout.setAlpha(1.0F);

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;
            linearLayout.setAlpha(0.4F);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        return super.onSupportNavigateUp();
    }


}
