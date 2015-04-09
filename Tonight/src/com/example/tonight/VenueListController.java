package com.example.tonight;

import android.graphics.Bitmap;

import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * This class holds all the venues as well as the venue ParseObjects once the MainActivity has been
 * completely loaded.
 *
 * @author Group 8: CMPUT 401
 */
public class VenueListController {
    public static ArrayList<ParseObject> venueInfoList;
    public static ArrayList<Venue> venueList;

    /**
     *
     * @param vInfoList
     * @param vList
     */
    public static void setVenueList(ArrayList<ParseObject> vInfoList, ArrayList<Venue> vList){
        if(venueList != null){venueList.clear();}
        venueInfoList = vInfoList;
        venueList = vList;
        System.out.println(venueList.size());
    }

    /**
     * Returns the list of venues, organized in parents and children so that the drawer can
     * properly display them.
     *
     * @return The formatted list of Venues
     */
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

    /**
     * Returns the list of venues, organized in parents and children so that the drawer can
     * properly display them for the specified Area.
     *
     * @param area  The area to retrieve the Venues from
     * @return      The formatted list of Venues for the area
     */
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

    /**
     * Returns the list of IDs for each Venue in alphabetical order
     *
     * @return the list of IDs for the venue
     */
    public static ArrayList<String> returnVenueIds() {
        ArrayList<String> venueIds = new ArrayList<String>();
        if (venueList != null) {
            for (ParseObject venue : venueInfoList) {
                venueIds.add(venue.getObjectId());
            }
        }
        return venueIds;
    }

    /**
     * Returns the list of IDs for each Venue in the area in alphabetical order
     *
     * @param area  The area to retrieve the Venue IDs from
     * @return      The list of IDs for the venue
     */
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

    /**
     * Returns the venue name for the specified Venue ID
     *
     * @param venueID   The ID of the venue to want to retrieve the name of
     * @return          The name of the Venue
     */
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
