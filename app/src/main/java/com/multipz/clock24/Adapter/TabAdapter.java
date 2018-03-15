package com.multipz.clock24.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.multipz.clock24.utils.Shared;

import java.util.ArrayList;

/**
 * Created by Admin on 12-07-2017.
 */

public class TabAdapter extends FragmentStatePagerAdapter {
    /*private String tabTitles[] = new String[]{"All Channel", "Business", "Sports", "Entertainment", "Gaming", "Technology", "Science", "General", "Music", "Politics"};*/

    ArrayList<String> tabTitles;
    private Context context;
    Shared shared;
    ArrayList<Fragment> fragment;

    public TabAdapter(FragmentManager fm, Context context, Shared shared, ArrayList<String> tabTitles, ArrayList<Fragment> fragment) {
        super(fm);
        this.context = context;
        this.shared = shared;
        this.fragment = fragment;
        this.tabTitles = tabTitles;

//
//        tabTitles=new ArrayList<>();
//        fragment= new ArrayList<>();

//        if (shared.getBoolean(Constant.IS_Business_oN,true))
//        {
//            tabTitles.add("Business");
//            fragment.add(new BusinessFragment());
//        }
//        else if (shared.getBoolean(Constant.Is_Sport_oN,true))
//        {
//            tabTitles.add("Sport");
//            fragment.add(new SportFragment());
//        }
//        else if (shared.getBoolean(Constant.Is_Entertainment_oN,true))
//        {
//            tabTitles.add("Entertainment");
//            fragment.add(new EnterinmentFragment());
//        }
//        else if (shared.getBoolean(Constant.Is_Gaming_oN,true))
//        {
//            tabTitles.add("Gaming");
//            fragment.add(new GamingFragment());
//        }
//        else if (shared.getBoolean(Constant.Is_Technology_oN,true))
//        {
//            tabTitles.add("Technology");
//            fragment.add(new TechnologyFragment());
//        }
//        else if (shared.getBoolean(Constant.Is_Science_oN,true))
//        {
//            tabTitles.add("Science");
//            fragment.add(new ScienceFragment());
//        }
//        else if (shared.getBoolean(Constant.Is_General_oN,true))
//        {
//            tabTitles.add("General");
//            fragment.add(new MusicFragment());
//        }
//        else if (shared.getBoolean(Constant.Is_Politics_oN,true))
//        {
//            tabTitles.add("Politics");
//            fragment.add(new PoliticsFragment());
//        }
    }


    @Override
    public int getCount() {
        return tabTitles.size();
    }

    @Override
    public int getItemPosition(Object object) {
        // Causes adapter to reload all Fragments when
        // notifyDataSetChanged is called
        return POSITION_NONE;
    }


    @Override
    public Fragment getItem(int position) {

        return fragment.get(position);
//
//        if (position == 0) {
//            return AllChannelFragment.newInstance(position);
//        } else if (position == 1) {
//            return BusinessFragment.newInstance(position);
//        } else if (position == 2) {
//            return SportFragment.newInstance(position);
//        } else if (position == 3) {
//            return EnterinmentFragment.newInstance(position);
//        } else if (position == 4) {
//            return GamingFragment.newInstance(position);
//        } else if (position == 5) {
//            return TechnologyFragment.newInstance(position);
//        } else if (position == 6) {
//            return ScienceFragment.newInstance(position);
//        } else if (position == 7) {
//            return GeneralFragment.newInstance(position);
//        } else if (position == 8) {
//            return MusicFragment.newInstance(position);
//        } else if (position == 9) {
//            return PoliticsFragment.newInstance(position);
//        } else {
//            return null;
//        }


    }


    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position

        return tabTitles.get(position);
    }
}