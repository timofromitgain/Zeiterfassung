package com.example.timo.Zeiterfassung.Activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.timo.Zeiterfassung.Helfer.Datum;
import com.example.timo.Zeiterfassung.Helfer.Kunde;
import com.example.timo.Zeiterfassung.Helfer.LocationService;
import com.example.timo.Zeiterfassung.Helfer.TaetigkeitsberichtUtil;
import com.example.timo.Zeiterfassung.Helfer.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseHandler {
    private FirebaseAuth firebaseAuth;
    Binder binder = new Binder();
    MainActivity mainActivity = new MainActivity();
    //KarteActivity karteActivity = KarteActivity.getInstance();
    private String userId;
    public static String val = "555";
    //  private ArrayList<Kunde> listKunde;
    FirebaseUser user;
    public static HashMap<String, Kunde> listKunde = new HashMap<>();
    public static HashMap<String, Kunde> listAuftrag = new HashMap<>();
    public static User monteur = new User();
    public  HashMap<String,TaetigkeitsberichtUtil> listBericht = new HashMap<>();
    public FirebaseHandler() {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


    }

    public FirebaseHandler(boolean mock) {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        DatabaseReference refKunde = FirebaseDatabase.getInstance().getReference().child("kunde");
        DatabaseReference refAuftrag = FirebaseDatabase.getInstance().getReference().child("auftrag");
        refKunde.addChildEventListener(listenerKunde);
        refAuftrag.addChildEventListener(listenerAuftrag);
        setUserData();


    }

    public String getUserId() {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        return user.getUid();
    }

    public void insertDate(String child, Object object) {
        Log.d("Firemire", String.valueOf(user.getUid()));
        DatabaseReference refUser = FirebaseDatabase.getInstance().getReference(user.getUid());
        DatabaseReference ref = refUser.child(child);
        // ref.push().setValue(object);
        ref.setValue(object);
    }

    public void insert(String ref, Object object) {
        Log.d("Firemire", String.valueOf(user.getUid()));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(ref);
        //   DatabaseReference ref = refUser.child(child);
        // ref.push().setValue(object);
        databaseReference.setValue(object);
    }
    public void insert(String ref, Object object, Context context,boolean ende) {
        Log.d("Firemire", String.valueOf(user.getUid()));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(ref);
        //   DatabaseReference ref = refUser.child(child);
        // ref.push().setValue(object);
        databaseReference.setValue(object);
        Intent myService = new Intent(context, LocationService.class);
      //  stopService(myService);
        if (ende){
            try{
                mainActivity.stopService(myService);
            }catch (Exception e){

            }

        }

    }


    public String userId() {
        return firebaseAuth.getCurrentUser().getUid();
    }

    ChildEventListener listenerKunde = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.i("changeevent", "onChildAddedKunde");

            //  Kunde kunde = dataSnapshot.getValue(Kunde.class);
            String kundeid = dataSnapshot.getKey();
            //  Object obj = dataSnapshot.getValue();
            Kunde kunde = dataSnapshot.getValue(Kunde.class);
            listKunde.put(kundeid, kunde);
            mainActivity.neuerKunde(listKunde);

            KarteActivity karteActivity = KarteActivity.karteActivity;
            //  karteActivity.getInstance().neuerKunde(kundeid,kunde);

            try {
                karteActivity.neuerKunde(kundeid, kunde);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //   iFirebase.neuerKunde(listKunde);


        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Log.i("listenerfb", "onChildChangedJau");
            String kundeid = dataSnapshot.getKey();
            Kunde kunde = dataSnapshot.getValue(Kunde.class);
            Log.d("changeevent", "kstatus: " + String.valueOf(kunde.getStatus()) + " list: " + String.valueOf(listKunde.get(kundeid).getStatus()));
            Boolean istStatusAenderung = istStatusAender(listKunde.get(kundeid), kunde);

            // kunde.setListAuftrag(listAuftrag.get(kundeid).getListAuftrag());
            if (!istStatusAenderung) {
                listKunde.put(kundeid, kunde);
                mainActivity.neuerKunde(kundeid, kunde);
                KarteActivity karteActivity = KarteActivity.karteActivity;
                //  karteActivity.getInstance().neuerKunde(kundeid,kunde);

                karteActivity.neuerKunde(kundeid, kunde);
            }


            Intent intent;
            intent = new Intent("firebase");
            //      intent.putExtra("markernr", uid);

            //Kunde besitzt Kundenradius als Erkennungsbereich

            //   intent.putExtra("status", listKunde.get(uid).getStatus());
            //Kunde besitzt Polygon als Erkennungsbereich

        }

        private boolean istStatusAender(Kunde kundeAlt, Kunde kundeNeu) {
            if (!kundeAlt.getFirma().equals(kundeNeu.getFirma())) {
                Log.i("changeevent", "firma ");
                return false;
            } else if (kundeAlt.getLatitude() != kundeNeu.getLatitude()) {
                Log.i("changeevent", "lati ");
                return false;
            } else if (kundeAlt.getLongitude() != kundeNeu.getLongitude()) {
                Log.i("changeevent", "longi ");
                return false;
            } else if (kundeAlt.getRadius() != kundeNeu.getRadius()) {
                Log.i("changeevent", "radi ");
                return false;
            } else if (!kundeAlt.getStadt().equals(kundeNeu.getStadt())) {
                Log.i("changeevent", "stadti ");
                return false;
            } else if (!kundeAlt.getStrasse().equals(kundeNeu.getStrasse())) {
                Log.i("changeevent", "straßi ");
                return false;
            } else {
                Log.i("changeevent", "nothing ");
                return true;

            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Log.i("changeevent", "onChildCRemoved");
            String kundeid = dataSnapshot.getKey();
            Kunde kunde = dataSnapshot.getValue(Kunde.class);
            listKunde.remove(kundeid);
            mainActivity.loescheKunde(kundeid);
            KarteActivity karteActivity = KarteActivity.karteActivity;
            //  karteActivity.getInstance().neuerKunde(kundeid,kunde);

            karteActivity.loescheKunde(kundeid);
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            Log.i("listenerfb", "onChildMoved");
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.i("listenerfb", "onChildCancelled");
        }
    };

    ChildEventListener listenerAuftrag = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.i("changeevent", "onChildAddedAuftrag");
            Map<Object, Object> hm = new HashMap<Object, Object>();
            String kundeid = dataSnapshot.getKey();
            Object object = dataSnapshot.getValue();

            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String auftragid = postSnapshot.getKey();
                String tateigkeit = postSnapshot.child("taetigkeit").getValue(String.class);
                try {
                    ArrayList<String> listAuftragAlt = listKunde.get(kundeid).getListAuftrag();
                    listKunde.get(kundeid).getListAuftrag().add(tateigkeit);
                } catch (Exception e) {
                    ArrayList<String> listAuftrag = new ArrayList<String>();
                    listAuftrag.add(tateigkeit);

                    listKunde.get(kundeid).setListAuftrag(listAuftrag);


                }
                String g = "f";

            }


            //    String t = object.taetigkeit;
            //    String auftragid = dataSnapshot.getValue();
            //    Kunde kunde = dataSnapshot.getValue(Kunde.class);
            //      listKunde.put(key,kunde);

            //    Map<String, String> map = ...


        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Log.i("listenerfb", "onChildChanged");
            Log.i("changeevent", "onChildAddedAuftrag");
            Map<Object, Object> hm = new HashMap<Object, Object>();
            String kundeid = dataSnapshot.getKey();
            Object object = dataSnapshot.getValue();

            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String auftragid = postSnapshot.getKey();
                String tateigkeit = postSnapshot.child("taetigkeit").getValue(String.class);
                try {
                    ArrayList<String> listAuftragAlt = listKunde.get(kundeid).getListAuftrag();
                    listKunde.get(kundeid).getListAuftrag().add(tateigkeit);
                } catch (Exception e) {
                    ArrayList<String> listAuftrag = new ArrayList<String>();
                    listAuftrag.add(tateigkeit);
                    if (!kundeid.equals("firma")) {
                        listKunde.get(kundeid).setListAuftrag(listAuftrag);
                    }

                }
                String g = "f";
            }

            KarteActivity karteActivity = KarteActivity.karteActivity;
            //  karteActivity.getInstance().neuerKunde(kundeid,kunde);

            karteActivity.neuerKunde(kundeid, listKunde.get(kundeid));
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Log.i("listenerfb", "onChildCRemoved");
            Log.i("listenerfb", "onChildChanged");
            Log.i("changeevent", "onChildAddedAuftrag");
            Map<Object, Object> hm = new HashMap<Object, Object>();
            String kundeid = dataSnapshot.getKey();
            Object object = dataSnapshot.getValue();

            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String auftragid = postSnapshot.getKey();
                String tateigkeit = postSnapshot.child("taetigkeit").getValue(String.class);
                try {
                    ArrayList<String> listAuftragAlt = listKunde.get(kundeid).getListAuftrag();
                    listKunde.get(kundeid).getListAuftrag().add(tateigkeit);
                } catch (Exception e) {
                    ArrayList<String> listAuftrag = new ArrayList<String>();
                    listAuftrag.add(tateigkeit);
                    if (!kundeid.equals("firma")) {
                        listKunde.get(kundeid).setListAuftrag(listAuftrag);
                    }

                }
                String g = "f";
            }

            KarteActivity karteActivity = KarteActivity.karteActivity;
            //  karteActivity.getInstance().neuerKunde(kundeid,kunde);

            karteActivity.neuerKunde(kundeid, listKunde.get(kundeid));


        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            Log.i("listenerfb", "onChildMoved");
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.i("listenerfb", "onChildCancelled");
        }
    };


    public void setUserData() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("user/" + firebaseUser.getUid());
        DatabaseReference refKunde = refUser.child("Kunde");


        refUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ;
                User user = dataSnapshot.getValue(User.class);
                Kunde kundeZuhause = new Kunde();
                kundeZuhause.setFirma("Zuhause");
                kundeZuhause.setStrasse(user.getStrasse());
                kundeZuhause.setStadt(user.getStadt());
                kundeZuhause.setMonteurBeimKunden(false);
                kundeZuhause.setRadius(75);
                kundeZuhause.setLatitude(user.getLatitude());
                kundeZuhause.setLongitude(user.getLongitude());
                ArrayList<String> listTaetigkeit = new ArrayList<String>();
                listTaetigkeit.add("Aufenthalt Zuhause");
                kundeZuhause.setListAuftrag(listTaetigkeit);
                listKunde.put("Zuhause", kundeZuhause);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void getSingleData(String path) {

        DatabaseReference ref;
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        //  DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("user/" + firebaseUser.getUid());
        ref = FirebaseDatabase.getInstance().getReference(path);


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ;
                TaetigkeitsberichtUtil taetigkeitsberichtUtil;

                taetigkeitsberichtUtil = dataSnapshot.getValue(TaetigkeitsberichtUtil.class);

                Taetigkeitsbericht taetigkeitsbericht = Taetigkeitsbericht.taetigkeitsbericht;
                MainActivity mainActivity = MainActivity.mainActivity;

                if (taetigkeitsbericht != null) {
                    taetigkeitsbericht.onDayOfTaetigkeitsberichtChange(taetigkeitsberichtUtil);
                } else if (mainActivity != null) {
                    mainActivity.onGetTaetigkeitsberichtData(taetigkeitsberichtUtil);
                }


                String iodfdof = "dd";
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String iofdof = "dd";
            }
        });


    }

    public void getMultipleData(String path, String wochentag) {



        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference(path);


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ;
                TaetigkeitsberichtUtil taetigkeitsberichtUtil;
                taetigkeitsberichtUtil = dataSnapshot.getValue(TaetigkeitsberichtUtil.class);
                Taetigkeitsbericht taetigkeitsbericht = Taetigkeitsbericht.taetigkeitsbericht;
                listBericht.put(wochentag,taetigkeitsberichtUtil);
                if (listBericht.size() == 7){
                    taetigkeitsbericht.onGetWochenbericht(listBericht);
                }
                String iodfdof = "dd";
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String iofdof = "dd";
            }
        });


    }

    public String getWochenData() {

        String path = "taetigkeitsbericht/" + getUserId() + "/";
        Datum datum = new Datum();
        String wochentag = datum.getFormatedTag("MONTAG");
        String pathMo = path + wochentag;
        wochentag = datum.getFormatedTag("DIENSTAG");
        String pathDi = path + wochentag;
        wochentag = datum.getFormatedTag("MITTWOCH");
        String pathMi = path + wochentag;
        wochentag = datum.getFormatedTag("DONNERSTAG");
        String pathDo = path + wochentag;
        wochentag = datum.getFormatedTag("FREITAG");
        String pathFr = path + wochentag;
        wochentag = datum.getFormatedTag("SAMSTAG");
        String pathSa = path + wochentag;
        wochentag = datum.getFormatedTag("SONNTAG");
        String pathSo = path + wochentag;

        //  String wochentag = datum.getFormatedTag("MONTAG");
        path = path + wochentag;
        getMultipleData(pathMo,"MONTAG");
        getMultipleData(pathDi,"DIENSTAG");
        getMultipleData(pathMi,"MITTWOCH");
        getMultipleData(pathDo,"DONNERSTAG");
        getMultipleData(pathFr,"FREITAG");
        getMultipleData(pathSa,"SAMSTAG");
        getMultipleData(pathSo,"SONNTAG");

        return null;
    }

    public void removePath(String path) {
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference(path);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public class Binder extends android.os.Binder {
        public FirebaseHandler getService() {
            return FirebaseHandler.this;
        }
    }
}
