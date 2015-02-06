package com.example.tonight;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by David on 2015-02-03.
 */
@ParseClassName("VenueModel")
public class VenueModel extends ParseObject {
    String name;
    String address;
    String area;


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

}
