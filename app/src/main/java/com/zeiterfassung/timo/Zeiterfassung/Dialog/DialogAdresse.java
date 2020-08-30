package com.zeiterfassung.timo.Zeiterfassung.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zeiterfassung.timo.Zeiterfassung.Helfer.Geo;
import com.zeiterfassung.timo.Zeiterfassung.Interface.DialogAdresseInterface;
import com.zeiterfassung.timo.Zeiterfassung.R;
import com.google.android.gms.maps.model.LatLng;

public class DialogAdresse extends AppCompatDialogFragment  {

    private EditText editStrasse,editStadt;
  private DialogAdresseInterface dialogListener;
    Boolean bAnmerkung;
    String strasse,stadt;
    Geo geo;
    LatLng latLng;
    int id;
    int id2;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        strasse = getArguments().getString("Strasse");
        geo = new Geo(getContext());

  //      String Wert = getArguments().getString("Wert");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_adresse,null);

        builder.setView(view)
                .setTitle("Adresse")
                .setNegativeButton("Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      strasse = editStrasse.getText().toString();
                      stadt = editStadt.getText().toString();
                      latLng  = geo.setGeo(strasse,stadt);
                       if (latLng.latitude==0 || latLng.longitude==0){
                           String meldungAdresse = getString(R.string.meldungAdresseNichtGefunden);
                            Toast.makeText(getContext(), meldungAdresse, Toast.LENGTH_LONG).show();
                        }else{
                            dialogListener.listenerAdresse(strasse,stadt,latLng);
    }
                    }
                });

        editStrasse = view.findViewById(R.id.dialogStrasse);
        editStadt = view.findViewById(R.id.dialogStadt);
        editStrasse.setText(strasse);
        editStadt.setText(stadt);


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogAdresseInterface) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString() +
            "Dialoglistener muss erstellt werden");
        }
    }




}
