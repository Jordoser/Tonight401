package com.example.tonight;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScreenName {
    static String filename = "screenName";
    static String name = "anonymous";

    public static void save(Context context, String string) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename, context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void load(Context context) {
        FileInputStream fis;
        try {
            fis = context.openFileInput(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    fis));
            name = in.readLine();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void alert(final Context context){
        load(context);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle("Current Name: "+name);
        alert.setMessage("Change Name: ");

        final EditText input = new EditText(context);
        alert.setView(input);

        alert.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                save(context,value);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                save(context,name);
            }
        });

        alert.show();
    }

}
