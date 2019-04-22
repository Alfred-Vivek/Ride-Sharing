package com.example.ridesharing.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.ridesharing.Adapter.CarAdapter;
import com.example.ridesharing.Adapter.TripAdapter;
import com.example.ridesharing.Pojo.Car;
import com.example.ridesharing.Pojo.UpTrips;
import com.example.ridesharing.R;
import com.example.ridesharing.Response.UpcomingTripData;
import com.example.ridesharing.Response.UpcomingTripResponse;
import com.example.ridesharing.Response.carResponse;
import com.example.ridesharing.Rest.ApiClient;
import com.example.ridesharing.Rest.ApiInterface;
import com.example.ridesharing.Utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourTrips extends AppCompatActivity  {

    public int Trip_VALUE;
    private String label;
    List <UpcomingTripData> upTripList;
    List <UpTrips> upTrips;
    RecyclerView mCarRecyclerView;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_trips);
        Bundle mbundle= getIntent().getExtras();
        if (mbundle!=null){
            Trip_VALUE = mbundle.getInt("trip");
        }
        if (Trip_VALUE==1){
            label = "Upcoming Trips";
            loadTripApi();
        }
        if (Trip_VALUE ==2){
            label = "Past Trips";
        }
        setTitle(label);
    }
    public void loadTripApi()
    {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Log.d("reached","insideload");
        Call<UpcomingTripResponse> call = apiInterface.getTripDetails(PrefUtils.getFromPrefs(this,PrefUtils.user_email,""));
        call.enqueue(new Callback<UpcomingTripResponse>() {
            @Override
            public void onResponse(Call<UpcomingTripResponse> call, Response<UpcomingTripResponse> response) {
                Log.d("Got",response.body().getStatus()+"");
                upTripList = response.body().getUpcomingTripDataList();
                initViews();
            }
            @Override
            public void onFailure(Call<UpcomingTripResponse> call, Throwable t) {
                Log.d("fail","Failed");
            }
        });
    }
    public void initViews()
    {
        mCarRecyclerView = (RecyclerView) findViewById(R.id.recycleView_Trips);
        mCarRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mCarRecyclerView.setLayoutManager(layoutManager);
        upTrips = new ArrayList<>();
        for(int i=0; i<upTripList.size(); i++)
        {
            UpTrips upTrip = new UpTrips(upTripList.get(i).getTripID(),upTripList.get(i).getVin(),upTripList.get(i).getFrom(),upTripList.get(i).getTo());
            upTrips.add(upTrip);
        }
        RecyclerView.Adapter adapter = new TripAdapter(this,upTrips);

        mCarRecyclerView.setAdapter(adapter);
        //((TripAdapter) adapter).setOnItemClickListener(this);

    }

   /* @Override
    public void onItemClick(int position) {
        Toast.makeText(this,position+"",Toast.LENGTH_SHORT).show();
        UpTrips upTrip = upTrips.get(position);
        Intent intent_unlock= new Intent(YourTrips.this,SingleTrip.class);
        intent_unlock.putExtra("title",upTrip.getVin());
        startActivity(intent_unlock);

    }*/
}
