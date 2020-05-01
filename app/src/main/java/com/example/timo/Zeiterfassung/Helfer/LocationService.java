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

import static android.app.Notification.EXTRA_NOTIFICATION_ID;


public class LocationService extends Service {
    private static final String TAG = "logger";
    private static final int LOCATION_INTERVAL = 5000;
    private static final float LOCATION_DISTANCE = 0f;
    public static LocationService locationService;
    public static ArrayList<LatLng> listGeo = new ArrayList<LatLng>();
    public static int letzteIdNachrichtBezahlung;
    public static Boolean serviceAktiviert = false;
    public static DatenbankHelfer dbHelfer;
    public static Context context;
    public static ArrayList<Kunde> listKunde = new ArrayList<Kunde>();
    public static Binder binder;
    public static NotificationManagerCompat notificationManager;
    public static ArrayList<Position> listPosition = new ArrayList<Position>();
    public static Position position;
    public static HashMap<String,Integer> locIntervalls = new HashMap<>();
    public ArrayList<LatLng> LatLngPolyline = new ArrayList<LatLng>();
    public ArrayList<String> zahlartList = new ArrayList<String>();
    public ArrayList<String> zahlungsIntervallList = new ArrayList<String>();
    public ArrayList<String> zahlungsZeitpunkList = new ArrayList<String>();
    public ArrayList<String> zugangList = new ArrayList<String>();
    public ArrayList<String> TestArray = new ArrayList<String>();
    public ArrayList<String> listAbgang = new ArrayList<String>();
    public double lastValidLatitude;
    public double lastValidLongitude;
    public float currentAccuracy;
    public int countGeo;
    public Boolean inKreis;
    public Boolean aktuellBeimKunden = false, monteurBeiKeinemKunden = false;
    public Date datum;
    public Boolean mapsKoordinaten = false;
    public String strDatum;
    public double distance;
    public int indexLetzterKunde;
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
    public BroadcastReceiver receiverMaps = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int position = -1;
            Kunde kunde = (Kunde) intent.getSerializableExtra("KUNDE");
            int id = kunde.getId();

