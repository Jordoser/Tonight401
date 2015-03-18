package com.example.tonight;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.parse.ParseFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by David on 2015-03-13.
 */
public class CommentActivity extends Activity {
    private LayoutInflater mInflater;
    private ActionBar mActionBar;
    private View mCustomView;

    private ScreenName screenname;
    private String name;
    private Button takePic;
    private Button takeVid;
    private Button postButton;
    private TextView mTitleTextView;
    private ImageView imageView;
    private VideoView videoView;
    private EditText editText;

    private int dist;

    private int MEDIA_TYPE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);

        mInflater = LayoutInflater.from(this);

        mCustomView = mInflater.inflate(R.layout.comment_actionbar, null);

        //Write Comment box
        editText = (EditText) findViewById(R.id.commentText);

        //Post Button Post comment (upload to Parse)
        postButton = (Button) mCustomView.findViewById(R.id.postButton1);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = editText.getText().toString();
                postMessage(str);
                Toast.makeText(getApplicationContext(), "Comment Posted!",
                        Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });


        //Set screen name at the top
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("Screenname: " + loadScreenName());

        //Button and Listener for photos and videos
        takeVid = (Button) findViewById(R.id.takeVideo);
        takePic = (Button) findViewById(R.id.takePicture);

        takePic.setOnClickListener(onClickVideoPhoto);
        takeVid.setOnClickListener(onClickVideoPhoto);

        //Set Custom Actionbar
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public View.OnClickListener onClickVideoPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //REMOVE WHEN DONE
            deleteMedia();

            switch (v.getId()) {
                case R.id.takePicture:
                    MEDIA_TYPE = 1;
                    break;
                case R.id.takeVideo:
                    MEDIA_TYPE = 2;
                    break;
            }
            Intent intent = new Intent(CommentActivity.this, CameraActivity.class);
            intent.putExtra("media_type", String.valueOf(MEDIA_TYPE));
            startActivity(intent);
        }
    };

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        imageView = (ImageView) findViewById(R.id.photoView);
        videoView = (VideoView) findViewById(R.id.videoView);

        imageView.setImageURI(null);
        videoView.setVideoURI(null);

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

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
            LinearLayout posting = (LinearLayout) findViewById(R.id.addMedia);
            if (dist < 15){
                posting.setVisibility(LinearLayout.VISIBLE);
            }else{
                posting.setVisibility(LinearLayout.GONE);
            }
        }catch (IOException ie){
            ie.printStackTrace();
        }
        Log.d("dist =",String.valueOf(dist));

        if (MEDIA_TYPE == 1) {
            handlePicture(path);
        } else if (MEDIA_TYPE == 2) {
            handleVideo(path);
        }
    }

    public void handleVideo(String path) {
        imageView.setVisibility(ImageView.GONE);

        File file = new File(path + "/video.mp4");
        if (file.exists()) {
            MediaController mc = new MediaController(this);
            mc.setAnchorView(videoView);
            mc.setMediaPlayer(videoView);
            videoView.setMediaController(mc);
            videoView.setVideoPath(path + "/video.mp4");
            videoView.requestFocus();
            videoView.setVisibility(VideoView.VISIBLE);

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

    //Back arrow at the top left corner
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Prepares videos and images for Parse
    public void prepareForParse(String type){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ParseFile parseFile = null;

        if(type.equals("photo")){
            Bitmap bitmap = BitmapFactory.decodeFile(path+"/photo.jpg");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        }
        else{

        }
        byte [] image = stream.toByteArray();
        parseFile = new ParseFile("File", image);
    }

    public String loadScreenName() {
        screenname = null;
        screenname.load(this);
        name = screenname.getName();
        return name;
    }

    public void postMessage(String post) {
        String barId = VenueHolder.getBarID();
        ParseOperations.addComment(barId, post, name);
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        editText.setText("");
    }

    public void deleteMedia(){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        File photo = new File(path+"/photo.jpg");
        File video = new File(path+"/video.mp4");
        boolean deletePhoto = photo.delete();
        boolean deleteVideo = video.delete();
    }
}