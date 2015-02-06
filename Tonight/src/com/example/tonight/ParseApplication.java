package com.example.tonight;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(this, "CbiQV5jXE0OQaWM6NI9gEWKwMh30pjkTpPhhmZ7R", "AuECXeGSmupEsy4hY0C5hZA2BYkpw2gOzrCapm06");


    ParseUser.enableAutomaticUser();
    //ParseACL defaultACL = new ParseACL();

    // Optionally enable public read access.
    // defaultACL.setPublicReadAccess(true);
    //ParseACL.setDefaultACL(defaultACL, true);
  }
}
