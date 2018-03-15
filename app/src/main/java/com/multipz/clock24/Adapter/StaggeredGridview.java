package com.multipz.clock24.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.multipz.clock24.Model.NewsItem;
import com.multipz.clock24.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Admin on 12-07-2017.
 */

public class StaggeredGridview extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    Typeface custom_font;
    ArrayList<NewsItem> data;

    public StaggeredGridview(ArrayList<NewsItem> data, Context context) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View v = view;
        Holder holder;

        if (view == null) {

            holder = new StaggeredGridview.Holder();
            v = inflater.inflate(R.layout.view_hz_all, null);

            /*holder.newsname=(TextView)v.findViewById(R.id.newsname);*/
            holder.imageView = (ImageView) v.findViewById(R.id.imageView);
            holder.newsdes = (TextView) v.findViewById(R.id.newsdes);

            custom_font = Typeface.createFromAsset(context.getAssets(),  "Leelawadee_font/LeelawUI.ttf");
            holder.newsdes.setTypeface(custom_font);
            holder.channelname = (TextView) v.findViewById(R.id.channelname);
            /*holder.newscategory=(TextView)v.findViewById(R.id.newscategory);*/
            custom_font = Typeface.createFromAsset(context.getAssets(),  "Leelawadee_font/LeelawUI.ttf");
            holder.channelname.setTypeface(custom_font);


            try {
                // get input stream
                InputStream ims = context.getAssets().open(data.get(position).getId() + ".png");
                // load image as Drawable
                Drawable d = Drawable.createFromStream(ims, null);
                // set image to ImageView
                holder.imageView.setImageDrawable(d);

       /*         Picasso.with(context).load(data.get(position).getId());*/
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            v.setTag(holder);
        }

        holder = (Holder) v.getTag();

        holder.newsdes.setText(data.get(position).getDescription());
        holder.channelname.setText(data.get(position).getName());

        try {
            // get input stream
            InputStream ims = context.getAssets().open(data.get(position).getId() + ".png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            holder.imageView.setImageDrawable(d);

       /*         Picasso.with(context).load(data.get(position).getId());*/
        } catch (IOException e) {
            e.printStackTrace();
        }
      /*  holder.newscategory.setText(data.get(position).getCategory());
        holder.newsname.setText(data.get(position).getName());*/
        return v;
    }

    public class Holder {
        TextView newsdes, newscategory, channelname;
        ImageView imageView;


    }
}