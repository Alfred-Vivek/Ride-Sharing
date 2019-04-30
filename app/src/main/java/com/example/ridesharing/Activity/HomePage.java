package com.example.ridesharing.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.ridesharing.R;
import com.example.ridesharing.Utils.PrefUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Button mFromDate,mToDate,mShowCars;
    private int mDay,mMonth,mYear,mHour,mMinute;
    public String fromDate="",fromTime="",toDate="",toTime="";
    private TextView fromDateTv,toDateTv,mTextView,muserName;
    private ImageView mFromImageView,mToImageView;
    private View navHeader;
    public boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        mFromDate = (Button) findViewById(R.id.from_date_btn);
        mToDate = (Button) findViewById(R.id.to_date_btn);
        mShowCars = (Button) findViewById(R.id.show_cars_btn);
        mFromDate.setOnClickListener(this);
        mToDate.setOnClickListener(this);
        mShowCars.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navHeader = navigationView.getHeaderView(0);
        muserName = (TextView) navHeader.findViewById(R.id.usr_name);
        mTextView = (TextView) navHeader.findViewById(R.id.textView);

        muserName.setText(PrefUtils.getFromPrefs(this,PrefUtils.user_name,""));
        mTextView.setText(PrefUtils.getFromPrefs(this,PrefUtils.user_email,""));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            PrefUtils.removePrefs(this);
            Intent logout =new Intent(HomePage.this,LoginActivity.class);
            startActivity(logout);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.up_trips) {
            // Handle the camera action
            Intent trip_intent = new Intent(HomePage.this,YourTrips.class);
            trip_intent.putExtra("trip",1);
            startActivity(trip_intent);
        } else if (id == R.id.past_trips) {
            Intent trip_intent = new Intent(HomePage.this,YourTrips.class);
            trip_intent.putExtra("trip",2);
            startActivity(trip_intent);
        }
        //else if (id == R.id.nav_slideshow) { }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view==this.mFromDate){
            flag=false;
            showDateView(1);
            showDate();
            Log.d("fromview",fromDate+"");

            Log.d("fromview",fromDate+"");
             //fromDateTv.setText(fromDate);

        }

        if (view==this.mToDate){
            flag = true;
            showDate();
            showDateView(2);
            //toDateTv.setText(toDate);
            //toDateTv.append(toTime);
        }

        if (view == this.mShowCars){
            String strDate=null,ToDate=null;
            String from = mFromDate.getText().toString().trim();
            from = from.replace("\n", "");
            String to = mToDate.getText().toString().trim();
            to = to.replace("\n","");

           /* String to =mToDate.getText().toString();
            try {
                Date date=new SimpleDateFormat("dd/MM/yyyy").parse(from);
                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(to);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
                strDate = dateFormat.format(date);
                ToDate  =dateFormat.format(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            finally {
                Log.d("final",strDate+""+ToDate);
            }*/

            Intent intent = new Intent(HomePage.this,ShowCars.class);
            intent.putExtra("from",from);
            intent.putExtra("to",to);
            startActivity(intent);
        }

    }

    private void showDateView(int i) {
        if (i==1) {
            mFromDate.setCompoundDrawablesRelative(null,null,null,null);
            //fromDateTv.setVisibility(View.VISIBLE);
        }
        if (i==2){
            mToDate.setCompoundDrawablesRelative(null,null,null,null);
            //mToImageView.setVisibility(View.GONE);
            //toDateTv.setVisibility(View.VISIBLE);
        }
    }

    private void showDate(){
        final Calendar c = Calendar.getInstance();
        String from1;
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date date=null;
                        try {
                            date = formatter.parse(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            //System.out.println("Date is: "+date);
                        } catch (ParseException e) {e.printStackTrace();}
                        Calendar cl = Calendar.getInstance();
                        SimpleDateFormat formatter1 = new SimpleDateFormat("E, dd MMM yyyy ");
                        fromDate = formatter1.format(date);
                        Log.d("from",fromDate);
                        //fromDateTv.setText(fromDate);
                        showTime();
                    }
                }, mYear, mMonth, mDay);
        // mDate.setText(datePickerDialog.getDatePicker().getMonth());
        datePickerDialog.show();
    }


    private void showTime() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        fromTime=hourOfDay+":"+minute;
                        fromDate+="\n"+fromTime;
                        String toDate;

                        if (flag){
                            //mToDate.setBackgroundResource(0);
                            toDate = fromDate;
                            //if ()
                            mToDate.setText(toDate);
                        }
                        //mFromDate.setBackgroundResource(0);
                        else {
                            mFromDate.setText(fromDate);
                        }
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }
}
