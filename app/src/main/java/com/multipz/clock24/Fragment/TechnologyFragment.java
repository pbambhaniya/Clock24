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
import com.multipz.clock24.Activity.NewsListActivity;
import com.multipz.clock24.Adapter.StaggeredGridview;
import com.multipz.clock24.Model.NewsItem;
import com.multipz.clock24.R;
import com.multipz.clock24.utils.Constant_method;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TechnologyFragment extends Fragment {

    Typeface custom_font;
    Context context;

    TextView textView;


    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    ArrayList<NewsItem> datatechnology;
    StaggeredGridView gridViewtechnology;

    public static TechnologyFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TechnologyFragment fragment = new TechnologyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mPage = getArguments().getInt(ARG_PAGE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_technology, container, false);


        gridViewtechnology = (StaggeredGridView) view.findViewById(R.id.gridvietechnology);


        datatechnology = new ArrayList<>();
        ActivityDrawer.drawer.category(datatechnology, "technology");

        StaggeredGridview adapter = new StaggeredGridview(datatechnology, getActivity());
        gridViewtechnology.setAdapter(adapter);


        textView = (TextView) view.findViewById(R.id.textnodata);
//        custom_font = Typeface.createFromAsset(context.getAssets(), "Leelawadee_font/LeelawUI.ttf");
//        textView.setTypeface(custom_font);


        gridViewtechnology.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), NewsListActivity.class);
                intent.putExtra("category", "technology");
                intent.putExtra("name", datatechnology.get(i).getName());
                intent.putExtra("sources", datatechnology.get(i).getId());


                if (Constant_method.checkConn(getContext())) {
                    startActivity(intent);
                } else {
                    Snackbar.make(gridViewtechnology, "InterNet Connection Not Detected..", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

}
