package com.example.ridesharing.Response;

import com.google.gson.annotations.SerializedName;

public class UpcomingTripData {
    @SerializedName("tripID")
    String tripID;
    @SerializedName("vin")
    String vin;
    @SerializedName("from")
    String from;
    @SerializedName("to")
    String to;
    @SerializedName("tripStatus")
    String tripStatus;
    @SerializedName("userName")
    String userName;

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
