package com.example.ridesharing.Adapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ridesharing.Activity.CurrentTrip;
import com.example.ridesharing.Activity.PastTrips;
import com.example.ridesharing.Activity.ShowCars;
import com.example.ridesharing.Activity.YourTrips;
import com.example.ridesharing.Pojo.UpTrips;
import com.example.ridesharing.R;
import com.example.ridesharing.Request.EndTripRequest;
import com.example.ridesharing.Response.EndTripResponse;
import com.example.ridesharing.Rest.ApiClient;
import com.example.ridesharing.Rest.ApiInterface;
import com.example.ridesharing.Utils.PrefUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ImageViewHolder> {
    private Context mContext;
    private List<UpTrips> tripsList;
    int act;
    ApiInterface apiServices;
    public TripAdapter(Context context,List<UpTrips> tripsList, int act){
        this.mContext = context;
        this.tripsList = tripsList;
        this.act = act;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.trip_card,viewGroup,false);
        return new ImageViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ImageViewHolder imageViewHolder, final int i) {
        imageViewHolder.tripid.setText("Trip Id : \t"+tripsList.get(i).getTripID());
        imageViewHolder.vin.setText("Vehicle No. : \t"+tripsList.get(i).getVin());
        imageViewHolder.from.setText("Start Date : \t"+tripsList.get(i).getFrom());
        imageViewHolder.to.setText("End Date : \t"+tripsList.get(i).getTo());
        imageViewHolder.Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), CurrentTrip.class);
        intent.putExtra("tripId",tripsList.get(i).getTripID());
        mContext.startActivity(intent);
            }
        });
        imageViewHolder.Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Cancel Trip!")
                        .setMessage("Do you want to cancel this trip?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                CallEndTripApi(i,"cancelled");
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {}
                        }).show();
            }
        });
        imageViewHolder.End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("End Trip!")
                        .setMessage("Do you want to end this trip?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                CallEndTripApi(i,"finished");
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {}
                        }).show();
            }
        });
        if(act == 2)
        {
            imageViewHolder.Start.setVisibility(View.GONE);
            imageViewHolder.Cancel.setVisibility(View.GONE);
            imageViewHolder.End.setVisibility(View.VISIBLE);
        }
        else if(act ==3)
        {
            imageViewHolder.Start.setVisibility(View.GONE);
            imageViewHolder.End.setVisibility(View.GONE);
            imageViewHolder.Cancel.setVisibility(View.GONE);
            imageViewHolder.divider.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return tripsList.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView tripid;
        public TextView vin;
        public TextView from;
        public TextView to;
        public Button Start, End, Cancel;
        public View divider;
        public ImageViewHolder(View itemView) {
            super(itemView);
            tripid = (TextView) itemView.findViewById(R.id.trip_Id);
            vin = (TextView) itemView.findViewById(R.id.vin);
            from = (TextView) itemView.findViewById(R.id.from);
            to = (TextView) itemView.findViewById(R.id.to);
            Start = (Button) itemView.findViewById(R.id.startTrip_btn);
            End = (Button) itemView.findViewById(R.id.endTrip_btn);
            Cancel = (Button) itemView.findViewById(R.id.cancelTrip_btn);
            divider = (View) itemView.findViewById(R.id.divider);
        }
    }
    private void CallEndTripApi(final int i, final String status) {
        final String tripStatus;
        if(status.equalsIgnoreCase("finished"))tripStatus = "Trip Ended Successfully. Thank You for Riding with us!";
        else tripStatus = "Trip Cancelled Successfully. Ride with us again!";
        String user_name = PrefUtils.getFromPrefs(mContext,PrefUtils.user_email,"");
        final String mcaddress = PrefUtils.getFromPrefs(mContext,PrefUtils.mc_address,"");
        EndTripRequest EndRequest = new EndTripRequest();
        EndRequest.setTripID(tripsList.get(i).getTripID());
        EndRequest.setUserName(user_name);
        EndRequest.setMcaddress(mcaddress);
        EndRequest.setVin(tripsList.get(i).getVin());
        EndRequest.setStatus(status);
        Gson gson = new Gson();
        String json = gson.toJson(EndRequest);
        Map<String, String> map = new HashMap<>();
        map = gson.fromJson(json, map.getClass());
        apiServices = ApiClient.getClient().create(ApiInterface.class);
        Call<EndTripResponse> call = apiServices.sendTripRequest(map);
        call.enqueue(new Callback<EndTripResponse>() {
            @Override
            public void onResponse(Call<EndTripResponse> call, Response<EndTripResponse> response) {
                if (response.body().getStatus().equalsIgnoreCase("fail")) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Trip Status")
                            .setMessage(response.body().getMessage())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(mContext instanceof ShowCars)
                                    {
                                        ((ShowCars) mContext).loadCarApi();
                                    }
                                }
                            })
                            .show();
                }
                else {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Trip Status")
                            .setMessage(tripStatus)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent in = new Intent(mContext, YourTrips.class);
                                    in.putExtra("trip", 3);
                                    mContext.startActivity(in);
                                }
                            }).show();
                }
            }
            @Override
            public void onFailure(Call<EndTripResponse> call, Throwable t) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Failed to connect!")
                        .setMessage("Try connecting to server again?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                CallEndTripApi(i,status);
                            }
                        })
                        .setNegativeButton("Exit", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {}
                        }).show();
            }
        });
    }
}
