package com.example.timo.Zeiterfassung.Helfer;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timo.Zeiterfassung.Activity.FirebaseHandler;
import com.example.timo.Zeiterfassung.Beans.Position;
import com.example.timo.Zeiterfassung.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;


public class LocationService extends Service {
    String uid;
    private static final String TAG = "logger";
    private static final int LOCATION_INTERVAL = 5000;
    private static final float LOCATION_DISTANCE = 0f;
    public static LocationService locationService;
    public static ArrayList<LatLng> listGeo = new ArrayList<LatLng>();
    public static int letzteIdNachrichtBezahlung;
    public static Boolean serviceAktiviert = false;
    public static DatenbankHelfer dbHelfer;
    public static Context context;
    public static Map<String, Kunde> listKunde = new HashMap<String, Kunde>();
    public static Binder binder;
    public static NotificationManagerCompat notificationManager;
    public static ArrayList<Position> listPosition = new ArrayList<Position>();
    public static Position position;
    public static HashMap<String, Integer> locIntervalls = new HashMap<>();
    public ArrayList<LatLng> LatLngPolyline = new ArrayList<LatLng>();
    public ArrayList<String> zahlartList = new ArrayList<String>();
    public ArrayList<String> zahlungsIntervallList = new ArrayList<String>();
    public ArrayList<String> zahlungsZeitpunkList = new ArrayList<String>();
    public ArrayList<String> zugangList = new ArrayList<String>();
    public ArrayList<String> TestArray = new ArrayList<String>();
    public ArrayList<String> listAbgang = new ArrayList<String>();
    public double lastValidLatitude;
    public double lastValidLongitude;
    public float lasValidAcc;
    public float currentAccuracy;
    public int countGeo;
    public Boolean inKreis;
    public Boolean aktuellBeimKunden = false, monteurBeiKeinemKunden = false;
    public Date datum;
    public Boolean mapsKoordinaten = false;
    public String strDatum;
    public double distance;
    public String uidLetzterKunde;
    public Integer tagHeute;
    public Integer monatHeute;
    public Integer jahrHeute;
    public SimpleDateFormat datumFormat = new SimpleDateFormat("dd.MM.yyyy");
    public Date datumHeute = Calendar.getInstance().getTime();
    public Boolean sonntag = true;
    public String strDatumHeute = datumFormat.format(datumHeute);
    public Integer letzterSonntagImMonat = 0;
    public Integer ersterSonntagImMonat = 0;
    public View mapView;

