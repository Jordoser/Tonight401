package com.example.tonight;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VenueCommentsAdapter extends ParseQueryAdapter<ParseObject> {

    public VenueCommentsAdapter(Context context, final String venue_id) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("Comments");
                query.whereEqualTo("barId", venue_id);
                query.orderByDescending("createdAt");
                return query;
           }
        });

    }

    @Override
    public View getItemView(final ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.comment_item, null);
        }

        super.getItemView(object, v, parent);

        // Add and download the image
        ParseImageView commentImage = (ParseImageView) v.findViewById(R.id.comment_image);
        ParseFile imageFile = object.getParseFile("photo");
        if (imageFile != null) {
            commentImage.setParseFile(imageFile);
            commentImage.loadInBackground();
        }
        else{
            commentImage.setImageResource(android.R.color.transparent);
        }


        TextView userTextView = (TextView) v.findViewById(R.id.user);
        userTextView.setText(object.getString("user"));

        TextView postTimeTextView = (TextView) v.findViewById(R.id.post_time);
        Date postTime = object.getCreatedAt();
        Date currentTime = new Date();
        long diff = currentTime.getTime() - postTime.getTime();
        long diffSeconds = diff/1000;
        long diffMinutes = diff/(1000 * 60);
        long diffHours = diff/(1000 * 60 * 60);
        long diffDays = diff/(1000 * 60 * 60 * 24);
        long diffWeeks = diff/(1000 * 60 * 60 * 24 * 7);

        String diffString = "";
        if (diffWeeks > 0) {
            diffString = diffWeeks + " week";
            if (diffWeeks > 1) {
                diffString += "s";
            }
            postTimeTextView.setText(diffString + " ago");

        } else if (diffDays > 0) {
            diffString = diffDays + " day";
            if (diffDays > 1) {
                diffString += "s";
            }
            postTimeTextView.setText(diffString + " ago");

        } else if (diffHours > 0) {
            diffString = diffHours + " hour";
            if (diffHours > 1) {
                diffString += "s";
            }
            postTimeTextView.setText(diffString + " ago");

        } else if (diffMinutes > 0) {
            diffString = diffMinutes + " minute";
            if (diffMinutes > 1) {
                diffString += "s";
            }
            postTimeTextView.setText(diffString + " ago");

        } else if (diffSeconds >= 0) {
            diffString = diffSeconds + " second";
            if (diffSeconds > 1) {
                diffString += "s";
            }
            postTimeTextView.setText(diffString + " ago");

        } else {
            SimpleDateFormat df = new SimpleDateFormat("MMM d");
            String postDate = df.format(postTime);
            postTimeTextView.setText(postDate);
        }

        TextView commentTextView = (TextView) v.findViewById(R.id.comment_text);
        commentTextView.setText(object.getString("commentText"));

        Integer totalLikes = object.getInt("likes") - object.getInt("dislikes");
        final TextView likesTextView = (TextView) v.findViewById(R.id.likes);
        likesTextView.setText(totalLikes.toString());


        final ImageButton likes= (ImageButton) v.findViewById(R.id.likeButton2);
        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likes.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.like_button_selector));
                ParseOperations.increment("likes",object);
                String total=ParseOperations.newTotalLikes(object.getString("objectId"),object.getInt("likes") - object.getInt("dislikes"));
                likesTextView.setText(total);
            }
        });

        final ImageButton disLikes= (ImageButton) v.findViewById(R.id.dislikeButton2);
        disLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disLikes.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.dislike_button_selector));
                ParseOperations.increment("dislikes",object);
                String total=ParseOperations.newTotalLikes(object.getString("objectId"),object.getInt("likes") - object.getInt("dislikes"));
                likesTextView.setText(total);
            }
        });
        return v;
    }
}
