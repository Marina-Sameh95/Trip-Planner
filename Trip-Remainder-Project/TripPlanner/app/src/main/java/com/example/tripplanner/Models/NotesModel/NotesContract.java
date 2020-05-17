package com.example.tripplanner.Models.NotesModel;

import com.example.tripplanner.POJOs.Trip;

public class NotesContract {
    public  interface IView{
    }

    public interface IPresenter{
     public void handleNotesChange(Trip trip);
     public void addNewNote(String note);
    }

    public interface IModel {
        public void updateTrip(Trip trip);
    }
}
