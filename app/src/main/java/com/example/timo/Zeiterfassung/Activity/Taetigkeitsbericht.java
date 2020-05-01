package com.example.timo.Zeiterfassung.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timo.Zeiterfassung.Beans.Position;
import com.example.timo.Zeiterfassung.Dialog.DialogDbErsetzen;
import com.example.timo.Zeiterfassung.Helfer.DatenbankHelfer;
import com.example.timo.Zeiterfassung.Helfer.Datum;
import com.example.timo.Zeiterfassung.Helfer.Kunde;
import com.example.timo.Zeiterfassung.Helfer.ListViewPositionAdapter;
import com.example.timo.Zeiterfassung.Helfer.ListViewPositionSonstiges;
import com.example.timo.Zeiterfassung.Interface.DialogDatenbankInterface;
import com.example.timo.Zeiterfassung.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static com.example.timo.Zeiterfassung.Beans.Position.comparatorPosition;

public class Taetigkeitsbericht extends AppCompatActivity implements Serializable, DialogDatenbankInterface {
    boolean taetigkeitsberichtNichtAbgeschlossen;
    int adapterPosition;
    FloatingActionButton fab;
    String wochentag;
    ListView lv, lv2;
    Spinner spinner;
    TextView tvArbeitszeit, tvPause;
    ListViewPositionAdapter adapter;
    ListViewPositionSonstiges adapterSonstges;
    ArrayList<Kunde> listAlleKunden;
    ArrayList<Position> listPositionGesamt;
    ArrayList<Position> listPosition;
    ArrayList<Position> listPositionGefiltert;
    ArrayList<Position> listPositionGueltig;
    ArrayList<Position> listPositionSonstiges;
    ArrayList<Position> listPositionWochentag;
    ArrayList<Position> listPositionAktuell;
    ArrayList<Position> listPositionOhneDuplikate;
    ArrayList<Position> listPos2;
    ArrayList<ArrayList<Position>> listSplit;
    String arbeitszeit;
    boolean isTaetigkeitsbericht;
    int posClickId;
    Date dPauseBeginn, dPauseEnde, dPauseBegin2, dPauseEnde2;
    DatenbankHelfer dbHelfer;
    FirebaseHandler firebaseHandler;
    public BroadcastReceiver receiverPosition = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Position position = (Position) intent.getSerializableExtra("Position");
            // füge weitere Informationen hinzu

            ArrayList<Position> zwischenListe = new ArrayList<Position>();
            listPosition.set(posClickId, position);
            Position[] listPos = new Position[listPosition.size()];
            //Zeitliche sortierung
            for (int l = 0; l < listPosition.size(); l++) {
                listPos[l] = listPosition.get(l);
            }

            Arrays.sort(listPos, comparatorPosition);
            zwischenListe = listToArraylist(listPos);
            listPosition.clear();
            listPosition.addAll(zwischenListe);

