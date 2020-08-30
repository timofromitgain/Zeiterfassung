package com.zeiterfassung.timo.Zeiterfassung.Dialog;

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
import android.widget.Toast;

import com.zeiterfassung.timo.Zeiterfassung.Interface.DialogBearbeitenZeitungInterface;
import com.zeiterfassung.timo.Zeiterfassung.R;

public class DialogBearbeitenZeitung extends AppCompatDialogFragment {

    private EditText editBild, editWelt, editWeltK;
    private Button btBildDown, btBildUp, btWeltDown, btWeltUp, btWeltKDown, btWeltkUp;
    private Boolean bToast;
    private DialogBearbeitenZeitungInterface dialogListener;
    int id;
    int id2;
    int anzahl = 0;
    int anzahlBild;
    int anzahlWamS;
    int anzahlWamSK;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        anzahlBild = getArguments().getInt("anzahlBild", 0);
        anzahlWamS = getArguments().getInt("anzahlWamS", 0);
        anzahlWamSK = getArguments().getInt("anzahlWamSK", 0);
        bToast = getArguments().getBoolean("Toast", false);
        String Title = getArguments().getString("Title");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_bearbeiten_zeitung, null);

        builder.setView(view)
                .setTitle("Anzahl Zeitungen")
                .setNegativeButton("Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (bToast) {
                            if (anzahlBild == 0 && anzahlWamS == 0 && anzahlWamSK == 0) {
                                String meldungZeitung = getString(R.string.meldungZeitung);
                                Toast.makeText(getContext(), meldungZeitung,
                                        Toast.LENGTH_LONG).show();

                            } else {
                                dialogListener.listenerZeitung(anzahlBild, anzahlWamS, anzahlWamSK);
                            }
                        } else {
                            dialogListener.listenerZeitung(anzahlBild, anzahlWamS, anzahlWamSK);
                        }

                    }
                });
        editBild = view.findViewById(R.id.editBild);
        editWelt = view.findViewById(R.id.editWelt);
        editWeltK = view.findViewById(R.id.editWeltK);
        btBildDown = view.findViewById(R.id.btZeitungBildDown);
        btBildUp = view.findViewById(R.id.btZeitungBildUp);
        btWeltDown = view.findViewById(R.id.btZeitungWeltDown);
        btWeltUp = view.findViewById(R.id.btZeitungWeltUp);
        btWeltKDown = view.findViewById(R.id.btZeitungWeltKDown);
        btWeltkUp = view.findViewById(R.id.btZeitungWeltKUp);

        editBild.setText(String.valueOf(anzahlBild));
        editWelt.setText(String.valueOf(anzahlWamS));
        editWeltK.setText(String.valueOf(anzahlWamSK));

        btBildDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (anzahlBild != 0) {
                    anzahlBild--;
                    aktuallisiereEditText(editBild, anzahlBild);
                }

            }
        });

        btBildUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anzahlBild++;
                aktuallisiereEditText(editBild, anzahlBild);

            }
        });

        btWeltDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (anzahlWamS != 0) {
                    anzahlWamS--;
                    aktuallisiereEditText(editWelt, anzahlWamS);
                }

            }
        });

        btWeltUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anzahlWamS++;
                aktuallisiereEditText(editWelt, anzahlWamS);
            }
        });

        btWeltkUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anzahlWamSK++;
                aktuallisiereEditText(editWeltK, anzahlWamSK);
             }
        });

        btWeltKDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (anzahlWamSK != 0) {
                    anzahlWamSK--;
                    aktuallisiereEditText(editWeltK, anzahlWamSK);
                }
            }
        });


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogBearbeitenZeitungInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "Dialoglistener muss erstellt werden");
        }
    }


    void aktuallisiereEditText(EditText editText, int anzahl) {
        editText.setText(String.valueOf(anzahl));
    }

    void erhoehe(EditText editText) {
        anzahl = Integer.parseInt(editText.getText().toString());
        anzahl = anzahl + 1;
    }

    void verringere(EditText editText) {
        anzahl = Integer.parseInt(editText.getText().toString());
        anzahl = anzahl - 1;
    }

}
