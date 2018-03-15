package com.multipz.clock24.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.clock24.Fragment.NewsFragment;
import com.multipz.clock24.Fragment.HelpFeedbackFragment;
import com.multipz.clock24.Menu.DrawerAdapter;
import com.multipz.clock24.Menu.DrawerItem;
import com.multipz.clock24.Menu.SimpleItem;
import com.multipz.clock24.Menu.SpaceItem;
import com.multipz.clock24.Model.NewsItem;

import com.multipz.clock24.R;
import com.multipz.clock24.Fragment.SettingFragment;
import com.multipz.clock24.utils.Constant;
import com.multipz.clock24.utils.Constant_method;
import com.multipz.clock24.utils.Shared;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Admin on 17-07-2017.
 */

public class ActivityDrawer extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    Context context;
    ActivityDrawer activity;

    private String[] screenTitles;
    private Drawable[] screenIcons;
//    int News = 0, WEATHER = 1, HELP = 2, Setting = 3;

    int News = 0, Feed = 1, WEATHER = 2, HELP = 3;

    public static ActivityDrawer drawer;
    Shared shared;
    private JSONArray jsonArray = new JSONArray();
    public static boolean isApplicationLive = false;
    SlidingRootNav sn;
    DrawerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        drawer = this;
        shared = new Shared(context);

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

            sn = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withDragDistance(150)
                .withRootViewScale(0.8f)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();


//        adapter = new DrawerAdapter(Arrays.asList(
//                createItemFor(News).setChecked(true),
//                createItemFor(WEATHER),
//                createItemFor(HELP),
//                createItemFor(Setting),
//                new SpaceItem(48)));


        adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(News).setChecked(true),
                createItemFor(Feed),
                createItemFor(WEATHER),
                createItemFor(HELP),
                new SpaceItem(48)));


        adapter.setListener(this);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(News);

        if (ContextCompat.checkSelfPermission(ActivityDrawer.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ActivityDrawer.this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(ActivityDrawer.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.white))
                .withTextTint(color(R.color.white))
                .withSelectedIconTint(color(R.color.colorMenuSelected))
                .withSelectedTextTint(color(R.color.colorMenuSelected));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onItemSelected(int position) {

        if (position == News) {
            showFragment(new NewsFragment());
            sn.closeMenu();
        } else if (position == Feed) {
            showFragment(new SettingFragment());
            sn.closeMenu();

        } else if (position == WEATHER) {

            Intent intent = new Intent(ActivityDrawer.this, WeatherActivity.class);
            if (Constant_method.checkConn(context)) {
                startActivity(intent);
                sn.closeMenu();
            } else {
                Toast.makeText(context, "No InterNet Connection", Toast.LENGTH_SHORT).show();
//                Snackbar.make(get,"InterNet Connection Not Detected..", Snackbar.LENGTH_LONG).show();
            }

        } else if (position == HELP) {
            showFragment(new HelpFeedbackFragment());
            sn.closeMenu();
        } else {
           /* Fragment selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
            showFragment(selectedScreen);*/
            sn.closeMenu();
        }
    }

    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
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


    @Override
    protected void onResume() {
        app_launched(this);
        Log.e("jkjk", "jk");
        super.onResume();
    }


    //Rate Dialog//
    private final static int DAYS_UNTIL_PROMPT = 3;//Min number of days
    private final static int LAUNCHES_UNTIL_PROMPT = 3;//Min number of launches

    public void app_launched(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) {
            return;
        }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {


            if (System.currentTimeMillis() >= date_firstLaunch +
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                if (Constant_method.load) {
                    Constant_method.load = false;
                    showRateDialog(mContext, editor);
                }
            }
        }
        editor.commit();
    }


    public void showRateDialog(final Context mcontext, final SharedPreferences.Editor editor) {
        final Dialog dialog = new Dialog(mcontext);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setContentView(R.layout.dialogue);
        TextView nothanks = (TextView) dialog.findViewById(R.id.nothanks);
        TextView later = (TextView) dialog.findViewById(R.id.later);
        TextView rate = (TextView) dialog.findViewById(R.id.ratenow);

        nothanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });

        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                   dialog.hide();
                dialog.dismiss();
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                Intent rate = new Intent(Intent.ACTION_VIEW, uri);

                rate.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(rate);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
                }
            }
        });
        dialog.show();
    }

    public void showExitDialog() {
        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setContentView(R.layout.exit_dialogue);
        TextView exit = (TextView) dialog.findViewById(R.id.exit);
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);

        exit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                   dialog.hide();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    @Override
    protected void onStart() {
        // app_launched(this);
        super.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
