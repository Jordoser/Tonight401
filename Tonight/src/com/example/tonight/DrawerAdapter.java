package com.example.tonight;

/**
 * Created by junker4ce on 15-03-09.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseImageView;

import java.util.ArrayList;

public class DrawerAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ArrayList <Venue>> venues;
    private LayoutInflater inflater;

    private static final int[] EMPTY_STATE_SET = {};
    private static final int[] GROUP_EXPANDED_STATE_SET =
            {android.R.attr.state_expanded};
    private static final int[][] GROUP_STATE_SETS = {
            EMPTY_STATE_SET, // 0
            GROUP_EXPANDED_STATE_SET // 1
    };

    public DrawerAdapter(Context context, ArrayList<ArrayList <Venue>> venues) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.venues = venues;
        this.inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return venues.size();
    }

    @Override
    public int getChildrenCount(int gPosition) {
        // TODO Auto-generated method stub
        return venues.get(gPosition).size();
    }

    @Override
    public Object getGroup(int gPosition) {
        // TODO Auto-generated method stub
        return venues.get(gPosition);
    }

    @Override
    public Object getChild(int gPosition, int cPosition) {
        // TODO Auto-generated method stub
        return venues.get(gPosition).get(cPosition);
    }

    @Override
    public long getGroupId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long getChildId(int gPosition, int cPosition) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int gPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.drawer_list, null);
            mViewHolder = new ViewHolder();
            convertView.setTag(mViewHolder);
        }
        else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.tvTitle = (TextView) convertView
                .findViewById(R.id.barText);
        mViewHolder.ivIcon = (ImageView) convertView
                .findViewById(R.id.barLogo);

        if (venues.get(gPosition).get(0).getLogo() != null){
            mViewHolder.ivIcon.setImageBitmap(venues.get(gPosition).get(0).getLogo());
            mViewHolder.tvTitle.setText(null);
        }
        else {
            mViewHolder.tvTitle.setText(venues.get(gPosition).get(0).getName());
            mViewHolder.ivIcon.setImageBitmap(null);
        }

        View arrow = convertView.findViewById(R.id.groupIndicator);

        if( arrow != null ) {
            ImageView indicator = (ImageView)arrow;
            if( venues.get(gPosition).size() == 1 ) {
                indicator.setVisibility( View.INVISIBLE );
            } else {
                indicator.setVisibility( View.VISIBLE );
                int stateSetIndex = ( isExpanded ? 1 : 0) ;
                Drawable drawable = indicator.getDrawable();
                drawable.setState(GROUP_STATE_SETS[stateSetIndex]);
            }
        }
        if(isExpanded) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.pink));
        }
        else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        return convertView;
    }

    @Override
    public View getChildView(int gPosition, int cPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Venue posVenue = venues.get(gPosition).get(cPosition);

        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.subdrawer_list, null);
            mViewHolder = new ViewHolder();
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.tvTitle = (TextView) convertView
                .findViewById(R.id.areaText);
        String areaDisc = posVenue.getArea() + " - " + posVenue.getAddress();

        mViewHolder.tvTitle.setText(areaDisc);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;
    }
}