package com.example.tripplanner.Presenters.NotesPresenter;

import com.example.tripplanner.Models.NotesModel.NotesContract;
import com.example.tripplanner.Models.NotesModel.NotesModel;
import com.example.tripplanner.POJOs.Trip;
import com.example.tripplanner.Views.NotesView.NotesActivity;

public class NotesPresenter implements NotesContract.IPresenter {
    NotesActivity view;
    NotesContract.IModel model;
    public NotesPresenter(NotesContract.IView view){
        this.model=new NotesModel();
        this.view= (NotesActivity) view;
    }
    @Override
    public void handleNotesChange(Trip trip) {
            model.updateTrip(trip);
    }

    @Override
    public void addNewNote(String note) {
        if(!note.equals("")){
            view.trip.getNotes().add(note);
            view.arrayAdapter.notifyDataSetChanged();
            view.noteTextView.setText("");
        }
    }
}
