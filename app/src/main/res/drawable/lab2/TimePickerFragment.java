package co.edu.udea.compumovil.gr02_20181.lab2;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;

;

public class TimePickerFragment extends DialogFragment implements
        TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Set the current date in the DatePickerFragment
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        //return new TimePickerDialog(getActivity(), this, hour, minute,
        //     DateFormat.is24HourFormat(getActivity()));

        return new TimePickerDialog(getActivity(), this, hour, minute,true
        );


    }

    // Callback to TimePickerActivity.onDateSet() to update the UI
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        ((TimePickerDialog.OnTimeSetListener)getFragmentManager().findFragmentByTag("platesFragmentTag")).onTimeSet(view, hourOfDay, minute);
    }

}
