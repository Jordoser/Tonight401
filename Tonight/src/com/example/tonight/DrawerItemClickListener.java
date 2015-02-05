package com.example.tonight;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DrawerItemClickListener extends Activity implements ListView.OnItemClickListener {
    private String[] mVenueNames;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        selectItem(position);
    }

    private void selectItem(int position) {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        /*
        Fragment fragment = new VenueFragment();
        Bundle args = new Bundle();
        args.putInt(VenueFragment.ARG_VENUE_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        */
        mDrawerList.setItemChecked(position, true);
        //setTitle(mVenueNames[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    /*
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    */
}
