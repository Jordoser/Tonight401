package com.example.tonight;

import android.graphics.Bitmap;

/**
 * This class represents a Venue in our app. This class holds all of the information that the
 * expandable drawer requires to display properly for each item in the drawer.
 *
 * @author Group 8: CMPUT 401
 */
public class Venue {
    public String name;
    public String ID;
    public String area;
    public String address;
    public Bitmap logo;

    /**
     * Returns the name of the Venue
     * @return the name of the Venue
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Venue
     * @param name The name of the Venue
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the unique ID of the Venue
     * @return the ID of the Venue
     */
    public String getID() {
        return ID;
    }


    /**
     * Sets the unique ID of the Venue
     * @param ID The parse ID of the Venue
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Returns the address of the Venue
     * @return the address of the Venue
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the Venue
     * @param address The address of the Venue
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the area of the Venue
     * @return the area of the Venue
     */
    public String getArea() {
        return area;
    }

    /**
     * Sets the area of the Venue
     * @param area The area of the Venue
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * Returns the Bitmap logo of the Venue
     * @return the Bitmap logo of the Venue
     */
    public Bitmap getLogo() {
        return logo;
    }

    /**
     * Sets the Bitmap logo of the Venue
     * @param logo The Bitmap logo of the Venue
     */
    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

}
