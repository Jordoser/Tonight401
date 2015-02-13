package com.example.tonight;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarek on 2015-02-10.
 */
public class ParseOperations extends ParseObject {
    public static ArrayList<ParseObject> listVenues= new ArrayList<ParseObject>();

    public static void getVenues(){
        listVenues = new ArrayList<ParseObject>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Venue");
        query.orderByAscending("barName");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> venueList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + venueList.size() + " bars");
                    for (ParseObject venue:venueList) {
                        System.out.println(venue.get("barName").toString());
                        listVenues.add(venue);
                        System.out.println("Added Venue");
                    }
                    VenueListController.setVenueList(listVenues);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                    //listVenues.add("Error");
                }
            }
        });
    }

    //public static ArrayList<String> returnList(){
    //    Log.d("score", "Retrieved " + listVenues.size() + " Foster");
    //    return listVenues;
    //}
}
