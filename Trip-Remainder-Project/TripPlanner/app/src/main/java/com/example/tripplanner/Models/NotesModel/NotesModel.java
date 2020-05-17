package com.example.tripplanner.Models.NotesModel;

import androidx.annotation.NonNull;

import com.example.tripplanner.POJOs.Trip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotesModel implements NotesContract.IModel {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("trips");
    Trip trip;
    public NotesModel(){

    }
    @Override
    public void updateTrip(Trip trip) {
        myRef.child(trip.getId()).setValue(trip);
    }

}
