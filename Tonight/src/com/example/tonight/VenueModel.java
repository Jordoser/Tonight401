package com.example.tonight;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


@ParseClassName("Venue")
public class VenueModel extends ParseObject {
    String name;
    String address;
    String area;
    public static ArrayList<String> listVenues= new ArrayList<String>();
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void getVenues(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Venue");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> venueList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + venueList.size() + " bars");
                    for (ParseObject venue:venueList) {
                        add(venue.get("barName").toString());
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                    add("Error");
                }
            }
        });
    }
    private static void add(String string){
        listVenues.add(string);
        Log.d("score", "Retrieved " + listVenues.size() + " HHA");
    }
    public static ArrayList<String> returnList(){
        Log.d("score", "Retrieved " + listVenues.size() + " Foster");
        return listVenues;
    }
}
