package com.example.ridesharing.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ridesharing.Activity.PastTrips;
import com.example.ridesharing.Activity.YourTrips;
import com.example.ridesharing.Adapter.TripAdapter;
import com.example.ridesharing.Pojo.UpTrips;
import com.example.ridesharing.R;
import com.example.ridesharing.Response.UpcomingTripData;
import com.example.ridesharing.Response.UpcomingTripResponse;
import com.example.ridesharing.Rest.ApiClient;
import com.example.ridesharing.Rest.ApiInterface;
import com.example.ridesharing.Utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingFragment extends Fragment {
    List <UpcomingTripData> upTripList;
    List <UpTrips> upTrips;
    RecyclerView mCarRecyclerView;
    ApiInterface apiInterface;
    TextView msg;
    ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_upcoming_trip, container, false);
        mCarRecyclerView = v.findViewById(R.id.recycleView_Trips);
        msg = v.findViewById(R.id.msg);
        loadTripApi();
        mCarRecyclerView.setHasFixedSize(true);
        return v;
    }
    public void loadTripApi()
    {
        progressDialog.show();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UpcomingTripResponse> call = apiInterface.getTripDetails(PrefUtils.getFromPrefs(getContext(),PrefUtils.user_email,""),"upcoming");
        call.enqueue(new Callback<UpcomingTripResponse>() {
            @Override
            public void onResponse(Call<UpcomingTripResponse> call, Response<UpcomingTripResponse> response) {
                progressDialog.dismiss();
                upTripList = response.body().getUpcomingTripDataList();
                initViews();
            }
            @Override
            public void onFailure(Call<UpcomingTripResponse> call, Throwable t) {
                progressDialog.dismiss();
                new AlertDialog.Builder(getContext())
                        .setTitle("Failed to connect!")
                        .setMessage("Try connecting to server again?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                loadTripApi();
                            }
                        })
                        .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) { }
                        }).show();
            }
        });
    }
    public void initViews()
    {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mCarRecyclerView.setLayoutManager(layoutManager);
        upTrips = new ArrayList<>();
        for(int i=0; i<upTripList.size(); i++)
        {
            UpTrips upTrip = new UpTrips(upTripList.get(i).getTripID(),upTripList.get(i).getVin(),upTripList.get(i).getFrom(),upTripList.get(i).getTo());
            upTrips.add(upTrip);
        }
        if(upTrips.size()==0)
        {
            mCarRecyclerView.setVisibility(View.GONE);
            msg.setVisibility(View.VISIBLE);
            msg.setText("No Upcoming Trips to Show");

        }
        else
        {
            RecyclerView.Adapter adapter = new TripAdapter(getContext(),upTrips,1);
            mCarRecyclerView.setAdapter(adapter);
        }
    }
}
