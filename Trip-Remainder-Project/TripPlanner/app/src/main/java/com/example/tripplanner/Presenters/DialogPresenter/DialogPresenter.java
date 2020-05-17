package com.example.tripplanner.Presenters.DialogPresenter;

import com.example.tripplanner.Models.DialogModel.DialogContract;
import com.example.tripplanner.Models.DialogModel.DialogModel;
import com.example.tripplanner.POJOs.Trip;

public class DialogPresenter implements DialogContract.IPresenter {
    DialogContract.IModel model;
    DialogContract.IView view;

    public DialogPresenter(DialogContract.IView view){
        this.view=view;
        this.model=new DialogModel();
    }

    @Override
    public void handleDoneTrip(String trip) {
        model.addDoneTrip(trip);
    }
}
