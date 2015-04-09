package com.example.tonight.fragment;

import android.content.Intent;
import android.os.Handler;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.tonight.DrawerAdapter;
import com.example.tonight.ParseOperations;
import com.example.tonight.R;
import com.example.tonight.Venue;
import com.example.tonight.VenueActivity;
import com.example.tonight.VenueListController;

import java.util.ArrayList;

/**
 * Fragment used for managing interactions for and presentation of a navigation
 * drawer. See the <a href="*" "https:="" developer.android.com="" design="" patterns=""
 * navigation-drawer.html#interaction"="" *=""> design guidelines</a> for a complete explanation
 * of the behaviors implemented here.
 *
 * @author Group 8: CMPUT 401
 */
public class DrawerFragment extends Fragment {

    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private NavigationDrawerCallbacks mCallbacks;
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ExpandableListView mDrawerList;
    private View mFragmentContainerView;

    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private DrawerAdapter mDrawerAdapter;
    private ArrayList<ArrayList <Venue>> mVenueList;

    /**
     * Initial declaration for the class
     */
    public DrawerFragment() {
    }

    /**
     * Called to do initial creation of a fragment. This is called after onAttach(Activity) and
     * before onCreateView(LayoutInflater, ViewGroup, Bundle).
     *
     * Note that this can be called while the fragment's activity is still in the process of being
     * created. As such, you can not rely on things like the activity's content view hierarchy
     * being initialized at this point. If you want to do work once the activity itself is created,
     * see onActivityCreated(Bundle).
     *
     * @param savedInstanceState    If the fragment is being re-created from a previous saved state,
     *                              this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    /**
     * Called when the fragment's activity has been created and this fragment's view hierarchy
     * instantiated. It can be used to do final initialization once these pieces are in place, such
     * as retrieving views or restoring state. It is also useful for fragments that use
     * setRetainInstance(boolean) to retain their instance, as this callback tells the fragment
     * when it is fully associated with the new activity instance. This is called after
     * onCreateView(LayoutInflater, ViewGroup, Bundle) and before onViewStateRestored(Bundle).
     *
     * @param savedInstanceState    If the fragment is being re-created from a previous saved state,
     *                              this is the state.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * Called to have the fragment instantiate its user interface view. This is optional, and
     * non-graphical fragments can return null (which is the default implementation). This will be
     * called between onCreate(Bundle) and onActivityCreated(Bundle).
     *
     * Initalizes what happens when the items in the drawer are selected. For a group without
     * children, start the venue activity. For a group with children return false (expand the group).
     * For a child start the venue activity for the venue.
     *
     * @param inflater              The LayoutInflater object that can be used to inflate any views
     *                              in the fragment.
     * @param container             If non-null, this is the parent view that the fragment's UI
     *                              should be attached to. The fragment should not add the view
     *                              itself, but this can be used to generate the LayoutParams of
     *                              the view.
     * @param savedInstanceState    If non-null, this fragment is being re-constructed from a
     *                              previous saved state as given here.
     * @return                      Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerList = (ExpandableListView) inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false);

        mDrawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener(){
            public boolean onGroupClick(ExpandableListView parent, View v, final int groupPosition, long id) {
                if(mVenueList.get(groupPosition).size() == 1) {
                    ParseOperations.getName(mVenueList.get(groupPosition).get(0).getID());

                    v.setBackgroundColor(getResources().getColor(R.color.pink));

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Intent intent = new Intent(getActivity(), VenueActivity.class);
                            intent.putExtra("venue_id", mVenueList.get(groupPosition).get(0).getID());
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
                        }
                    }, 750);
                    return true;
                }
                else{ return false; }
            }
        });

        mDrawerList.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            public boolean onChildClick(ExpandableListView parent, View v, final int gPosition, final int cPosition, long id){
                ParseOperations.getName(mVenueList.get(gPosition).get(cPosition).getID());

                v.setBackgroundColor(getResources().getColor(R.color.pink));

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(getActivity(), VenueActivity.class);
                        intent.putExtra("venue_id", mVenueList.get(gPosition).get(cPosition).getID());
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
                    }
                }, 750);
                return true;
            }
        });

        mVenueList = VenueListController.returnVenues();

        mDrawerAdapter = new DrawerAdapter(getActivity(), mVenueList);
        mDrawerList.setAdapter(mDrawerAdapter);
        return mDrawerList;
    }

    /**
     * Check if the given drawer view is currently in an open state. To be considered "open" the
     * drawer must have settled into its fully visible state. If there is no drawer with the given
     * gravity this method will return false.
     *
     * @return true if the given drawer view is in an open state
     */
    public boolean isDrawerOpen() {
        return mDrawerLayout != null
                && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Sets up the way the drawer interacts and overlays with the rest of the MainActivity
     *
     * @param fragmentId    the id of the xml file for the drawer
     * @param drawerLayout  the xml layout of the drawer
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open, /*
           * "open drawer" description for
           * accessibility
           */
                R.string.drawer_close /*
           * "close drawer" description for
           * accessibility
           */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls
                // onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to
                    // prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true)
                            .commit();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls
                // onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce
        // them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     * Called when a fragment is first attached to its activity. onCreate(Bundle) will be called
     * after this.
     *
     * @param activity  The Activity the fragment is attaching to.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    "Activity must implement NavigationDrawerCallbacks.");
        }
    }

    /**
     * Called when the fragment is no longer attached to its activity. This is called after
     * onDestroy().
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    /**
     * Called to ask the fragment to save its current dynamic state, so it can later be
     * reconstructed in a new instance of its process is restarted. If a new instance of the
     * fragment later needs to be created, the data you place in the Bundle here will be available
     * in the Bundle given to onCreate(Bundle), onCreateView(LayoutInflater, ViewGroup, Bundle),
     * and onActivityCreated(Bundle).
     *
     * @param outState  Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    /**
     * Called by the system when the device configuration changes while your component is running.
     * Note that, unlike activities, other components are never restarted when a configuration
     * changes: they must always deal with the results of the change, such as by re-retrieving
     * resources.
     *
     * @param newConfig The new device configuration.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Initialize the contents of the Activity's standard options menu. You should place your menu
     * items in to menu. For this method to be called, you must have first called
     * setHasOptionsMenu(boolean). See Activity.onCreateOptionsMenu for more information.
     *
     * @param menu      The options menu in which you place your items.
     * @param inflater  The LayoutInflater object that can be used to inflate any views
     *                  in the fragment.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * This hook is called whenever an item in your options menu is selected. The default
     * implementation simply returns false to have the normal processing happen (calling the item's
     * Runnable or sending a message to its Handler as appropriate). You can use this method for
     * any items for which you would like to do processing without those other facilities.
     *
     * @param item  The menu item that was selected.
     * @return      boolean Return false to allow normal menu processing to proceed,
     *              true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Callbacks interface that all activities using this fragment must
     * implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onDrawerItemSelected(int position);
    }

    /**
     * Updates the drawer when to display the correct venues for the current fragment
     */
    public void updateDrawer() {
        System.out.println("Updated Drawer");
        mVenueList.clear();
        mVenueList.addAll(VenueListController.returnVenues());
        mDrawerAdapter.notifyDataSetChanged();
        mDrawerList.invalidate();
    }

    /**
     * Updates the drawer when to display the correct venues for the current area fragment
     */
    public void updateDrawer(String area) {
        System.out.println("Updated Drawer from Area");
        mVenueList.clear();
        mVenueList.addAll(VenueListController.returnVenuesArea(area));
        mDrawerAdapter.notifyDataSetChanged();
        mDrawerList.invalidate();
    }
}