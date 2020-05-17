package com.example.tripplanner.Models.HomeModel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.tripplanner.POJOs.Trip;
import com.example.tripplanner.Views.HomeView.MainActivity;
import com.example.tripplanner.Views.TripView.ReminderBroadcast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeModel implements HomeContract.IModel {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("trips");
    ArrayList<Trip>trips=new ArrayList<>();
    MainActivity view;
    String user;
    boolean firstLogin;
    Calendar calendar;
    public HomeModel(final MainActivity view, final String user, final boolean firstLogin){
        this.view=view;
        this.user =user;
        this.firstLogin=firstLogin;
        calendar = Calendar.getInstance();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                trips.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Trip curTrip=snapshot.getValue(Trip.class);

                    if(curTrip.getStatus().equals("Upcoming")&&(curTrip.getUser().equals(user))){
                        trips.add(curTrip);
                        if(firstLogin){
                            //add to calender
                            startAlarm(calendar,curTrip);
                        }
                    }
                    HomeModel.this.view.arrayAdapter.notifyDataSetChanged();
                }
                // we must notify that we have loaded all trips coz it is running in a different thread, so we can see
                // trips on after activity loaded not else
                HomeModel.this.view.arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText((Context) HomeModel.this.view,"err",Toast.LENGTH_SHORT).show();
            }
        }
        );
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               trips.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Trip curTrip=snapshot.getValue(Trip.class);
                    if(curTrip.getStatus().equals("Upcoming")&&(curTrip.getUser().equals(user)))
                        trips.add(curTrip);
                    HomeModel.this.view.arrayAdapter.notifyDataSetChanged();
                }
                // we must notify that we have loaded all trips coz it is running in a different thread, so we can see
                // trips on after activity loaded not else
                HomeModel.this.view.arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public ArrayList<Trip> getUpcomings(String user){
        this.user=user;
        return trips;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c, Trip curTrip) {

        AlarmManager alarmang = (AlarmManager) view.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(view, ReminderBroadcast.class);
        intent.putExtra("id",curTrip.getId());
        intent.putExtra("reqCode",curTrip.getRequestCode()+"");
        intent.putExtra("endPoint",curTrip.getEndPlaceName());
        intent.putExtra("lati",curTrip.getEndLatitude()+"");
        intent.putExtra("long",curTrip.getEndLongtude()+"");
        intent.putExtra("notes",curTrip.getNotes());

        c.set(Calendar.YEAR , curTrip.getYear());
        c.set(Calendar.MONTH , curTrip.getMonth());
        c.set(Calendar.DAY_OF_MONTH , curTrip.getDayOfMonth());
        c.set(Calendar.MINUTE , curTrip.getMinute());
        c.set(Calendar.HOUR_OF_DAY , curTrip.getHourOfDay());
        c.set(Calendar.SECOND , 0);

        PendingIntent pi =  PendingIntent.getBroadcast(view , curTrip.getRequestCode(), intent , 0);
        alarmang.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pi);

    }
}
