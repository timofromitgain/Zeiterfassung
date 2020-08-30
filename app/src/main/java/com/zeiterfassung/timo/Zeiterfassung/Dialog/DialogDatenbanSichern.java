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

import com.zeiterfassung.timo.Zeiterfassung.Interface.DialogDatenbankInterface;
import com.zeiterfassung.timo.Zeiterfassung.R;

public class DialogDatenbanSichern extends AppCompatDialogFragment {

    private EditText editText;
    private DialogDatenbankInterface dialogListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_bearbeiten, null);


        editText = view.findViewById(R.id.editDialog);
        editText.setHint("Name der Datenbank");

        builder.setView(view)
                .setTitle("Datenbank sichern")
                .setNegativeButton("Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String dbName = editText.getText().toString();
                        dialogListener.listenerDbSpeichern(dbName);
                    }
                });


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogDatenbankInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "Dialoglistener muss erstellt werden");
        }
    }


}
