package com.example.tonight;

import android.content.Context;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.parse.FindCallback;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.List;

public class MainCommentsAdapter extends ParseQueryAdapter<ParseObject>{
    public MainCommentsAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("Comments");
                query.orderByDescending("likes");


                return query;
            }
        });
    }

    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.comment_item_main, null);
        }
        //else{
          //  view=v;
        //}

        super.getItemView(object, v, parent);

        // Add and download the image
        ParseImageView commentImage = (ParseImageView) v.findViewById(R.id.comment_image_main);
        ParseFile imageFile = object.getParseFile("photo");
        if (imageFile != null) {
            commentImage.setParseFile(imageFile);
            commentImage.loadInBackground();
        }

        TextView userTextView = (TextView) v.findViewById(R.id.user_main);
        userTextView.setText(object.getString("user"));

        TextView postTimeTextView = (TextView) v.findViewById(R.id.post_time_main);
        postTimeTextView.setText(object.getCreatedAt().toString());

        TextView commentTextView = (TextView) v.findViewById(R.id.comment_text_main);
        commentTextView.setText(object.getString("commentText"));

        Integer totalLikes = object.getInt("likes") - object.getInt("dislikes");
        TextView likesTextView = (TextView) v.findViewById(R.id.likes_main);
        likesTextView.setText(totalLikes.toString());


        TextView barNameTextView = (TextView) v.findViewById(R.id.bar_name_main);
        String venueName = VenueListController.getVenueName(object.getString("barId"));
        barNameTextView.setText("@" + venueName);

        return v;
    }
}
