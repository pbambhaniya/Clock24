package com.multipz.clock24.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.multipz.clock24.Adapter.NewsAdapter;
import com.multipz.clock24.Model.NewsModel;
import com.multipz.clock24.R;
import com.multipz.clock24.utils.Constant;
import com.multipz.clock24.utils.Constant_method;
import com.multipz.clock24.utils.MyAsyncTask;
import com.multipz.clock24.utils.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class NewsListActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    private static final int SELECT_OPERATION = 2;
    Context context;
    ListView newslistviews;
    MyAsyncTask asyncTask;
    String url;
    Shared shared;
    ArrayList<NewsModel> userList;
    private AdView mAdView;
    Dialog dialog;
    NewsListActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //dialog = new Dialog(this);
        shared = new Shared(this);

        context = this;
//        Loader(context);

        setTitle(getIntent().getStringExtra("name"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        newslistviews = (ListView) findViewById(R.id.newslistviews);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getIntent().getStringExtra("category");
        getIntent().getStringExtra("sources");
        if (Constant_method.checkConn(context)) {
            url = Constant.BASE_URL + "category=" + getIntent().getStringExtra("category") + "&source=" + getIntent().getStringExtra("sources");/* + "&country=" + shared.getString(Constant.country, "") + "&language=" + shared.getString(Constant.Language, "");*/
            Log.e("helll", url);

            asyncTask = new MyAsyncTask(url, NewsListActivity.this, SELECT_OPERATION);
            asyncTask.execute();
        } else {

            Toast.makeText(context, "No InterNet Connection", Toast.LENGTH_SHORT).show();
        }


        newslistviews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NewsListActivity.this, NewsDetailActivity.class);
                intent.putExtra("url", userList.get(i).getUrl());
//                intent.putExtra("category", getIntent().getStringExtra("category"));
                intent.putExtra("description", userList.get(i).getDescription());

                overridePendingTransition(R.anim.right_in, R.anim.left_out);

                if (Constant_method.checkConn(context)) {
                    startActivity(intent);
                } else {
                    Snackbar.make(newslistviews, "InterNet Connection Not Detected..", Snackbar.LENGTH_LONG).show();
//                                        Toast.makeText(context, "No InterNet Connection", Toast.LENGTH_SHORT).show();
                }


            }
        });
        //dialog.show();
    }

    @Override
    public void onResponseService(String response, int flag) {


        if (flag == SELECT_OPERATION) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String json = jsonObject.getString("status");
                JSONArray jsonArray = jsonObject.getJSONArray("articles");

                userList = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++)

                {
                    JSONObject userJson = jsonArray.getJSONObject(i);
                    NewsModel user = new NewsModel();
                    user.setAuthor(userJson.getString("author"));
                    user.setTitle(userJson.getString("title"));
                    user.setDescription(userJson.getString("description"));
                    user.setUrl(userJson.getString("url"));
                    user.setUrlToImage(userJson.getString("urlToImage"));
                    user.setPublishedAt(userJson.getString("publishedAt"));
                    userList.add(user);
                }

                NewsAdapter adapter = new NewsAdapter(this, userList);
                newslistviews.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        dialog.dismiss();


    }

    @Override
    public void onBackPressed() {
        super.finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }

    public boolean onSupportNavigateUp() {

        onBackPressed();

        // or call onBackPressed()
        return true;
    }

    @Override
    public void onPause() {

        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    public void Loader(final Context context) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.loader);

        GifImageView gifImageView = (GifImageView) dialog.findViewById(R.id.imgloader);

        try {
            GifDrawable gifFromResource = new GifDrawable(getResources(), R.drawable.loader);
            //gifFromResource.reset();
            gifFromResource.setLoopCount(50);
            gifImageView.setImageDrawable(gifFromResource);
            gifImageView.setFreezesAnimation(true);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
