package com.multipz.clock24.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.multipz.clock24.Fragment.FeedbackFragment;
import com.multipz.clock24.Fragment.HELPSFragment;

/**
 * Created by Admin on 21-07-2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                HELPSFragment tab1 = new HELPSFragment();
                return tab1;
            case 1:
                FeedbackFragment tab2 = new FeedbackFragment();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}