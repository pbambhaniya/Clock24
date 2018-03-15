package com.multipz.multipzapps;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity{

    ArrayList<MyAppModel> dataModels;
    ListView listView;
    private static MyAppAdapter adapter;
    Context context;
    ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_about);

        context=this;

        listView=(ListView)findViewById(R.id.lst_app);
        back=(ImageView)findViewById(R.id.nav_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dataModels= new ArrayList<>();
        dataModels.add(new MyAppModel("GST","This application enables user to get GST information.","com.multilingual_demo",R.drawable.gst));
        dataModels.add(new MyAppModel("Video Downloder","Download videos in parallel parts to increase and accelerate the download speed","com.multipz.fbvideodownloader",R.drawable.video));
        dataModels.add(new MyAppModel("Zoo Voice","Best nature app with birds sounds! Help you relax after hard day!","com.multipz.zoovoice",R.drawable.zoo));

        adapter= new MyAppAdapter(context,dataModels);
        listView.setAdapter(adapter);*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
