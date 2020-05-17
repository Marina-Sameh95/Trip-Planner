package com.example.tripplanner.POJOs;

import java.io.Serializable;
import java.util.ArrayList;

public class Trip implements Serializable {
    private String tripName;
    private String tripDate;
    private String tripTime;
    private String tripType;    //round or oneWay
    private String status;      // upcoming, or Cancelled, or Done
    double endLatitude;        //new
    double endLongtude;         //new
    double startLatitude;       //new
    double startLongtude;       //new
    String thePlaceId;          //new
    String startPlaceName;     //new
    String endPlaceName;       //new
    private String id;
    private ArrayList<String> notes= new ArrayList<>();

    private int requestCode;
    private int year;
    private int month;
    private int dayOfMonth;
    private int minute;
    private int hourOfDay;
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Trip() {
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }

    public Trip(String tripName, String tripDate, String tripTime, String tripType, String status) {
        this.tripName = tripName;
        this.tripDate = tripDate;
        this.tripTime = tripTime;
        this.tripType = tripType;
        this.status=status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addNewNote(String note){
        notes.add(note);
    }
    public ArrayList<String> getNotes(){
        return notes;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public String getTripTime() {
        return tripTime;
    }

    public void setTripTime(String tripTime) {
        this.tripTime = tripTime;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public double getEndLongtude() {
        return endLongtude;
    }

    public void setEndLongtude(double endLongtude) {
        this.endLongtude = endLongtude;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongtude() {
        return startLongtude;
    }

    public void setStartLongtude(double startLongtude) {
        this.startLongtude = startLongtude;
    }

    public String getThePlaceId() {
        return thePlaceId;
    }

    public void setThePlaceId(String thePlaceId) {
        this.thePlaceId = thePlaceId;
    }

    public String getStartPlaceName() {
        return startPlaceName;
    }

    public void setStartPlaceName(String startPlaceName) {
        this.startPlaceName = startPlaceName;
    }

    public String getEndPlaceName() {
        return endPlaceName;
    }

    public void setEndPlaceName(String endPlaceName) {
        this.endPlaceName = endPlaceName;
    }
}
