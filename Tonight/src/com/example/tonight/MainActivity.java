package com.example.tonight;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.support.v4.app.FragmentActivity;
import android.app.ActionBar;

import com.example.tonight.adapter.TabsPagerAdapter;

import java.io.FileOutputStream;
import java.util.ArrayList;

//Look at https://developer.android.com/training/implementing-navigation/nav-drawer.html

public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener {
    //private String[] mVenueNames;
    private ArrayList<String> mVenueNames;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter<String> mListAdapter;

    // Tab titles
    private String[] tabs = { "All", "Downtown", "South", "West", "Whyte" };
    private ViewPager viewPager;
    private ActionBar actionBar;
    private TabsPagerAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        //getActionBar().show();

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mVenueNames = new ArrayList<String>(VenueListController.returnVenues());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        //For testing
        //mVenueNames=new String[] {"Venue1", "Bar2", "Pub3"};
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);


        mListAdapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, mVenueNames);
        mDrawerList.setAdapter(mListAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));

            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    // on changing the page
                    // make respected tab selected
                    actionBar.setSelectedNavigationItem(position);
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                }
            });
        }
    }

    public void updateDrawer() {
        System.out.println("Updated Drawer");
        mVenueNames.clear();
        mVenueNames.addAll(VenueListController.returnVenues());
        mListAdapter.notifyDataSetChanged();
    }

    public void updateDrawer(String area) {
        System.out.println("Updated Drawer from Area");
        mVenueNames.clear();
        mVenueNames.addAll(VenueListController.returnVenues(area));
        mListAdapter.notifyDataSetChanged();
    }

    //Drawer OnClick listener
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this, VenueActivity.class);
            startActivity(intent);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        else if(item.getItemId()==(R.id.userName)){
            ScreenName.alert(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        System.out.println("Selected Tab");
        System.out.println(tab.getText());
        if (tab.getText().equals("All")){
            updateDrawer();
        }
        else {updateDrawer((String)tab.getText());}
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}