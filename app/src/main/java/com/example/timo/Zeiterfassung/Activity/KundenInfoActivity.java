package com.example.timo.Zeiterfassung.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.timo.Zeiterfassung.Dialog.DialogAdresse;
import com.example.timo.Zeiterfassung.Dialog.DialogAnrufen;
import com.example.timo.Zeiterfassung.Dialog.DialogBearbeiten;
import com.example.timo.Zeiterfassung.Helfer.DatenbankHelfer;
import com.example.timo.Zeiterfassung.Helfer.Geo;
import com.example.timo.Zeiterfassung.Helfer.Kunde;
import com.example.timo.Zeiterfassung.Interface.DialogAdresseInterface;
import com.example.timo.Zeiterfassung.Interface.DialogBearbeitenInterface;
import com.example.timo.Zeiterfassung.Interface.DialogLoeschenInterface;
import com.example.timo.Zeiterfassung.R;
import com.google.android.gms.maps.model.LatLng;

public class KundenInfoActivity extends AppCompatActivity implements DialogBearbeitenInterface,
        DialogAdresseInterface,
        DialogLoeschenInterface {

    private String strBezahlung = "", dbSpalte;
    private int id, idTv, bamsAnzahl, wamsAnzahl, wamskAnzahl, position;
    private Boolean bams = false,
            wams = false,
            wamsk = false,
            kundeWurdeGeaendert = false,
            abgang;
    private CheckBox chkBamS,
            chkWamS,
            chkWamSK;
    private TextView[] tv = new TextView[8];
    private ImageButton imgbtnZeitung, imgbtnZahlart;

    private DatenbankHelfer dbHelfer;
    private BottomNavigationView bottomNavigationView;
    private Kunde kunde = new Kunde();
    private Binder binder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String kundeZahlart;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leser_info);
        final Bundle extras = getIntent().getExtras();
        binder = new Binder();



        dbHelfer = new DatenbankHelfer(this);
        kunde = (Kunde) extras.getSerializable("kunde");

        id = kunde.getId();



        initialisiere();

        Menu menu = bottomNavigationView.getMenu();
        menu.findItem(R.id.nav_loeschen).setIcon(R.drawable.ic_loeschen).setTitle("Löschen");


        tv[0].setText(kunde.getFirma());
        tv[1].setText(kunde.getAnsprechpartner());
        tv[2].setText(kunde.getStrasse());
        tv[3].setText(kunde.getStadt());
        tv[4].setText(kunde.getTaetigkeit_1());
        tv[5].setText(kunde.getTaetigkeit_2());
        tv[6].setText(kunde.getTaetigkeit_3());
        tv[7].setText(kunde.getAnmerkung());

        //Beim Klick auf Textview - Nachname
        tv[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbSpalte = "Firma";
                idTv = 0;
                String tvText = tv[0].getText().toString();
                starteDialogBearbeiten(tvText, "Firma", true);
            }
        });

        //Beim Klick auf Textview - Vorname
        tv[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbSpalte = "Ansprechpartner";
                idTv = 1;
                String tvText = tv[1].getText().toString();
                starteDialogBearbeiten(tvText, "Vorname", false);
            }
        });

        //Beim Klick auf Textview - Straße
        tv[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbSpalte = "ADRESSE";
                idTv = 2;
                String kundeStrasse = tv[2].getText().toString();
                String kundeStadt = tv[3].getText().toString();
                starteDialogAdresse(kundeStrasse, kundeStadt);
            }
        });


        //Beim Klick auf Textview - Festnetz
        tv[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbSpalte = "ADRESSE";
                idTv = 3;
                String kundeStrasse = tv[2].getText().toString();
                String kundeStadt = tv[3].getText().toString();
                starteDialogAdresse(kundeStrasse, kundeStadt);
            }
        });

        //Beim Klick auf Textview - Mobil
        tv[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbSpalte = "Taetigkeit1";
                idTv = 4;
                String tvText = tv[4].getText().toString();
                starteDialogBearbeiten(tvText, "Tätigkeit_1", true);
            }
        });

        tv[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbSpalte = "Taetigkeit5";
                idTv = 5;
                String tvText = tv[5].getText().toString();
                starteDialogBearbeiten(tvText, "Tätigkeit_5", false);
            }
        });

        tv[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbSpalte = "Taetigkeit6";
                idTv = 6;
                String tvText = tv[6].getText().toString();
                starteDialogBearbeiten(tvText, "Tätigkeit_6", false);
            }
        });


        //Beim Klick auf Textview - Anmerkung
        tv[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbSpalte = "Anmerkung";
                idTv = 7;
                String tvText = tv[7].getText().toString();
                starteDialogBearbeiten(tvText, "Anmerkung", false);
            }
        });


    }

    /*

        private BottomNavigationView.OnNavigationItemSelectedListener navListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.nav_bezahlung:
                                //Id dem Intent übergeben und Activity starten
                                Intent intent = new Intent(getApplicationContext(), BezahlungActivity.class);
                                intent.putExtra("Id", id);
                                intent.putExtra("Bezahlmarker", kunde.getBezahlMarker());
                                startActivityForResult(intent, 1);
                                break;
                            case R.id.nav_anrufen:
                                Boolean kundeBesitztFestnetz, kundeBesitztMobil;
                                kundeBesitztFestnetz = kunde.getFestnetz() != null;
                                kundeBesitztMobil = kunde.getMobil() != null;
                                if (kundeBesitztMobil && kundeBesitztFestnetz) {
                                    starteDialogAnrufen(kunde.getFestnetz(), kunde.getMobil());
                                } else {
                                    if (kundeBesitztFestnetz) {
                                        anrufen(kunde.getFestnetz());
                                    } else if (kundeBesitztMobil) {
                                        anrufen(kunde.getMobil());
                                    } else {
                                        String meldungKeineNummer = getString(R.string.meldungKeineNummer);
                                        Toast.makeText(getApplicationContext(), meldungKeineNummer,
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                                break;
                            case R.id.nav_loeschen:
                                if (abgang) {
                                    DialogLoeschen dialog = new DialogLoeschen();
                                    dialog.show(getSupportFragmentManager(), "isn Beispiel");
                                } else {
                                    Datum datum = new Datum();
                                    String strDatum = datum.getDatumHeute();
                                    dbHelfer.loesche("ZUGANG", "KnId = ?", new String[]{String.valueOf(id)});
                                    dbHelfer.datensatzEinfuegen(kunde, "ABGANG", strDatum);

                                    int pos = dbHelfer.getPositionKunde(id) + 1;
                                    int pos2 = dbHelfer.getPositionKunde(id);

                                    int nextId = 0;
                                    while (nextId != -1) {
                                        nextId = dbHelfer.getIdKunde(pos++);
                                        dbHelfer.update(nextId, String.valueOf(pos2++), "Position");
                                    }

                                    dbHelfer.update(id, "-1", "Position");
                                    sendeBroadcast();
                                    finish();
                                }


                                break;
                        }
                        return true;

                    }
                };

    */
    @Override
    public void listenerLoeschen(Boolean loeschen) {
        if (loeschen) {
            dbHelfer.loesche("KUNDE", "KnId = ?", new String[]{String.valueOf(id)});


            sendeBroadcast();
            finish();
        }

    }


    //Dialog Adresse
    private void starteDialogAdresse(String strasse, String stadt) {
        Bundle daten = new Bundle();
        daten.putString("Strasse", strasse);
        daten.putString("Stadt", stadt);
        DialogAdresse dialog = new DialogAdresse();
        starteDialog(dialog, daten);
    }

    //Dialog Kundeninformation
    private void starteDialogBearbeiten(String text, String title, Boolean pflichtfeld) {
        Bundle daten = new Bundle();
        daten.putString("Text", text);
        daten.putString("Title", title);
        daten.putBoolean("Pflichtfeld", pflichtfeld);
        DialogBearbeiten dialog = new DialogBearbeiten();
        starteDialog(dialog, daten);
    }


    private void starteDialogAnrufen(String festnetzNr, String mobilNr) {
        Bundle daten = new Bundle();
        daten.putString("Festnetz", festnetzNr);
        daten.putString("Mobil", mobilNr);
        DialogAnrufen dialog = new DialogAnrufen();
        starteDialog(dialog, daten);
    }


    private void starteDialog(AppCompatDialogFragment dialog, Bundle daten) {
        dialog.setArguments(daten);
        dialog.show(getSupportFragmentManager(), "dia");
    }

    private void anrufen(String nummer) {
        Intent intentAnrufen = new Intent(Intent.ACTION_DIAL);
        intentAnrufen.setData(Uri.parse("tel:" + nummer));
        startActivity(intentAnrufen);

    }


    //Referenz zu den XML-Komponenten
    private void initialisiere() {
        int resId;
        String tvId;

        bottomNavigationView = findViewById(R.id.bottomNavigationViewKundeInfoActivity);


        for (int i = 0; i < 8; i++) {
            tvId = "rv" + i;
            resId = getResources().getIdentifier(tvId, "id", getPackageName());
            tv[i] = new TextView(getApplicationContext());
            tv[i] = findViewById(resId);
        }
    }

    @Override
    public void listenerBearbeiten(String text, Boolean anmerkung) {
        kundeWurdeGeaendert = true;
        tv[idTv].setText(text);
        if (!anmerkung) {
            if (dbSpalte.equals("Firma")) {
                kunde.setFirma(text);
            } else if (dbSpalte.equals("Ansprechpartner")) {
                kunde.setAnsprechpartner(text);
            } else if (dbSpalte.equals("Taetigkeit1")) {
                kunde.setTaetigkeit_1(text);
            } else if (dbSpalte.equals("Taetigkeit2")) {
                kunde.setTaetigkeit_2(text);
            } else if (dbSpalte.equals("Taetigkeit3")) {
                kunde.setTaetigkeit_3(text);

            }
            dbHelfer.update(id, text, dbSpalte);
        } else {
            dbHelfer.update(id, text, dbSpalte);
            kunde.setAnmerkung(text);
        }
    }



    @Override
    public void listenerAdresse(String strasse, String stadt, LatLng latLng) {
        kundeWurdeGeaendert = true;
        Geo geo = new Geo(getApplicationContext());

        String latitude = String.valueOf(latLng.latitude);
        String longitude = String.valueOf(latLng.longitude);
        dbHelfer.update(id, strasse, "Strasse");
        dbHelfer.update(id, stadt, "Stadt");
        dbHelfer.update(id, latitude, "Latitude");
        dbHelfer.update(id, longitude, "Longitude");
        kunde.setStrasse(strasse);
        kunde.setStadt(stadt);
        kunde.setLatiude(Double.parseDouble(latitude));
        kunde.setLongitude(Double.parseDouble(longitude));
    }



    public class Binder extends android.os.Binder {
        public KundenInfoActivity getService() {
            return KundenInfoActivity.this;
        }
    }

    private void sendeBroadcast() {
        Intent i = new Intent("loeschen");

        i.putExtra("id", id);
        i.putExtra("beliefert", 0);

        LocalBroadcastManager.getInstance(binder.getService()).sendBroadcast(i);

    }

    @Override
    public void onBackPressed() {


        if (kundeWurdeGeaendert) {
            Intent intent = new Intent("aenderung");
            intent.putExtra("position", position);
      //      intent.putExtra("kunde", kunde);
            intent.putExtra("id", id);
            LocalBroadcastManager.getInstance(binder.getService()).sendBroadcast(intent);
        }
        super.onBackPressed();
    }
}
