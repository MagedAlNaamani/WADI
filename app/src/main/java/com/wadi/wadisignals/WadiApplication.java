package com.wadi.wadisignals;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

/**
 * Created by magedalnaamani on 11/15/15.
 */
public class WadiApplication extends Application

{

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this, "oI8Za40YuSeUPItHeUozG2zwsxW6qHv90CCOZbMM", "ChBccl3eOJo79ROCMmiKtNRBYcvqZDKh92CDYGnl");

         /**
          * NEEDED TO REIGSTER THE DEVICE IN PARSE INSTALLTIONS FOR PUSH NOTIFICATOIN
          * */
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseUser.enableAutomaticUser();

//    ParseACL defaultACL = new ParseACL();
//    // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);

    }
}