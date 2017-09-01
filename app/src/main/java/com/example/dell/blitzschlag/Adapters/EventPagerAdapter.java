package com.example.dell.blitzschlag.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dell.blitzschlag.Events.CulturalTab;
import com.example.dell.blitzschlag.Events.FineartsTab;
import com.example.dell.blitzschlag.Events.RoboticsTab;
import com.example.dell.blitzschlag.Events.TechnicalTab;

public class EventPagerAdapter extends FragmentStatePagerAdapter{
    int mNumOfTabs;
    public EventPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new TechnicalTab();
            case 1:
                return new CulturalTab();
            case 2:
                return new RoboticsTab();
            case 3:
                return new FineartsTab();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
