package com.example.ridesharing.Response;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("email")
    String email;
    @SerializedName("name")
    String name;
    @SerializedName("phone")
    String phone;
    @SerializedName("mcaddress")
    String mcaddress;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMcaddress() {
        return mcaddress;
    }

    public void setMcaddress(String mcaddress) {
        this.mcaddress = mcaddress;
    }
}
