package com.multipz.multipzapps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Admin on 28-07-2017.
 */

public class ViewListview extends ListView{

    ArrayList<MyAppModel> listData;

    public ViewListview(Context context) {
        super(context);
    }

    public ViewListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        listData = new ArrayList<>();
        new MyAsyncTask(Constant.Api, context).execute();
    }

    public class MyAsyncTask extends AsyncTask<String, Void, String> {

        private String url;
        ProgressDialog pd;
        private Context context;

        public MyAsyncTask(String url, Context activity) {
            this.url = url;
            context = (Context) activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(context, "Wait", "Loading");
        }

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("app_id", context.getPackageName());
                jsonObject.put("device_type","A");
                jsonObject.put("action","getData");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("json", jsonObject.toString())
                    .build();
            Request request = new Request.Builder().url(url)
                    .method("POST", RequestBody.create(null, new byte[0]))
                    .post(requestBody)
                    .build();
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
            try {
                Log.e("Responce MyApp", s);
                JSONObject responce = new JSONObject(s);
                JSONArray data = responce.getJSONArray("data");
                JSONObject row;
                for (int i=0; i<data.length(); i++){
                    row = data.getJSONObject(i);
                    listData.add(new MyAppModel(row.getInt("tbl_app_info_id"), row.getString("appname"), row.getString("imgname"), row.getString("link"), row.getString("descri"), row.getString("app_id")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            MyAppAdapter adapter = new MyAppAdapter(context, listData);
            ViewListview.this.setAdapter(adapter);
        }
    }
}
