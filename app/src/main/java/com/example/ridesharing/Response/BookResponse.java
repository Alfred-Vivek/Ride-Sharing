package com.example.ridesharing.Response;

import com.google.gson.annotations.SerializedName;

public class BookResponse {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
