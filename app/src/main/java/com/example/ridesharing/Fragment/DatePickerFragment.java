package com.example.ridesharing.Fragment;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;
import com.example.ridesharing.R;
import java.util.Calendar;
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public String date;
    int flag;
    public DatePickerFragment()
    {}
    @SuppressLint("ValidFragment")
    public DatePickerFragment(int flag)
    {
        this.flag = flag;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
    public void onDateSet(DatePicker view, int year, int month, int day) {
        date = day+"-"+(month+1)+"-"+year;
        if(day < 10)
            date = "0"+day+"-"+(month+1)+"-"+year;
        if((month+1) < 10)
            date = day+"-"+"0"+(month+1)+"-"+year;
        if(day < 10 && (month+1) < 10)
            date = "0"+day+"-"+"0"+(month+1)+"-"+year;
        DialogFragment newFragment = new TimePickerFragment(flag,date);
                    newFragment.show(getFragmentManager(), "timePicker");
    }
}