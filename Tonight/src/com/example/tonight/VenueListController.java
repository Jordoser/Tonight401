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
    public static ArrayList<ParseObject> venueList;
    public static ArrayList<Bitmap> logos;

    public static void setVenueList(ArrayList<ParseObject> vList, ArrayList<Bitmap> vImgs){
        if(venueList != null){venueList.clear();}
        venueList = vList;
        logos = vImgs;
        System.out.println(venueList.size());
    }

    public static ArrayList<ParseObject> getVenueList(){
        return venueList;
    }

    public static ArrayList<String> returnVenues() {
        //returns the list of all venues
        ArrayList<String> venues = new ArrayList<String>();
        if( venueList != null) {
            int count = 0;
            for (ParseObject venue : venueList) {
                if (logos.get(count) == null) {
                    venues.add(venue.get("barName").toString());
                }
                else{venues.add(null);}
                count++;
            }
        }else{}
        return venues;
    }

    public static ArrayList<String> returnVenues(String area) {
        //returns the list of all venues from the specified area
        ArrayList<String> venues = new ArrayList<String>();
        if(venueList != null) {
            int count = 0;
            for (ParseObject venue : venueList) {
                if (venue.get("Area").equals(area)) {
                    if (logos.get(count) == null) {
                        venues.add(venue.get("barName").toString());
                    }
                    else{venues.add(null);}
                }
                count++;
            }

        }
        return venues;
    }

    public static ArrayList<String> returnVenueIds() {
        ArrayList<String> venueIds = new ArrayList<String>();
        if (venueList != null) {
            for (ParseObject venue : venueList) {
                venueIds.add(venue.getObjectId());
            }
        }
        return venueIds;
    }

    public static ArrayList<String> returnVenueIds(String area) {
        ArrayList<String> venueIds = new ArrayList<String>();
        if(venueList != null) {
            for (ParseObject venue : venueList) {
                if (venue.get("Area").equals(area)) {
                    venueIds.add(venue.getObjectId());
                }
            }
        }
        return venueIds;
    }

    public static ArrayList<Bitmap> getVenueLogos() {
        ArrayList<Bitmap> areaLogos = new ArrayList<Bitmap>();
        int count = 0;
        if(venueList != null) {
            for (ParseObject venue : venueList) {
                areaLogos.add(logos.get(count));
                count++;
            }
        }
        return areaLogos;
    }

    public static ArrayList<Bitmap> getVenueLogos(String area) {
        ArrayList<Bitmap> areaLogos = new ArrayList<Bitmap>();
        int count = 0;
        for(ParseObject venue:venueList){
            if(venue.get("Area").equals(area)) {
                areaLogos.add(logos.get(count));
            }
            count++;
        }
        return areaLogos;
    }

    public static String getVenueName(String venueID){
        String venueName = null;
        for(ParseObject venue:venueList){
            if(venue.getObjectId().equals(venueID)) {
                venueName = venue.get("barName").toString();
            }
        }
        return venueName;
    }
}
