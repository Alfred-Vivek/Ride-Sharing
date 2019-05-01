package com.example.ridesharing.Response;
import com.google.gson.annotations.SerializedName;
public class EndTripResponse {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    String message;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}