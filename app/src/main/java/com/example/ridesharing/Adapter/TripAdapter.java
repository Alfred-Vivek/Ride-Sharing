package com.example.ridesharing.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ridesharing.Activity.ShowCars;
import com.example.ridesharing.Activity.SingleTrip;
import com.example.ridesharing.Activity.YourTrips;
import com.example.ridesharing.Pojo.Car;
import com.example.ridesharing.Pojo.UpTrips;
import com.example.ridesharing.Request.BookRequest;
import com.example.ridesharing.Response.BookResponse;
import com.example.ridesharing.Rest.ApiClient;
import com.example.ridesharing.Rest.ApiInterface;
import com.example.ridesharing.Utils.PrefUtils;
import com.example.ridesharing.R;
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
    //private OnItemClickListener mListener;
    public TripAdapter(Context context,List<UpTrips> tripsList){
        this.mContext = context;
        this.tripsList = tripsList;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.trip_card,viewGroup,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder imageViewHolder, int i) {
        imageViewHolder.tripid.setText("Trip Id : \t"+tripsList.get(i).getTripID());
        imageViewHolder.vin.setText("Vehicle No. : \t"+tripsList.get(i).getVin());
        imageViewHolder.from.setText("Start Date : \t"+tripsList.get(i).getFrom());
        imageViewHolder.to.setText("End Date : \t"+tripsList.get(i).getTo());
        imageViewHolder.Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SingleTrip.class);
                mContext.startActivity(intent);
            }
        });
        /*imageViewHolder.End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
*/
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
        public Button Start, End;

        public ImageViewHolder(View itemView) {
            super(itemView);
            tripid = (TextView) itemView.findViewById(R.id.trip_Id);
            vin = (TextView) itemView.findViewById(R.id.vin);
            from = (TextView) itemView.findViewById(R.id.from);
            to = (TextView) itemView.findViewById(R.id.to);
            Start = (Button) itemView.findViewById(R.id.startTrip_btn);
            End = (Button) itemView.findViewById(R.id.endTrip_btn);
            //itemView.setOnClickListener(this);
        }

       /* @Override
        public void onClick(View view) {
            if(mListener !=null){*//* if suppose an image is clicked *//*
                int position = getAdapterPosition();*//* position of image is obtained *//*
         *//*The below condition is VVimp condition it gives the position of image
                 only if IMAGE doesn't fade by clicking on it
                 *//*
                if(position!=RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);// calling "onItemClick" method
                }
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    // Note : Below in click listener ,we have to pass interface we created
    public void setOnItemClickListener(OnItemClickListener listener){
        // we have to use the object of OnItemClickListener
        mListener = listener;

    }*/


    }
}
