package com.example.timo.Zeiterfassung.Activity;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.timo.Zeiterfassung.Beans.Position;
import com.example.timo.Zeiterfassung.Dialog.DialogZeit;
import com.example.timo.Zeiterfassung.Helfer.DatenbankHelfer;
import com.example.timo.Zeiterfassung.Helfer.Kunde;
import com.example.timo.Zeiterfassung.Interface.IdialogZeit;
import com.example.timo.Zeiterfassung.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AddPosition extends AppCompatActivity implements IdialogZeit {
    LinearLayout linearLayoutKunde;
    Button btUebernehmen;
    Spinner spinner;
    Binder binder;
    ImageButton btnAnfang;
    ImageButton btnEnde;
    TextView tvUhrzeitAnfang;
    TextView tvUhrzeitEnde;
    DatenbankHelfer dbHelfer;
    Position position;
    boolean startTime;
    String selectedFirma;
    RadioGroup radioGroup;
    String selectedArt = "Kunde";
    Kunde kunde;
    RadioButton rbKunde,rbSonstiges;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = new Position("");
        setContentView(R.layout.activity_add_position);
        btUebernehmen = findViewById(R.id.btnUebernehmenPosition);
        btnAnfang = findViewById(R.id.imtBtnAnfangEditPos);
        btnEnde = findViewById(R.id.imtBtnEndeEditPos);
        tvUhrzeitAnfang = findViewById(R.id.tvUhrzeitBeginnPos);
        tvUhrzeitEnde = findViewById(R.id.tvUhrzeitEndePos);
        spinner = findViewById(R.id.spinnerArt);
        radioGroup = findViewById(R.id.rgAuswahlPosition);
        rbKunde = findViewById(R.id.rbKunde);
        rbSonstiges = findViewById(R.id.rbSonstiges);
        linearLayoutKunde = findViewById(R.id.linearLayoutKunde);
        SpinnerAdapter spinnerAdapter;
        kunde = new Kunde();
        dbHelfer = new DatenbankHelfer(AddPosition.this);
        ArrayList<Kunde> listKunde = new ArrayList<Kunde>();
        listKunde = dbHelfer.getListKunde();
        ArrayList<String> listFirma = new ArrayList<String>();
        rbKunde.setChecked(true);

        for (int i = 0;i<listKunde.size();i++){
            listFirma.add(listKunde.get(i).getFirma());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listFirma);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        btUebernehmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedArt.equals("Kunde")){
                    kunde.setFirma(selectedFirma);
                    position.setNamePosition("Kunde");
                    position.setKunde(kunde);
                }else{
                    position.setNamePosition("Sonstiges");
                }

                Intent intent = new Intent("neuePosition");
                binder = new Binder();
                intent.putExtra("Position",position);
                LocalBroadcastManager.getInstance(binder.getService()).sendBroadcast(intent);

                //   setResult(RESULT_OK,intent2);
                finish();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFirma = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAnfang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = true;
                DialogZeit dialogZeit = new DialogZeit();
                dialogZeit.show(getSupportFragmentManager(), "dia");
            }
        });

        btnEnde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = false;
                DialogZeit dialogZeit = new DialogZeit();
                dialogZeit.show(getSupportFragmentManager(), "dia");
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                CharSequence charSequence = rb.getText();
                selectedArt = String.valueOf(charSequence);
                if (selectedArt.equals("Kunde")){
                    linearLayoutKunde.setVisibility(View.VISIBLE);
                }else{
                    linearLayoutKunde.setVisibility(View.GONE);
                }


            }
        });

    }

    @Override
    public void listenerZeit(Calendar calendar) {
        if (startTime){
            position.setStartTime(calendar);
            tvUhrzeitAnfang.setText("Beginn: " + position.getArbeitsZeitBeginn());
        }else{
            position.setEndTime(calendar);
            tvUhrzeitEnde.setText("Ende: " + position.getArbeitsZeitEnde());
        }
    }

    public class Binder extends android.os.Binder {
        public AddPosition getService() {
            return AddPosition.this;
        }
    }
}
