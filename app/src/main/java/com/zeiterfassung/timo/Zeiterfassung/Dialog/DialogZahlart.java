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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;

import com.zeiterfassung.timo.Zeiterfassung.Interface.DialogBezahlungInterface;
import com.zeiterfassung.timo.Zeiterfassung.R;

public class DialogZahlart extends AppCompatDialogFragment {
    public String
            kundeZahlart,
            kundeZahlungsintervall,
            kundeZahlungszeitpunkt,
            rechnungszahler,
            barzahler,
            woechentlich,
            monatlich,
            monatlich2,
            monatlich3,
            monatsanfang;

    private RadioButton rbRechnungszahler,
            rbBarzahler,
            rbWoechentlich,
            rbMonatlich,
            rb2Monatlich,
            rb3Moantlich,
            rbMonatsanfang,
            rbMonatsende;
    private RadioGroup rgZahlart,
            rgZahlungsintervall,
            rgZahlungszeitpunkt;
    private TableRow trZahlungsintervall,
            trZahlungszeitpunkt;
    private EditText editText;
    private DialogBezahlungInterface dialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        kundeZahlart = getArguments().getString("zahlart");
        kundeZahlungsintervall = getArguments().getString("zahlungsintervall");
        kundeZahlungszeitpunkt = getArguments().getString("zeitpunkt");


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_dialog_zahlart, null);

        initialisiere(view);

        builder.setView(view)
                .setTitle("Bezahlung")
                .setNegativeButton("Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (kundeZahlart.equals(rechnungszahler)) {
                            kundeZahlungsintervall = null;
                            kundeZahlungszeitpunkt = null;
                        } else {
                            if (kundeZahlungsintervall != null && kundeZahlungsintervall.equals(woechentlich)) {
                                kundeZahlungszeitpunkt = null;
                            }
                        }
                        dialogListener.listenerBezahlung(kundeZahlart, kundeZahlungsintervall, kundeZahlungszeitpunkt);
                    }
                });


        if (kundeZahlart.equals(barzahler)) {
            rbBarzahler.setChecked(true);
            trZahlungsintervall.setVisibility(View.VISIBLE);
            if (kundeZahlungsintervall.equals(woechentlich)) {
                rbWoechentlich.setChecked(true);
            } else if (kundeZahlungsintervall.equals(monatlich)) {
                rbMonatlich.setChecked(true);
                trZahlungszeitpunkt.setVisibility(View.VISIBLE);
            } else if (kundeZahlungsintervall.equals(monatlich2)) {
                rb2Monatlich.setChecked(true);
                trZahlungszeitpunkt.setVisibility(View.VISIBLE);
            } else {
                rb3Moantlich.setChecked(true);
                trZahlungszeitpunkt.setVisibility(View.VISIBLE);
            }
            if (String.valueOf(kundeZahlungszeitpunkt).equals(monatsanfang)) {
                rbMonatsanfang.setChecked(true);
            } else {
                rbMonatsende.setChecked(true);
            }
        } else {
            rbRechnungszahler.setChecked(true);
        }

        rgZahlart.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = view.findViewById(checkedId);
                CharSequence charSequence = rb.getText();
                kundeZahlart = String.valueOf(charSequence);
                if (kundeZahlart.equals(rechnungszahler)) {

                    trZahlungsintervall.setVisibility(View.GONE);
                    trZahlungszeitpunkt.setVisibility(View.GONE);


                } else {
                    trZahlungsintervall.setVisibility(View.VISIBLE);
                        if (kundeZahlungsintervall == null){
                            kundeZahlungsintervall = woechentlich;
                        }else{
                            if (!kundeZahlungsintervall.equals(woechentlich)){
                                trZahlungszeitpunkt.setVisibility(View.VISIBLE);
                            }
                        }
                }

            }
        });


        rgZahlungsintervall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = view.findViewById(checkedId);
                CharSequence charSequence = rb.getText();
                kundeZahlungsintervall = String.valueOf(charSequence);
                if (kundeZahlungsintervall.equals(woechentlich)) {
                    trZahlungszeitpunkt.setVisibility(View.GONE);

                } else {
                    trZahlungszeitpunkt.setVisibility(View.VISIBLE);
                    if (kundeZahlungszeitpunkt == null){
                        kundeZahlungszeitpunkt = monatsanfang;
                    }
                }

            }
        });

        rgZahlungszeitpunkt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = view.findViewById(checkedId);
                CharSequence charSequence = rb.getText();
                kundeZahlungszeitpunkt = String.valueOf(charSequence);
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogBezahlungInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "Dialoglistener muss erstellt werden");
        }
    }

    private void initialisiere(View view) {
        rbRechnungszahler = view.findViewById(R.id.rbRechnungszahler_Dialog);
        rbBarzahler = view.findViewById(R.id.rbBarzahler_Dialog);
        rbWoechentlich = view.findViewById(R.id.rbWoechentlich_Dialog);
        rbMonatlich = view.findViewById(R.id.rbMonatlich_Dialog);
        rb2Monatlich = view.findViewById(R.id.rb2Monatlich_Dialog);
        rb3Moantlich = view.findViewById(R.id.rb3Monatlich_Dialog);
        rbMonatsanfang = view.findViewById(R.id.rbMonatsanfang_Dialog);
        rbMonatsende = view.findViewById(R.id.rbMonatsende_Dialog);
        rgZahlart = view.findViewById(R.id.rgZahlart_Dialog);
        rgZahlungsintervall = view.findViewById(R.id.rgZahlungsintervall_Dialog);
        rgZahlungszeitpunkt = view.findViewById(R.id.rgZahlungszeitpunkt_Dialog);
        trZahlungsintervall = view.findViewById(R.id.trZahlungsintervall_Dialog);
        trZahlungszeitpunkt = view.findViewById(R.id.trZahlungszeitpunkt_Dialog);

        barzahler = getString(R.string.barzahler);
        woechentlich = getString(R.string.woechentlich);
        rechnungszahler = getString(R.string.rechnungszahler);
        monatlich = getString(R.string.monatlich);
        monatlich2 = getString(R.string.monatlich2);
        monatlich3 = getString(R.string.monatlich3);
        monatsanfang = getString(R.string.monatsanfang);
    }


}
