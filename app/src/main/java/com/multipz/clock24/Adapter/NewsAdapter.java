package com.multipz.clock24.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.multipz.clock24.Activity.NewsDetailActivity;
import com.multipz.clock24.Model.NewsModel;
import com.multipz.clock24.R;
import com.multipz.clock24.utils.method;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Admin on 24-06-2017.
 */

public class NewsAdapter extends BaseAdapter {
    Context context;
    Typeface custom_font;

    ArrayList<NewsModel> list;
    LayoutInflater inflate;

    public NewsAdapter(Context context, ArrayList<NewsModel> list) {
        this.context = context;
        this.list = list;
        this.inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View v = view;


        Holder holder;
        if (v == null) {
            holder = new Holder();
            v = inflate.inflate(R.layout.news_list_item, null);

            holder.frmbig = (FrameLayout) v.findViewById(R.id.fmlbig);

            holder.txttime = (TextView) v.findViewById(R.id.txt_time);
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "Leelawadee_font/LeelawUI.ttf");
            holder.txttime.setTypeface(custom_font);

            holder.txt_title = (TextView) v.findViewById(R.id.txt_title);
            custom_font = Typeface.createFromAsset(context.getAssets(),  "Leelawadee_font/LeelawUI.ttf");
            holder.txt_title.setTypeface(custom_font);

            holder.imgbig = (ImageView) v.findViewById(R.id.imagebig);
            holder.rllist = (RelativeLayout) v.findViewById(R.id.rllist);
            /*holder.txt_des = (TextView) v.findViewById(R.id.txt_des);*/
            holder.txtfrm = (TextView) v.findViewById(R.id.frameText);
            custom_font = Typeface.createFromAsset(context.getAssets(),  "Leelawadee_font/LeelawUI.ttf");
            holder.txtfrm.setTypeface(custom_font);
            holder.img = (ImageView) v.findViewById(R.id.urlimage);
//
//holder.pb=(ProgressBar)v.findViewById(R.id.pb);
//
//            holder.progressBar=(ProgressBar)v.findViewById(R.id.progressbar);
          /*  holder.lnr_des = (LinearLayout) v.findViewById(R.id.lnr_des);*/
            /*holder.lnr_show = (LinearLayout) v.findViewById(R.id.lnr_show);*/
            /*holder.imgshare = (ImageView) v.findViewById(R.id.imgshare);*/


            /*holder.img_show = (ImageView) v.findViewById(R.id.img_show);*/
            /*holder.imgup = (ImageView) v.findViewById(R.id.imgup);*/


            v.setTag(holder);
        }


        holder = (Holder) v.getTag();


        if (position == 0) {
            Log.e("Error", position + "");
            holder.frmbig.setVisibility(View.VISIBLE);
            holder.txtfrm.setText(list.get(position).getTitle());
            holder.rllist.setVisibility(View.GONE);



            final Holder finalHolder = holder;
            Picasso.with(context).load(list.get(position).getUrlToImage()).placeholder(R.drawable.noimagebig).into(holder.imgbig);

            holder.imgbig.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, NewsDetailActivity.class);
                    intent.putExtra("url", list.get(position).getUrl());
                    intent.putExtra("category", ((Activity) context).getIntent().getStringExtra("category"));
                    intent.putExtra("description",list.get(position).getDescription());
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            });
        } else {
            Log.e("Error", position + "");
            holder.rllist.setVisibility(View.VISIBLE);
            holder.frmbig.setVisibility(View.GONE);
            holder.txt_title.setText(list.get(position).getTitle());

           /* try {
                // get input stream
                InputStream ims = context.getAssets().open(list.get(position).getId() + ".png");
                // load image as Drawable
                Drawable d = Drawable.createFromStream(ims, null);
                // set image to ImageView
                holder.img.setImageDrawable(d);
            } catch (Exception ex) {
                ex.printStackTrace();
            }*/
            try {
                Picasso.with(context).load(list.get(position).getUrlToImage()).placeholder(R.drawable.noimage).into(holder.img, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("Error","");
                    }

                    @Override
                    public void onError() {
                        Log.e("Error","");
                    }
                });
            }catch (Exception e)
            {
                e.printStackTrace();
            }


        }
            /*holder.txt_title.setText(list.get(position).getTitle());
        holder.txtfrm.setText(list.get(position).getTitle());*/
      /*  holder.txt_des.setText(list.get(position).getDescription());*/
        holder.txttime.setText(method.getTimeAgo(list.get(position).getPublishedAt()));

       /* final Holder finalHolder = holder;
        final Holder finalHolder1 = holder;*/
     /*   holder.img_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              *//*  Animation shake = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                finalHolder1.img_show.startAnimation(shake);*//*


                if (finalHolder.lnr_des.getVisibility() == View.GONE) {
                    finalHolder.lnr_des.setVisibility(View.VISIBLE);
                    finalHolder.img_show.setVisibility(View.GONE);

                    //animFadein = AnimationUtils.loadAnimation(context,R.anim.bounce_j);
                    // animFadein.start();
                    Animation slide_up = AnimationUtils.loadAnimation(context,
                            R.anim.slide_down);
                    finalHolder.lnr_des.startAnimation(slide_up);

                }

            }
        });*/


    /*    holder.lnr_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finalHolder.lnr_des.setVisibility(View.GONE);
                    }
                }, 500);


                finalHolder.img_show.setVisibility(View.VISIBLE);

                Animation slide_up = AnimationUtils.loadAnimation(context,
                        R.anim.slide_up);
                finalHolder.lnr_des.startAnimation(slide_up);

            }
        });*/

     /*   holder.imgshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = list.get(position).getUrl();
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "News");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
*/


        return v;

    }

    class Holder {
ProgressBar progressBar,pb;
        TextView txt_title, txt_des, txttime, txtfrm;
        ImageView img, imgbig;
        FrameLayout frmbig;
        LinearLayout lnr_des, lnr_show;
        RelativeLayout rllist;

    }
}
