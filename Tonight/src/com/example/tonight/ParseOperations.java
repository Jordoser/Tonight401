package com.example.tonight;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarek on 2015-02-10.
 */
public class ParseOperations extends ParseObject {
    public static ArrayList<ParseObject> listVenues= new ArrayList<ParseObject>();
    public static String venue_name = new String();
    public static ArrayList<String> hoursList= new ArrayList<String>();

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

    public static void getName(String id) {
        venue_name = new String();
        hoursList= new ArrayList<String>();
        VenueHolder.setBarID(id);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Venue");
        query.whereEqualTo("objectId", id);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> venueList2, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + venueList2.size() + " bars");
                    for (ParseObject venue:venueList2) {
                        System.out.println(venue.get("barName").toString());
                        venue_name = venue.get("barName").toString();
                        if(venue.get("MonHours") != null) {
                            hoursList.add(venue.get("MonHours").toString());
                        }else{hoursList.add("Closed");}
                        if(venue.get("TuesHours") != null) {
                            hoursList.add(venue.get("TuesHours").toString());
                        }else{hoursList.add("Closed");}
                        if(venue.get("WedHours") != null) {
                            hoursList.add(venue.get("WedHours").toString());
                        }else{hoursList.add("Closed");}
                        if( venue.get("ThursHours") != null) {
                            hoursList.add(venue.get("ThursHours").toString());
                        }else{hoursList.add("Closed");}
                        if(venue.get("FriHours")!=null) {
                            hoursList.add(venue.get("FriHours").toString());
                        }else{hoursList.add("Closed");}
                        if(venue.get("SatHours")!=null) {
                            hoursList.add(venue.get("SatHours").toString());
                        }else{hoursList.add("Closed");}
                        if(venue.get("SunHours") != null) {
                            hoursList.add(venue.get("SunHours").toString());
                        }else{hoursList.add("Closed");}
                    }
                    VenueHolder.setBarName(venue_name);
                    VenueHolder.setListHours(hoursList);
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
