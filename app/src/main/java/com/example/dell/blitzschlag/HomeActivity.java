package com.example.dell.blitzschlag;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dell.blitzschlag.About.AboutActivity;
import com.example.dell.blitzschlag.Accomodations.AccomodationsActivity;
import com.example.dell.blitzschlag.Classes.Config;
import com.example.dell.blitzschlag.Classes.FeedImageView;
import com.example.dell.blitzschlag.Contact.ContactActivity;
import com.example.dell.blitzschlag.Events.EventsActivity;
import com.example.dell.blitzschlag.Gallery.GalleryActivity;
import com.example.dell.blitzschlag.Lectures.LecturesActivity;
import com.example.dell.blitzschlag.Schedule.ScheduleActivity;
import com.example.dell.blitzschlag.Shows.ShowsActivity;
import com.example.dell.blitzschlag.Sponsors.SponsorsActivity;
import com.example.dell.blitzschlag.Teaser.YoutubeFragment;
import com.example.dell.blitzschlag.Venue.VenueActivity;
import com.example.dell.blitzschlag.Wishlist.WishlistActivity;
import com.example.dell.blitzschlag.Workshops.WorkshopsActivity;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dell.blitzschlag.app.AppController;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final String url = "https://jsonblob.com/api/jsonBlob/5753b219e4b01190df731db0";

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    // YouTube player view
    private YouTubePlayerView youTubeView;
    private DrawerLayout mDrawerLayout;
    private Toolbar mtoolbar;
    private NavigationView nvView;
    private ActionBarDrawerToggle drawerToggle;
    static final int NUM_ITEMS = 6;
    ImageFragmentPagerAdapter imageFragmentPagerAdapter;
    ViewPager viewPager;
    //public static final String[] IMAGE_NAME = {"spotlightimage", "spotlightimage", "spotlightimage", "spotlightimage", "spotlightimage", "spotlightimage"};
    public static List<String> IMAGE_NAME = new ArrayList<>();

    YoutubeFragment youtubeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mtoolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        //setTitle("Blitzschlag");
        //setupWindowAnimations();
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        nvView=(NavigationView)findViewById(R.id.nvView);
        nvView.setItemIconTintList(null);
        setupDrawerContent(nvView);

        drawerToggle=setupDrawerToggle();
        mDrawerLayout.addDrawerListener(drawerToggle);

        //FragmentManager manager = getSupportFragmentManager();
        //FragmentTransaction transaction = manager.beginTransaction();
        //transaction.replace(R.id.youtube_fragment, new YoutubeFragment()).commit();

        youtubeFragment= YoutubeFragment.newInstance(Config.YOUTUBE_VIDEO_CODE);
        getSupportFragmentManager().beginTransaction().replace(R.id.teaser_fl,youtubeFragment).commit();


        imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.spotlight_pager);
        viewPager.setAdapter(imageFragmentPagerAdapter);

        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                String string = response.getString(i);
                                IMAGE_NAME.add(string);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        imageFragmentPagerAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();

                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        AppController.getmInstance().addToRequestQueue(movieReq);

        //youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        //youTubeView.initialize(Config.DEVELOPER_KEY, this);

    }

    public static class ImageFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public ImageFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            SwipeFragment fragment = new SwipeFragment();
            return fragment.newInstance(position);
        }
    }

    public static class SwipeFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ProgressBar progressBar;
            View swipeView = inflater.inflate(R.layout.spotlight_swipe_fragment, container, false);
            progressBar = (ProgressBar)swipeView.findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.VISIBLE);
            //FeedImageView imageView = (FeedImageView) swipeView.findViewById(R.id.spotlight_image);
            ImageView imageView=(ImageView) swipeView.findViewById(R.id.spotlight_image);
            //ImageLoader imageLoader = AppController.getmInstance().getImageLoader();
            Bundle bundle = getArguments();
            int position = bundle.getInt("position");
            if(!IMAGE_NAME.isEmpty()){
                String imageFileName = IMAGE_NAME.get(position);
                //int imgResId = getResources().getIdentifier(imageFileName, "drawable","com.example.dell.blitzschlag");
                //imageView.setImageResource(imgResId);
                if (imageFileName != null) {
                    Glide.with(getContext()).load(imageFileName)
                            .thumbnail(0.5f)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView);
                    /*imageView.setImageUrl(imageFileName, imageLoader);
                    imageView.setResponseObserver(new FeedImageView.ResponseObserver() {
                        @Override
                        public void onError() {
                        }

                        @Override
                        public void onSuccess() {
                        }
                    });*/

                } else {
                    imageView.setVisibility(View.GONE);
                }
            }
            return swipeView;
        }

        static SwipeFragment newInstance(int position) {
            SwipeFragment swipeFragment = new SwipeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            swipeFragment.setArguments(bundle);
            return swipeFragment;
        }
    }

    @TargetApi(21)
    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(2000);
        getWindow().setExitTransition(slide);
    }

    private void setupDrawerContent(final NavigationView nvView){
        nvView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                }
        );
    }

    public  void selectDrawerItem(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.events:
                startActivity(new Intent(HomeActivity.this, EventsActivity.class));
                break;
            case R.id.workshops:
                startActivity(new Intent(HomeActivity.this, WorkshopsActivity.class));
                break;
            case R.id.lectures:
                startActivity(new Intent(HomeActivity.this, LecturesActivity.class));
                break;
            case R.id.shows:
                startActivity(new Intent(HomeActivity.this, ShowsActivity.class));
                break;
            case R.id.venue:
                startActivity(new Intent(HomeActivity.this, VenueActivity.class));
                break;
            case R.id.schedule:
                startActivity(new Intent(HomeActivity.this, ScheduleActivity.class));
                break;
            case R.id.accomodations:
                startActivity(new Intent(HomeActivity.this, AccomodationsActivity.class));
                break;
            case R.id.wishlist:
                startActivity(new Intent(HomeActivity.this, WishlistActivity.class));
                break;
            case R.id.gallery:
                startActivity(new Intent(HomeActivity.this, GalleryActivity.class));
                break;
            case R.id.sponsors:
                startActivity(new Intent(HomeActivity.this, SponsorsActivity.class));
                break;
            case R.id.contactus:
                startActivity(new Intent(HomeActivity.this, ContactActivity.class));
                break;
            case R.id.about:
                startActivity(new Intent(HomeActivity.this, AboutActivity.class));
                break;
            default:
                break;
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        //menuItem.setChecked(true);
        //mDrawerLayout.closeDrawers();

    }

    private ActionBarDrawerToggle setupDrawerToggle(){
        return new ActionBarDrawerToggle(this,mDrawerLayout,mtoolbar,R.string.drawer_open,R.string.drawer_close);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.FAQ) {
            return true;
        }
        if(id == R.id.home){
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
