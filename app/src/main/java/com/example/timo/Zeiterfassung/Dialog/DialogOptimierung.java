package com.example.timo.Zeiterfassung.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.timo.Zeiterfassung.Interface.DialogOptimierungInterface;
import com.example.timo.Zeiterfassung.R;

public class DialogOptimierung extends AppCompatDialogFragment {
    String text;
    Boolean bestehendeRoute = true;
    EditText editStrasse, editStadt;
    LinearLayout linerLayoutOpt;
    private Button btFestnetz;
    private Button btMobil;
    private DialogOptimierungInterface dialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_optimierung, null);


        linerLayoutOpt = view.findViewById(R.id.lv_DialogOptimierung);

        editStrasse = view.findViewById(R.id.editStrasse_DialogOptimierung);
        editStadt = view.findViewById(R.id.editStadt_DialogOptimierung);


        builder.setView(view)
                .setTitle("Routenreihenfolge bestimmen")
                .setNegativeButton("Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strasse,
                                stadt;

                        strasse = editStrasse.getText().toString();
                        stadt = editStadt.getText().toString();

                        dialogListener.listenerOptimierung(strasse, stadt);
                    }
                });


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogOptimierungInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "Dialoglistener muss erstellt werden");
        }
    }


}
