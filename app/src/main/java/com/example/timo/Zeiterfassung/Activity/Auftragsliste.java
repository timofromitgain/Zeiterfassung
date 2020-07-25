package com.example.timo.Zeiterfassung.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.timo.Zeiterfassung.Helfer.DatenbankHelfer;
import com.example.timo.Zeiterfassung.Helfer.Kunde;
import com.example.timo.Zeiterfassung.Helfer.ListViewKundenAdapter;
import com.example.timo.Zeiterfassung.Helfer.LocationService;
import com.example.timo.Zeiterfassung.R;

import java.util.ArrayList;
import java.util.HashMap;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_zugaenge);
            boolean firmaGefunden = false;
            int indexFirma = 0;

            Map<String,Kunde> listKunde = new HashMap<String,Kunde>();

            listKunde = FirebaseHandler.listKunde;



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
            adapter = new ListViewKundenAdapter(getApplicationContext(), R.layout.item_kunde, hashToArray(listKunde),false);
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
            listKunde.add(list.get(i));
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