    double latAlt_test;
    double longAlt_test;
    long time_test;
    String strTime_test;
    boolean hasAcc_test;
    float acc_test;
    boolean hasSpeedAcc_test;
    double distanceToLastPoint_test;
    String provider_test;
    ArrayList<HelferLocation> listHelferLocation;
    ArrayList<String> listAusreiser_test = new ArrayList<String>();
    Kunde kundeVorher;
    Kunde kundeAktuell;
    FirebaseHandler firebaseHandler;
    FirebaseUser user;
    Boolean warBeiEinemKunden = false;
    Boolean warSonstiges = false;
    BroadcastReceiver mReceiver;
    BroadcastReceiver receiverNeuerKunde;
    Double latitudeFirma = 55.55;
    Double longitudeFirma = 5555.4444;
    int idVorherigerKunde;
    LocationService mService;
    Geo geo;
    String KundeNichtBeliefert;
    String kundeNachname;
    String kundeVorname;
    String knAdresse;
    EditText txtRadius;
    Boolean LetzteKundeBeliefert = false;
    Boolean NaechsterKundeBeliefert = false;
    Boolean nachrichtBezahlung = false;
    Boolean zeitGesetzt = false;
    Boolean letzterDurchlauf = false;
    Gson gson = new Gson();
    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER),
            new LocationListener(LocationManager.PASSIVE_PROVIDER)
    };
    private ArrayList<String> locList = new ArrayList<>();
    private ArrayList<Long> timeList = new ArrayList<Long>();
    private int countLocationChange = 0;
    private ArrayList<Integer> listCountLocationChange = new ArrayList<Integer>();
    private ArrayList<String> longitude = new ArrayList<String>();
    private ArrayList<String> latitude = new ArrayList<String>();
    private ArrayList<String> RadiusList = new ArrayList<String>();
    private ArrayList<String> KnBeliefertList = new ArrayList<String>();
    private ArrayList<String> AdressList = new ArrayList<String>();
    private ArrayList<String> PolygonList = new ArrayList<String>();
    private LocationManager mLocationManager = null;

    public static LocationService getInstance() {
        if (locationService == null) {
            locationService = new LocationService();
        }
        return locationService;
    }

    public static Boolean isLocation() {
        if (locationService == null) {
            return false;
        } else {
            return true;
        }

    }

    public BroadcastReceiver receiverMaps = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int position = -1;
            Kunde kunde = (Kunde) intent.getSerializableExtra("KUNDE");
            int id = kunde.getId();

            for (int i = 0; i < listKunde.size(); i++) {
                if (id == listKunde.get(uid).getId()) {
                    position = i;
                }

            }
            //HIER
            //   listKunde.set(position, kunde);
        }

    };

    public BroadcastReceiver receiverFirebase = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(LocationService.this, String.valueOf("Message from Firebase"), Toast.LENGTH_SHORT).show();
        }

    };

    public ArrayList<LatLng> getListGeo() {
        return listGeo;
    }

    public HashMap<String, Integer> getListIntervalls() {
        return locIntervalls;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = this;
        dbHelfer = new DatenbankHelfer(context);
        listHelferLocation = new ArrayList<HelferLocation>();
        listAusreiser_test = new ArrayList<String>();
        serviceAktiviert = true;
        listKunde = FirebaseHandler.listKunde;
        //    firebaseHandler.insertDate("auftrag/" + listKunde.get(1).getAuftragsId(),3);
        // countGeo = (int) dbHelfer.ermittleAnzahlKunden(true);
        notificationManager = NotificationManagerCompat.from(this);
        LocalBroadcastManager.getInstance(context).registerReceiver(receiverMaps, new IntentFilter("maps"));
        LocalBroadcastManager.getInstance(context).registerReceiver(receiverFirebase, new IntentFilter("firebase"));
        String meldungTrackingGestartet = getString(R.string.meldungTrackingGestartet);
        startForeground(1, pushNotificationTracking(LocationService.this, meldungTrackingGestartet, new Intent()));
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }


    @Override
    public void onCreate() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("auswahlTaetigkeit");
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("neuerKunde");
        mReceiver = new MyBroadcastReceiver();
        registerReceiver(mReceiver, filter);
        receiverNeuerKunde = new MyBroadcastReceiverNeuerKunde();
        registerReceiver(receiverNeuerKunde, filter2);
        serviceAktiviert = true;
        Datum datum = new Datum();
        tagHeute = datum.getTag();
        monatHeute = datum.getMonat();
        jahrHeute = datum.getJahr();
        strDatum = datum.getDatumHeute();
        sonntag = datum.istSonntag();
        listGeo.clear();
        timeList.clear();
        locList.clear();
        initializeLocationManager();
        binder = new Binder();
        firebaseHandler = new FirebaseHandler();

        //NUR ZUM TESTEN
        locIntervalls.put("unter 5", 0);
        locIntervalls.put("unter 10", 0);
        locIntervalls.put("unter 15", 0);
        locIntervalls.put("unter 20", 0);
        locIntervalls.put("unter 30", 0);
        locIntervalls.put("unter 40", 0);
        locIntervalls.put("über 40", 0);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                mLocationListeners[0]);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        /*
        mLocationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                mLocationListeners[1]);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.requestLocationUpdates(
                LocationManager.PASSIVE_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                mLocationListeners[2]);

*/
    }

    @Override
    public void onDestroy() {


        unregisterReceiver(mReceiver);
        unregisterReceiver(receiverNeuerKunde);

        pushNotificationTracking(LocationService.this, "Tracking wurde beendet", new Intent());
        setAktiviert(false);
        locationService = null;
        Log.d("changeEvent", "onDestroy: ");
        //  stopForeground(STOP_FOREGROUND_DETACH);
        // stopSelf();
        //   super.onDestroy();
  /*
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {

                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "", ex);
                    System.exit(0);
                }
            }

        }
        */

        System.exit(0);

    }

    //Location Manager initialisieren falls noch nicht geschehen
    private void initializeLocationManager() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            // criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
            mLocationManager.getBestProvider(criteria, false);


        }
    }


    public android.app.Notification pushNotificationTracking(Context context, String titel, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        //   String channelId = cha;
        String channelId = "chanel-0";
        String channelName = "Channel";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(titel);
        //   .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
        return (mBuilder.build());
    }

    //Nachricht - BezahlungActivity
    public void pushNotificationTaetigkeit(
            Kunde kunde,
            Integer id,
            String uid

    ) {


        String nachrichtTaetigkeit = "Bitte wählen Sie eine Tätigkeit aus";

        String tat1 = "", tat2 = "", tat3 = "";
        tat1 = kunde.getListAuftrag().get(0);
        tat2 = kunde.getListAuftrag().get(1);
        if (kunde.getListAuftrag().size() > 2) {
            tat3 = kunde.getListAuftrag().get(2);
        }


        nachrichtTaetigkeit = String.format("Bitte Tätigkeit bei der Firma " + kunde.getFirma() + " auswählen");

        Intent snoozeIntent = new Intent(context, NotificationReceiver.class);
        snoozeIntent.setAction(tat1);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        snoozeIntent.putExtra("kunde", kunde);
        snoozeIntent.putExtra("uid", uid);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(context, 0, snoozeIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent snoozeIntent2 = new Intent(context, NotificationReceiver.class);
        snoozeIntent2.setAction(tat2);
        snoozeIntent2.putExtra(EXTRA_NOTIFICATION_ID, 0);
        snoozeIntent2.putExtra("kunde", kunde);
        snoozeIntent2.putExtra("uid", uid);
        PendingIntent snoozePendingIntent2 =
                PendingIntent.getBroadcast(context, 0, snoozeIntent2, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent snoozeIntent3 = new Intent(context, NotificationReceiver.class);
        snoozeIntent3.setAction(tat3);
        snoozeIntent3.putExtra(EXTRA_NOTIFICATION_ID, 0);
        snoozeIntent3.putExtra("kunde", kunde);
        snoozeIntent3.putExtra("uid", uid);
        PendingIntent snoozePendingIntent3 =
                PendingIntent.getBroadcast(context, 0, snoozeIntent3, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        String channelId = "channel-03";
        String channelName = "Channel-3";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_gps_on)
                .setContentTitle("Tätigkeit")
                .addAction(R.drawable.ic_snooze, tat1,
                        snoozePendingIntent)
                .addAction(R.drawable.ic_snooze, tat2,
                        snoozePendingIntent2)
                .setContentText(nachrichtTaetigkeit)
                .setSound(uri)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setColor(8)
                .setAutoCancel(true);


        if (tat3 != null) {
            mBuilder.addAction(R.drawable.ic_snooze, tat3,
                    snoozePendingIntent3);
        }
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(new Intent());
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);
        notificationManager.notify("taetigkeit", 555, mBuilder.build());
        letzteIdNachrichtBezahlung = id;
    }

/*
    //Nachricht - Warnung
    public void pushNotificationAbgang(Integer id, String kundeAdresse) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String nachrichtWarnung = getString(R.string.nachrichtWarnung);
        nachrichtWarnung = String.format(nachrichtWarnung, kundeNachname, kundeVorname, knAdresse);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        String channelId = "channel-02";
        String channelName = "Channel2";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_warnung)
                .setContentTitle("Warnung!!!")
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .setContentText(nachrichtWarnung)
                .setSound(uri)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setColor(8)
                .setAutoCancel(false);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(new Intent());
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(id, mBuilder.build());

    }

    //Nachricht - BezahlungActivity
    public void pushNotificationBezahlung(

            Integer id,
            String kundeAdresse,
            String zahlungsintervall,
            String zahlungszeitpunkt,
            boolean bezahlmarker) {
        String nichtBezahlt = context.getString(R.string.btnNichtBezahlt);
        String nachrichtBezahlung = context.getString(R.string.nachrichtBezahlung);
        String zumKunden = context.getString(R.string.btnZumKunden);
        String trennung;
        if (kundeVorname == null || kundeVorname.equals("")) {
            trennung = " ";
        } else {
            trennung = ", ";
        }
        nachrichtBezahlung = String.format(nachrichtBezahlung, kundeNachname, trennung, kundeVorname, kundeAdresse);
        Intent snoozeIntent = new Intent(context, NotificationReceiver.class);
        snoozeIntent.setAction(nichtBezahlt);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        snoozeIntent.putExtra("id", id);
        snoozeIntent.putExtra("zahlungsintervall", zahlungsintervall);
        snoozeIntent.putExtra("zahlungszeitpunkt", zahlungszeitpunkt);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(context, 0, snoozeIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Intent snoozeIntent2 = new Intent(context, NotificationReceiver.class);

        snoozeIntent2.setAction(zumKunden);
        snoozeIntent2.putExtra("id", id);
        snoozeIntent2.putExtra(EXTRA_NOTIFICATION_ID, 0);
        snoozeIntent2.putExtra("kundeNachname", kundeNachname);
        snoozeIntent2.putExtra("knAdresse", knAdresse);
        snoozeIntent2.putExtra("knBezahlmarker", bezahlmarker);
        PendingIntent snoozePendingIntent2 =
                PendingIntent.getBroadcast(context, 0, snoozeIntent2, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        String channelId = "channel-03";
        String channelName = "Channel-3";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_bezahlung_nachricht)
                .setContentTitle("Bezahlung")
                .setContentText(nachrichtBezahlung)
                .addAction(R.drawable.ic_snooze, "Nicht bezahlt",
                        snoozePendingIntent)
                .addAction(R.drawable.ic_snooze, "Zum Kunden",
                        snoozePendingIntent2)
                .setSound(uri)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setColor(8)
                .setAutoCancel(true);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(new Intent());
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);
        notificationManager.notify("bezahlung", id, mBuilder.build());
        letzteIdNachrichtBezahlung = id;
    }
*/

    public ArrayList<Position> getListPosition(Boolean stopTracking) {
        Log.d("zeittest", "AAAAAAAAAAAAAAAAAAAA " + String.valueOf(stopTracking));
        ArrayList<Position> listPositionCopy = new ArrayList<Position>();
        //Kopie der Liste erstellen
        listPositionCopy.clear();

/*        if (listPosition.size() == 0){
            return listPosition;
        }*/


        for (int i = 0; i < listPosition.size(); i++) {
            listPositionCopy.add(listPosition.get(i));
        }

        if (stopTracking) {
            position.setEndTime(Calendar.getInstance());
            position.setArbeitszeitMinuten();
            position.setArbeitsZeitSekunden();
            position.setKundeAktuell(kundeAktuell);
            if (listPositionCopy.size() == 0) {
                //   rundeZeitBegin(position);
            }
            listPositionCopy.add(position);
        } else {
            Position posDump = new Position("");


if (position != null){
    posDump.setStartTime(position.getStartTime());


                if (position.getNamePosition().equals("Kunde")) {
                    posDump.setKunde(position.getKunde());
                } else {
                    posDump.setKundeVorher(position.getKundeVorher());
                }

}else{
    posDump.setStartTime(Calendar.getInstance());
}
            posDump.setNamePosition("AKTUELL");
            posDump.setEndTime(Calendar.getInstance());

            if (position != null) {
                posDump.setArbeitszeitMinuten();
                posDump.setArbeitsZeitSekunden();
            }else{
                posDump.setArbeitszeitMinuten(0);
            }
            if (listPositionCopy.size() == 0) {
                //(position);
            }
            listPositionCopy.add(posDump);
        }

        return listPositionCopy;
    }

    public Boolean getAktiviert() {
        return serviceAktiviert;
    }

    public void setAktiviert(Boolean aktiviert) {
        serviceAktiviert = aktiviert;
    }

    private void ueberpruefeKunde(String uid, int k, int status) {
     /*   kundeNachname = listKunde.get(uid).getNname();
        kundeVorname = listKunde.get(uid).getVname();
        knAdresse = listKunde.get(uid).getStrasse();
        abgang = listKunde.get(uid).getPosition() == -1;
        Zahlung zahlung = new Zahlung();*/
        //Falls es sich bei Kunden nicht um einen Abgang hält


        if (status != listKunde.get(uid).getStatus()) {

            sendeBroadcast(uid, k, listKunde.get(uid).getStatus());
        }


    }

    private void sendeBroadcast(String uid, int k, int status) {
        Intent intent;
        intent = new Intent("kundeBeliefert");
        intent.putExtra("markernr", uid);

        //Kunde besitzt Kundenradius als Erkennungsbereich

        intent.putExtra("status", listKunde.get(uid).getStatus());
        //Kunde besitzt Polygon als Erkennungsbereich
        Log.d("status", "SENDEVONLOCCCCCC");
        LocalBroadcastManager.getInstance(binder.getService()).sendBroadcast(intent);
    }

    public void checkAenderung(Kunde kunde, Boolean warBeiEinemKunden, String uid) {

        if (kunde != null) {
            Boolean aktuellBeimKunden = kunde.getMonteurBeimKunden();
            Log.d("AktuellK", "aktuellBeimKunden " + String.valueOf(aktuellBeimKunden));
            //kreis true
            if (!aktuellBeimKunden) {
                Log.d("AktuellK", "UID " + uid);
                listKunde.get(uid).setMonteurBeimKunden(true);
                try {
                    position.setEndTime(Calendar.getInstance());
                    position.setArbeitszeitMinuten();
                    position.setArbeitsZeitSekunden();
                    position.setKundeAktuell(listKunde.get(uid));
                    kundeAktuell = listKunde.get(uid);
                    if (listPosition.size() == 0) {
                        // rundeZeitBegin(position);
                    }
                    listPosition.add(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                position = new Position("Kunde", kunde.getFirma());
                position.setStartTime(Calendar.getInstance());
                position.setKunde(kunde);
                kundeVorher = listKunde.get(uid);

                if (position.getKunde().getFirma().equals("Zuhause")) {
                    Log.d("AktuellK", "2");
                    Log.d("changeevent", "Zuhause");
                    boolean istKundeInListe = position.istKundeInListe(listPosition);
                    if (istKundeInListe) {
                        Log.d("AktuellK", "3");
                        Log.d("testev", "Stop Tracking");
                        //    if (listPosition == null) {
                        ArrayList<Position> listPostionGesamt = new ArrayList<Position>();
                        listPosition = getListPosition(true);
                        TaetigkeitsberichtUtil taetigkeitsberichtUtil = new TaetigkeitsberichtUtil();
                        //Filter Positionen
                        listPostionGesamt.addAll(listPosition);
                        Position posGesamt = new Position("");
                        listPosition = posGesamt.getListPositionOhneAusreisser(listPosition);
                        listPosition = taetigkeitsberichtUtil.sortiereListe(listPosition, listPostionGesamt);

                        listPosition = posGesamt.getListPositonOhneDuplikate(listPosition);
                        listPosition = posGesamt.getListPositonOhneDuplikateSonstiges(listPosition);
                        listPosition = posGesamt.getGueltigePositionen(listPosition);
                        //Arbeitszeit runden
                        if (listPosition.size() != 0) {
                            listPosition.set(0, posGesamt.getRoundStartzeit(listPosition.get(0)));
                            listPosition.set(listPosition.size() - 1, posGesamt.getRoundEndzeit(listPosition.get(listPosition.size() - 1)));
                        }
                        String arbeitszeit;
                        if (listPosition.size() != 0) {
                            arbeitszeit = posGesamt.getArbeitszeitGesamt(listPosition, null, null, null, null);
                        } else {
                            arbeitszeit = "0";
                        }
                        //Time String setzen
                        for (int i = 0; i < listPosition.size(); i++) {
                            listPosition.get(i).setDauerString(listPosition.get(i).getDauerString());
                        }
                        String taetigkeitsbericht = taetigkeitsberichtUtil.getTaetigkeitsbericht(listPosition);
                        Datum datum = new Datum();
                        String tagHeuteFormated = datum.getFormatedTagHeute();
                        if (listPostionGesamt.size() != 0) {
                            firebaseHandler.insert("taetigkeitsbericht/" + firebaseHandler.getUserId() + "/" + tagHeuteFormated + "/bericht", taetigkeitsbericht);
                            firebaseHandler.insert("taetigkeitsbericht/" + firebaseHandler.getUserId() + "/" + tagHeuteFormated + "/abgeschlossen", false);
                            firebaseHandler.insert("taetigkeitsbericht/" + firebaseHandler.getUserId() + "/" + tagHeuteFormated + "/arbeitszeit", arbeitszeit);
                        } else {
                            Toast.makeText(LocationService.this, "Es wurde keine Arbeitszeit erfasst", Toast.LENGTH_LONG).show();
                        }

                        stopSelf();
                        stopForeground(true);
                        //     stopService(new Intent(MainActivity.this, LocationService.class));
                        //Stop
                    }
                }
            }
        } else {
            Log.d("AktuellK", "4");
            if (warBeiEinemKunden) {
                Log.d("AktuellK", "5");
                this.warBeiEinemKunden = false;
                try {
                    Log.d("AktuellK", "7");
                    Log.d("uidLetzte ", uidLetzterKunde);
                    listKunde.get(uidLetzterKunde).setMonteurBeimKunden(false);
                } catch (Exception e) {
                    //Kunde exisitiert nemma
                    e.printStackTrace();
                }

                position.setEndTime(Calendar.getInstance());
                position.setArbeitszeitMinuten();
                position.setArbeitsZeitSekunden();
                if (listPosition.size() == 0) {
                    // rundeZeitBegin(position);
                }
                listPosition.add(position);
                position = new Position("Sonstiges");
                position.setStartTime(Calendar.getInstance());
                position.setKundeVorher(kundeVorher);
            }
            if (position == null) {
                Log.d("AktuellK", "6");
                position = new Position("Sonstiges");
                position.setKundeVorher(kundeVorher);
            }
        }
    }

    private Integer posFromId(int id) {
        for (int i = 0; i < listKunde.size(); i++) {
            if (listKunde.get(uid).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private void rundeZeitBegin(Position position) {
        // position.setArbeitsZeitBeginnMinGerundet(position);
    }

    private void rundeZeitEnde(Position position) {
        //  position.setArbeitsZeitEndeMinGerundet(position);
    }

    public String convertTime(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // do something
            //   Integer id = intent.getIntExtra("id", -1);
            String uid = intent.getStringExtra("uid");
            String auswahlTaetigkeit = intent.getStringExtra("auswahl");
            Log.i("555", "Auswahl " + auswahlTaetigkeit);
            Log.i("555", "UidLoc " + uid);
            //  int pos = posFromId(id);
            listKunde.get(uid).setAuswahlTaetigkeit(auswahlTaetigkeit);
            notificationManager.cancelAll();

        }


    }

    public class MyBroadcastReceiverNeuerKunde extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // do something
            //     listKunde.clear();
            //   listKunde = dbHelfer.getListKunde();
            Log.d("status", "empfangeBroadcast");
            Kunde kunde = (Kunde) intent.getSerializableExtra("Kunde");
            //HIER
            //   listKunde.add(kunde);
        }


    }

    //Location Listener
    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            mLastLocation = new Location(provider);
        }

        //Standort hat sich geändert - Überprüfung auf neu belieferte Kunden

        @Override
        public void onLocationChanged(Location location) {
            Log.d("changeEvent", "onLocationChanged: ");
            if (location.getAccuracy() < 13) {


                //    Log.d(TAG, location.toString());
                LatLng latLng;
                Intent intent;
                Integer statusVorher;
                Integer anzahlWerte;
                String key;
                Float t = location.getAccuracy();
                mLastLocation.set(location);
                geo = new Geo(context);
                double latitudeCurrentLocation = lastValidLatitude;
                double longitudeCurrenLocation = lastValidLongitude;
                currentAccuracy = location.getAccuracy();

                if (currentAccuracy < 5) {
                    key = "unter 5";
                } else if (currentAccuracy < 10) {
                    key = "unter 10";
                } else if (currentAccuracy < 15) {
                    key = "unter 15";
                } else if (currentAccuracy < 20) {
                    key = "unter 20";
                } else if (currentAccuracy < 30) {
                    key = "unter 30";
                } else if (currentAccuracy < 40) {
                    key = "unter 40";
                } else {
                    key = "über 40";
                }
                anzahlWerte = locIntervalls.get(key);
                anzahlWerte++;
                locIntervalls.put(key, anzahlWerte);


                if (currentAccuracy > 8 && listGeo.size() != 0) {
                    latitudeCurrentLocation = lastValidLatitude;
                    longitudeCurrenLocation = lastValidLongitude;
                    acc_test = lasValidAcc;
                } else {
                    latitudeCurrentLocation = location.getLatitude();
                    longitudeCurrenLocation = location.getLongitude();
                    acc_test = location.getAccuracy();
                    lastValidLatitude = latitudeCurrentLocation;
                    lastValidLongitude = longitudeCurrenLocation;
                    lasValidAcc = acc_test;
                }
                //   Toast.makeText(LocationService.this, String.valueOf(location.getProvider()), Toast.LENGTH_SHORT).show();
                firebaseHandler.insert("Standort/" + firebaseHandler.getUserId(), new LatLng(latitudeCurrentLocation, longitudeCurrenLocation));
                latLng = new LatLng(latitudeCurrentLocation, longitudeCurrenLocation);
                listGeo.add(latLng);


                double longitudeOfMarker;
                double latitudeOfMarker;
                int radius;
                int k = 0;
                double dist;
                Boolean KnBeliefet;
                //   Log.d(TAG, "LocationChange");

                int i = 0;
                for (Map.Entry<String, Kunde> entry : listKunde.entrySet()) {
                    i++;
                    String uid = entry.getKey();

                    if (i == listKunde.size()) {
                        letzterDurchlauf = true;
                    }
                    longitudeOfMarker = listKunde.get(uid).getLongitude();
                    latitudeOfMarker = listKunde.get(uid).getLatitude();
                    radius = listKunde.get(uid).getRadius();
                    dist = geo.getDistance(latitudeCurrentLocation, longitudeCurrenLocation, latitudeOfMarker, longitudeOfMarker);
                    //     Log.d("zeittest", "koordinaten: " + String.valueOf(latitudeCurrentLocation) +
                    //           "   " + String.valueOf(longitudeCurrenLocation)
                    //         + "   " + String.valueOf(latitudeOfMarker)
                    //       + "   " + String.valueOf(longitudeOfMarker));
                    if (listKunde.get(uid).getStatus() == 1) {
                        KnBeliefet = true;
                    } else {
                        KnBeliefet = false;
                    }
                    //Falls Kunden einen Kundenradius als Erkennungsbereich besitzt

                    inKreis = geo.inKreis(dist, radius);


                    //Falls Kunde einen Polygon als Erkennungsberich besitzt
                    statusVorher = listKunde.get(uid).getStatus();
                    //Falls sich der aktuelle Standort im Kunden Erkennungsbereich befindet und der Kunde noch nicht beliefert wurde
                    if (inKreis) {

                        Log.d("listsize", String.valueOf(listKunde.get(uid).getBesucht()));
                        uidLetzterKunde = uid;
                        warBeiEinemKunden = true;
                        zeitGesetzt = true;
                        checkAenderung(listKunde.get(uid), warBeiEinemKunden, uid);

                        //Default Tätigkeite
                        if (listKunde.get(uid).getListAuftrag() == null) {
                            listKunde.get(uid).setAuswahlTaetigkeit("-");
                        } else {

                         //   listKunde.get(uid).setAuswahlTaetigkeit(listKunde.get(uid).getAuswahlTaetigkeit());
                         //   listKunde.get(uid).setAuswahlTaetigkeit(listKunde.get(uid).getListAuftrag().get(0));
                        }

                        if (listKunde.get(uid).getListAuftrag() != null) {
                            if (listKunde.get(uid).getListAuftrag().size() > 1 && !listKunde.get(uid).getBesucht()) {
                                pushNotificationTaetigkeit(listKunde.get(uid), i, uid);
                            }
                        }

                        listKunde.get(uid).setBesucht(true);
                        if (listKunde.get(uid).getStatus() == 1 || listKunde.get(uid).getStatus() == 2) {
                            listKunde.get(uid).setStatus(3);
                            //  dbHelfer.update(listKunde.get(uid).getId(), "3", "Status");
                            if (!uid.equals("Zuhause")) {
                                firebaseHandler.insert("kunde/" + uid + "/status", 3);
                            }

                        }

                    } else {
                        Log.d("kreischeck", "elsefall: " + uid);
                        if (!zeitGesetzt && letzterDurchlauf) {
                            checkAenderung(null, warBeiEinemKunden, uid);
                        }

                        if (listKunde.get(uid).getBesucht()) {
                            listKunde.get(uid).setStatus(2);
                            //     dbHelfer.update(listKunde.get(uid).getId(), "2", "Status");
                            if (!uid.equals("Zuhause")) {
                                firebaseHandler.insert("kunde/" + uid + "/status", 2);
                            }

                        }
                        /*
                        else {
                            listKunde.get(uid).setStatus(1);
                            //      dbHelfer.update(listKunde.get(uid).getId(), "1", "Status");
                            if (!uid.equals("Zuhause")) {
                                firebaseHandler.insert("kunde/" + uid + "/status", 1);
                            }

                        }*/

                    }

//                Log.d("zeittest", String.valueOf("position" + position.toString()));
                    ueberpruefeKunde(uid, k, statusVorher);
                }


                zeitGesetzt = false;
                letzterDurchlauf = false;
                monteurBeiKeinemKunden = false;


                //NUR ZUM TESTEN
                if (listHelferLocation.size() > 1) {
                    latAlt_test = listHelferLocation.get(listHelferLocation.size() - 2).getLatAlt();
                    longAlt_test = listHelferLocation.get(listHelferLocation.size() - 2).getLongAlt();
                } else {
                    latAlt_test = 0;
                    longAlt_test = 0;
                }

                time_test = location.getTime();
                strTime_test = convertTime(time_test);
                hasAcc_test = location.hasAccuracy();
                //  acc_test = location.getAccuracy();
                hasSpeedAcc_test = location.hasSpeed();
                provider_test = location.getProvider();
                distanceToLastPoint_test = geo.getDistance(latitudeCurrentLocation, longitudeCurrenLocation, latAlt_test, longAlt_test);


                listHelferLocation.add(new HelferLocation(
                        latitudeCurrentLocation,
                        longitudeCurrenLocation,
                        strTime_test,
                        hasAcc_test,
                        acc_test,
                        hasSpeedAcc_test,
                        distanceToLastPoint_test,
                        provider_test));

                if (acc_test >= 100 || !provider_test.equals("gps")) {
                    listAusreiser_test.add(provider_test + " -> " + " -> index: " + String.valueOf(listHelferLocation.size()) + " -> " + String.valueOf(acc_test));
                }

            }
        }


        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }


        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    public class Binder extends android.os.Binder {
        public LocationService getService() {
            return LocationService.this;
        }
    }
}
