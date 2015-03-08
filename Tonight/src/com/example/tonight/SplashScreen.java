package com.example.tonight;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;


public class SplashScreen extends ActionBarActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        setContentView(R.layout.activity_splash);
        ParseOperations.getVenues();

        if (isNetworkAvailable(this)) {

        } else {
            Toast toast = Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }


        new Handler().postDelayed(new Runnable() {
            int test = 0;
                                      @Override
                                      public void run() {
                                          //Do stuff after the SPLASH_DISPLAY LENGTH here
                                              Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                              startActivity(main);

                                              finish();

                                      }
                                  }, SPLASH_DISPLAY_LENGTH);



    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

}
