package com.example.tripplanner.Models.DialogModel;

import com.example.tripplanner.POJOs.Trip;

public class DialogContract {
    public interface IView{
    }
    public interface IModel{
        public void addDoneTrip(String trip);
    }
    public interface IPresenter{
        public void handleDoneTrip(String trip);
    }
}
