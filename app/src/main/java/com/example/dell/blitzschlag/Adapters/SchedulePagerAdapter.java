package com.example.dell.blitzschlag.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dell.blitzschlag.Schedule.Day1;
import com.example.dell.blitzschlag.Schedule.Day2;
import com.example.dell.blitzschlag.Schedule.Day3;

public class SchedulePagerAdapter extends FragmentStatePagerAdapter{
    int mNumOfTabs;

    public SchedulePagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new Day1();
            case 1:
                return new Day2();
            case 2:
                return new Day3();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