            for (int i = 0; i < listKunde.size(); i++) {
                if (id == listKunde.get(i).getId()) {
                    position = i;
                }

            }
            listKunde.set(position, kunde);
        }

    };
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

    public ArrayList<LatLng> getListGeo() {
        return listGeo;
    }
    public HashMap<String,Integer> getListIntervalls() {
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
        listKunde = dbHelfer.getListKunde();
        //    firebaseHandler.insertDate("auftrag/" + listKunde.get(1).getAuftragsId(),3);
        countGeo = (int) dbHelfer.ermittleAnzahlKunden(true);
        notificationManager = NotificationManagerCompat.from(this);
        LocalBroadcastManager.getInstance(context).registerReceiver(receiverMaps, new IntentFilter("maps"));

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
        locIntervalls.put("unter 5",0);
        locIntervalls.put("unter 10",0);
        locIntervalls.put("unter 15",0);
        locIntervalls.put("unter 20",0);
        locIntervalls.put("unter 30",0);
        locIntervalls.put("unter 40",0);
        locIntervalls.put("über 40",0);

        String meldungTrackingGestartet = getString(R.string.meldungTrackingGestartet);
        startForeground(1, pushNotificationTracking(LocationService.this, meldungTrackingGestartet, new Intent()));
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
        Log.e(TAG, "onDestroy");
        pushNotificationTracking(LocationService.this, "Tracking wurde beendet", new Intent());
        super.onDestroy();
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

    /*
        //Nachricht - Erinnerung
        public void pushNotification(Kunde kunde) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            String nachrichtErinnerung = context.getString(R.string.nachrichtErinnerung);
            String kundeNachnameVorherige = kunde.getNname();
            String kundeVornameVorherige = kunde.getVname();
            String kundeStrasseVorherige = kunde.getStrasse();
            int id = kunde.getId();
            nachrichtErinnerung = String.format(nachrichtErinnerung, kundeNachnameVorherige, kundeVornameVorherige, kundeStrasseVorherige);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            String channelId = "channel-01";
            String channelName = "Channel1";

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                notificationManager.createNotificationChannel(mChannel);
            }
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.ic_erinnerung)
                    .setContentTitle("Erinnerung")
                    .setContentText(nachrichtErinnerung)
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

            notificationManager.notify("erinnerung", id, mBuilder.build());
        }

        public void showNotification(Context context, String title, String body, Intent intent) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            int notificationId = 0;
            String channelId = "channel-01";
            String channelName = "Channel Name";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                notificationManager.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setSound(uri)
                    .setPriority(PRIORITY_HIGH)
                    .setContentText(body);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntent(intent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            mBuilder.setContentIntent(resultPendingIntent);

            notificationManager.notify(notificationId, mBuilder.build());
        }
    */
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

            Integer id,
            String firma,
            String kundeStrasse,
            int index
    ) {

        String nichtBezahlt = context.getString(R.string.btnNichtBezahlt);
        String nachrichtTaetigkeit = "Bitte wählen Sie eine Tätigkeit aus";
        String zumKunden = context.getString(R.string.btnZumKunden);
        String trennung;
        String tat1, tat2, tat3;
        tat1 = listKunde.get(index).getTaetigkeit_1();
        tat2 = listKunde.get(index).getTaetigkeit_2();
        tat3 = listKunde.get(index).getTaetigkeit_3();

        nachrichtTaetigkeit = String.format("Bitte Tätigkeit bei der Firma " + firma + " auswählen");

        Intent snoozeIntent = new Intent(context, NotificationReceiver.class);
        snoozeIntent.setAction(tat1);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        snoozeIntent.putExtra("kunde", listKunde.get(index));
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(context, 0, snoozeIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent snoozeIntent2 = new Intent(context, NotificationReceiver.class);
        snoozeIntent2.setAction(tat2);
        snoozeIntent2.putExtra(EXTRA_NOTIFICATION_ID, 0);
        snoozeIntent2.putExtra("kunde", listKunde.get(index));
        PendingIntent snoozePendingIntent2 =
                PendingIntent.getBroadcast(context, 0, snoozeIntent2, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent snoozeIntent3 = new Intent(context, NotificationReceiver.class);
        snoozeIntent3.setAction(tat3);
        snoozeIntent3.putExtra(EXTRA_NOTIFICATION_ID, 0);
        snoozeIntent3.putExtra("kunde", listKunde.get(index));
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

            posDump.setStartTime(position.getStartTime());
            if (position.getNamePosition().equals("Kunde")) {
                posDump.setKunde(position.getKunde());
            } else {
                posDump.setKundeVorher(position.getKundeVorher());
            }

            posDump.setNamePosition("AKTUELL");
            posDump.setEndTime(Calendar.getInstance());
            posDump.setArbeitszeitMinuten();
            posDump.setArbeitsZeitSekunden();

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

    private void ueberpruefeKunde(int i, int k, int status) {
     /*   kundeNachname = listKunde.get(i).getNname();
        kundeVorname = listKunde.get(i).getVname();
        knAdresse = listKunde.get(i).getStrasse();
        abgang = listKunde.get(i).getPosition() == -1;
        Zahlung zahlung = new Zahlung();*/
        //Falls es sich bei Kunden nicht um einen Abgang hält


        if (status != listKunde.get(i).getStatus()) {
            sendeBroadcast(i, k, listKunde.get(i).getStatus());
        }


    }

    private void sendeBroadcast(int i, int k, int status) {
        Intent intent;
        intent = new Intent("kundeBeliefert");
        intent.putExtra("markernr", i);

        //Kunde besitzt Kundenradius als Erkennungsbereich

        intent.putExtra("status", listKunde.get(i).getStatus());
        //Kunde besitzt Polygon als Erkennungsbereich
        Log.d("status", "SENDEVONLOCCCCCC");
        LocalBroadcastManager.getInstance(binder.getService()).sendBroadcast(intent);
    }

    public void checkAenderung(Kunde kunde, Boolean warBeiEinemKunden, int i) {

        if (kunde != null) {
            Boolean aktuellBeimKunden = kunde.getMonteurBeimKunden();
            //kreis true
            if (!aktuellBeimKunden) {
                listKunde.get(i).setMonteurBeimKunden(true);
                try {
                    position.setEndTime(Calendar.getInstance());
                    position.setArbeitszeitMinuten();
                    position.setArbeitsZeitSekunden();
                    position.setKundeAktuell(listKunde.get(i));
                    kundeAktuell = listKunde.get(i);
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
                kundeVorher = listKunde.get(i);
            }
        } else {
            if (warBeiEinemKunden) {
                this.warBeiEinemKunden = false;
                listKunde.get(indexLetzterKunde).setMonteurBeimKunden(false);
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
                position = new Position("Sonstiges");
                position.setStartTime(Calendar.getInstance());
                position.setKundeVorher(kundeVorher);
            }
        }

        /*
            if (aktuellBeimKunden && monteurBeiKeinemKunden) {
                aktuellBeimKunden = false;

                position.setEndTime();
                position.setArbeitszeitMinuten();
                position.setArbeitsZeitSekunden();
                listPosition.add(position);
                position = new Position("Sonstiges");
                position.setStartTime();



            } else {
                if (position == null) {
                    position = new Position("Sonstiges");
                    position.setStartTime();


                }
            }
            //listPosition.add(new Position("Kunde",listKunde.get(i).getFirma()));
        }
      */


    }

    private Integer posFromId(int id) {
        for (int i = 0; i < listKunde.size(); i++) {
            if (listKunde.get(i).getId() == id) {
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
            Integer id = intent.getIntExtra("id", -1);
            String auswahlTaetigkeit = intent.getStringExtra("auswahl");
            int pos = posFromId(id);
            listKunde.get(pos).setAuswahlTaetigkeit(auswahlTaetigkeit);
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
            listKunde.add(kunde);
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
            //    Log.d(TAG, location.toString());
            LatLng latLng;
            Intent intent;
            Integer statusVorher;
            Integer anzahlWerte;
            String key;
            mLastLocation.set(location);
            geo = new Geo(context);
          double latitudeCurrentLocation = lastValidLatitude;
          double longitudeCurrenLocation = lastValidLongitude;
            currentAccuracy = location.getAccuracy();

           if (currentAccuracy<5){
               key = "unter 5";
           }else if (currentAccuracy<10){
               key = "unter 10";
           }else if (currentAccuracy<15){
               key = "unter 15";
           }else if (currentAccuracy<20){
               key = "unter 20";
           }else if (currentAccuracy<30){
               key = "unter 30";
           }else if (currentAccuracy<40){
               key = "unter 40";
           }else {
               key = "über 40";
           }
           anzahlWerte =  locIntervalls.get(key);
           anzahlWerte++;
           locIntervalls.put(key,anzahlWerte);



            if (currentAccuracy >15 && listGeo.size() != 0){
                latitudeCurrentLocation = lastValidLatitude;
                longitudeCurrenLocation = lastValidLongitude;
            }else{
                 latitudeCurrentLocation = location.getLatitude();
                 longitudeCurrenLocation = location.getLongitude();
                 lastValidLatitude = latitudeCurrentLocation;
                 lastValidLongitude = longitudeCurrenLocation;
            }
            //   Toast.makeText(LocationService.this, String.valueOf(location.getProvider()), Toast.LENGTH_SHORT).show();
            firebaseHandler.insertDate("Standort", new LatLng(latitudeCurrentLocation, longitudeCurrenLocation));
            latLng = new LatLng(latitudeCurrentLocation, longitudeCurrenLocation);
            listGeo.add(latLng);


            double longitudeOfMarker;
            double latitudeOfMarker;
            int radius;
            int k = 0;
            double dist;
            Boolean KnBeliefet;
            //   Log.d(TAG, "LocationChange");
            for (int i = 0; i < listKunde.size(); i++) {
                Log.d("listSize", String.valueOf(listKunde.size()));
                if (i == listKunde.size() - 1) {
                    letzterDurchlauf = true;
                }
                longitudeOfMarker = listKunde.get(i).getLongitude();
                latitudeOfMarker = listKunde.get(i).getLatiude();
                radius = listKunde.get(i).getRadius();
                dist = geo.getDistance(latitudeCurrentLocation, longitudeCurrenLocation, latitudeOfMarker, longitudeOfMarker);
                //     Log.d("zeittest", "koordinaten: " + String.valueOf(latitudeCurrentLocation) +
                //           "   " + String.valueOf(longitudeCurrenLocation)
                //         + "   " + String.valueOf(latitudeOfMarker)
                //       + "   " + String.valueOf(longitudeOfMarker));
                if (listKunde.get(i).getStatus() == 1) {
                    KnBeliefet = true;
                } else {
                    KnBeliefet = false;
                }
                //Falls Kunden einen Kundenradius als Erkennungsbereich besitzt

                inKreis = geo.inKreis(dist, radius);


                String gg = "IJ";
                //Falls Kunde einen Polygon als Erkennungsberich besitzt
                statusVorher = listKunde.get(i).getStatus();
                //Falls sich der aktuelle Standort im Kunden Erkennungsbereich befindet und der Kunde noch nicht beliefert wurde
                if (inKreis) {
                    Log.d("listsize", String.valueOf(listKunde.get(i).getBesucht()));
                    indexLetzterKunde = i;
                    warBeiEinemKunden = true;
                    zeitGesetzt = true;
                    checkAenderung(listKunde.get(i), warBeiEinemKunden, i);

                    if (listKunde.get(i).getTaetigkeit_2() != null && !listKunde.get(i).getBesucht()) {
                        pushNotificationTaetigkeit(listKunde.get(i).getId(), listKunde.get(i).getFirma(), listKunde.get(i).getStrasse(), i);
                    }
                    listKunde.get(i).setBesucht(true);
                    if (listKunde.get(i).getStatus() == 1 || listKunde.get(i).getStatus() == 2) {
                        listKunde.get(i).setStatus(3);
                        dbHelfer.update(listKunde.get(i).getId(), "3", "Status");
                        firebaseHandler.insert("auftrag/" + firebaseHandler.getUserId() + "/" + listKunde.get(i).getAuftragsId() + "/status", 3);
                    }

                } else {
                    if (!zeitGesetzt && letzterDurchlauf) {
                        checkAenderung(null, warBeiEinemKunden, i);
                    }

                    if (listKunde.get(i).getBesucht()) {
                        listKunde.get(i).setStatus(2);
                        dbHelfer.update(listKunde.get(i).getId(), "2", "Status");
                        firebaseHandler.insert("auftrag/" + firebaseHandler.getUserId() + "/" + listKunde.get(i).getAuftragsId() + "/status", 2);
                    } else {
                        listKunde.get(i).setStatus(1);
                        dbHelfer.update(listKunde.get(i).getId(), "1", "Status");
                        firebaseHandler.insert("auftrag/" + firebaseHandler.getUserId() + "/" + listKunde.get(i).getAuftragsId() + "/status", 1);
                    }

                }

//                Log.d("zeittest", String.valueOf("position" + position.toString()));
                ueberpruefeKunde(i, k, statusVorher);
            }
            zeitGesetzt = false;
            letzterDurchlauf = false;
            monteurBeiKeinemKunden = false;


            //NUR ZUM TESTEN
            if (listHelferLocation.size() >1){
                latAlt_test = listHelferLocation.get(listHelferLocation.size() - 2).getLatAlt();
                longAlt_test = listHelferLocation.get(listHelferLocation.size() - 2).getLongAlt();
            }else{
                latAlt_test = 0;
                longAlt_test = 0;
            }

            time_test = location.getTime();
            strTime_test = convertTime(time_test);
            hasAcc_test = location.hasAccuracy();
            acc_test = location.getAccuracy();
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

            if (acc_test >= 100 || !provider_test.equals("gps")){
                listAusreiser_test.add(provider_test + " -> " + " -> index: " + String.valueOf(listHelferLocation.size()) + " -> " + String.valueOf(acc_test));
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
