package com.example.tonight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by junker4ce on 15-02-12.
 */
public class VenueActivity extends Activity {
    private ArrayList<CharSequence> spinnerArray;
    private Spinner spinner;
    private ListView commentList;
    private String venue_id;
    private String name;
    private EditText eText;
    private Button btn;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        day = day -1;

        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_venue);

        eText = (EditText) findViewById(R.id.postText);
        btn = (Button) findViewById(R.id.postButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = eText.getText().toString();
                postMessage(str);
            }
        });

        FileInputStream fis;
        Context context = this;
        try {
            fis = context.openFileInput("screenName");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    fis));
            name = in.readLine();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }

        TextView barName = (TextView)findViewById(R.id.venueName);
        barName.setText(VenueHolder.getBarName());
        Intent intent = getIntent();
        venue_id = intent.getStringExtra("venue_id");

        TextView venueHours = (TextView)findViewById(R.id.venueSpec);
        venueHours.setText("Hours: "+VenueHolder.getListHours().get(day));

        TextView venueInfo = (TextView)findViewById(R.id.venueSpecList);
        venueInfo.setText("Specials: "+VenueHolder.getListSpecials().get(day));

        //Code For Weekday Spinner
        spinner = (Spinner) findViewById(R.id.weekday_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.weekday_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(day);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Called when a new item is selected (in the Spinner)
             */
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An spinnerItem was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                String test = Integer.toString(pos);
                TextView venueHours = (TextView)findViewById(R.id.venueSpec);
                venueHours.setText("Hours: "+VenueHolder.getListHours().get(pos));

                TextView venSpec = (TextView)findViewById(R.id.venueSpecList);
                venSpec.setText("Specials: " + VenueHolder.getListSpecials().get(pos));
                view.invalidate();

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing, just another required interface callback
            }

        }); // (optional)
        
        //Comment ListView
        refreshComments();

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

    public void postMessage(String post){
        String barId = VenueHolder.getBarID();
        ParseOperations.addComment(barId, post, name);
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        eText.setText("");
        refreshComments();
    }

    private void refreshComments() {
        commentList = (ListView) findViewById(R.id.venueCommentList);
        VenueCommentsAdapter commentAdapter = new VenueCommentsAdapter(this, venue_id);
        commentAdapter.notifyDataSetChanged();
        commentAdapter.setObjectsPerPage(10);
        commentList.setAdapter(commentAdapter);
       // commentAdapter.notifyDataSetChanged();
    }

    public void infoScreen(View view) {
        //Toast toast = Toast.makeText(this, "Test", Toast.LENGTH_LONG);
        // toast.show();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        String info = VenueHolder.getInfo();

        // set dialog message
        alertDialogBuilder
                .setMessage(info)
                .setCancelable(true);
                /*.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        dialog.cancel();
                    }*/

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

}
