package com.zeiterfassung.timo.Zeiterfassung.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zeiterfassung.timo.Zeiterfassung.Helfer.DatenbankHelfer;
import com.zeiterfassung.timo.Zeiterfassung.Helfer.Kunde;
import com.zeiterfassung.timo.Zeiterfassung.Helfer.ListViewKundenAdapter;
import com.zeiterfassung.timo.Zeiterfassung.Helfer.LocationService;
import com.zeiterfassung.timo.Zeiterfassung.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by User on 2/28/2017.
 */

public class Auftragsliste extends AppCompatActivity {
    private static final String TAG = "BerichtNichtBeliefertFragment";
    private ArrayList<Kunde> listKunde = new ArrayList<Kunde>();
    private ListViewKundenAdapter adapter;
    private DatenbankHelfer dbHelfer;
    private ListView lv;
    private View view;
    private Boolean initialisiert = false;
    private Context context;
    public int adapterPosition;

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_zugaenge);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  actionBar.setDisplayHomeAsUpEnabled(true);

            boolean firmaGefunden = false;
            int indexFirma = 0;

            Map<String,Kunde> listKunde = new HashMap<String,Kunde>();
        Map<String,Kunde> listKundeCopy = new HashMap<String,Kunde>();


            listKunde = FirebaseHandler.listKunde;
            listKundeCopy.putAll(listKunde);



       //     HashMap<String,Kunde> listKunde = FirebaseHandler.listKunde;
            initialisiert = true;
            dbHelfer = new DatenbankHelfer(getApplicationContext());
            lv = findViewById(R.id.lvZugang);
          //  listKunde = dbHelfer.getListKunde();

            //lösche Firma aus Liste
        /*
            while (!firmaGefunden){
                if (listKunde.get(indexFirma).getFirma().equalsIgnoreCase("Firma")){
                    firmaGefunden = true;
                }else{
                    indexFirma++;
                }
            }

            listKunde.remove(indexFirma);*/
            //Adapter initialisieren
int size = listKunde.size();
Boolean delete = false;
int index = 0;
Kunde kunde;

//Firma löschen
        Iterator it = listKundeCopy.entrySet().iterator();
        while (it.hasNext() && !delete) {
            Map.Entry pair = (Map.Entry)it.next();
            Object ob = pair.getKey();
            kunde = (Kunde) pair.getValue();
            if (kunde.getFirma().equals("Firma") ){
                listKundeCopy.remove(ob);
                delete = true;
            }
        }
delete = false;
//Zuhause löschen
         it = listKundeCopy.entrySet().iterator();
        while (it.hasNext() && !delete) {
            Map.Entry pair = (Map.Entry)it.next();
            Object ob = pair.getKey();
            kunde = (Kunde) pair.getValue();
            if (kunde.getFirma().equals("Zuhause") ){
                listKundeCopy.remove(ob);
                delete = true;
            }
        }






            adapter = new ListViewKundenAdapter(getApplicationContext(), R.layout.item_kunde, hashToArray(listKundeCopy),false);
            lv.setAdapter(adapter);
            registerForContextMenu(lv);





        }






    public void entferneKunde() {
        initialisiereAdapter();

    }

    public void updateKunde() {
        initialisiereAdapter();

    }

    private void initialisiereAdapter() {
        ArrayList<Kunde> list = dbHelfer.getListKunde();
        listKunde.clear();
        for (int i = 0; i < list.size(); i++) {
            if (!listKunde.get(i).getFirma().equals("Zuhause")){
                listKunde.add(list.get(i));
            }

        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) menuInfo;

        adapterPosition = (int) info.id;
        inflater.inflate(R.menu.loeschen_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        DatenbankHelfer dbHelfer = new DatenbankHelfer(getApplicationContext());
        int knId;
        switch (item.getItemId()) {
            case R.id.menuLoeschen:
               if (!LocationService.getInstance().getAktiviert()){


                knId = listKunde.get(adapterPosition).getId();
                dbHelfer.loesche("KUNDE", "KnId = ?", new String[]{String.valueOf(knId)});
                listKunde.remove(adapterPosition);
                adapter.notifyDataSetChanged();
               }else{
                   Toast.makeText(Auftragsliste.this, "Nicht möglich beim Tracking" , Toast.LENGTH_LONG).show();
               }
                //dbHelfer.update(knId, "true", "Beliefert");
             //   fragmentListener.updateKunde(kundeNichtBeliefert.get(adapterId).getPosition() - 1, true);
            default:
                return super.onContextItemSelected(item);
        }
    }

    private ArrayList<Kunde> hashToArray(Map<String,Kunde> hashMap){
        ArrayList<Kunde> listKunde = new ArrayList<>();
        for (Map.Entry<String, Kunde> entry : hashMap.entrySet()) {
            Kunde kunde = entry.getValue();
            listKunde.add(kunde);

        }
        return listKunde;
    }

}
