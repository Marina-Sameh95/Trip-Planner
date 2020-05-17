package com.example.tripplanner.Models.HistoryModel;

import com.example.tripplanner.POJOs.Trip;

import java.util.ArrayList;

public class HistoryContract {
    public interface IView{
        public void renderHistory(ArrayList<Trip> trips);
    }
    public interface IModel{
        public ArrayList<Trip> getHistory(String user);
    }
    public interface IPresenter{
        public void handleHistory(String user);
    }
}
