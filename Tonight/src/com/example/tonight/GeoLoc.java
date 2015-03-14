/**
 * Created by Jordan on 13/03/2015.
 */
package com.example.tonight;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

public class GeoLoc implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5641804950085965912L;

    // this ArrayList was considered a God class
    // this method was not extracted into another class, as the code is very small
    public ArrayList<Double> Location = new ArrayList();


    // this Context was considered a God class
    // this method was not extracted into another class, as the code is very small
    public Context currentContext;

    public GeoLoc(Context activityContext) {
        currentContext = activityContext;
    }

    /**
     * Gets location based on the last known values of latitude and longitude*
     */
    // this was method was considered a God class
    // this method was not extracted into another class, as it basically makes
    // up the entire GeoLoc class
    public ArrayList<Double> findLocation() throws IOException {
        LocationManager locMan = (LocationManager) currentContext.getSystemService(Context.LOCATION_SERVICE);
        String provider = LocationManager.NETWORK_PROVIDER;
        Location lastKnownLoc = locMan.getLastKnownLocation(provider);
        if (lastKnownLoc != null) {
            double lat = lastKnownLoc.getLatitude();
            double lon = lastKnownLoc.getLongitude();
            Location.add(lat);
            Location.add(lon);


        } else {
            double err = 0;
            Location.add(err);
            Location.add(err);
        }
        return Location;
    }

}