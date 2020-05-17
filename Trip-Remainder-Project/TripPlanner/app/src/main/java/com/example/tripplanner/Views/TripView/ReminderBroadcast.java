package com.example.tripplanner.Views.TripView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.tripplanner.POJOs.Trip;

public class ReminderBroadcast extends BroadcastReceiver {
    Trip trip=new Trip();
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentD = new Intent(context, Dialog.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentD.putExtra("id",intent.getStringExtra("id"));
        intentD.putExtra("reqCode",intent.getStringExtra("reqCode"));
        intentD.putExtra("endPoint",intent.getStringExtra("endPoint"));
        intentD.putExtra("lati",intent.getStringExtra("lati"));
        intentD.putExtra("long",intent.getStringExtra("long"));
        context.startActivity(intentD);

    }

}
