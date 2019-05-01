package com.example.ridesharing.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ridesharing.Pojo.Car;
import com.example.ridesharing.Adapter.CarAdapter;
import com.example.ridesharing.R;
import com.example.ridesharing.Rest.ApiClient;
import com.example.ridesharing.Rest.ApiInterface;
import com.example.ridesharing.Response.carData;
import com.example.ridesharing.Response.carResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowCars extends AppCompatActivity {
    List<carData> car_list;
    List<Car> carList;
    ApiInterface apiInterface;
    private RecyclerView mCarRecyclerView;
    public String from,to;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cars);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        Bundle mbundle = getIntent().getExtras();
        if(mbundle!=null){
            from = mbundle.getString("from");
            to = mbundle.getString("to");
        }
        loadCarApi();
    }
    public void loadCarApi() {
        progressDialog.show();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<carResponse> call = apiInterface.getPackage();
        call.enqueue(new Callback<carResponse>() {
            @Override
            public void onResponse(Call<carResponse> call, Response<carResponse> response) {
                progressDialog.dismiss();
                car_list = response.body().getCarDataList();
                initView();
            }
            @Override
            public void onFailure(Call<carResponse> call, Throwable t) {
                progressDialog.dismiss();
                new AlertDialog.Builder(ShowCars.this)
                        .setTitle("Failed to connect!")
                        .setMessage("Try connecting to server again?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                loadCarApi();
                            }
                        })
                        .setNegativeButton("Exit", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                finishAffinity();
                            }
                        }).show();
            }
        });

    }
    private void initView() {
        mCarRecyclerView = (RecyclerView) findViewById(R.id.recycleView_cars);
        mCarRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mCarRecyclerView.setLayoutManager(layoutManager);
        carList = new ArrayList<>();
        for(int i=0; i<car_list.size(); i++)
        {
            Car car = new Car(car_list.get(i).getVin(),car_list.get(i).getCompany(),car_list.get(i).getBrand(),car_list.get(i).getSeatingCapacity(),car_list.get(i).getCarImage(),car_list.get(i).getRate50(),car_list.get(i).getRate100(),car_list.get(i).getMcAddress(),car_list.get(i).getOwnerName(),car_list.get(i).getRcImage(),car_list.get(i).getCurrentStatus());
            carList.add(car);
        }
        RecyclerView.Adapter adapter = new CarAdapter(this,from,to,carList);
        mCarRecyclerView.setAdapter(adapter);
    }
}
