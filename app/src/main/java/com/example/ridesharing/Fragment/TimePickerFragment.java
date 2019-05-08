package com.example.ridesharing.Fragment;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
import com.example.ridesharing.R;
import java.util.Calendar;
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public String time;
    TextView mTextView;
    int flag;
    String date;
    public TimePickerFragment()
    {}
    @SuppressLint("ValidFragment")
    public TimePickerFragment(int flag, String date)
    {
        this.flag = flag;
        this.date = date;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        time = hourOfDay+":"+minute;
        if(minute < 10)
            time = hourOfDay+":"+"0"+minute;
        if(hourOfDay < 10)
            time = "0"+hourOfDay+":"+minute;
        if(minute < 10 && hourOfDay < 10)
            time = "0"+hourOfDay+":"+"0"+minute;
        mTextView = getActivity().findViewById(R.id.dateTime1);
        if(flag == 1)
        {
            mTextView = getActivity().findViewById(R.id.dateTime1);
            mTextView.setText(date+" "+time);
            mTextView.setTextColor(Color.BLACK);
        }
        else
        {
            mTextView = getActivity().findViewById(R.id.dateTime2);
            mTextView.setText(date+" "+time);
            mTextView.setTextColor(Color.BLACK);
        }
    }
}