package com.example.ridesharing.Response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    LoginData loginData;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoginData getLoginData() {
        return loginData;
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
    }

}
