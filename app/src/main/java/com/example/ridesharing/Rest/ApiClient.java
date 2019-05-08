package com.example.ridesharing.Rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String Base_URL = "http://192.168.0.160:5000";
    public static final String MAPS_URL = "https://maps.googleapis.com/";
    private static Retrofit retrofit = null;
    public static Retrofit retrofitMaps = null;
    public static Retrofit getClient(){
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Base_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getGoogleClient(){
        if(retrofitMaps == null)
        {
            retrofitMaps = new Retrofit.Builder()
                    .baseUrl(MAPS_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitMaps;
    }
}
