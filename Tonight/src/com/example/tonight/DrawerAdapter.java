package com.example.tonight;

/**
 * Created by junker4ce on 15-03-09.
 */
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DrawerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> titles;
//    private int[] images;
    private LayoutInflater inflater;

    /*public DrawerAdapter(Context context, ArrayList<String> titles, int[] images,
                           int[] selectedPosition) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.titles = titles;
        this.images = images;
        this.inflater = LayoutInflater.from(this.context);
        this.selectedPosition = selectedPosition;
    }*/

    public DrawerAdapter(Context context, ArrayList<String> titles) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.titles = titles;
        this.inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.drawer_list, null);
            mViewHolder = new ViewHolder();
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.tvTitle = (TextView) convertView
                .findViewById(R.id.barText);
        mViewHolder.ivIcon = (ImageView) convertView
                .findViewById(R.id.barLogo);

        mViewHolder.tvTitle.setText(titles.get(position));
        //mViewHolder.ivIcon.setImageResource(images[position]);

        /*//Highlight the selected list item
        if (position == selectedPosition[0]) {
            convertView.setBackgroundColor(Color.WHITE);
            mViewHolder.tvTitle.setTextColor(Color.BLUE);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
            mViewHolder.tvTitle.setTextColor(Color.WHITE);
        }*/

        return convertView;
    }

    private class ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;
    }
}