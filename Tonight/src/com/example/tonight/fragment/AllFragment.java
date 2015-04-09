package com.example.tonight.fragment;

import com.example.tonight.MainCommentsAdapter;
import com.example.tonight.R;
import com.example.tonight.VenueActivity;
import com.example.tonight.VenueListController;

import android.app.ListFragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * This class displays the fragment with all of the comments from
 * all of the areas in the city displayed on the MainActivity
 *
 * @author Group 8: CMPUT 401
 */
public class AllFragment extends Fragment {
    private ListView listview;
    private SwipeRefreshLayout swipeLayout;
    MainCommentsAdapter adapter;

    /**
     * This function sets up the fragment for display and sets up
     * the adapter for displaying the comments. It also sets up the
     * ability to allow swipe refreshing of comments.
     *
     * @param  inflater             The LayoutInflater object that can be used to inflate any
     *                              views in the fragment,
     * @param  container            If non-null, this is the parent view that the fragment's
     *                              UI should be attached to. The fragment should not add the view
     *                              itself, but this can be used to generate the LayoutParams
     *                              of the view.
     * @param  savedInstanceState   If non-null, this fragment is being re-constructed from a
     *                              previous saved state as given here.
     * @return                      Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_all, container, false);
        listview = (ListView) rootView.findViewById(R.id.venueCommentListAll);
        adapter = new MainCommentsAdapter(this.getActivity().getApplicationContext(), VenueListController.returnVenueIds());
        adapter.setObjectsPerPage(10);
        if (listview != null) {
            listview.setAdapter(adapter);
        }

        //Allows for refresh on swipe up
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.venueCommentsSwipe);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                listview.setAdapter(adapter);
                ( new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return rootView;
    }
}