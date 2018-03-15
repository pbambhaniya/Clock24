package com.multipz.clock24.Activity;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.multipz.clock24.Model.NewsItem;
import com.multipz.clock24.R;
import com.multipz.clock24.utils.Constant;
import com.multipz.clock24.utils.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Context context;
    private static final int Weather = 0;
    private static final int Help_Feedback = 1;

    ArrayList<Fragment> fragment;
    ArrayList<String> tabTitles;
    private String[] screenTitles;
    private Drawable[] screenIcons;


    Shared shared;
    private JSONArray jsonArray = new JSONArray();
    public static MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shared = new Shared(this);
        activity = this;





     /*   ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), MainActivity.this,shared,tabTitles,fragment));*/

        // Give the TabLayout the ViewPager
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
//        tabLayout.setupWithViewPager(viewPager);

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


}

