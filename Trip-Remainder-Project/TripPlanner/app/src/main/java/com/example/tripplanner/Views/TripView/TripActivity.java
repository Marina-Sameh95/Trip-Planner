package com.example.tripplanner.Views.TripView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.example.tripplanner.Models.TripModel.TripContract;
import com.example.tripplanner.POJOs.Trip;
import com.example.tripplanner.Presenters.TripPresenter.TripPresenter;
import com.example.tripplanner.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class TripActivity extends AppCompatActivity implements TripContract.IView , DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {

    Button cal;
    Button time;
    TextView calDate;
    TextView timeTxt;
    Calendar calendar;
    int year;
    int month;
    int dayOfMonth;
    int minute;
    int hourOfDay;
    PlacesClient placesClient;
    TripPresenter tripPresenter;
    Switch type;
    AutocompleteSupportFragment autocompleteFragment;
    AutocompleteSupportFragment autocompleteFragment2;
    EditText tripName;
    TextView startPoint;
    TextView endPoint;
    String toggleCheck="oneWay";
    double endLatitude;        //new
    double endLongtude;         //new
    double startLatitude;       //new
    double startLongtude;       //new
    String thePlaceId;          //new
    String startPlaceName;     //new
    String endPlaceName;       //new
    String purpose="";
    Trip trip;
    int reqCode = 0;
    String user="";
    Button addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Add New Trip");
        tripPresenter=new TripPresenter(this);


        //ids
        addBtn=findViewById(R.id.addTripBtn);
        type=findViewById(R.id.switch1);
        tripName=findViewById(R.id.tripName);
        startPoint=findViewById(R.id.startPoint);   startPoint.setText("Baltim");   //for testing only
        endPoint=findViewById(R.id.endPoint);       endPoint.setText("Alexandria"); // for testing only
        calDate = findViewById(R.id.calDate);
        time = findViewById(R.id.time);
        timeTxt = findViewById(R.id.timeText);
        cal = findViewById(R.id.calendar);
        time.setBackgroundResource(R.drawable.ala);
        cal.setBackgroundResource(R.drawable.calendar7);


        // to check if am coming from add new trip or edit a trip
        Intent intent =getIntent();
        purpose=intent.getExtras().getString("purpose");

        //[here]
        if(purpose.equals("editTrip")) {
            trip = (Trip) intent.getExtras().getSerializable("curTrip");
            getSupportActionBar().setTitle("Edit Trip");
            addBtn.setText("Save");
            type.setChecked(trip.getTripType().equals("round"));
            tripName.setText(trip.getTripName());
            startPoint.setText(trip.getStartPlaceName());
            endPoint.setText(trip.getEndPlaceName());
            startLatitude=trip.getStartLatitude();
            endLatitude=trip.getEndLatitude();
            startLongtude=trip.getStartLongtude();
            endLongtude=trip.getEndLongtude();
            thePlaceId=trip.getThePlaceId();
            startPlaceName=trip.getStartPlaceName();
            endPlaceName=trip.getEndPlaceName();
            reqCode=trip.getRequestCode();
            //set in start and end
            calDate.setText(trip.getTripDate());
            timeTxt.setText(trip.getTripTime());

            calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR , trip.getYear());
            calendar.set(Calendar.MONTH , trip.getMonth());
            calendar.set(Calendar.DAY_OF_MONTH , trip.getDayOfMonth());
            calendar.set(Calendar.MINUTE , trip.getMinute());
            calendar.set(Calendar.HOUR_OF_DAY , trip.getHourOfDay());
            calendar.set(Calendar.SECOND , 0);


            year=trip.getYear();
            month=trip.getMonth();
            dayOfMonth=trip.getDayOfMonth();
            minute=trip.getMinute();
            hourOfDay=trip.getHourOfDay();
            user=trip.getUser();
        }else{
            user =intent.getExtras().getString("user");
        }

        type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked)
                    toggleCheck="round";
                else
                    toggleCheck="oneWay";
            }
        });

        //Auto Complete fragments
        String apiKey = "AIzaSyBKk5YhTb5cMuBXSmXOaYBzbPzKFRhsQ38";
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);

        }
       placesClient = Places.createClient(this);
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment2 = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.LAT_LNG ,Place.Field.NAME));
        autocompleteFragment2.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.LAT_LNG, Place.Field.NAME));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                startLatitude = place.getLatLng().latitude;
                startLongtude = place.getLatLng().longitude;
                startPlaceName = place.getName().toString();
                startPoint.setText(startPlaceName);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
            }
        });

        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                endLatitude = place.getLatLng().latitude;
                endLongtude = place.getLatLng().longitude;
                endPlaceName = place.getName().toString();
                endPoint.setText(endPlaceName);
                thePlaceId = place.getId();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
            }
        });




        addBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                startPlaceName=startPoint.getText().toString(); //for demo only
                endPlaceName=endPoint.getText().toString();     // for demo only
                if(tripName.getText().toString().length()>0&&startPoint.getText().toString().length()>0&&endPoint.getText().toString().length()>0
                        && calDate.getText().toString().length()>0&&timeTxt.getText().toString().length()>0){

                    Trip curTrip=new Trip(tripName.getText().toString()
                            ,calDate.getText().toString(),timeTxt.getText().toString(),toggleCheck,"Upcoming");
                    curTrip.setStartLatitude(startLatitude);
                    curTrip.setStartLongtude(startLongtude);
                    curTrip.setEndLatitude(endLatitude);
                    curTrip.setEndLongtude(endLongtude);
                    curTrip.setThePlaceId(thePlaceId);
                    curTrip.setStartPlaceName(startPlaceName);
                    curTrip.setEndPlaceName(endPlaceName);
//                    curTrip.addNewNote("Java");
                    curTrip.setYear(year);
                    curTrip.setMonth(month);
                    curTrip.setDayOfMonth(dayOfMonth);
                    curTrip.setMinute(minute);
                    curTrip.setHourOfDay(hourOfDay);


                    if(purpose.equals("newTrip")){
                        curTrip.setUser(user);
                        reqCode=tripPresenter.getRequestCode();
                        curTrip.setRequestCode(reqCode);  // if delete it is OK, if we will edit, so update it for newTrip only [viiiiiiiiip]
                        tripPresenter.addNewTrip(curTrip);
                        startAlarm(calendar,curTrip);
                    }else{  //editTrip
                        curTrip.setUser(user);
                        curTrip.setNotes(trip.getNotes());
                        curTrip.setId(trip.getId());
                        curTrip.setRequestCode(reqCode);      //here is a problem trip.getRequestCode()
                        tripPresenter.updateTrip(curTrip);
                        // delete or update using requestCode of "trip" object not curTrip, coz trip object is the coming one to be edited
                        startAlarm(calendar,curTrip);
                        finish();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Please fill all fields",Toast.LENGTH_SHORT).show();
                }

            }
        });




        // notifHelper = new NotificationHelper(this );
        calendar = Calendar.getInstance();

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DialogFragment datePicker = new com.example.tripplanner.Views.TripView.DatePickerFrag();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFrag();
                timePicker.show(getSupportFragmentManager(), "Time picker");  //calls notifiction function
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TripActivity.this.year=year;
        TripActivity.this.month=month;
        TripActivity.this.dayOfMonth=dayOfMonth;
        calendar.set(Calendar.YEAR , year);
        calendar.set(Calendar.MONTH , month);
        calendar.set(Calendar.DAY_OF_MONTH , dayOfMonth);
        String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        calDate.setText(date);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TripActivity.this.hourOfDay=hourOfDay;
        TripActivity.this.minute=minute;
        calendar.set(Calendar.MINUTE , minute);
        calendar.set(Calendar.HOUR_OF_DAY , hourOfDay);
        calendar.set(Calendar.SECOND , 0);
        timeTxt.setText(String.valueOf(hourOfDay)+ " : " + String.valueOf(minute));
        //    startAlarm(c);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c,Trip curTrip) {

        AlarmManager alarmang = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderBroadcast.class);
        intent.putExtra("id",curTrip.getId());
        intent.putExtra("reqCode",curTrip.getRequestCode()+"");
        intent.putExtra("endPoint",curTrip.getEndPlaceName());
        intent.putExtra("lati",curTrip.getEndLatitude()+"");
        intent.putExtra("long",curTrip.getEndLongtude()+"");
        intent.putExtra("notes",curTrip.getNotes());

        c.set(Calendar.YEAR , year);
        c.set(Calendar.MONTH , month);
        c.set(Calendar.DAY_OF_MONTH , dayOfMonth);
        c.set(Calendar.MINUTE , minute);
        Log.i("nasor",minute+"");
        Log.i("nasor",curTrip.getMinute()+"");
        c.set(Calendar.HOUR_OF_DAY , hourOfDay);
        c.set(Calendar.SECOND , 0);

        PendingIntent pi =  PendingIntent.getBroadcast(this , curTrip.getRequestCode(), intent , 0);
        alarmang.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pi);

    }

    @Override
    public void addedNewTrip() {
        finish();
    }
}




