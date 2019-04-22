package com.example.ridesharing.Response;

import com.google.gson.annotations.SerializedName;

public class UnlockResponse {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    UnlockData unlockData;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UnlockData getUnlockData() {
        return unlockData;
    }

    public void setUnlockData(UnlockData unlockData) {
        this.unlockData = unlockData;
    }
}
