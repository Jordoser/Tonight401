package com.example.tonight;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class VenueCommentsAdapter extends ParseQueryAdapter<ParseObject> {

    public final String venue_id;

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

        this.venue_id = venue_id;
    }

    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.comment_item, null);
        }

        super.getItemView(object, v, parent);

        TextView userTextView = (TextView) v.findViewById(R.id.user);
        userTextView.setText(object.getString("user"));

        TextView postTimeTextView = (TextView) v.findViewById(R.id.post_time);
        postTimeTextView.setText(object.getCreatedAt().toString());

        TextView commentTextView = (TextView) v.findViewById(R.id.comment_text);
        commentTextView.setText(object.getString("commentText"));

        Integer totalLikes = object.getInt("likes") - object.getInt("dislikes");
        TextView likesTextView = (TextView) v.findViewById(R.id.likes);
        likesTextView.setText(totalLikes.toString());

        return v;
    }
}
