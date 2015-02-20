package com.example.tonight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;

/**
 * Created by junker4ce on 15-02-12.
 */
public class VenueActivity extends Activity {
    private ArrayList<CharSequence> spinnerArray;
    private Spinner spinner;
    private ListView commentList;

    //For Testing ListView
    /*
    String[] testValues = new String[]{"Android List View",
            "Adapter implementation",
            "Simple List View In Android",
            "Create List View Android",
            "Android Example",
            "List View Source Code",
            "List View Array Adapter",
            "Android Example List View"
    };*/
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_venue);

        Intent intent = getIntent();
        String venue_id = intent.getStringExtra("venue_id");

        //Code For Weekday Spinner
        spinner = (Spinner) findViewById(R.id.weekday_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.weekday_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(new SpinnerListener());
        
        //Comment ListView
        commentList = (ListView) findViewById(R.id.venueCommentList);
        VenueCommentsAdapter commentAdapter = new VenueCommentsAdapter(this, venue_id);
        commentAdapter.setObjectsPerPage(10);
        commentList.setAdapter(commentAdapter);

        //commentList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, testValues));
    }

    //Back Button at the top left corner
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //onClick Listener for the weekday spinner
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinner.setSelection(position);
        String selState = (String) spinner.getSelectedItem();
        Log.e("Here", selState);
    }
}
