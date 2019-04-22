package com.example.ridesharing.Request;

import com.google.gson.annotations.SerializedName;

public class BookRequest {
    @SerializedName("vin")
    String vin;
    @SerializedName("from")
    String from;
    @SerializedName("to")
    String to;
    @SerializedName("userName")
    String user;
    @SerializedName("mcaddress")
    String mcaddress;

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMcaddress() {
        return mcaddress;
    }

    public void setMcaddress(String mcaddress) {
        this.mcaddress = mcaddress;
    }
}
