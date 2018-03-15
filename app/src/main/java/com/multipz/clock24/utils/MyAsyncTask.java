package com.multipz.clock24.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Admin on 27-02-2017.
 */

public class MyAsyncTask extends AsyncTask<String, Void, String> {

    private String url;
    ProgressDialog pd;
    private Context context;
    private AsyncInterface asyncReference;
    private int flag;

    public interface AsyncInterface {
        void onResponseService(String response, int flag);
    }

    public MyAsyncTask(String url, Activity activity, int flag) {
        this.url = url;
        context = (Context) activity;
        asyncReference = (AsyncInterface) activity;
        this.flag = flag;
    }

    public MyAsyncTask(String url, Activity activity, AsyncInterface asyncInterface, int flag) {
        this.url = url;
        context = (Context) activity;
        asyncReference = asyncInterface;
        this.flag = flag;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = ProgressDialog.show(context, "Wait", "Loading");
    }

    @Override
    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        asyncReference.onResponseService(s, flag);
    }
}
