package com.example.timo.Zeiterfassung.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timo.Zeiterfassung.Beans.Position;
import com.example.timo.Zeiterfassung.Dialog.DialogBerichtMessage;
import com.example.timo.Zeiterfassung.Fragment.Heute;
import com.example.timo.Zeiterfassung.Fragment.Woche;
import com.example.timo.Zeiterfassung.Helfer.DatenbankHelfer;
import com.example.timo.Zeiterfassung.Helfer.Datum;
import com.example.timo.Zeiterfassung.Helfer.FragmentAdapter;
import com.example.timo.Zeiterfassung.Helfer.Kunde;
import com.example.timo.Zeiterfassung.Helfer.ListViewPositionAdapter;
import com.example.timo.Zeiterfassung.Helfer.ListViewPositionSonstiges;
import com.example.timo.Zeiterfassung.Helfer.TaetigkeitsberichtUtil;
import com.example.timo.Zeiterfassung.Interface.DialogDatenbankInterface;
import com.example.timo.Zeiterfassung.Interface.DialogMessage;
import com.example.timo.Zeiterfassung.Interface.IBericht;
import com.example.timo.Zeiterfassung.Interface.ITaetigkeitsbericht;
import com.example.timo.Zeiterfassung.R;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import static com.example.timo.Zeiterfassung.Beans.Position.comparatorPosition;

public class Taetigkeitsbericht extends AppCompatActivity implements Serializable, DialogDatenbankInterface, DialogMessage, ITaetigkeitsbericht, IBericht {
    boolean taetigkeitsberichtNichtAbgeschlossen;
    int adapterPosition;

    MainActivity mainActivity = MainActivity.mainActivity;
    String wochentag;
    ListView lv, lv2;
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
    String arbeitszeit="";
    boolean isTaetigkeitsbericht;
    int posClickId;
    Date dPauseBeginn, dPauseEnde, dPauseBegin2, dPauseEnde2;
    DatenbankHelfer dbHelfer;
    FirebaseHandler firebaseHandler;
    public static Taetigkeitsbericht taetigkeitsbericht;
    private NavigationView bottomNavigationView;
    Button btnSenden;
    private Boolean berichtHeute = false;

    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;

