package com.example.ridesharing.Response;

import com.google.gson.annotations.SerializedName;

public class carData {
    @SerializedName("vin")
    String vin;
    @SerializedName("company")
    String company;
    @SerializedName("brand")
    String brand;
    @SerializedName("seatingCapacity")
    String seatingCapacity;
    @SerializedName("rate50")
    String rate50;
    @SerializedName("rate100")
    String rate100;
    @SerializedName("mcAddress")
    String mcAddress;
    @SerializedName("ownerName")
    String ownerName;
    @SerializedName("carImage")
    String carImage;
    @SerializedName("rcImage")
    String rcImage;
    @SerializedName("docType")
    String docType;
    @SerializedName("currentStatus")
    String currentStatus;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(String seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public String getRate50() {
        return rate50;
    }

    public void setRate50(String rate50) {
        this.rate50 = rate50;
    }

    public String getRate100() {
        return rate100;
    }

    public void setRate100(String rate100) {
        this.rate100 = rate100;
    }

    public String getMcAddress() {
        return mcAddress;
    }

    public void setMcAddress(String mcAddress) {
        this.mcAddress = mcAddress;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCarImage() {
        return carImage;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

    public String getRcImage() {
        return rcImage;
    }

    public void setRcImage(String rcImage) {
        this.rcImage = rcImage;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
}
