package com.example.tripplanner.Views.HomeView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplanner.Adapters.TripAdapter;
import com.example.tripplanner.Models.HomeModel.HomeContract;
import com.example.tripplanner.POJOs.Trip;
import com.example.tripplanner.Presenters.HomePresenter.HomePresenter;
import com.example.tripplanner.R;
import com.example.tripplanner.Views.HistoryView.History;
import com.example.tripplanner.Views.Login.Login;
import com.example.tripplanner.Views.TripDetails.TripDetails;
import com.example.tripplanner.Views.TripView.TripActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HomeContract.IView, TripAdapter.OnTripListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    HomePresenter homePresenter;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    public TripAdapter arrayAdapter;
    RecyclerView.LayoutManager recyce;
    ArrayList<Trip> trips=new ArrayList<>();
    String user="";
    DatabaseReference reference;
    boolean firstLoginFlag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user=getIntent().getExtras().getString("user");
        Log.i("nasor",user);
         //new                   [hhhhhhhhhhhhhhhhhhhhrrrrrrrrrrrrreeeeeeeeeeeee][
        reference = FirebaseDatabase.getInstance().getReference("trips");
        reference.keepSynced(true);

        // if coming from login add the trips to remainder loop over all of them add to remainder only. after they come from firebase
        // search for await in android coz it must be after you have got all trips
        // momkn in single event in model


        // here is presenter handling
        firstLoginFlag=getIntent().getExtras().getBoolean("firstLogin");;
        homePresenter=new HomePresenter(this,user,firstLoginFlag);
        homePresenter.handleUpcomings(user);


        Toast.makeText(this,"Welcome "+user,Toast.LENGTH_SHORT).show();


        Toolbar toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        NavigationView navigationView=findViewById(R.id.nav_drawer);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nav_header_textView);
        navUsername.setText(user);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.upcomings){
                    menuItem.setCheckable(true);
                    menuItem.setChecked(true);
                }else if(id == R.id.history){
                    menuItem.setCheckable(true);
                    menuItem.setChecked(true);
                    Intent intent=new Intent(getApplicationContext(), History.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }else if(id==R.id.logout){
                    menuItem.setCheckable(true);
                    menuItem.setChecked(true);
                    Toast.makeText(getApplicationContext(),"logout",Toast.LENGTH_SHORT).show();
                    auth.getInstance().signOut();
                    finish();
                    SharedPreferences sharedPreferences = getSharedPreferences("MyLogin.txt", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("FirstLogin", false);
                    editor.commit();
                    Intent intent=new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
        recyclerView=findViewById(R.id.recyclerView);
        recyce = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(recyce);
        arrayAdapter=new TripAdapter(this,R.layout.trip_row ,R.id.tripIdHist,trips,this);
        recyclerView.setAdapter(arrayAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // only add the upcoming trips, if the status is upcoming
        homePresenter.handleUpcomings(user);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void renderUpcomings(ArrayList<Trip> trips) {
        // render here the trips on the recycler listView
        this.trips=trips;

    }

    public void addNewTrip(View view) {
        Intent intent=new Intent(this, TripActivity.class);
        intent.putExtra("purpose","newTrip");
        intent.putExtra("user",user);
        startActivity(intent);
    }

    @Override
    public void onTripClick(int position) {
        // here we navigate to details of current Trip
        Intent intent=new Intent(MainActivity.this, TripDetails.class);
        intent.putExtra("curTrip",trips.get(position));
        startActivity(intent);
    }
}
