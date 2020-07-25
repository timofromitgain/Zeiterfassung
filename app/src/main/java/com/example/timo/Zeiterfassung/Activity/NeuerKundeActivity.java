package com.example.timo.Zeiterfassung.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timo.Zeiterfassung.Helfer.DatenbankHelfer;
import com.example.timo.Zeiterfassung.Helfer.Datum;
import com.example.timo.Zeiterfassung.Helfer.Geo;
import com.example.timo.Zeiterfassung.Helfer.Kunde;
import com.example.timo.Zeiterfassung.Helfer.LocationService;
import com.example.timo.Zeiterfassung.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class NeuerKundeActivity extends AppCompatActivity {

    private String
            firma,
            ansprechpartner,
            kundeStrasse,
            kundeStadt,
            taetigkeit1,
            taetigkeit2,
            taetigkeit3,
            anmerkung = "";


    private int
            radius = 75;

    private Double
            latitudeKunde,
            longitudeKunde;


    private Button
            btnSpeichern;

    private EditText
            editAnsprechpartner,
            editFirma,
            editStrasse,
            editStadt,
            editAnmerkung,
            editTaetigkeit1,
            editTaetigkeit2,
            editTaetigkeit3;


    private DatenbankHelfer dbHelfer;

    private Geo geo;
    Binder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neuerkunde);
        geo = new Geo(this);
        binder = new Binder();
        initialisiere();

        dbHelfer = new DatenbankHelfer(this);

        btnSpeichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firma,
                        ansprechpartner,
                        anmerkung = "",
                        taetigkeit1,
                        taetigkeit2,
                        taetigkeit3;
                LatLng latLng;
                Datum datum = new Datum();
                setEditTextNormal(R.drawable.edittext_normal);

                firma = editFirma.getEditableText().toString();
                ansprechpartner = editAnsprechpartner.getEditableText().toString();
                kundeStrasse = editStrasse.getEditableText().toString();
                kundeStadt = editStadt.getEditableText().toString();
                taetigkeit1 = editTaetigkeit1.getEditableText().toString();
                taetigkeit2 = editTaetigkeit2.getEditableText().toString();
                taetigkeit3 = editTaetigkeit3.getEditableText().toString();


                if (firma.isEmpty() || kundeStrasse.isEmpty() || kundeStadt.isEmpty() || taetigkeit1.isEmpty()) {
                    if (firma.isEmpty()) {
                        editFirma.setBackgroundResource(R.drawable.edittext_fehler);
                    } else {
                        editFirma.setBackgroundResource(R.drawable.edittext_normal);
                    }
                    if (kundeStrasse.isEmpty()) {
                        editStrasse.setBackgroundResource(R.drawable.edittext_fehler);
                    } else {
                        editStrasse.setBackgroundResource(R.drawable.edittext_normal);
                    }
                    if (kundeStadt.isEmpty()) {
                        editStadt.setBackgroundResource(R.drawable.edittext_fehler);
                    } else {
                        editStadt.setBackgroundResource(R.drawable.edittext_normal);
                    }
                    if (taetigkeit1.isEmpty()) {
                        editTaetigkeit1.setBackgroundResource(R.drawable.edittext_fehler);
                    } else {
                        editTaetigkeit1.setBackgroundResource(R.drawable.edittext_normal);
                    }
                    firma = "";
                    kundeStrasse = "";
                    kundeStadt = "";
                    String medlungFehlerEingabe = getString(R.string.meldungFehlerBeiDerEingabe);
                    Toast.makeText(getApplicationContext(), medlungFehlerEingabe, Toast.LENGTH_SHORT).show();
                    return;
                }


                ansprechpartner = editAnsprechpartner.getEditableText().toString();
                if (ansprechpartner.isEmpty()) {
                    ansprechpartner = null;
                }
                taetigkeit2 = editTaetigkeit2.getEditableText().toString();
                if (taetigkeit2.isEmpty()) {
                    taetigkeit2 = null;
                }
                taetigkeit3 = editTaetigkeit3.getEditableText().toString();
                if (taetigkeit3.isEmpty()) {
                    taetigkeit3 = null;
                }


                latLng = geo.setGeo(kundeStrasse, kundeStadt);
                latitudeKunde = latLng.latitude;
                longitudeKunde = latLng.longitude;
                if (!(latitudeKunde > 0) || (!(longitudeKunde > 0))) {
                    String meldungAdresseNichtGefunden = getString(R.string.meldungAdresseNichtGefunden);
                    Toast.makeText(getApplicationContext(), meldungAdresseNichtGefunden, Toast.LENGTH_SHORT).show();
                    return;
                }


                Kunde kunde = new Kunde(
                        getApplicationContext(),
                        "",
                        firma,
                        "TEST",
                        kundeStrasse,
                        kundeStadt,
                null,
                        anmerkung,
                        radius,
                        latitudeKunde,
                        longitudeKunde,
                        0);
                Toast.makeText(getApplicationContext(), "Gespeichert", Toast.LENGTH_LONG).show();
                dbHelfer.datensatzEinfuegen(new Kunde(
                                getApplicationContext(),
                                "dd",
                                firma,
                                "TEST",
                                kundeStrasse,
                                kundeStadt,
null,
                                anmerkung,
                                radius,
                                latitudeKunde,
                                longitudeKunde,
                                0),
                        "Kunde"
                );
                dbHelfer.datenSatzEinfuegenFirebase(new Kunde(
                        getApplicationContext(),
                                "d",
                                firma,
                                "TEST",
                                kundeStrasse,
                                kundeStadt,
                                null,
                                anmerkung,
                                radius,
                                latitudeKunde,
                                longitudeKunde,
                                0)

                );
                if (LocationService.getInstance().getAktiviert()) {
                    int id;
                    ArrayList<Kunde> listDummy = new ArrayList<Kunde>();
                    listDummy = dbHelfer.getListKunde();
                    id = listDummy.get(listDummy.size()-1).getId();
                    kunde.setId(id);
                    Intent intent = new Intent("neuerKunde");
                    intent.putExtra("Kunde", kunde);
                    Log.d("status","sendBroadcast");
                    getApplicationContext().sendBroadcast(intent);
               //     LocalBroadcastManager.getInstance(binder.getService()).sendBroadcast(intent);

                }
            }

        });


    }

    private void initialisiere() {

        editFirma = findViewById(R.id.editFirma);
        editAnsprechpartner = findViewById(R.id.editAnsprechpartner);
        editStrasse = findViewById(R.id.editStrasse_NeuerAuftrag);
        editStadt = findViewById(R.id.editStadt_NeuerAuftrag);
        editAnmerkung = findViewById(R.id.editKundeAnmerkung_NeuerAuftrag);
        editTaetigkeit1 = findViewById(R.id.editTaetigkeit1);
        editTaetigkeit2 = findViewById(R.id.editTaetigkeit2);
        editTaetigkeit3 = findViewById(R.id.editTaetigkeit3);
        btnSpeichern = findViewById(R.id.btnSpeichern_NeuerAuftrag);


    }

    private void setEditTextNormal(int editTextNormal) {
        editFirma.setBackgroundResource(editTextNormal);
        editAnsprechpartner.setBackgroundResource(editTextNormal);
        editStrasse.setBackgroundResource(editTextNormal);
        editStadt.setBackgroundResource(editTextNormal);
        editAnmerkung.setBackgroundResource(editTextNormal);
        editTaetigkeit1.setBackgroundResource(editTextNormal);
        editTaetigkeit2.setBackgroundResource(editTextNormal);
        editTaetigkeit3.setBackgroundResource(editTextNormal);
    }

    public class Binder extends android.os.Binder {
        public NeuerKundeActivity getService() {
            return NeuerKundeActivity.this;
        }
    }
}

