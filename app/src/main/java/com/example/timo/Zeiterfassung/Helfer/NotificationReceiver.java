package com.example.timo.Zeiterfassung.Helfer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


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
        String uid = intent.getStringExtra("uid");
        Log.i("555","NotRecUid: " + uid);
        String nachname = intent.getStringExtra("kundeNachname");
        Kunde kunde = (Kunde) intent.getSerializableExtra("kunde");
        kunde.setAuswahlTaetigkeit(action);




        Intent it = new Intent("auswahlTaetigkeit");
        it.putExtra("auswahl",action);
        it.putExtra("uid",uid);
        context.sendBroadcast(it);
    }

    public class Binder extends android.os.Binder {
        public NotificationReceiver getService() {
            return NotificationReceiver.this;
        }
    }

}
