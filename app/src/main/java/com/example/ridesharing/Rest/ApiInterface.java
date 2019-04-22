package com.example.ridesharing.Rest;

import com.example.ridesharing.Response.BookResponse;
import com.example.ridesharing.Response.LoginResponse;
import com.example.ridesharing.Response.UnlockResponse;
import com.example.ridesharing.Response.UpcomingTripResponse;
import com.example.ridesharing.Response.carResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

    public interface ApiInterface {
//        @FormUrlEncoded
        @POST("/book")
        Call<BookResponse> sendBookRequest(@Body Map<String, String> request);

        @GET("/login")
        Call<LoginResponse> sendLoginDetails(@Query("userName") String userName, @Query("password") String password);

        @GET("/unlockDevice")
        Call<UnlockResponse> unlockCar(@Query("mcaddress") String mcaddress, @Query("enckey") String enckey);

        @GET("/upcomingtrip")
        Call<UpcomingTripResponse> getTripDetails(@Query("userName") String userName);

        @GET("/userslist?flag=carData&type=vehicle/")
        Call<carResponse> getPackage();


//        @PUT("/logistic/updateASN/")
//        Call<PackageAcceptResponse> sendAcceptance(@Body Map<String,String> accept);
    }
