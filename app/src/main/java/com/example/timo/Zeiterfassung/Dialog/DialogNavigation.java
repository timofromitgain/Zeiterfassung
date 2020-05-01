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
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.timo.Zeiterfassung.R;

public class DialogNavigation extends AppCompatDialogFragment {
    LinearLayout linerLayoutNavi;
    private int anzahlNaviKunden = 1;
    private RadioGroup rgNavigation;
    private RadioButton rbNaviNaechsterKunde;
    private Button btnVerringern, btnErhoehen;
    private TextView tvAnzahlKunden;
    private DialogListener dialogListener;
    private NumberPicker numberPicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_navigation, null);

        rgNavigation = view.findViewById(R.id.rgNavigation_DialogNavigation);
        btnVerringern = view.findViewById(R.id.btnAnzahlVerringern_DialogNavigation);
        btnErhoehen = view.findViewById(R.id.btnAnzahlErhoehen_DialogNavigation);
        tvAnzahlKunden = view.findViewById(R.id.tvAnzah_DialogNavigation);
        linerLayoutNavi = view.findViewById(R.id.lv_DialogNavigation);
        rbNaviNaechsterKunde = view.findViewById(R.id.rbNaechsterKunde_DialogNavigation);
        numberPicker = view.findViewById(R.id.numberpicker_DialogNavigation);

        rbNaviNaechsterKunde.setChecked(true);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(8);
        builder.setView(view)
                .setTitle("Navigation")
                .setNegativeButton("Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        anzahlNaviKunden = numberPicker.getValue();
                        dialogListener.listenerNavigation(anzahlNaviKunden);
                    }
                });


        btnVerringern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String anzahl = tvAnzahlKunden.getText().toString();
                anzahlNaviKunden = Integer.parseInt(anzahl) - 1;
                anzahl = String.valueOf(anzahlNaviKunden);
                tvAnzahlKunden.setText(anzahl);
            }
        });
        btnErhoehen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String anzahl = tvAnzahlKunden.getText().toString();
                anzahlNaviKunden = Integer.parseInt(anzahl) + 1;
                anzahl = String.valueOf(anzahlNaviKunden);
                tvAnzahlKunden.setText(anzahl);
            }
        });

        rgNavigation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                String text;
                RadioButton rb = view.findViewById(checkedId);
                CharSequence charSequence = rb.getText();
                text = String.valueOf(charSequence);
                String optNaechsterKunde = getString(R.string.navigationNaechsteKunde);
                if (text.equals(optNaechsterKunde)) {
                    linerLayoutNavi.setVisibility(View.GONE);
                    anzahlNaviKunden = 1;
                } else {
                    anzahlNaviKunden = 2;
                    linerLayoutNavi.setVisibility(View.VISIBLE);
                }
            }

        });


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogNavigation.DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "Dialoglistener muss erstellt werden");
        }
    }

    public interface DialogListener {
        void listenerNavigation(Integer anzahl);
    }
}
