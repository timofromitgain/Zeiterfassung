package com.example.timo.Zeiterfassung.Helfer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;


import com.example.timo.Zeiterfassung.R;


public class NotificationReceiver extends BroadcastReceiver {
DatenbankHelfer dbHelfer;
Binder binder;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        binder = new Binder();
    //    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        String action = intent.getAction();
        Toast.makeText(context, "Es wurde "+ action + " ausgewählt", Toast.LENGTH_SHORT).show();
        Integer id = intent.getIntExtra("id",0);
        String nachname = intent.getStringExtra("kundeNachname");
        Kunde kunde = (Kunde) intent.getSerializableExtra("kunde");
        kunde.setAuswahlTaetigkeit(action);

          /*
            String test = intent.getStringExtra("testvalue");

            String zahlungsintervall = intent.getStringExtra("zahlungsintervall");
            String zahlungszeitpunkt = intent.getStringExtra("zahlungszeitpunkt");
            Integer erserSonntagImMonat = intent.getIntExtra("ersterSonntagImMonat",0);
            Integer letzterSonntagImMonat = intent.getIntExtra("letzterSonntagImMonat",0);
            Integer monat = intent.getIntExtra("monat",0);
            Integer jahr = intent.getIntExtra("jahr",0);
            String datum = intent.getStringExtra("datum");
        //    dbHelfer.update(id,"true","Bezahlmarker");

*/

         /*
           String knName = intent.getStringExtra("knName");
           String knAdresse = intent.getStringExtra("knAdresse");
           Boolean knBezahlmarker = intent.getBooleanExtra("knBezahlmarker",false);
           Intent intent2 = new Intent(context,BezahlungActivity.class);
           intent2.putExtra("Id",id);
           intent2.putExtra("knName",knName);
           intent2.putExtra("knAdresse",knAdresse);
           intent2.putExtra("Bezahlmarker",knBezahlmarker);

           context.startActivity(intent2);
           */


        Intent it = new Intent("auswahlTaetigkeit");
        it.putExtra("auswahl",action);
        it.putExtra("id",kunde.getId());
        context.sendBroadcast(it);
    }

    public class Binder extends android.os.Binder {
        public NotificationReceiver getService() {
            return NotificationReceiver.this;
        }
    }

}
