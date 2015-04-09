package com.example.tonight;


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

/**
 * This class extends BaseExpandableListAdapter to create an expandable drawer on the
 * left side of the user interface. This adapter sets up the interface of the drawer
 * as well as sets up base functions.
 *
 * @author Group 8: CMPUT 401
 */
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

    /**
     * The initializer for this class.
     *
     * @param context   The calling context being used to instantiate the fragment.
     * @param venues    The Venues already organized into parents and children
     */
    public DrawerAdapter(Context context, ArrayList<ArrayList <Venue>> venues) {
        this.context = context;
        this.venues = venues;
        this.inflater = LayoutInflater.from(this.context);
    }

    /**
     * Gets the number of groups.
     *
     * @return          the number of groups
     */
    @Override
    public int getGroupCount() {
        return venues.size();
    }

    /**
     * Gets the number of children in a specified group.
     *
     * @param gPosition the position of the group for which the children count should be returned
     * @return          the children count in the specified group
     */
    @Override
    public int getChildrenCount(int gPosition) {
        return venues.get(gPosition).size();
    }

    /**
     * Gets the data associated with the given group.
     *
     * @param gPosition the position of the group
     * @return          the data child for the specified group
     */
    @Override
    public Object getGroup(int gPosition) {
        return venues.get(gPosition);
    }

    /**
     * Gets the data associated with the given child within the given group.
     *
     * @param gPosition the position of the group
     * @param cPosition the position of the child within the group
     * @return          the data of the child
     */
    @Override
    public Object getChild(int gPosition, int cPosition) {
        return venues.get(gPosition).get(cPosition);
    }

    /**
     * Gets the ID for the group at the given position. This group ID must be unique across
     * groups. The combined ID (see getCombinedGroupId(long)) must be unique across ALL items
     * (groups and all children).
     * <Not Implemented>
     *
     * @param gPosition the position of the group
     * @return          the ID associated with the group
     */
    @Override
    public long getGroupId(int gPosition) {
        return 0;
    }

    /**
     * Gets the ID for the given child within the given group. This ID must be unique across
     * all children within the group. The combined ID (see getCombinedChildId(long, long))
     * must be unique across ALL items (groups and all children).
     * <Not Implemented>
     *
     * @param gPosition the position of the group
     * @param cPosition the position of the child within the group
     * @return          the ID associated with the child
     */
    @Override
    public long getChildId(int gPosition, int cPosition) {
        return 0;
    }

    /**
     * Indicates whether the child and group IDs are stable across changes to the underlying data.
     *
     * @return whether or not the same ID always refers to the same object
     * <Not Implemented>
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Gets a View that displays the given group. This View is only for the group--the Views for
     * the group's children will be fetched using getChildView(int, int, boolean, View, ViewGroup).
     *
     * @param gPosition     the position of the group
     * @param isExpanded    whether the group is expanded or collapsed
     * @param convertView   the old view to reuse, if possible. You should check that this view is
     *                      non-null and of an appropriate type before using. If it is not possible
     *                      to convert this view to display the correct data, this method can
     *                      create a new view. It is not guaranteed that the convertView will have
     *                      been previously created by getGroupView(int, boolean, View, ViewGroup).
     * @param parent        the parent that this view will eventually be attached to
     * @return              the View corresponding to the group at the specified position
     */
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

        //if the venue has a logo
        if (venues.get(gPosition).get(0).getLogo() != null){
            mViewHolder.ivIcon.setImageBitmap(venues.get(gPosition).get(0).getLogo());
            mViewHolder.tvTitle.setText(null);
        }
        //if the venue does not have a logo
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

    /**
     * Gets a View that displays the data for the given child within the given group.
     *
     * @param gPosition     the position of the group
     * @param cPosition     the position of the child within the group
     * @param isLastChild   whether the child is the last child within the group
     * @param convertView   the old view to reuse, if possible. You should check that this view is
     *                      non-null and of an appropriate type before using. If it is not possible
     *                      to convert this view to display the correct data, this method can
     *                      create a new view. It is not guaranteed that the convertView will have
     *                      been previously created by
     *                      getChildView(int, int, boolean, View, ViewGroup).
     * @param parent        the parent that this view will eventually be attached to
     * @return              the View corresponding to the child at the specified position
     */
    @Override
    public View getChildView(int gPosition, int cPosition, boolean isLastChild, View convertView, ViewGroup parent) {
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

    /**
     * Whether the child at the specified position is selectable.
     *
     * @param gPosition the position of the group
     * @param cPosition the position of the child within the group
     * @return whether the child is selectable.
     */
    @Override
    public boolean isChildSelectable(int gPosition, int cPosition) {
        return true;
    }

    /**
     * Holder for the text and image views from the venues
     */
    private class ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;
    }
}