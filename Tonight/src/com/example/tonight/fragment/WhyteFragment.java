package com.example.tonight.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tonight.MainCommentsAdapter;
import com.example.tonight.R;
import com.example.tonight.VenueListController;

/**
 * Created by junker4ce on 15-02-11.
 */
public class WhyteFragment extends Fragment {
    private ListView listview;
    private SwipeRefreshLayout swipeLayout;
    MainCommentsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_whyte, container, false);
        listview =(ListView)rootView.findViewById(R.id.venueCommentListWhyte);
        adapter = new MainCommentsAdapter(this.getActivity().getApplicationContext(), VenueListController.returnVenueIds("Whyte"));
        adapter.setObjectsPerPage(10);
        if (listview != null) {
            listview.setAdapter(adapter);
        }

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
                }, 3000);
            }
        });
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return rootView;
    }
}
