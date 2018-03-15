package com.multipz.clock24.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.multipz.clock24.Model.NewsModel;
import com.multipz.clock24.R;

import java.io.IOException;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class NewsDetailActivity extends AppCompatActivity {
    WebView webView;
    ArrayList<NewsModel> list;
    Context context;
    String url;
    private AdView mAdView;
    NewsModel newsModel;
    Dialog dialog;
    ProgressDialog pd;

    SharedPreferences.Editor editor;

    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        setTitle(getIntent().getStringExtra("description"));
        dialog = new Dialog(this);

        context = this;
//       Loader(context);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (WebView) findViewById(R.id.webview);


        pd = new ProgressDialog(NewsDetailActivity.this);

        pd.setCancelable(false);
        pd.setMessage("Loading...");
        pd.show();

        //dialog.show();

        webView.setWebViewClient(new MyBrowser());

        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);


    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pd.dismiss();

//            dialog.dismiss();
        }
    }


    @Override
    public void onBackPressed() {
        super.finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        // or call onBackPressed()
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                showfullscreenad();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = getIntent().getStringExtra("url") + "\n" + "\n" + "via-" + getResources().getString(R.string.app_name);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

        }
        return super.onOptionsItemSelected(item);
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


    public void showfullscreenad() {
        // Intertial Advertise
        mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        AdRequest adRequest = new AdRequest.Builder()
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }


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
