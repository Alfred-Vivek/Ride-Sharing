package com.example.ridesharing.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class carResponse {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    List<carData> carDataList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<carData> getCarDataList() {
        return carDataList;
    }

    public void setCarDataList(List<carData> carDataList) {
        this.carDataList = carDataList;
    }


}
