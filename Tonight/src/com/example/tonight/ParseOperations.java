package com.example.tonight;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarek on 2015-02-10.
 */
public class ParseOperations extends ParseObject {
    public static ArrayList<String> listVenues= new ArrayList<String>();

    public static void getVenues(){
        listVenues = new ArrayList<String>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Venue");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> venueList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + venueList.size() + " bars");
                    for (ParseObject venue:venueList) {
                        listVenues.add(venue.get("barName").toString());
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                    listVenues.add("Error");
                }
            }
        });
    }

    public static ArrayList<String> returnList(){
        Log.d("score", "Retrieved " + listVenues.size() + " Foster");
        return listVenues;
    }
}
