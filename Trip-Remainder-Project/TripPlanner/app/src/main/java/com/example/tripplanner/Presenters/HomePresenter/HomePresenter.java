package com.example.tripplanner.Presenters.HomePresenter;

import com.example.tripplanner.Models.HomeModel.HomeContract;
import com.example.tripplanner.Models.HomeModel.HomeModel;
import com.example.tripplanner.POJOs.Trip;
import com.example.tripplanner.Views.HomeView.MainActivity;

import java.util.ArrayList;

public class HomePresenter implements HomeContract.IPresenter {
    HomeContract.IView view;
    HomeContract.IModel model;
    String user;
    boolean firstLogin;
    public HomePresenter(HomeContract.IView view,String user,boolean firstLogin){
       this.view=view;
       this.user=user;
       this.firstLogin=firstLogin;               //newwwwwwwwwwww remeber to do this once [logout]
       model=new HomeModel((MainActivity) view,user,firstLogin);
    }

    @Override
    public void handleUpcomings(String user) {
        ArrayList<Trip> trips=model.getUpcomings(user);
        view.renderUpcomings(trips);
    }
}
