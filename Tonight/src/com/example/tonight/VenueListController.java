package com.example.tonight;

import android.graphics.Bitmap;

import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by junker4ce on 15-02-12.
 */
public class VenueListController {
    public static ArrayList<ParseObject> venueInfoList;
    public static ArrayList<Venue> venueList;

    public static void setVenueList(ArrayList<ParseObject> vInfoList, ArrayList<Venue> vList){
        if(venueList != null){venueList.clear();}
        venueInfoList = vInfoList;
        venueList = vList;
        System.out.println(venueList.size());
    }

    public static ArrayList<ParseObject> getVenueList(){
        return venueInfoList;
    }

    public static ArrayList<ArrayList <Venue>> returnVenues() {
        //returns the list of all venues from the specified area
        ArrayList<ArrayList <Venue>> venues = new ArrayList<ArrayList <Venue>>();
        if(venueList != null) {
            //int count = 0;
            for (Venue venue : venueList) {
                if(venues.size() == 0 ||
                        !venue.getName().equals(venues.get(venues.size()-1).get(0).getName())) {

                    ArrayList<Venue> org = new ArrayList<Venue>();
                    org.add(venue);
                    venues.add(org);
                }
                else{
                    venues.get(venues.size()-1).add(venue);
                }
            }
        }
        return venues;
    }

    public static ArrayList<ArrayList <Venue>> returnVenuesArea(String area) {
        //returns the list of all venues from the specified area
        ArrayList<ArrayList <Venue>> venues = new ArrayList<ArrayList <Venue>>();
        if(venueList != null) {
            //int count = 0;
            for (Venue venue : venueList) {
                if (venue.getArea().equals(area)) {
                    if (venues.size() == 0 ||
                            !venue.getName().equals(venues.get(venues.size() - 1).get(0).getName())) {

                        ArrayList<Venue> org = new ArrayList<Venue>();
                        org.add(venue);
                        venues.add(org);
                    } else {
                        venues.get(venues.size() - 1).add(venue);
                    }
                }
            }
        }
        return venues;
    }

    public static ArrayList<String> returnVenueIds() {
        ArrayList<String> venueIds = new ArrayList<String>();
        if (venueList != null) {
            for (ParseObject venue : venueInfoList) {
                venueIds.add(venue.getObjectId());
            }
        }
        return venueIds;
    }

    public static ArrayList<String> returnVenueIds(String area) {
        ArrayList<String> venueIds = new ArrayList<String>();
        if(venueList != null) {
            for (ParseObject venue : venueInfoList) {
                if (venue.get("Area").equals(area)) {
                    venueIds.add(venue.getObjectId());
                }
            }
        }
        return venueIds;
    }

    public static String getVenueName(String venueID){
        String venueName = null;
        for(ParseObject venue : venueInfoList){
            if(venue.getObjectId().equals(venueID)) {
                venueName = venue.get("barName").toString();
            }
        }
        return venueName;
    }
}
