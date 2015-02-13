package com.example.tonight;

import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by junker4ce on 15-02-12.
 */
public class VenueListController {
    public static ArrayList<ParseObject> venueList;

    public static void setVenueList(ArrayList<ParseObject> vList){
        if(venueList != null){venueList.clear();}
        venueList = vList;
        System.out.println(venueList.size());
    }

    public static ArrayList<ParseObject> getVenueList(){
        return venueList;
    }

    public static ArrayList<String> returnVenues() {
        //returns the list of all venues
        ArrayList<String> venues = new ArrayList<String>();
        for(ParseObject venue:venueList){
            venues.add(venue.get("barName").toString());
        }
        return venues;
    }

    public static ArrayList<String> returnVenues(String area) {
        //returns the list of all venues from the specified area
        ArrayList<String> venues = new ArrayList<String>();
        for(ParseObject venue:venueList){
            if(venue.get("Area").equals(area)) {
                venues.add(venue.get("barName").toString());
            }
        }
        return venues;
    }
}