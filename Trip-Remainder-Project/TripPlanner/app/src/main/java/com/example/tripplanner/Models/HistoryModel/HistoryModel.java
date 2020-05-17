package com.example.tripplanner.Models.HistoryModel;

import android.content.Context;
import android.widget.Toast;

import com.example.tripplanner.POJOs.Trip;
import com.example.tripplanner.Views.HistoryView.History;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryModel implements HistoryContract.IModel {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("trips");
    ArrayList<Trip>trips=new ArrayList<>();
    History view;
    String user;
    public HistoryModel(final History view,final String user){
//        myRef.child("test").setValue("test");
        this.view=view;
        this.user=user;
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                trips.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Trip curTrip=snapshot.getValue(Trip.class);

//                    Iterable<DataSnapshot> dateTime = snapshot.getChildren();
//                    String key = dateTime.iterator().next().getKey();
//                    Trip curTrip=snapshot.child(key).getValue(Trip.class);
                    if((!curTrip.getStatus().equals("Upcoming"))&&(!curTrip.getStatus().equals("Deleted"))&&(curTrip.getUser().equals(user))){
                        trips.add(curTrip);
                        HistoryModel.this.view.arrayAdapter.notifyDataSetChanged();
                    }
                }
                // we must notify that we have loaded all trips coz it is running in a different thread, so we can see
                // trips on after activity loaded not else
                HistoryModel.this.view.arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText((Context) HistoryModel.this.view,"err",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public ArrayList<Trip> getHistory(String user) {
        this.user=user;
        return trips;
    }
}
