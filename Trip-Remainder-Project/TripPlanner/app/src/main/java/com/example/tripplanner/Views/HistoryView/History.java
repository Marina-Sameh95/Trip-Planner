package com.example.tripplanner.Views.HistoryView;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplanner.Adapters.HistoryAdapter;
import com.example.tripplanner.Models.HistoryModel.HistoryContract;
import com.example.tripplanner.POJOs.Trip;
import com.example.tripplanner.Presenters.HistoryPresenter.HistoryPresenter;
import com.example.tripplanner.R;
import com.example.tripplanner.Views.TripDetails.TripDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class History extends AppCompatActivity implements HistoryContract.IView, HistoryAdapter.OnHistoryListener {

    RecyclerView recyclerView;
    public HistoryAdapter arrayAdapter;
    RecyclerView.LayoutManager recyce;
    public ArrayList<Trip> trips=new ArrayList<>();
    HistoryPresenter historyPresenter;
    String user="";
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        reference = FirebaseDatabase.getInstance().getReference("trips");
        reference.keepSynced(true);

        user=getIntent().getExtras().getString("user");

        historyPresenter=new HistoryPresenter(this,user);
        historyPresenter.handleHistory(user);

        recyclerView=findViewById(R.id.historyRecyclerView);
        recyce = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(recyce);
        arrayAdapter=new HistoryAdapter(this,R.layout.history_row ,R.id.tripIdHist,trips,  this);
        recyclerView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        historyPresenter.handleHistory();
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void renderHistory(ArrayList<Trip> trips) {
        this.trips=trips;
    }



    @Override
    public void onHistoryClick(int position) {
        Intent intent=new Intent(History.this, TripDetails.class);
        intent.putExtra("curTrip",trips.get(position));
        startActivity(intent);
    }
}
