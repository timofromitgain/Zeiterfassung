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

import com.zeiterfassung.timo.Zeiterfassung.Interface.DialogBearbeitenInterface;
import com.zeiterfassung.timo.Zeiterfassung.R;

public class DialogBearbeiten extends AppCompatDialogFragment {

    private String text = "";
    Boolean bAnmerkung,
            bPflichtfeld;
    private EditText editText;
  private DialogBearbeitenInterface dialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("Title");

        text = getArguments().getString("Text");
        bPflichtfeld = getArguments().getBoolean("Pflichtfeld");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_dialog_bearbeiten, null);

        bAnmerkung = title.equals("Anmerkung");
        editText = view.findViewById(R.id.editDialog);
        editText.setText(text);

        builder.setView(view)
                .setTitle(title)
                .setNegativeButton("Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        text = editText.getText().toString();
                        if (text.equals("")){
                            text = null;
                        }
                        if (bPflichtfeld) {
                            if (!text.equals("")) {
                                dialogListener.listenerBearbeiten(text, bAnmerkung);
                            } else {
                                String meldungNachname = getString(R.string.meldungNachame);
                                Toast.makeText(getActivity(), meldungNachname,
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            dialogListener.listenerBearbeiten(text, bAnmerkung);
                        }

                    }
                });
        return builder.create();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogBearbeitenInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "Dialoglistener muss erstellt werden");
        }
    }




}
