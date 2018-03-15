package com.multipz.multipzapps;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Admin on 24-07-2017.
 */

public class MyAppAdapter extends BaseAdapter {

    Context context;
    ArrayList<MyAppModel> list;
    LayoutInflater inflater;

    public MyAppAdapter(Context context, ArrayList<MyAppModel> list) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Holder holder;

        if (convertView == null){
            holder = new Holder();
            rowView = inflater.inflate(R.layout.layout_app, parent, false);

            holder.txtAppName = (TextView) rowView.findViewById(R.id.txt_title);
            holder.txtAppDesc = (TextView) rowView.findViewById(R.id.txt_desc);
            holder.txtGet = (TextView) rowView.findViewById(R.id.txt_get);
            holder.imgAppLogo = (ImageView) rowView.findViewById(R.id.img_lg);
            rowView.setTag(holder);
        }

        holder = (Holder) rowView.getTag();


        final MyAppModel myApp = list.get(position);

        holder.txtAppName.setText(myApp.getAppname());
        holder.txtAppDesc.setText(myApp.getDescri());

        Picasso.with(context).load(Constant.ImagePath + myApp.getImgname()).into(holder.imgAppLogo);

        holder.txtGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("market://details?id=" + list.get(position).getApp_id());
                Intent rate = new Intent(Intent.ACTION_VIEW, uri);

                rate.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                try {
                    context.startActivity(rate);
                } catch (ActivityNotFoundException e) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + myApp.getApp_id())));
                }

            }
        });

        return rowView;
    }

    class Holder{
        TextView txtAppName, txtAppDesc, txtGet;
        ImageView imgAppLogo;
    }
}
