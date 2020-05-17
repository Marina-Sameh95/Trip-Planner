package com.example.tripplanner.Presenters.HistoryPresenter;

import com.example.tripplanner.Models.HistoryModel.HistoryContract;
import com.example.tripplanner.Models.HistoryModel.HistoryModel;
import com.example.tripplanner.POJOs.Trip;
import com.example.tripplanner.Views.HistoryView.History;

import java.util.ArrayList;

public class HistoryPresenter implements HistoryContract.IPresenter {

    HistoryContract.IView view;
    HistoryContract.IModel model;
    String user;

    public HistoryPresenter(HistoryContract.IView view,String user){
        this.view=view;
        this.user=user;
        this.model=new HistoryModel((History) view,user);
    }

    @Override
    public void handleHistory(String user) {
        ArrayList<Trip> trips=model.getHistory(user);
        view.renderHistory(trips);
    }
}
