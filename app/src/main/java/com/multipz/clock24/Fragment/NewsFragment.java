package com.multipz.clock24.Fragment;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.multipz.clock24.Activity.ActivityDrawer;
import com.multipz.clock24.Adapter.TabAdapter;
import com.multipz.clock24.Model.NewsItem;
import com.multipz.clock24.R;
import com.multipz.clock24.utils.Constant;
import com.multipz.clock24.utils.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Admin on 19-07-2017.
 */

public class NewsFragment extends android.app.Fragment {
    Context context;
    ArrayList<Fragment> fragment;
    ArrayList<String> tabTitles;
    private static final int Weather = 0;
    private static final int Help_Feedback = 1;

    private String[] screenTitles;
    private Drawable[] screenIcons;


    Shared shared;
    private JSONArray jsonArray = new JSONArray();

    //public static MainActivity activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        setHasOptionsMenu(true);

        shared = new Shared(getActivity());

        tabTitles = new ArrayList<>();
        fragment = new ArrayList<>();


        tabTitles.add("All Channel");
        fragment.add(new AllChannelFragment());

        if (shared.getBoolean(Constant.IS_Business_oN, true)) {
            tabTitles.add("Business");
            fragment.add(new BusinessFragment());
        }
        if (shared.getBoolean(Constant.Is_Sport_oN, true)) {
            tabTitles.add("Sport");
            fragment.add(new SportFragment());
        }
        if (shared.getBoolean(Constant.Is_Entertainment_oN, true)) {
            tabTitles.add("Entertainment");
            fragment.add(new EnterinmentFragment());
        }
        if (shared.getBoolean(Constant.Is_Gaming_oN, true)) {
            tabTitles.add("Gaming");
            fragment.add(new GamingFragment());
        }
        if (shared.getBoolean(Constant.Is_Technology_oN, true)) {
            tabTitles.add("Technology");
            fragment.add(new TechnologyFragment());
        }
        if (shared.getBoolean(Constant.Is_Science_oN, true)) {
            tabTitles.add("Science");
            fragment.add(new ScienceFragment());
        }
        if (shared.getBoolean(Constant.Is_General_oN, true)) {
            tabTitles.add("General");
            fragment.add(new GeneralFragment());
        }
        if (shared.getBoolean(Constant.Is_Music_oN, true)) {
            tabTitles.add("Music");
            fragment.add(new MusicFragment());
        }
        if (shared.getBoolean(Constant.Is_Politics_oN, true)) {
            tabTitles.add("Politics");
            fragment.add(new PoliticsFragment());
        }

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabAdapter(ActivityDrawer.drawer.getSupportFragmentManager(), getActivity(), shared, tabTitles, fragment));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }


    public void category(ArrayList<NewsItem> data, String category) {
        ArrayList<NewsItem> name;
        try {
            int i;
            jsonArray = new JSONArray(shared.getString(Constant.Result, "[{}]"));//aa json array sharedprefrence ma save thayo se


            for (i = 0; i < jsonArray.length(); i++) {

                JSONObject object = jsonArray.getJSONObject(i);//json object category wise data avi gaya
                object.getString("category");

                if (object.getString("category").contentEquals(category) || "All".contentEquals(category)) {


                    if (object.getString("country").contentEquals(shared.getString(Constant.country, "")) || "".contentEquals(shared.getString(Constant.country, ""))) {

                        if (object.getString("language").contentEquals(shared.getString(Constant.Language, "")) || "".contentEquals(shared.getString(Constant.Language, ""))) {


                            JSONObject userJson = jsonArray.getJSONObject(i);

                            NewsItem user = new NewsItem();
                            user.setId(userJson.getString("id"));
                            user.setName(userJson.getString("name"));
                            user.setDescription(userJson.getString("description"));
                            user.setUrl(userJson.getString("url"));
                            user.setCategory(userJson.getString("category"));
                            user.setLanguage(userJson.getString("language"));
                            user.setCountry(userJson.getString("country"));
                            user.setUrlsToLogos(userJson.getString("urlsToLogos"));
                            user.setSortBysAvailable(userJson.getString("sortBysAvailable"));
                            data.add(user);

                        }
                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(getActivity(), res);
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.filter, menu);
//        return ;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.filter:
//                android.app.Fragment fragment=new SettingFragment();
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.container, fragment)
//                        .commit();
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

}

