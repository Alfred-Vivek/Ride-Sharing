package com.example.ridesharing.Rest;

import com.example.ridesharing.Response.BookResponse;
import com.example.ridesharing.Response.EndTripResponse;
import com.example.ridesharing.Response.LoginResponse;
import com.example.ridesharing.Response.MapResponse.Result;
import com.example.ridesharing.Response.UnlockResponse;
import com.example.ridesharing.Response.UpcomingTripResponse;
import com.example.ridesharing.Response.carResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {
//        @FormUrlEncoded
        @POST("/book")
        Call<BookResponse> sendBookRequest(@Body Map<String, String> request);

        @GET("/login")
        Call<LoginResponse> sendLoginDetails(@Query("userName") String userName, @Query("password") String password);

        @GET("/unlockDevice")
        Call<UnlockResponse> unlockCar(@Query("tripID") String tripID, @Query("mcaddress") String mcaddress, @Query("enckey") String enckey);

        @GET("/trips")
        Call<UpcomingTripResponse> getTripDetails(@Query("userName") String userName, @Query("tripsType") String type);

        @GET("/userslist?flag=carData&type=vehicle&status=open")
        Call<carResponse> getPackage();

        @GET
        Call<Result> getDirections(@Url String url);

        @PUT("/endtrip")
        Call<EndTripResponse> sendTripRequest(@Body Map<String, String> request);

//        @PUT("/logistic/updateASN/")
//        Call<PackageAcceptResponse> sendAcceptance(@Body Map<String,String> accept);
    }
