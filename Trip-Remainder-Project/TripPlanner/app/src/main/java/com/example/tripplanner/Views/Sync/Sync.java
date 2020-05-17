package com.example.tripplanner.Views.Sync;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Sync extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
