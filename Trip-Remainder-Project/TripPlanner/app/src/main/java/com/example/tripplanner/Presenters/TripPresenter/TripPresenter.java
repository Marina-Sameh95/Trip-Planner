package com.example.tripplanner.Presenters.TripPresenter;

import com.example.tripplanner.Models.TripModel.TripContract;
import com.example.tripplanner.Models.TripModel.TripModel;
import com.example.tripplanner.POJOs.Trip;
import com.example.tripplanner.Views.HomeView.MainActivity;
import com.example.tripplanner.Views.TripView.TripActivity;

public class TripPresenter implements TripContract.IPresenter {
    TripContract.IView view;
    TripContract.IModel model;
    public TripPresenter(TripContract.IView  view){
        this.view=view;
        model=new TripModel((TripActivity) this.view);
    }
    public void addNewTrip(Trip trip){
        // add it to the firebase
        model.addNewTrip(trip);
        view.addedNewTrip();
    }

    @Override
    public void updateTrip(Trip trip) {
        model.updateTrip(trip);
    }
    public int getRequestCode(){
        return model.getRequestCode();
    }
}
