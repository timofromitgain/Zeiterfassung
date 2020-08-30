package com.zeiterfassung.timo.Zeiterfassung.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.zeiterfassung.timo.Zeiterfassung.R;

public class DialogAnrufen extends AppCompatDialogFragment {
    private Button btnFestnetz;
    private Button btnMobil;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {





        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_anrufen,null);

        btnFestnetz = view.findViewById(R.id.btnFestnetzDialogAnrufen);
        btnMobil = view.findViewById(R.id.btnMobilDialogAnrufen);

        builder.setView(view)
                .setTitle("Anrufen")
                .setNegativeButton("Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });


        btnFestnetz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String festnetzNr = getArguments().getString("Festnetz");
                anrufen(festnetzNr);
            }
        });
        btnMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String mobilNr = getArguments().getString("Mobil");
                anrufen(mobilNr);
            }
        });
        return builder.create();
    }
    private void anrufen(String nummer){
        Intent intentAnrufen = new Intent(Intent.ACTION_DIAL);
        intentAnrufen.setData(Uri.parse("tel:"+nummer));
        startActivity(intentAnrufen);

    }
}