            arbeitszeit = new Position("").getArbeitszeitGesamt(listPosition, dPauseBeginn, dPauseEnde,dPauseBegin2,dPauseEnde2);
            if (arbeitszeit.equals("-999")) {
                Toast.makeText(Taetigkeitsbericht.this, "FEHLER BEIM PARSEN!!!", Toast.LENGTH_LONG).show();
            } else {
                tvArbeitszeit.setText("Arbeitszeit: " + arbeitszeit);
                adapter.notifyDataSetChanged();
            }

        }

    };
    public BroadcastReceiver receiverNeuePosition = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Position> zwischenListe = new ArrayList<Position>();
            Position position = (Position) intent.getSerializableExtra("Position");
            // Position position = (Position) bundle.getSerializable("Position");

            if (position.getNamePosition().equals("Kunde")) {
                if (!position.getKunde().getFirma().equals("Firma")){
                    int index = 0;
                    while (position.getKunde().getFirma().equals(listAlleKunden.get(index).getFirma())) {
                        index++;
                    }
                    position.getKunde().setStrasse(listPosition.get(index).getKunde().getStrasse());
                    position.getKunde().setStadt(listPosition.get(index).getKunde().getStadt());
                    position.getKunde().setAuswahlTaetigkeit("TEST");
                }else{
                    position.getKunde().setStrasse("Holthausstraße 20");
                    position.getKunde().setStadt("Dinklage");
                    position.getKunde().setAuswahlTaetigkeit("TEST");
                }

            }

            listPosition.add(position);
            Log.d("guckn", String.valueOf(listPosition.size()));

            Position[] listPos = new Position[listPosition.size()];
            //Zeitliche sortierung
            for (int l = 0; l < listPosition.size(); l++) {
                listPos[l] = listPosition.get(l);
            }

            Arrays.sort(listPos, comparatorPosition);
            zwischenListe = listToArraylist(listPos);
            listPosition.clear();
            listPosition.addAll(zwischenListe);
            //     listPosition = listToArraylist(listPos);

            arbeitszeit = new Position("").getArbeitszeitGesamt(listPosition, dPauseBeginn, dPauseEnde,dPauseBegin2,dPauseEnde2);
            if (arbeitszeit.equals("-999")) {
                Toast.makeText(Taetigkeitsbericht.this, "FEHLER BEIM PARSEN!!!", Toast.LENGTH_LONG).show();
            } else {
                Log.d("guckn", String.valueOf(listPosition.size()));
                tvArbeitszeit.setText("Arbeitszeit: " + arbeitszeit);
                adapter.notifyDataSetChanged();
            }

        }

    };
    Button btnSenden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taetigkeitsbericht);
        dbHelfer = new DatenbankHelfer(Taetigkeitsbericht.this);
        firebaseHandler = new FirebaseHandler();

        //      Log.d("testtest",String.valueOf(listPosition.size()));

        try {
            fab = findViewById(R.id.btnAddPosition);
            listPosition = new ArrayList<Position>();
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(receiverPosition, new IntentFilter("rPosition"));
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(receiverNeuePosition, new IntentFilter("neuePosition"));
            lv = findViewById(R.id.lvTaetigkeitsbericht);
            btnSenden = (Button) findViewById(R.id.btnsendn);
            listPositionAktuell = new ArrayList<Position>();
            listAlleKunden = new ArrayList<Kunde>();
            listAlleKunden = dbHelfer.getListKunde();
            adapter = new ListViewPositionAdapter(getApplicationContext(), R.layout.item_position, listPosition);
            lv.setAdapter(adapter);
            registerForContextMenu(lv);


            dPauseBeginn = new Date();
            dPauseEnde = new Date();
            dPauseBegin2 = new Date();
            dPauseEnde2 = new Date();

            dPauseBeginn.setHours(9);
            dPauseBeginn.setMinutes(0);
            dPauseBeginn.setSeconds(0);

            dPauseEnde.setHours(9);
            dPauseEnde.setMinutes(15);
            dPauseEnde.setSeconds(0);

            dPauseBegin2.setHours(12);
            dPauseBegin2.setMinutes(35);
            dPauseBegin2.setSeconds(0);

            dPauseEnde2.setHours(13);
            dPauseEnde2.setMinutes(20);
            dPauseEnde2.setSeconds(0);



            final Position posGesamt = new Position("");
            final Bundle extras = getIntent().getExtras();
            taetigkeitsberichtNichtAbgeschlossen = extras.getBoolean("taetigkeitsbericht");



            tvArbeitszeit = findViewById(R.id.tvArbeitszeit);

            spinner = findViewById(R.id.spin);
            tvPause = findViewById(R.id.tvPause);
            listPos2 = new ArrayList<Position>();
            listSplit = new ArrayList<ArrayList<Position>>();
            //DUMMY
       //     Dummy dummy = new Dummy();
         //   listPositionGesamt = dummy.getDummyList();
            listPositionGesamt = (ArrayList<Position>) extras.getSerializable("listPosition");
            Log.d("posSize",String.valueOf(listPositionGesamt.size()));
            if (taetigkeitsberichtNichtAbgeschlossen){
                btnSenden.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
                spinner.setVisibility(View.VISIBLE);
            }
            if (listPositionGesamt != null) {
           //     listPositionGefiltert = posGesamt.getListPositionOhneAusreisser(listPositionGesamt);
                listPositionGefiltert = listPositionGesamt;
            //    listPositionGueltig = posGesamt.getGueltigePositionen(listPositionGefiltert);

                listPositionOhneDuplikate = sortiereListe(listPositionGefiltert);

                listPos2 = posGesamt.getListPositonOhneDuplikate(listPositionOhneDuplikate);
                listPosition.clear();
                listPosition.addAll(listPos2);

                // listPosition = listPositionGueltig;
                // erstellen einer Kopie des aktuellen Berichts
                for (int i = 0; i < listPosition.size(); i++) {
                    listPositionAktuell.add(listPosition.get(i));
                }
                //ARBEITSZEIT
                String arbeitszeit = posGesamt.getArbeitszeitGesamt(listPosition, dPauseBeginn, dPauseEnde,dPauseBegin2,dPauseEnde2);




                if (arbeitszeit.equals("-999")) {
                    Toast.makeText(Taetigkeitsbericht.this, "FEHLER BEIM PARSEN!!!", Toast.LENGTH_LONG).show();
                } else {
                    listPosition = Position.listPosition;
                    DecimalFormat formatDouble = new DecimalFormat("#.##");
                    tvArbeitszeit.setText("Arbeitszeit: " + arbeitszeit);
                }
            }
            //   tvPause.setText("Pause: 09:00 Uhr - 09:15 Uhr\n12:00 Uhr - 12:30");


            btnSenden.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Position position = new Position("");
                    Boolean istZeitüberschneidung = position.istZeitüberschneidung(listPosition);
                    istZeitüberschneidung = false;

                    if (istZeitüberschneidung) {
                        Toast.makeText(Taetigkeitsbericht.this, "Nicht möglich aufgrund von Zeitüberschneidungen", Toast.LENGTH_LONG).show();
                    } else {
                        String wochentag = dbHelfer.getWochentag();
                        if (!wochentag.equals("SONNTAG")){
                            String jsonArray = dbHelfer.getBerichtWochentag(wochentag);
                            isTaetigkeitsbericht = String.valueOf(jsonArray.charAt(0)).equals("[");
                            if (isTaetigkeitsbericht) {
                                Bundle daten = new Bundle();
                                DialogDbErsetzen dialog = new DialogDbErsetzen();
                                dialog.setArguments(daten);
                                dialog.show(getSupportFragmentManager(), "dia");

                            } else {
                                setNewTaetigkeitsbericht();
                            }
                        }else{
                            Toast.makeText(Taetigkeitsbericht.this, "Nicht an einem Sonntag möglich", Toast.LENGTH_LONG).show();
                        }



                    }

                }


            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AddPosition.class);
                    startActivity(intent);
                    //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    //          .setAction("Action", null).show();
                }
            });
            //Auswahl nach was sortiert werden soll
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                         wochentag = adapterView.getSelectedItem().toString().toUpperCase();

                        if (!wochentag.equals("AKTUELL")) {
                            String jsonArray = dbHelfer.getBerichtWochentag(wochentag);
                            isTaetigkeitsbericht = String.valueOf(jsonArray.charAt(0)).equals("[");
                            if (isTaetigkeitsbericht) {
                                Type listType = new TypeToken<ArrayList<Position>>() {
                                }.getType();
                                listPositionWochentag = new Gson().fromJson(jsonArray, listType);
                                listPosition.clear();
                                adapter.clear();
                                //         adapter.notifyDataSetChanged();
                                //       adapter = new ListViewPositionAdapter(getApplicationContext(), R.layout.item_position, listPosition);
                                listPosition.addAll(listPositionWochentag);
                            } else {
                                listPosition.clear();
                                adapter.clear();
                                //           adapter = new ListViewPositionAdapter(getApplicationContext(), R.layout.item_position, listPosition);
                                Toast.makeText(getApplicationContext(), "Keine Daten für den Tag verfügbar",
                                        Toast.LENGTH_LONG).show();
                            }

                        } else {
                            listPosition.clear();
                            adapter.clear();
                            //  adapter.notifyDataSetChanged();
                            // adapter = new ListViewPositionAdapter(getApplicationContext(), R.layout.item_position, listPosition);
                            listPosition.addAll(listPositionAktuell);
                        }
                        String arbeitszeit = posGesamt.getArbeitszeitGesamt(listPosition, dPauseBeginn, dPauseEnde,dPauseBegin2,dPauseEnde2);
                        tvArbeitszeit.setText("Arbeitszeit: " + arbeitszeit);
                        //       adapter.notifyDataSetChanged();


                        String gg = "jf";
                    } catch (Exception e) {
                        e.printStackTrace();
                   //     Toast.makeText(Taetigkeitsbericht.this, "Fehler", Toast.LENGTH_LONG).show();
                    }
                    //Keine Sortierung

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
// Log.d("zeittest", String.valueOf(listPosition.size()));
//        Log.d("testtest",String.valueOf(listPosition.size()));

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    //    Intent intent = new Intent(getApplicationContext(), KundenInfoActivity.class);
                    //  intent.putExtra("kunde", listKunde.get(position));
                    // intent.putExtra("position", position);
                    // startActivity(intent);
                    Intent intent = new Intent(getApplicationContext(), TaetigkeitsberichtUeberarbeiten.class);
                    intent.putExtra("Position", listPosition.get(position));
                    startActivity(intent);
                    posClickId = position;
                    // startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ArrayList<Position> sortiereListe(ArrayList<Position> listPosition) {
        ArrayList<Position> listKunde = new ArrayList<Position>();
        ArrayList<Position> listSonstiges = new ArrayList<Position>();
        ArrayList<Position> listPositionenSortiert = new ArrayList<Position>();
        Position posAktuell;
        Boolean listForTaetigkeitsbericht = false;
