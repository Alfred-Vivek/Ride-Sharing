package com.example.ridesharing.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpcomingTripResponse {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    List<UpcomingTripData> upcomingTripDataList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<UpcomingTripData> getUpcomingTripDataList() {
        return upcomingTripDataList;
    }

    public void setUpcomingTripDataList(List<UpcomingTripData> upcomingTripDataList) {
        this.upcomingTripDataList = upcomingTripDataList;
    }
}
