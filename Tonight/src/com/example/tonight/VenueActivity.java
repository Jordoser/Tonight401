package com.example.tonight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.File;
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
    private Button pictureBtn;
    private ImageView imageView;
    private VideoView videoView;
    private int MEDIA_TYPE;
    private int dist;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Delete Photo and or Video
        deleteLocalMedia();

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        day = day - 1;

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

        //onClick Listener to camera
        pictureBtn = (Button) findViewById(R.id.pictureButton);
        pictureBtn.setOnClickListener(photoOnClick);

        FileInputStream fis = null;
        //Context context = this;
        try {
            fis = this.openFileInput("screenName");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    fis));
            name = in.readLine();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }

        TextView barName = (TextView) findViewById(R.id.venueName);
        barName.setText(VenueHolder.getBarName());
        Intent intent = getIntent();
        venue_id = intent.getStringExtra("venue_id");

        TextView venueHours = (TextView) findViewById(R.id.venueSpec);
        venueHours.setText("Hours: " + VenueHolder.getListHours().get(day));

        TextView venueInfo = (TextView) findViewById(R.id.venueSpecList);
        venueInfo.setText("Specials: " + VenueHolder.getListSpecials().get(day));

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
                TextView venueHours = (TextView) findViewById(R.id.venueSpec);
                venueHours.setText("Hours: " + VenueHolder.getListHours().get(pos));

                TextView venSpec = (TextView) findViewById(R.id.venueSpecList);
                venSpec.setText("Specials: " + VenueHolder.getListSpecials().get(pos));
                view.invalidate();

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing, just another required interface callback
            }

        }); // (optional)

        //Comment ListView
        refreshComments();
    }

    @Override
    public void onResume() {
        super.onResume();

        imageView = (ImageView) findViewById(R.id.photo);
        videoView = (VideoView) findViewById(R.id.video);
        imageView.setImageURI(null);
        videoView.setVideoURI(null);

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        if (MEDIA_TYPE == 1) {
            handlePicture(path);
        } else if (MEDIA_TYPE == 2) {
            handleVideo(path);
        }


        GeoLoc gl = new GeoLoc(this);
        try {
            ArrayList<Double> loc = gl.findLocation();
            ArrayList<Double> barloc = VenueHolder.getGeoPoint();
            double earthRadius = 6371.0;
            double dLat = Math.toRadians(barloc.get(0) - loc.get(0));
            double dLng = Math.toRadians(barloc.get(1) - loc.get(1));
            double sindLat = Math.sin(dLat / 2);
            double sindLng = Math.sin(dLng / 2);
            double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                    * Math.cos(Math.toRadians(loc.get(0))) * Math.cos(Math.toRadians(barloc.get(0)));
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distdouble = earthRadius * c;
            dist = (int) distdouble;
            LinearLayout posting = (LinearLayout) findViewById(R.id.WriteCommentWall);
            if (dist < 15){
                posting.setVisibility(LinearLayout.VISIBLE);
            }else{
                posting.setVisibility(LinearLayout.GONE);
            }
        }catch (IOException ie){
            ie.printStackTrace();
        }
        Log.d("dist =",String.valueOf(dist));








        }

    public View.OnClickListener photoOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            PopupMenu popup = new PopupMenu(VenueActivity.this, pictureBtn);

            popup.getMenuInflater().inflate(R.menu.camera_popup, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    MEDIA_TYPE = 0;
                    if (String.valueOf(item.getTitle()).equals("Photo")) {
                        MEDIA_TYPE = 1;

                    } else if (String.valueOf(item.getTitle()).equals("Video")) {
                        MEDIA_TYPE = 2;
                    }
                    Intent intent = new Intent(VenueActivity.this, CameraActivity.class);
                    intent.putExtra("media_type", String.valueOf(MEDIA_TYPE));

                    startActivity(intent);
                    return true;
                }
            });
            popup.show();
        }
    };


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

    public void postMessage(String post) {
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

    //Deletes photos and videos from /sdcard/ after the picutre or video has been uploaded

    public void deleteLocalMedia() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File photo = new File(path + "/photo.jpg");
        photo.delete();
        File video = new File(path + "/video.mp4");
        video.delete();
    }

    public void handleVideo(String path) {
        imageView.setVisibility(ImageView.GONE);

        File file = new File(path + "/video.mp4");
        if (file.exists()) {
            videoView.setVisibility(VideoView.VISIBLE);
            MediaController mc = new MediaController(this);
            mc.setAnchorView(videoView);
            mc.setMediaPlayer(videoView);
            videoView.setMediaController(mc);
            videoView.setVideoPath(path + "/video.mp4");
            videoView.requestFocus();

            videoView.start();
        }
    }

    public void handlePicture(String path) {
        videoView.setVisibility(VideoView.GONE);
        File file = new File(path + "/photo.jpg");
        Uri imageUri = Uri.fromFile(file);
        if (file.exists()) {
            imageView.setImageURI(null);
            imageView.setImageURI(imageUri);
            imageView.setVisibility(ImageView.VISIBLE);
        }
    }

    @Override
    public void onBackPressed(){
        finish();
    }


}