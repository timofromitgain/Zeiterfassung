package com.zeiterfassung.timo.Zeiterfassung.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zeiterfassung.timo.Zeiterfassung.Interface.DialogDatenbankInterface;
import com.zeiterfassung.timo.Zeiterfassung.R;

public class DialogDbErsetzen extends AppCompatDialogFragment {

    private TextView tvMeldung;
    Boolean bDbUeberschreiben;
   private DialogDatenbankInterface dialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_ja_nein,null);

        tvMeldung = view.findViewById(R.id.tvMeldungDialog);
        String meldungDatenbankExistiert = getString(R.string.meldungDatenbankExistiert);
        tvMeldung.setText("Es gibt für den Tag bereits einen Tätigkeitsbericht\nSoll die überschrieben werden ? ");

        builder.setView(view)
                .setTitle("Warnung!")
                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogListener.listenerUeberschreiben(false);
                    }
                })
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogListener.listenerUeberschreiben(true);
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
            throw  new ClassCastException(context.toString() +
            "Dialoglistener muss erstellt werden");
        }
    }


}