/*
        Position posDump = new Position("");
        Calendar calendar;
        calendar = posDump.getAzeitBegGerundet(listPosition.get(0));
        listPosition.get(0).setStartTime(calendar);
       // Log.d("zzz",String.valueOf(listPosition.get(0).getStartTime().getTime().getMinutes()));
*/


        for (int i = 0; i < listPosition.size(); i++) {
            if (listPosition.get(i).getNamePosition().equals("Kunde")) {
                listKunde.add(listPosition.get(i));
            } else if (listPosition.get(i).getNamePosition().equals("Sonstiges")) {
                listSonstiges.add(listPosition.get(i));
            } else {
                listForTaetigkeitsbericht = true;
                posAktuell = listPosition.get(i);
                listPositionenSortiert.add(posAktuell);
            }
        }

        // Überprüfe ob der Weg zurück von der Baustelle angerechnet werden kann
        Log.d("anrechnung", "vorher");
        if (!listForTaetigkeitsbericht) {
            Log.d("anrechnung", "nachher");

            Boolean letztePositionFirma = listKunde.get(listKunde.size() - 1).getKunde().getFirma().equals("Firma");
            Boolean firmaInArbeitszeit;

            if (letztePositionFirma) {
                Log.d("anrechnung", "letzePos");
                firmaInArbeitszeit = anrechnungFirma(listKunde.get(listKunde.size() - 1));
                Log.d("anrechnung", String.valueOf(listKunde.size()));
                if (!firmaInArbeitszeit) {
                    Log.d("anrechnung", "!firma");
                    //Letzte Positon Kunde UND Sonstiges nicht anrechnungsfähig
                    //  if (listKunde.size() != 0 || listKunde.size() != 1) {
                    if (listKunde.size() > 1) {
                        listKunde.remove(listKunde.size() - 1);
                    }

                    if (listSonstiges.size() > 0) {

                        listSonstiges.remove(listSonstiges.size() - 1);
                    }

                }

            } else {
                //Letzte Positon Sonstiges nicht anrechnungsfähig
                Boolean letztePositionIstSonstiges = listPositionGesamt.get(listPositionGesamt.size() - 1).getNamePosition().equals("Sonstiges");
                if (letztePositionIstSonstiges) {
                    listSonstiges.remove(listSonstiges.get(listSonstiges.size() - 1));
                }

            }

        }

