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
public class WhyteFragment extends Fragment {
    private ListView listview;
    ArrayAdapter<String> adapter;
    String[] skeletor = new String[] {
            "skeletor",
            "skeletor",
            "skeletor",
            "skeletor",
            "skeletor",
            "skeletor",
            "skeletor",
            "skeletor",
            "skeletor",
            "skeletor",
            "skeletor",
            "skeletor",
            "skeletor",
            "skeletor",
            "skeletor",
            "skeletor"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_whyte, container, false);
        listview =(ListView)rootView.findViewById(R.id.venueCommentListWhyte);
        adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,skeletor);
        listview.setAdapter(adapter);

        return rootView;
    }
}
