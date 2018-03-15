package com.multipz.clock24.Menu;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.multipz.clock24.R;

/**
 * Created by yarolegovich on 25.03.2017.
 */
public class SimpleItem extends DrawerItem<SimpleItem.ViewHolder> {

    private int selectedItemIconTint;
    private int selectedItemTextTint;

    private int normalItemIconTint;
    private int normalItemTextTint;

    private Drawable icon;
    private String title;


    public SimpleItem(Drawable icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    @Override
    public ViewHolder createViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_option, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {
        holder.title.setText(title);
        //holder.icon.setImageDrawable(icon);

        holder.title.setTextColor(isChecked ? selectedItemTextTint : normalItemTextTint);
        //holder.icon.setColorFilter(isChecked ? selectedItemIconTint : normalItemIconTint);

        if (isChecked) {
            holder.iconBig.setVisibility(View.VISIBLE);
            holder.icon.setVisibility(View.GONE);
            holder.title.setTextSize(18);
            holder.title.setTypeface(null, Typeface.BOLD);
        } else {
            holder.icon.setVisibility(View.VISIBLE);
            holder.iconBig.setVisibility(View.GONE);
            holder.title.setTextSize(15);
            holder.title.setTypeface(null, Typeface.NORMAL);
        }
    }

    public SimpleItem withSelectedIconTint(int selectedItemIconTint) {
        this.selectedItemIconTint = selectedItemIconTint;
        return this;
    }

    public SimpleItem withSelectedTextTint(int selectedItemTextTint) {
        this.selectedItemTextTint = selectedItemTextTint;
        return this;
    }

    public SimpleItem withIconTint(int normalItemIconTint) {
        this.normalItemIconTint = normalItemIconTint;
        return this;
    }

    public SimpleItem withTextTint(int normalItemTextTint) {
        this.normalItemTextTint = normalItemTextTint;
        return this;
    }

    static class ViewHolder extends DrawerAdapter.ViewHolder {

        private ImageView icon, iconBig;
        private TextView title;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            iconBig = (ImageView) itemView.findViewById(R.id.icon_big);


            title = (TextView) itemView.findViewById(R.id.title);
//            Typeface   custom_font = Typeface.createFromAsset( "Leelawadee_font/LeelawUI.ttf");
//            title.setTypeface(custom_font);


        }
    }
}
