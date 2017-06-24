package com.hackerman.kkn.input;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by hackerman on 21/06/17.
 */

public class EndTimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    int year, month, day;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(((InputActivity)getActivity()).tanggal);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = 0;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = new GregorianCalendar(year, month, day, hourOfDay, minute);
        ((InputActivity)getActivity()).endTime = calendar.getTimeInMillis();
        ((InputActivity)getActivity()).setEndTimeButton(calendar.getTimeInMillis());
    }
}