    private Heute fragmentHeute;
    private Heute listenerHeute;
    private Woche listenerWoche;
    private Woche fragmentWoche;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taetigkeitsbericht);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        taetigkeitsbericht = this;
        viewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        initialisiereViewPager(viewPager);

        listenerHeute = Heute.heute;
        dbHelfer = new DatenbankHelfer(Taetigkeitsbericht.this);
        firebaseHandler = new FirebaseHandler();
        taetigkeitsbericht = this;

        listPosition = new ArrayList<Position>();


        listPositionAktuell = new ArrayList<Position>();
        //    listAlleKunden = new ArrayList<Kunde>();
        //      listAlleKunden = dbHelfer.getListKunde();


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
        berichtHeute = extras.getBoolean("berichtHeute");


        listPos2 = new ArrayList<Position>();
        listSplit = new ArrayList<ArrayList<Position>>();
        //DUMMY
       // Dummy dummy = new Dummy();
      //     listPositionGesamt = dummy.getDummyList5();
        listPositionGesamt = (ArrayList<Position>) extras.getSerializable("listPosition");

        //Tracking gestartet
        TaetigkeitsberichtUtil taetigkeitsberichtUtil = new TaetigkeitsberichtUtil();
        if (listPositionGesamt != null && listPositionGesamt.size() > 0 ) {
            if (!berichtHeute){


            listPositionGefiltert = posGesamt.getListPositionOhneAusreisser(listPositionGesamt);
            //    listPositionGefiltert = listPositionGesamt;
            listPositionOhneDuplikate = taetigkeitsberichtUtil.sortiereListe(listPositionGefiltert, listPositionGesamt);
            listPos2 = posGesamt.getListPositonOhneDuplikate(listPositionOhneDuplikate);
            listPos2 = posGesamt.getListPositonOhneDuplikateSonstiges(listPos2);
            listPos2 = posGesamt.getGueltigePositionen(listPos2);
              //  listPositionOhneDuplikate = taetigkeitsberichtUtil.sortiereListe(listPos2, listPositionGesamt);
            //Arbeitszeit runden
            if (listPos2.size() != 0){
                listPos2.set(0,posGesamt.getRoundStartzeit(listPos2.get(0)));
                listPos2.set(listPos2.size()-1,posGesamt.getRoundEndzeit(listPos2.get(listPos2.size()-1)));
            }
            }else{
                listPos2.addAll(listPositionGesamt);
            }

            listPosition.clear();
            listPosition.addAll(listPos2);
            Log.i("posSize", String.valueOf(listPosition.size()));

            // listPosition = listPositionGueltig;
            // erstellen einer Kopie des aktuellen Berichts
            for (int i = 0; i < listPosition.size(); i++) {
                listPositionAktuell.add(listPosition.get(i));
            }
            //ARBEITSZEIT

            if (listPos2.size() != 0){
                 arbeitszeit = posGesamt.getArbeitszeitGesamt(listPosition, dPauseBeginn, dPauseEnde, dPauseBegin2, dPauseEnde2);
            }else{
                arbeitszeit = "0";
            }



            if (arbeitszeit.equals("-999")) {
                Toast.makeText(Taetigkeitsbericht.this, "FEHLER BEIM PARSEN!!!", Toast.LENGTH_LONG).show();
            } else {
                listPosition = Position.listPosition;
                DecimalFormat formatDouble = new DecimalFormat("#.##");
             //   fragmentHeute.onCalculateArbeitszeit(arbeitszeit);
                //  tvArbeitszeit.setText("Arbeitszeit: " + arbeitszeit);
             //   listenerHeute.onListPosFiltered(listPosition);
            }
            //Tracking nicht gestartet
        }

        if (!berichtHeute && listPositionGesamt == null) {
            {

            }


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
            } else if (minArbeitszeit >= 2) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
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
        tagHeute = tagHeute.replace(".", "-");
        //   tagHeute = "27-04-2020";
        String jsonArray = gson.toJson(listPosition);
        Log.d("sizeOfList", jsonArray);
        //    dbHelfer.update(1, jsonArray, "");
        firebaseHandler.insert("taetigkeitsbericht/" + firebaseHandler.getUserId() + "/" + tagHeute, jsonArray);

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
        Log.d("changeevent", "onCreateContextMenu ");
        String aktuellerTag = dbHelfer.getWochentag();
        //     if (aktuellerTag.toUpperCase().equals(wochentag.toUpperCase()) && !taetigkeitsberichtNichtAbgeschlossen) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) menuInfo;

        adapterPosition = (int) info.id;
     //   inflater.inflate(R.menu.loeschen_menu, menu);
        //     }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d("changeevent", "onContextItemSelected: ");
        DatenbankHelfer dbHelfer = new DatenbankHelfer(getApplicationContext());
        int knId;
        switch (item.getItemId()) {
            case R.id.menuLoeschen:

                listPosition.remove(adapterPosition);
                arbeitszeit = new Position("").getArbeitszeitGesamt(listPosition, dPauseBeginn, dPauseEnde, dPauseBegin2, dPauseEnde2);

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void listenerDbSpeichern(String dbName) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.bottom_navigation, menu);


        if (!berichtHeute) {
            MenuItem itemCheck = menu.findItem(R.id.it_check);
            MenuItem itemRemove = menu.findItem(R.id.it_remove);
            itemCheck.setVisible(false);
            itemRemove.setVisible(false);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Datum datum = new Datum();
        String datumHeute = datum.getFormatedTagHeute();

        switch (item.getItemId()) {
            case R.id.it_check:
                DialogBerichtMessage dialogBerichtMessage = new DialogBerichtMessage();
                dialogBerichtMessage.show(getSupportFragmentManager(), "dialog");
                return true;
            case android.R.id.home:
                finish();
                return true;
            case R.id.it_remove:
                firebaseHandler.removePath("taetigkeitsbericht/" + firebaseHandler.userId() + "/" + datumHeute);
                mainActivity.onRemoveTaetigkeitsbericht();
                taetigkeitsbericht = null;
                super.onBackPressed();
                finish();
                //finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    /*
        private void setLayout(){
            NavigationView navigationView = findViewById(R.id.bottomNavigation);
            if (berichtHeute) {
                Log.d("eventhandler", "berichtheute=sichtbar");
                navigationView.setVisibility(View.VISIBLE);
            }else{
                Log.d("eventhandler", "berichtheute=unsichtbar");
                navigationView.setVisibility(View.GONE);
            }
        }
    */
    @Override
    public void onGetTaetigkeitsberichtData(TaetigkeitsberichtUtil taetigkeitsberichtUtil) {
        String gg = "ff";

    }

    @Override
    public void onSendTaetigkeitsbericht() {

    }

    @Override
    public void onRemoveTaetigkeitsbericht() {

    }

    @Override
    public void onDayOfTaetigkeitsberichtChange(TaetigkeitsberichtUtil taetigkeitsberichtUtil) {
        if (taetigkeitsberichtUtil != null) {
            ArrayList<Position> listPostionOfDay = new ArrayList<Position>();

            String berichtOfDay = taetigkeitsberichtUtil.getBericht();

            listPostionOfDay = taetigkeitsberichtUtil.getTaetigkeitsbericht2(berichtOfDay);
            listPosition.clear();
            listPosition.addAll(listPostionOfDay);

        } else {
            listPosition.clear();
            Toast.makeText(getApplicationContext(), "Keine Daten für den Tag verfügbar",
                    Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onGetWochenbericht(HashMap<String, TaetigkeitsberichtUtil> listWochenBericht) {
        listenerWoche = Woche.woche;
        listenerWoche.onGetWochenbericht(listWochenBericht);

    }

    private void initialisiereViewPager(ViewPager viewPager) {
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.addFragment(new Heute(), "Heute");
        fragmentAdapter.addFragment(new Woche(), "Wochenübersicht");
        viewPager.setAdapter(fragmentAdapter);
     //   viewPager.setCurrentItem(1);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               // Intent intent = new Intent("register");
               // intent.putExtra("tabId", position);
                Log.i("changePage","change to page: " + String.valueOf(position));
                if (position == 0) {

                } else {
                   String gg =  firebaseHandler.getWochenData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }

        });
    }

    @Override
    public void onListPosFiltered(ArrayList<Position> listPosition) {




    }

    @Override
    public void onFragmetHeuteCreated(Heute contextFragmentHeute) {
        Log.d("ctxH",contextFragmentHeute.toString());
        String ff = "";
        contextFragmentHeute.onListPosFiltered(listPosition);
        fragmentHeute = contextFragmentHeute;
        if (!arbeitszeit.equals("")){
               fragmentHeute.onCalculateArbeitszeit(arbeitszeit);
        }


    }

    @Override
    public void listenerMessage(String nachricht) {
        Datum datum = new Datum();
        String datumHeute = datum.getFormatedTagHeute();
        mainActivity.onSendTaetigkeitsbericht();
        Toast.makeText(Taetigkeitsbericht.this, "check", Toast.LENGTH_LONG).show();
        firebaseHandler.insert("taetigkeitsbericht/" + firebaseHandler.userId() + "/" + datumHeute + "/abgeschlossen", true);
        firebaseHandler.insert("taetigkeitsbericht/" + firebaseHandler.userId() + "/" + datumHeute + "/notiz", nachricht);
        super.onBackPressed();
        finish();

    }
    @Override
    public void onCalculateArbeitszeit(String str) {


    }


}