/*
        calendar = posDump.getArbeitszeitEndeGerundet(listKunde.get(listKunde.size()-1));
        listKunde.get(listKunde.size()-1).setEndTime(calendar);
*/


        for (int j = 0; j < listKunde.size(); j++) {
            listPositionenSortiert.add(listKunde.get(j));
        }

        for (int k = 0; k < listSonstiges.size(); k++) {
            listPositionenSortiert.add(listSonstiges.get(k));
        }
        Position[] listPos = new Position[listPositionenSortiert.size()];
        //Zeitliche sortierung
        for (int l = 0; l < listPositionenSortiert.size(); l++) {
            listPos[l] = listPositionenSortiert.get(l);
        }

        Arrays.sort(listPos, comparatorPosition);
        listPositionenSortiert = listToArraylist(listPos);
        //  Collections.sort(listPositionenSortiert, new Position(""));
        return listPositionenSortiert;
    }

    private ArrayList<Position> listToArraylist(Position[] listPos) {
        ArrayList<Position> listPosition = new ArrayList<Position>();

        for (int i = 0; i < listPos.length; i++) {
            listPosition.add(listPos[i]);
        }
        return listPosition;
    }


    private Boolean anrechnungFirma(Position position) {
        Position firma = position;

        if (firma.getKunde().getFirma().equals("Firma")) {
            int stdArbeitszeit = firma.getArbeitsZeitStunden();
            int minArbeitszeit = firma.getArbeitszeitMinuten();
            if (stdArbeitszeit > 0) {
                return true;
            } else if (minArbeitszeit >= 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent intent) {
        super.onActivityResult(requestCode,
                resultCode,
                intent);
        if (requestCode == 1 || resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), "Perfekt!!!",
                    Toast.LENGTH_LONG).show();
            if (intent == null) {

            }
            //      final Bundle extras = getIntent().getExtras();
            //    intent = this.getIntent();
//            Bundle bundle = intent.getExtras();

            Position position = (Position) intent.getSerializableExtra("Position");
            // Position position = (Position) bundle.getSerializable("Position");
            listPosition.set(posClickId, position);
            adapter.notifyDataSetChanged();

        }


    }

    private void testF(View view) {

        Position position = new Position("");
        Boolean zeitUeberschneidung = position.istZeitüberschneidung(listPosition);
    }

    private void setNewTaetigkeitsbericht() {
        Toast.makeText(Taetigkeitsbericht.this, "Gesendet", Toast.LENGTH_LONG).show();
        Gson gson = new Gson();
        Datum datumHeute = new Datum();
        String tagHeute = datumHeute.getDatumHeute();
        tagHeute = tagHeute.replace(".","-");
      //  tagHeute = "27-04-2020";
        String jsonArray = gson.toJson(listPosition);
        Log.d("sizeOfList", jsonArray);
    //    dbHelfer.update(1, jsonArray, "");
        firebaseHandler.insert("taetigkeitsbericht/" + firebaseHandler.getUserId() + "/" + tagHeute,jsonArray);

    }

    @Override
    public void listenerUeberschreiben(Boolean TaetigkeitsberichtUeberserschrieben) {
        if (TaetigkeitsberichtUeberserschrieben) {
            setNewTaetigkeitsbericht();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
       String aktuellerTag = dbHelfer.getWochentag();
  //     if (aktuellerTag.toUpperCase().equals(wochentag.toUpperCase()) && !taetigkeitsberichtNichtAbgeschlossen) {

           super.onCreateContextMenu(menu, v, menuInfo);
           MenuInflater inflater = getMenuInflater();
           AdapterView.AdapterContextMenuInfo info =
                   (AdapterView.AdapterContextMenuInfo) menuInfo;

           adapterPosition = (int) info.id;
           inflater.inflate(R.menu.loeschen_menu, menu);
    //     }
       }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        DatenbankHelfer dbHelfer = new DatenbankHelfer(getApplicationContext());
        int knId;
        switch (item.getItemId()) {
            case R.id.menuLoeschen:

                listPosition.remove(adapterPosition);
                arbeitszeit = new Position("").getArbeitszeitGesamt(listPosition, dPauseBeginn, dPauseEnde,dPauseBegin2,dPauseEnde2);
                if (arbeitszeit.equals("-999")) {
                    Toast.makeText(Taetigkeitsbericht.this, "FEHLER BEIM PARSEN!!!", Toast.LENGTH_LONG).show();
                } else {
                    tvArbeitszeit.setText("Arbeitszeit: " + arbeitszeit);
                }
                adapter.notifyDataSetChanged();

                //dbHelfer.update(knId, "true", "Beliefert");
                //   fragmentListener.updateKunde(kundeNichtBeliefert.get(adapterId).getPosition() - 1, true);
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void listenerDbSpeichern(String dbName) {

    }
}
