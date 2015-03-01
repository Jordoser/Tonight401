package com.example.tonight.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tonight.R;

/**
 * Created by junker4ce on 15-02-11.
 */
public class DowntownFragment extends Fragment {
    private ListView listview;
    ArrayAdapter<String> adapter;
    String[] colors = new String[] {
            "Blue",
            "Pink",
            "Orange",
            "Red",
            "Black",
            "Green",
            "Yellow",
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_downtown, container, false);
        listview =(ListView)rootView.findViewById(R.id.venueCommentListDowntown);
        adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,colors);
        listview.setAdapter(adapter);

        return rootView;
    }
}
