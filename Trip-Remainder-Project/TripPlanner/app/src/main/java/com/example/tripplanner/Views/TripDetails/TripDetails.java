package com.example.tripplanner.Views.TripDetails;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripplanner.POJOs.Trip;
import com.example.tripplanner.R;

import java.util.ArrayList;

public class TripDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        Intent intent=getIntent();
        Trip curTrip= (Trip) intent.getExtras().getSerializable("curTrip");
        TextView status=findViewById(R.id.tripDetailStatus);
        TextView name=findViewById(R.id.tripDetailName);
        TextView start=findViewById(R.id.tripDetailStart);
        TextView end=findViewById(R.id.tripDetailEnd);
        TextView date=findViewById(R.id.tripDetailDate);
        TextView time=findViewById(R.id.tripDetailTime);
        TextView type=findViewById(R.id.tripDetailType);
        TextView notes=findViewById(R.id.tripDetailNotes);

        status.setText(curTrip.getStatus());
        name.setText(curTrip.getTripName());
        start.setText(curTrip.getStartPlaceName());
        end.setText(curTrip.getEndPlaceName());
        date.setText(curTrip.getTripDate());
        time.setText(curTrip.getTripTime());
        type.setText(curTrip.getTripType());
        String allNotes="";
        ArrayList<String>li=curTrip.getNotes();
        for(int i=0;i<li.size();i++){
            allNotes+=(li.get(i)+", ");
        }
        notes.setText(allNotes);
    }
}
