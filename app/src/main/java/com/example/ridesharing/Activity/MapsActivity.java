package com.example.ridesharing.Activity;
import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ridesharing.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener, NavigationView.OnNavigationItemSelectedListener {
    private GoogleMap mMap;
    private View mMapView;
    TextView miniTV, sedanTV, suvTV, primeTV;
    Marker my;
    LocationService ls;
    ArrayList<LatLng> markerPoints;
    public static final int REQUEST_CHECK_SETTINGS = 0x1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ls = new LocationService(this);
        ls.createLocationCallback();
        ls.createLocationRequest();
        ls.buildLocationSettingsRequest();
        miniTV = findViewById(R.id.miniTV);
        sedanTV = findViewById(R.id.sedanTV);
        suvTV = findViewById(R.id.suvTV);
        primeTV = findViewById(R.id.primeTV);
        markerPoints = new ArrayList<>();
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setListeners();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
        registerReceiver(mGpsSwitchStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
        Places.initialize(this,getString(R.string.google_maps_key));
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
                autocompleteFragment.setHint("Search your location");
                autocompleteFragment.setCountry("IN");
        ImageView searchIcon = (ImageView)((LinearLayout)autocompleteFragment.getView()).getChildAt(0);
        searchIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_black));
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("Error", "Place: " + place.getName() + ", " + place.getLatLng()+", "+place.getId());
                Marker m = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));
                m.showInfoWindow();
            }
            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("Error", "An error occurred: " + status);
            }
        });
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
                markerPoints.clear();
                markerPoints.add(new LatLng(ls.mCurrentLocation.getLatitude(),ls.mCurrentLocation.getLongitude()));
                markerPoints.add(marker.getPosition());
                LatLng origin = markerPoints.get(0);
                LatLng dest = markerPoints.get(1);
                String url = getDirectionsUrl(origin, dest);
                DownloadTask downloadTask = new DownloadTask();
                downloadTask.execute(url);
            return false;
            }
        });
        LatLng car1 = new LatLng(17.424616, 78.438198);
        Marker m = mMap.addMarker(new MarkerOptions().position(car1).title("Car 1").icon(BitmapDescriptorFactory.fromResource(R.drawable.car_icon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(car1, 12.0f));
        m.showInfoWindow();

        LatLng car2 = new LatLng(17.3957847, 78.4311944);
        Marker m1 = mMap.addMarker(new MarkerOptions().position(car2).title("Car 2").icon(BitmapDescriptorFactory.fromResource(R.drawable.car_icon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(car2, 12.0f));
        m1.showInfoWindow();

        LatLng car3 = new LatLng(17.4504102, 78.38103819999999);
        Marker m2 = mMap.addMarker(new MarkerOptions().position(car3).title("Car 3").icon(BitmapDescriptorFactory.fromResource(R.drawable.car_icon)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(car3, 12.0f));
        m2.showInfoWindow();
    }
    @Override
    public void onMapLongClick(LatLng latLng) {
        if(my != null)
            my.remove();
        my = mMap.addMarker(new MarkerOptions().position(latLng).title(latLng.toString()));
        my.showInfoWindow();
        // Already two locations
//        if(markerPoints.size()>1){
//            markerPoints.clear();
//            mMap.clear();
//        }
//        markerPoints.add(latLng);
//        MarkerOptions options = new MarkerOptions();
//        options.position(latLng);
//        if(markerPoints.size()==1){
//            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//        }else if(markerPoints.size()==2){
//            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//        }
//        mMap.addMarker(options);
//        if(markerPoints.size() >= 2){
//            LatLng origin = markerPoints.get(0);
//            LatLng dest = markerPoints.get(1);
//            String url = getDirectionsUrl(origin, dest);
//            DownloadTask downloadTask = new DownloadTask();
//            downloadTask.execute(url);
//        }
    }
    private BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    ls.startLocationUpdates();
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i("TAG", "User agreed to make required location settings changes.");
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i("TAG", "User chose not to make required location settings changes.");
                        Intent gps = new Intent(this,SetPermission.class);
                        gps.putExtra("message","Turn On GPS");
                        startActivity(gps);
                        break;
                }
                break;
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
    private String getDirectionsUrl(LatLng origin,LatLng dest){
        String str_origin = "origin="+origin.latitude+","+origin.longitude;
        String str_dest = "destination="+dest.latitude+","+dest.longitude;
        String sensor = "key="+getString(R.string.google_maps_key);
        String parameters = str_origin+"&"+str_dest+"&"+sensor;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
        return url;
    }
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb  = new StringBuffer();
            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        }catch(Exception e){
            Log.d("Exception", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = result.get(i);
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                lineOptions.addAll(points);
            }
            mMap.addPolyline(lineOptions);
        }
    }
}
