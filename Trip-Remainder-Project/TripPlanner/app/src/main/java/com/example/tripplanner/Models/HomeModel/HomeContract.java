package com.example.tripplanner.Models.HomeModel;

import com.example.tripplanner.POJOs.Trip;

import java.util.ArrayList;

public class HomeContract {
    public interface IView{
        public void renderUpcomings(ArrayList<Trip> trips);
    }
    public interface IModel{
        public ArrayList<Trip> getUpcomings(String user);
    }
    public interface IPresenter{
        public void handleUpcomings(String user);
    }
}
