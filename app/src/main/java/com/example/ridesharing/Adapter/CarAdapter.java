package com.example.ridesharing.Adapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.ridesharing.Activity.ShowCars;
import com.example.ridesharing.Activity.SplashScreen;
import com.example.ridesharing.Activity.YourTrips;
import com.example.ridesharing.Pojo.Car;
import com.example.ridesharing.Request.BookRequest;
import com.example.ridesharing.Response.BookResponse;
import com.example.ridesharing.Rest.ApiClient;
import com.example.ridesharing.Rest.ApiInterface;
import com.example.ridesharing.Utils.PrefUtils;
import com.example.ridesharing.R;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Car> cars_list;
    ApiInterface apiServices;
    String from,to;
    public CarAdapter(Context context, List<Car> cars_list){
        this.mContext = context;
        this.cars_list = cars_list;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.showcar_card,viewGroup,false);// Inflator points towards the XML layout
        return new ImageViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, final int i) {
        imageViewHolder.title.setText(cars_list.get(i).getCar_title());
        imageViewHolder.No_seat.setText(cars_list.get(i).getNo_seats()+" Seater");
        imageViewHolder.Vid.setText("VehicleNo: "+cars_list.get(i).getVin());
        imageViewHolder.price.setText(cars_list.get(i).getPrice());
        imageViewHolder.price100.setText(cars_list.get(i).getPrice100());
        imageViewHolder.mBook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                from = ShowCars.pickTV.getText().toString();
                to = ShowCars.dropTV.getText().toString();
                if(from.equalsIgnoreCase("--:--:--") || from.equalsIgnoreCase("Enter Pick Up Date"))
                {
                    ShowCars.pickTV.setText("Enter Pick Up Date");
                    ShowCars.pickTV.setTextColor(Color.RED);
                    Animation shake = AnimationUtils.loadAnimation(mContext, R.anim.shake);
                    ShowCars.pickTV.startAnimation(shake);
                    return;
                }
                if(to.equalsIgnoreCase("--:--:--") || to.equalsIgnoreCase("Enter Drop off Date"))
                {
                    ShowCars.dropTV.setText("Enter Drop off Date");
                    ShowCars.dropTV.setTextColor(Color.RED);
                    Animation shake = AnimationUtils.loadAnimation(mContext, R.anim.shake);
                    ShowCars.dropTV.startAnimation(shake);
                    return;
                }
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                try {
                    Date date1 = format.parse(from);
                    Date date2 = format.parse(to);
                    if(date1.before(date2))
                        new AlertDialog.Builder(mContext)
                                .setTitle("Confirm Booking!")
                                .setMessage("Do you want to book this car?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        callBookingApi(cars_list.get(i).getVin());
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {}
                                }).show();
                    else
                        new AlertDialog.Builder(mContext)
                                .setTitle("Error Selecting Date!")
                                .setMessage("Drop Off Date cannot be before Pick Up Date!")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setNeutralButton("Review", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton){}
                                }).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void callBookingApi(final String vin) {
        String user_name = PrefUtils.getFromPrefs(mContext,PrefUtils.user_email,"");
        final String mcaddress = PrefUtils.getFromPrefs(mContext,PrefUtils.mc_address,"");
        BookRequest bookRequest = new BookRequest();
        bookRequest.setVin(vin);
        bookRequest.setFrom(from);
        bookRequest.setTo(to);
        bookRequest.setUser(user_name);
        bookRequest.setMcaddress(mcaddress);
        Gson gson = new Gson();
        String json = gson.toJson(bookRequest);
        Map<String,String> map = new HashMap<String,String>();
        map = gson.fromJson(json, map.getClass());
        apiServices = ApiClient.getClient().create(ApiInterface.class);
        Call<BookResponse> call = apiServices.sendBookRequest(map);
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if(response.body().getStatus().equalsIgnoreCase("fail"))
                {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Booking Details")
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
                            }).show();
                }
                else
                {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Booking Details")
                            .setMessage(response.body().getMessage())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent in = new Intent(mContext,YourTrips.class);
                                    in.putExtra("trip",1);
                                    mContext.startActivity(in);
                                }
                            }).show();
                }
            }
            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Failed to book!")
                        .setMessage("Try connecting to server again?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                callBookingApi(vin);
                            }
                        })
                        .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {}
                        }).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return cars_list.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView title,price,No_seat,Vid,price100;
        public Button mBook_btn;
        public ImageViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.car_name);
            price = (TextView) itemView.findViewById(R.id.price50);
            No_seat = (TextView) itemView.findViewById(R.id.no_seat);
            Vid = (TextView) itemView.findViewById(R.id.v_id);
            mBook_btn = (Button) itemView.findViewById(R.id.book_btn);
            price100 = (TextView) itemView.findViewById(R.id.price100);
        }
    }
}
