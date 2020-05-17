package com.example.tripplanner.Models.TripModel;

import com.example.tripplanner.POJOs.Trip;

public class TripContract {
    public interface IView{
        public void addedNewTrip();
    }
    public interface IModel{
        public void addNewTrip(Trip trip);
        public void updateTrip(Trip trip);
        public int getRequestCode();
    }
    public interface IPresenter{
        public void addNewTrip(Trip trip);
        public void updateTrip(Trip trip);
        public int getRequestCode();
    }
}
