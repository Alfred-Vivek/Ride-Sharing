package com.example.ridesharing.Response;

import com.google.gson.annotations.SerializedName;

public class UnlockData {
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
