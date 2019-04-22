package com.example.ridesharing.Pojo;

public class Car {
    private String car_title;
    private String image_url;
    private String no_seats;
    private String price,vin,company,price100,mcAddress,ownerName,rcImage,currentStatus;

    public Car(String vin,String company,String car_title,String no_seats, String image_url,String price,String price100,String mcAddress,String ownerName,String rcImage,String currentStatus)
    {
        this.vin =vin;
        this.company = company;
        this.car_title = car_title;
        this.image_url = image_url;
        this.price100 = price100;
        this.no_seats = no_seats;
        this.price = price;
        this.mcAddress = mcAddress;
        this.ownerName = ownerName;
        this.rcImage = rcImage;
        this.currentStatus = currentStatus;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPrice100(String price100) {
        this.price100 = price100;
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

    public String getRcImage() {
        return rcImage;
    }

    public void setRcImage(String rcImage) {
        this.rcImage = rcImage;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }



    public String getCar_title() {
        return car_title;
    }

    public void setCar_title(String car_title) {
        this.car_title = car_title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getNo_seats() {
        return no_seats;
    }

    public void setNo_seats(String no_seats) {
        this.no_seats = no_seats;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getPrice100() {
        return price100;
    }

}
