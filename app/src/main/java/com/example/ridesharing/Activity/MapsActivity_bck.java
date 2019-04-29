package com.example.ridesharing.Activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ridesharing.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity_bck extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener {
    private GoogleMap mMap;
    private View mMapView;
    TextView miniTV, sedanTV, suvTV, primeTV;
    Marker my;
    private FusedLocationProviderClient mFusedLocationClient;
//    private LocationCallback mLocationCallback;
//    boolean mRequestingLocationUpdates;
//    private static final int REQUEST_CHECK_SETTINGS = 0x1;
//    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
//    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    private SettingsClient mSettingsClient;
//    LocationRequest mLocationRequest;
//    private Location mCurrentLocation;
//    private LocationSettingsRequest mLocationSettingsRequest;
    LocationService ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);
        ls = new LocationService(this);
        ls.createLocationCallback();
        ls.createLocationRequest();
        ls.buildLocationSettingsRequest();
        miniTV = findViewById(R.id.miniTV);
        sedanTV = findViewById(R.id.sedanTV);
        suvTV = findViewById(R.id.suvTV);
        primeTV = findViewById(R.id.primeTV);
        setListeners();
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mMapView = mapFragment.getView();
//        mapFragment.getMapAsync(this);
        registerReceiver(mGpsSwitchStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
    }
    private void setListeners() {
        miniTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(miniTV.getBackground() == null){
                    miniTV.setBackground(getResources().getDrawable(R.drawable.shape_round));
                    sedanTV.setBackgroundResource(0);
                    suvTV.setBackgroundResource(0);
                    primeTV.setBackgroundResource(0);
                }
                else
                    miniTV.setBackgroundResource(0);
            }
        });
        sedanTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sedanTV.getBackground() == null)
                {
                    sedanTV.setBackground(getResources().getDrawable(R.drawable.shape_round));
                    miniTV.setBackgroundResource(0);
                    suvTV.setBackgroundResource(0);
                    primeTV.setBackgroundResource(0);
                }

                else
                    sedanTV.setBackgroundResource(0);
            }
        });
        suvTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(suvTV.getBackground() == null)
                {
                    suvTV.setBackground(getResources().getDrawable(R.drawable.shape_round));
                    sedanTV.setBackgroundResource(0);
                    miniTV.setBackgroundResource(0);
                    primeTV.setBackgroundResource(0);
                }
                else
                    suvTV.setBackgroundResource(0);
            }
        });
        primeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(primeTV.getBackground() == null)
                {
                    primeTV.setBackground(getResources().getDrawable(R.drawable.shape_round));
                    sedanTV.setBackgroundResource(0);
                    suvTV.setBackgroundResource(0);
                    miniTV.setBackgroundResource(0);
                }
                else
                    primeTV.setBackgroundResource(0);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        ls.startLocationUpdates();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0, 1200, 180, 0);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e("Error", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("Error", "Can't find style. Error: ", e);
        }


        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(MapsActivity_bck.this,marker.getTitle(),Toast.LENGTH_LONG).show();
                return false;
            }
        });
        LatLng msit1 = new LatLng(17.424616, 78.438198);
        Marker m = mMap.addMarker(new MarkerOptions().position(msit1).title("Car 1").icon(BitmapDescriptorFactory.fromResource(R.drawable.car_icon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(msit1, 14.0f));
        m.showInfoWindow();

        LatLng msit2 = new LatLng(17.427305, 78.441424);
        Marker m1 = mMap.addMarker(new MarkerOptions().position(msit2).title("Car 2").icon(BitmapDescriptorFactory.fromResource(R.drawable.car_icon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(msit2, 14.0f));
        m1.showInfoWindow();

        LatLng msit3 = new LatLng(17.428470, 78.435525);
        Marker m2 = mMap.addMarker(new MarkerOptions().position(msit3).title("Car 3").icon(BitmapDescriptorFactory.fromResource(R.drawable.car_icon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(msit3, 14.0f));
        m2.showInfoWindow();
    }
    @Override
    public void onMapLongClick(LatLng latLng) {
        if(my != null)
            my.remove();
        my = mMap.addMarker(new MarkerOptions().position(latLng).title(latLng.toString()));
        my.showInfoWindow();
    }
//    private void buildLocationSettingsRequest() {
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
//        builder.addLocationRequest(mLocationRequest);
//        mLocationSettingsRequest = builder.build();
//    }
//    private void startLocationUpdates() {
//        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
//                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
//                    @Override
//                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
//                        Log.i("Res", "All location settings are satisfied.");
//                        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                            return;
//                        }
//                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        int statusCode = ((ApiException) e).getStatusCode();
//                        switch (statusCode) {
//                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                                Log.i("Res", "Location settings are not satisfied. Attempting to upgrade " +
//                                        "location settings ");
//                                try {
//                                    ResolvableApiException rae = (ResolvableApiException) e;
//                                    rae.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_SETTINGS);
//                                } catch (IntentSender.SendIntentException sie) {
//                                    Log.i("Res", "PendingIntent unable to execute request.");
//                                }
//                                break;
//                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                                String errorMessage = "Location settings are inadequate, and cannot be " +
//                                        "fixed here. Fix in Settings.";
//                                Log.e("Res", errorMessage);
//                                Toast.makeText(MapsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
//                                mRequestingLocationUpdates = false;
//                        }
//                    }
//                });
//    }
//    private void createLocationRequest() {
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
//        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//    }
//    private void createLocationCallback() {
//        mLocationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//            }
//        };
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//// Check for the integer request code originally supplied to startResolutionForResult().
//            case REQUEST_CHECK_SETTINGS:
//                switch (resultCode) {
//                    case Activity.RESULT_OK:
//                        Log.i("TAG", "User agreed to make required location settings changes.");
//                        startLocationUpdates();
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        Log.i("TAG", "User chose not to make required location settings changes.");
//                        Intent in = new Intent(this,SetPermission.class);
//                        in.putExtra("message","Turn On GPS");
//                        startActivity(in);
//
//// mRequestingLocationUpdates = false;
//// updateUI();
//                        break;
//                }
//                break;
//        }
//    }
    private BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    ls.startLocationUpdates();
            }
        }
    };
}
