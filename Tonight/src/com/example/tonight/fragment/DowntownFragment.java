package com.example.tonight.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class DowntownFragment extends Fragment {
    private ListView listview;
    MainCommentsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_downtown, container, false);
        listview =(ListView)rootView.findViewById(R.id.venueCommentListDowntown);
        adapter = new MainCommentsAdapter(this.getActivity().getApplicationContext(), VenueListController.returnVenueIds("Downtown"));
        adapter.setObjectsPerPage(10);
        if (listview != null) {
            listview.setAdapter(adapter);
        }

        return rootView;
    }
}
