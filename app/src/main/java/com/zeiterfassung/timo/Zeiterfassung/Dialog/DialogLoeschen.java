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

import com.zeiterfassung.timo.Zeiterfassung.Interface.DialogLoeschenInterface;
import com.zeiterfassung.timo.Zeiterfassung.R;

public class DialogLoeschen extends AppCompatDialogFragment {

    private TextView tvMeldung;
    private DialogLoeschenInterface dialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_ja_nein, null);

        tvMeldung = view.findViewById(R.id.tvMeldungDialog);
        String meldungLoeschen = getString(R.string.meldungLoeschen);
        tvMeldung.setText(meldungLoeschen);

        builder.setView(view)
                .setTitle("Löschen")
                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogListener.listenerLoeschen(false);
                    }
                })
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogListener.listenerLoeschen(true);
                    }
                });


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogLoeschenInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "Dialoglistener muss erstellt werden");
        }
    }

}
