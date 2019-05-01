package com.example.ridesharing.Request;
import com.google.gson.annotations.SerializedName;
public class EndTripRequest {
    @SerializedName("tripID")
    String tripID;
    @SerializedName("userName")
    String userName;
    @SerializedName("mcaddress")
    String mcaddress;
    @SerializedName("vin")
    String vin;
    @SerializedName("status")
    String status;

    public String getMcaddress() {
        return mcaddress;
    }

    public void setMcaddress(String mcaddress) {
        this.mcaddress = mcaddress;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getUserName() {
        return userName;
    }

    public String getVin() {
        return vin;
    }
}