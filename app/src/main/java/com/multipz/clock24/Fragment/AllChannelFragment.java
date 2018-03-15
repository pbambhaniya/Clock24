package com.multipz.clock24.Fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;
import com.multipz.clock24.Activity.ActivityDrawer;
import com.multipz.clock24.Adapter.StaggeredGridview;
import com.multipz.clock24.Model.NewsItem;
import com.multipz.clock24.Activity.NewsListActivity;
import com.multipz.clock24.R;
import com.multipz.clock24.utils.Constant;
import com.multipz.clock24.utils.Constant_method;
import com.multipz.clock24.utils.MyAsyncTask;
import com.multipz.clock24.utils.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllChannelFragment extends Fragment implements MyAsyncTask.AsyncInterface {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    Typeface custom_font;
    Constant_method constant_method;
    Context context;
    TextView textView;

    public static AllChannelFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        AllChannelFragment fragment = new AllChannelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static final int SELECT_OPERATION = 1;
    StaggeredGridView gridView;
    ArrayList<NewsItem> dataObj;
    MyAsyncTask asyncTask;
    String url;

    Shared shared;
    private JSONArray jsonArray = new JSONArray();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_channel, container, false);

        context = getActivity();
        shared = new Shared(getActivity());

        gridView = (StaggeredGridView) view.findViewById(R.id.gridview);
        dataObj = new ArrayList<>();

        textView = (TextView) view.findViewById(R.id.textnodata);
//        custom_font = Typeface.createFromAsset(getActivity().getAssets(), "Leelawadee_font/LeelawUI.ttf");
//        textView.setTypeface(custom_font);

        if (constant_method.checkConn(context) & !ActivityDrawer.isApplicationLive) {

            url = Constant.Top_URL;
            asyncTask = new MyAsyncTask(url, getActivity(), this, SELECT_OPERATION);
            asyncTask.execute();
        } else {
            dataObj = new ArrayList<>();
            try {
                jsonArray = new JSONArray(shared.getString(Constant.Result, "[{}]"));

                for (int i = 0; i < jsonArray.length(); i++) {
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
                    dataObj.add(user);
                }

                StaggeredGridview adapter = new StaggeredGridview(dataObj, getActivity());
                gridView.setAdapter(adapter);

            } catch (JSONException e) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), context.getString(R.string.msg_connection_not_available), Snackbar.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), NewsListActivity.class);
                intent.putExtra("category", dataObj.get(i).getCategory());
                intent.putExtra("name", dataObj.get(i).getName());
                intent.putExtra("sources", dataObj.get(i).getId());
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);

                if (constant_method.checkConn(context)) {
                    startActivity(intent);
                } else {

                }
                Snackbar.make(gridView, "InterNet Connection Not Detected..", Snackbar.LENGTH_LONG).show();


            }
        });
        return view;

    }


    @Override
    public void onResponseService(String response, int flag) {

        if (flag == SELECT_OPERATION) {

            try {
                JSONObject jsonObject = new JSONObject(response);
                String json = jsonObject.getString("status");

                if (json.contentEquals("ok")) {

                    gridView.setVisibility(View.VISIBLE);

                    JSONArray jsonArray = jsonObject.getJSONArray("sources");

                    shared.putString(Constant.Result, jsonArray.toString());
                    jsonArray = new JSONArray(shared.getString(Constant.Result, "[{}]"));

                    for (int i = 0; i < jsonArray.length(); i++) {
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
                        dataObj.add(user);
                    }
                    if (dataObj.size()>0){

                    ActivityDrawer.isApplicationLive = true;

                    StaggeredGridview adapter = new StaggeredGridview(dataObj, getActivity());
                    gridView.setAdapter(adapter);}
                    else {
                        gridView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    }
                } else {
                    gridView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
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
