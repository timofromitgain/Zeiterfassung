package com.example.timo.Zeiterfassung.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.timo.Zeiterfassung.Helfer.Geo;
import com.example.timo.Zeiterfassung.Interface.DialogAdresseInterface;
import com.example.timo.Zeiterfassung.Interface.IdialogZeit;
import com.example.timo.Zeiterfassung.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public class DialogZeit extends AppCompatDialogFragment  {

    private EditText editStrasse,editStadt;
  private IdialogZeit iDialogZeit;
    Boolean bAnmerkung;
    String strasse,stadt;
    Geo geo;
    LatLng latLng;
    int id;
    int id2;
    int std,min,sek;
    TimePicker timePicker;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {





  //      String Wert = getArguments().getString("Wert");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_zeit,null);

        builder.setView(view)
                .setTitle("Zeit")
                .setNegativeButton("Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        timePicker = view.findViewById(R.id.timePicker);
                        timePicker.setIs24HourView(true);
                        timePicker.is24HourView();
                        std = timePicker.getHour();
                        min = timePicker.getMinute();
                      //  sek = 55;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,std,min,sek);


                           iDialogZeit.listenerZeit(calendar);

                    }
                });




        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            iDialogZeit = (IdialogZeit) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString() +
            "Dialoglistener muss erstellt werden");
        }
    }




}
