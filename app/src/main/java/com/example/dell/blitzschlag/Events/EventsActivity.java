package com.example.dell.blitzschlag.Events;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dell.blitzschlag.Adapters.EventPagerAdapter;
import com.example.dell.blitzschlag.R;

public class EventsActivity extends AppCompatActivity {
    public boolean sortDate=false,sortStart=false,sortTitle=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setupWindowAnimations();

        TabLayout tabLayout= (TabLayout)findViewById(R.id.events_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Technical"));
        tabLayout.addTab(tabLayout.newTab().setText("Music Dance and Drama"));
        tabLayout.addTab(tabLayout.newTab().setText("Robotics"));
        tabLayout.addTab(tabLayout.newTab().setText("Miscellaneous"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.events_pager);
        final EventPagerAdapter adapter = new EventPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @TargetApi(21)
    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(3000);
        getWindow().setEnterTransition(fade);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}
