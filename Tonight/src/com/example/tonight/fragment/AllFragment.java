package com.example.tonight.fragment;

import com.example.tonight.MainCommentsAdapter;
import com.example.tonight.R;
import com.example.tonight.VenueActivity;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by junker4ce on 15-02-11.
 */
public class AllFragment extends Fragment {
    private ListView listview;
    MainCommentsAdapter adapter;
    /*String[] countries = new String[] {
            "India",
            "Pakistan",
            "Sri Lanka",
            "China",
            "Bangladesh",
            "Nepal",
            "Afghanistan",
            "North Korea",
            "South Korea",
            "Japan"
    };*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_all, container, false);
        listview =(ListView) rootView.findViewById(R.id.venueCommentListAll);
        adapter = new MainCommentsAdapter(this.getActivity().getApplicationContext());
        adapter.setObjectsPerPage(10);
        listview.setAdapter(adapter);

        return rootView;
    }
}



