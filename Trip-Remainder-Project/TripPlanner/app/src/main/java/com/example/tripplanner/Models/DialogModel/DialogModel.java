package com.example.tripplanner.Models.DialogModel;

import com.example.tripplanner.POJOs.Trip;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogModel implements DialogContract.IModel {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("trips");
    @Override
    public void addDoneTrip(String id) {
        myRef.child(id).child("status").setValue("Done");
    }
}
