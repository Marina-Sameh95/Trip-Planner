package com.example.tripplanner.Views.TripView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.tripplanner.R;
public class ForegroundService extends Service {
    //MediaPlayer media;

    MediaPlayer media;
    public static String CHANNEL_ID = "ServiceChannel";
    @Override
    public void onCreate() {
        super.onCreate();
        //  media = MediaPlayer.create(this, R.raw.cool);
        // media.setLooping(true); // Set looping
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createChannel();
        else
            startForeground(1, new Notification());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
   //         media.start();

        //    media.start();
        String reqCode=intent.getStringExtra("reqCode");
        int requestCode=Integer.parseInt(reqCode);
        Intent notifiIntent = new Intent(getApplicationContext(), Dialog.class);
        PendingIntent pending =  PendingIntent.getActivity(getApplicationContext(), requestCode, notifiIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setContentTitle("Notification")
                .setContentText("Take care you'll miss your trip")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pending)
                .build();
        startForeground(1, notification);

        return START_NOT_STICKY;
    }
    public void createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(

                    CHANNEL_ID,
                    "Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT

            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       //   media.stop();
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
