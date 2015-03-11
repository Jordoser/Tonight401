package com.example.tonight;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarek on 2015-02-10.
 */
public class ParseOperations extends ParseObject {
    public static ArrayList<ParseObject> listVenues= new ArrayList<ParseObject>();
    public static String venue_name = new String();
    public static String venueInfo = new String();
    public static ArrayList<String> hoursList= new ArrayList<String>();
    public static ArrayList<String> specList= new ArrayList<String>();
    public static ArrayList<Bitmap> logos = new ArrayList<Bitmap>();


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

                        ParseFile imgFile = (ParseFile)venue.get("barLogo");
                        if(imgFile != null){
                            try {
                                byte[] data = imgFile.getData();
                                Bitmap bMap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                logos.add(bMap);
                            } catch (ParseException e2) {
                                // TODO Auto-generated catch block
                                e2.printStackTrace();
                            }
                        }
                        else{logos.add(null);}
                    }
                    VenueListController.setVenueList(listVenues, logos);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }

            }
        });

    }

    public static void getName(String id) {
        venue_name = new String();
        hoursList= new ArrayList<String>();
        specList = new ArrayList<String>();
        venueInfo = new String();
        VenueHolder.setBarID(id);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Venue");
        query.whereEqualTo("objectId", id);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> venueList2, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + venueList2.size() + " bars");
                    for (ParseObject venue : venueList2) {
                        System.out.println(venue.get("barName").toString());
                        venue_name = venue.get("barName").toString();

                        if (venue.get("Info") != null) {
                            venueInfo = venue.get("Info").toString();
                        } else {
                            venueInfo = "No Info";
                        }
                        if (venue.get("SunHours") != null) {
                            hoursList.add(venue.get("SunHours").toString());
                        } else {
                            hoursList.add("Closed");
                        }
                        if (venue.get("MonHours") != null) {
                            hoursList.add(venue.get("MonHours").toString());
                        } else {
                            hoursList.add("Closed");
                        }
                        if (venue.get("TuesHours") != null) {
                            hoursList.add(venue.get("TuesHours").toString());
                        } else {
                            hoursList.add("Closed");
                        }
                        if (venue.get("WedHours") != null) {
                            hoursList.add(venue.get("WedHours").toString());
                        } else {
                            hoursList.add("Closed");
                        }
                        if (venue.get("ThursHours") != null) {
                            hoursList.add(venue.get("ThursHours").toString());
                        } else {
                            hoursList.add("Closed");
                        }
                        if (venue.get("FriHours") != null) {
                            hoursList.add(venue.get("FriHours").toString());
                        } else {
                            hoursList.add("Closed");
                        }
                        if (venue.get("SatHours") != null) {
                            hoursList.add(venue.get("SatHours").toString());
                        } else {
                            hoursList.add("Closed");
                        }



                        if (venue.get("SunSpec") != null) {
                            specList.add(venue.get("SunSpec").toString());
                        } else {
                            specList.add("None");
                        }
                        if (venue.get("MonSpec") != null) {
                            specList.add(venue.get("MonSpec").toString());
                        } else {
                            specList.add("None");
                        }
                        if (venue.get("TuesSpec") != null) {
                            specList.add(venue.get("TuesSpec").toString());
                        } else {
                            specList.add("None");
                        }
                        if (venue.get("WedSpec") != null) {
                            specList.add(venue.get("WedSpec").toString());
                        } else {
                            specList.add("None");
                        }
                        if (venue.get("ThursSpec") != null) {
                            specList.add(venue.get("ThursSpec").toString());
                        } else {
                            specList.add("None");
                        }
                        if (venue.get("FriSpec") != null) {
                            specList.add(venue.get("FriSpec").toString());
                        } else {
                            specList.add("None");
                        }
                        if (venue.get("SatSpec") != null) {
                            specList.add(venue.get("SatSpec").toString());
                        } else {
                            specList.add("None");
                            Log.d("score", "specials added");
                        }

                    }
                    VenueHolder.setListSpecials(specList);
                    VenueHolder.setBarName(venue_name);
                    VenueHolder.setListHours(hoursList);
                    VenueHolder.setInfo(venueInfo);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                    //listVenues.add("Error");
                }
            }
        });
}

    public static void addComment(String barID, String comment, String username){
        ParseObject commentObject = new ParseObject("Comments");
        Boolean saved;
        commentObject.put("barId", barID);
        commentObject.put("commentText", comment);
        commentObject.put("user", username);
        commentObject.put("likes", 0);
        commentObject.put("dislikes", 0);
        //commentObject.saveInBackground(new SaveCallback() {
        //   public void done(ParseException e) {
        //        if(e == null){
        //            Log.d("score", "Comment was uploaded to Parse");
        //        }
        //        else {
        //            Log.d("score", "Error: " + e.getMessage());
        //            //listVenues.add("Error");
        //        }
        //    }
        //});
        try {
            commentObject.save();
            Log.d("score", "Comment was uploaded to Parse");
        } catch (ParseException e) {
            Log.d("score", "Error: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    //public static ArrayList<String> returnList(){
    //    Log.d("score", "Retrieved " + listVenues.size() + " Foster");
    //    return listVenues;
    //}
}
