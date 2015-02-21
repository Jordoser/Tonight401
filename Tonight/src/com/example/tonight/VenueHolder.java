package com.example.tonight;

import java.util.ArrayList;

/**
 * Created by Jordan on 11/02/2015.
 */
public class VenueHolder {

    public static String barID = new String();
    public static String barName = new String();
    public static String info = new String();
    public static ArrayList<String> listHours = new ArrayList<String>();
    public static ArrayList<String> listSpecials = new ArrayList<String>();
    public static byte[] barPhoto;

    public static String getBarID() {
        return barID;
    }

    public static void setBarID(String barID) {
        VenueHolder.barID = barID;
    }

    public static String getBarName() {
        return barName;
    }

    public static void setBarName(String barName) {
        VenueHolder.barName = barName;
    }

    public static String getInfo() {
        return info;
    }

    public static void setInfo(String info) {
        VenueHolder.info = info;
    }

    public static ArrayList<String> getListHours() {
        return listHours;
    }

    public static void setListHours(ArrayList<String> listHours) {
        VenueHolder.listHours = listHours;
    }

    public static ArrayList<String> getListSpecials() {
        return listSpecials;
    }

    public static void setListSpecials(ArrayList<String> listSpecials) {
        VenueHolder.listSpecials = listSpecials;
    }

    public static byte[] getBarPhoto() {
        return barPhoto;
    }

    public static void setBarPhoto(byte[] barPhoto) {
        VenueHolder.barPhoto = barPhoto;
    }
}