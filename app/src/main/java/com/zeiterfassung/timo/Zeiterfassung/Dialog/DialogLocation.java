package com.zeiterfassung.timo.Zeiterfassung.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zeiterfassung.timo.Zeiterfassung.Helfer.Geo;
import com.zeiterfassung.timo.Zeiterfassung.Interface.DialogLocationInterface;
import com.zeiterfassung.timo.Zeiterfassung.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class DialogLocation extends AppCompatDialogFragment  {

    private TextView tv;
  private DialogLocationInterface dialogListener;
    Boolean bAnmerkung;
    String strasse,stadt;
    Geo geo;
    LatLng latLng;
    int id;
    int id2;
    String intervalls;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        HashMap<String,Integer> locationOldApi = new HashMap<>();
        HashMap<String,Integer> locationNewApi = new HashMap<>();


        locationOldApi= (HashMap<String, Integer>) getArguments().getSerializable("oldApi");
        locationNewApi= (HashMap<String, Integer>) getArguments().getSerializable("newApi");


  //      String Wert = getArguments().getString("Wert");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog,null);

        builder.setView(view)
                .setTitle("Location")
                .setNegativeButton("Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });



        if (locationNewApi != null && locationOldApi != null){
            intervalls = "zwischen 0 und 5: Alt: " + String.valueOf(locationOldApi.get("unter 5"))+
                    "    Neu: " + String.valueOf(locationNewApi.get("unter 5")) + "\n";
            intervalls = intervalls  + "zwischen 5 und 10: Alt: " + String.valueOf(locationOldApi.get("unter 10"))+
                    "    Neu: " + String.valueOf(locationNewApi.get("unter 10")) + "\n";
            intervalls = intervalls  + "zwischen 10 und 15: Alt: " + String.valueOf(locationOldApi.get("unter 15"))+
                    "    Neu: " + String.valueOf(locationNewApi.get("unter 15")) + "\n";
            intervalls = intervalls  + "zwischen 15 und 20: Alt: " + String.valueOf(locationOldApi.get("unter 20"))+
                    "    Neu: " + String.valueOf(locationNewApi.get("unter 20")) + "\n";
            intervalls = intervalls  + "zwischen 20 und 30: Alt: " + String.valueOf(locationOldApi.get("unter 30"))+
                    "    Neu: " + String.valueOf(locationNewApi.get("unter 30")) + "\n";
            intervalls =intervalls  +  "zwischen 30 und 40: Alt: " + String.valueOf(locationOldApi.get("unter 40"))+
                    "    Neu: " + String.valueOf(locationNewApi.get("unter 40")) + "\n";
            intervalls =intervalls  +  "über 40 " + String.valueOf(locationOldApi.get("über 40"))+
                    "    Neu: " + String.valueOf(locationNewApi.get("über 40")) + "\n";
        }else if (locationNewApi == null){
            intervalls = "zwischen 0 und 5: Alt: " + String.valueOf(locationOldApi.get("unter 5")+ "\n");
            intervalls = intervalls  + "zwischen 5 und 10: Alt: " + String.valueOf(locationOldApi.get("unter 10"))+ "\n";
            intervalls = intervalls  + "zwischen 10 und 15: Alt: " + String.valueOf(locationOldApi.get("unter 15"))+ "\n";
            intervalls = intervalls  + "zwischen 15 und 20: Alt: " + String.valueOf(locationOldApi.get("unter 20"))+ "\n";
            intervalls = intervalls  + "zwischen 20 und 30: Alt: " + String.valueOf(locationOldApi.get("unter 30"))+ "\n";
            intervalls =intervalls  +  "zwischen 30 und 40: Alt: " + String.valueOf(locationOldApi.get("unter 40"))+ "\n";
            intervalls =intervalls  +  "über 40 " + String.valueOf(locationOldApi.get("über 40"));

        }else {
            intervalls = "zwischen 0 und 5: Alt: " + String.valueOf(locationNewApi.get("unter 5"))+ "\n";
            intervalls = intervalls  + "zwischen 5 und 10: Alt: " + String.valueOf(locationNewApi.get("unter 10"))+ "\n";
            intervalls = intervalls  + "zwischen 10 und 15: Alt: " + String.valueOf(locationNewApi.get("unter 15"))+ "\n";
            intervalls = intervalls  + "zwischen 15 und 20: Alt: " + String.valueOf(locationNewApi.get("unter 20"))+ "\n";
            intervalls = intervalls  + "zwischen 20 und 30: Alt: " + String.valueOf(locationNewApi.get("unter 30"))+ "\n";
            intervalls =intervalls  +  "zwischen 30 und 40: Alt: " + String.valueOf(locationNewApi.get("unter 40"))+ "\n";
            intervalls =intervalls  +  "über 40 " + String.valueOf(locationNewApi.get("über 40"));

        }



        tv = view.findViewById(R.id.dialogLocation);
    ///    String intervalls = "Jau\nfff\nJau\nfff\nJau\nfff\nJau\nfff\nJau\nfff\nJau\nfff\nJau\nfff\nJau\nfff\nJau\nfff\nJau\nfff\nJau\nfff\n";
        tv.setText(intervalls);

        return builder.create();
    }






}
