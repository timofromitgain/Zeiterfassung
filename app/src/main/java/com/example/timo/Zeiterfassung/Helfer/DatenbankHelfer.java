package com.example.timo.Zeiterfassung.Helfer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.example.timo.Zeiterfassung.Activity.FirebaseHandler;
import com.example.timo.Zeiterfassung.Activity.MainActivity;
import com.example.timo.Zeiterfassung.Interface.OnGetData;
import com.example.timo.Zeiterfassung.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class DatenbankHelfer {
    private Datenbank db;
    private Context context;
    private String nameDatenbank;
    private FirebaseAuth firebaseAuth;
    private FirebaseHandler firebaseHandler;
    private ArrayList<Kunde> listKundeFirebase;
    public DatenbankHelfer(Context activity, String dbName, ArrayList<String> sqlList) {
        Datenbank db = new Datenbank(activity, dbName, sqlList);
        this.db = db;
        firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseHandler = new FirebaseHandler();


    }

    public DatenbankHelfer(Context context) {
        this.context = context;
        nameDatenbank = context.getString(R.string.nameDatenbank);
        Datenbank db = new Datenbank(context, nameDatenbank);
        this.db = db;
        firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseHandler = new FirebaseHandler();

    }

    public DatenbankHelfer() {

        String ff = "dfdf";
    }


    //Speicherung von neuen Kunde in Datenbank
    public void datensatzEinfuegen(Kunde kunde, String tabelle) {
        //  //Datenbank db = new Datenbank(context, nameDatenbank);
        ContentValues daten;


        HashMap<String, ContentValues> hashDaten = new HashMap<>();
        ContentValues contentValues;
        //  int anzahlAllerKunden = (int) db.ermittleAnzahlKunden(true) + 1;
        if (tabelle.equals("Kunde")) {
            hashDaten = erzeugeDatenObjekt(kunde);

            Set<String> keys = hashDaten.keySet();

            for (String key : keys) {
                db.einfuegen(hashDaten.get(key), "KUNDE");
            }
        } else {
            //Aktuelle CalenderWoche
            Calendar dummyCalendar = Calendar.getInstance();
            int aktuelleWoche = dummyCalendar.get(Calendar.WEEK_OF_YEAR);
            contentValues = erzeugeDatenObjektBericht(aktuelleWoche);
            db.einfuegen(contentValues, "BERICHT");
        }



    }

    public void datenSatzEinfuegenFirebase(Kunde kunde){
        // id wird automatisch von SQLite gefällt
        //Firebase
   //     if (db.ermittleAnzahlDatensaetze() != 0){
            FirebaseUser user = firebaseAuth.getCurrentUser();
            DatabaseReference refEmail = FirebaseDatabase.getInstance().getReference(user.getUid());
            DatabaseReference refAufrag = refEmail.child("Kunde");

            refAufrag.push().setValue(kunde);

        }
    //}
    public void copyDataFromFirebaseToLocal(){


         getListFromFb(new OnGetData() {
            ArrayList<Kunde> listKunde = new ArrayList<Kunde>();
            Kunde kunde = new Kunde();
            @Override
            public void onSuccess(ArrayList<Kunde> listKunde) {
                db.loesche("Kunde", "KnId<>?",new String[]{String.valueOf("1")});
                for (int i = 0;i<listKunde.size();i++){
                    kunde = listKunde.get(i);
                    datensatzEinfuegen(kunde,"Kunde");
                }
                Toast.makeText(context, "Neue Daten importiert", Toast.LENGTH_SHORT).show();
            }

        });
        Log.d("Firemire","Ende");

   //     ArrayList<Kunde> listKunde = new ArrayList<Kunde>();
      //  listKunde = firebaseHandler.getListKunde();




        }



    //Speicherung von neuen Kunde in Datenbank
    public void datensatzEinfuegenFirebase(String benutzerId,String status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("BenutzerId",benutzerId);
        contentValues.put("Status",status);
        db.einfuegen(contentValues,"Benutzer");
    }

    public HashMap<String, ContentValues> erzeugeDatenObjekt(Kunde kunde) {
        HashMap<String, ContentValues> hashDaten = new HashMap<>();
        ContentValues daten = new ContentValues();
     //   Integer id = db.getNaechsteId();
        daten = erzeugeDatenObjektKunde(kunde);
        hashDaten.put("KUNDE", daten);


        return hashDaten;
    }

    //Datensatz Kunde
    private ContentValues erzeugeDatenObjektKunde(Kunde kunde) {
        ContentValues daten = new ContentValues();
        daten.put("KnId", kunde.getAuftragsId());
        daten.put("Firma", kunde.firma);
        daten.put("Ansprechpartner", kunde.ansprechpartner);
        daten.put("Strasse", kunde.strasse);
        daten.put("Stadt", kunde.stadt);
        daten.put("Anmerkung", "hgg");
        daten.put("Latitude", kunde.latiude);
        daten.put("Longitude", kunde.longitude);
        daten.put("Status", 1);
        daten.put("Radius", 60);
        daten.put("Taetigkeit1", kunde.taetigkeit_1);
        daten.put("Taetigkeit2", kunde.taetigkeit_2);
        daten.put("Taetigkeit3", kunde.taetigkeit_3);
        return daten;
    }

    private ContentValues erzeugeDatenObjektBericht(int aktuelleWoche) {
        ContentValues daten = new ContentValues();
        daten.put("KnId", "1");
        daten.put("KWOCHE", aktuelleWoche);
        daten.put("MONTAG", "MONTAG");
        daten.put("DIENSTAG", "DIENSTAG");
        daten.put("MITTWOCH", "MITTWOCH");
        daten.put("DONNERSTAG", "DONNERSTAG");
        daten.put("FREITAG", "FREITAG");
        daten.put("SAMSTAG", "SAMSTAG");

        return daten;
    }

    public String getWochentag() {


        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
// 3 letter name form of the day
        String tagHeut = new SimpleDateFormat("EEEE", Locale.GERMAN).format(date.getTime()).toUpperCase();

        return tagHeut;
    }

    //Update einer Kundeninformation
    public void update(long id, String text, String spalte) {
        String tabelle = "KUNDE";
        //Datenbank db = new Datenbank(context, nameDatenbank);

        if (spalte.equals("")) {
            spalte = getWochentag();
            tabelle = "BERICHT";
        } else if (spalte.equals("MONTAG")) {
            spalte = "MONTAG";
            tabelle = "BERICHT";
        } else if (spalte.equals("DIENSTAG")) {
            spalte = "DIENSTAG";
            tabelle = "BERICHT";
        } else if (spalte.equals("MITTWOCH")) {
            spalte = "MITTWOCH";
            tabelle = "BERICHT";
        } else if (spalte.equals("DONNERSTAG")) {
            spalte = "DONNERSTAG";
            tabelle = "BERICHT";
        } else if (spalte.equals("FREITAG")) {
            spalte = "FREITAG";
            tabelle = "BERICHT";
        } else if (spalte.equals("SAMSTAG")) {
            spalte = "SAMSTAG";
            tabelle = "BERICHT";
        } else if (spalte.equals("KWOCHE")) {
            spalte = "KWOCHE";
            tabelle = "BERICHT";
        }


        ContentValues daten = new ContentValues();
     //   Toast.makeText(context, "spalte -> " + spalte + "text -> " + text, Toast.LENGTH_LONG).show();
        daten.put(spalte, text);
        db.update(id, daten, spalte, tabelle);

    }


    //Alles Kunden als Arraylist zurückgeben
    public ArrayList<Kunde> getListKunde() {

        int id;
        Cursor cursor;
        String sql = "";
        ArrayList<Kunde> listKunde = new ArrayList<Kunde>();
        Kunde kunde;

        sql = "SELECT DISTINCT * " +
                "FROM KUNDE";


        cursor = db.getErgebnisVonAbfrage(sql, null);

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
                kunde = setKundenInformation(id, cursor);
                listKunde.add(kunde);

            } while (cursor.moveToNext());
        }
        int idLetzterKunde = listKunde.get(listKunde.size() - 1).getId();
    /*    Kunde firma = new Kunde();
        firma.setLatiude(555.444);
        firma.setLongitude(33.44);
        firma.setId(idLetzterKunde+1);
        firma.setRadius(10)        listKunde.add(firma);*/
        return listKunde;
    }

    //Alles Kunden als Arraylist zurückgeben
    public String getBerichtWochentag(String wochentag) {

        int id;
        Cursor cursor;
        String sql = "";
        String jsonArray;
        ArrayList<Kunde> listKunde = new ArrayList<Kunde>();
        Kunde kunde;

        sql = "SELECT  " + wochentag + " FROM BERICHT";

        cursor = db.getErgebnisVonAbfrage(sql, null);
        cursor.moveToFirst();
        jsonArray = cursor.getString(cursor.getColumnIndex(wochentag));


    /*    Kunde firma = new Kunde();
        firma.setLatiude(555.444);
        firma.setLongitude(33.44);
        firma.setId(idLetzterKunde+1);
        firma.setRadius(10)        listKunde.add(firma);*/
        return jsonArray;
    }




    //Setzen von Kundendaten
    private Kunde setKundenInformation(int id, Cursor cursor) {
        Kunde kunde = new Kunde();
        kunde.setId(id);
        kunde.setAuftragsId(cursor.getString(cursor.getColumnIndex("KnId")));
        kunde.setAnsprechpartner(cursor.getString(cursor.getColumnIndex("Ansprechpartner")));
        kunde.setFirma(cursor.getString(cursor.getColumnIndex("Firma")));
        kunde.setStrasse(cursor.getString(cursor.getColumnIndex("Strasse")));
        kunde.setStadt(cursor.getString(cursor.getColumnIndex("Stadt")));
        kunde.setTaetigkeit_1(cursor.getString(cursor.getColumnIndex("Taetigkeit1")));
        kunde.setTaetigkeit_2(cursor.getString(cursor.getColumnIndex("Taetigkeit2")));
        kunde.setTaetigkeit_3(cursor.getString(cursor.getColumnIndex("Taetigkeit3")));
        kunde.setAnmerkung(cursor.getString(cursor.getColumnIndex("Anmerkung")));
        kunde.setStatus(cursor.getInt(cursor.getColumnIndex("Status")));
        kunde.setRadius(cursor.getInt(cursor.getColumnIndex("Radius")));
        kunde.setLatiude(cursor.getDouble(cursor.getColumnIndex("Latitude")));
        kunde.setLongitude(cursor.getDouble(cursor.getColumnIndex("Longitude")));

        return kunde;
    }

    /*
    //Alle Kunden als Liste zurückgeben
    public List<Kunde> getAllKunden(String kunden) {
        //Datenbank db = new Datenbank(context, nameDatenbank);
        List<Kunde> list = new ArrayList<>();
        String abfrage = "";
        if (kunden.equals("alle")) {
            abfrage = "SELECT DISTINCT * " +
                    "FROM KUNDE,ZAHLUNG,ZEITUNG,ERKENNUNGSBEREICH,LIEFERSTATUS " +
                    "WHERE KUNDE.KnId = ZAHLUNG.KnId " +
                    "AND KUNDE.KnId = ZEITUNG.KnId " +
                    "AND KUNDE.KnId = LIEFERSTATUS.KnId " +
                    "AND KUNDE.KnId = ERKENNUNGSBEREICH.KnId " +
                    "ORDER BY Position";
        } else if (kunden.equals("aktiv")) {
            abfrage = "SELECT DISTINCT * " +
                    "FROM KUNDE,ZUGANG,ZAHLUNG,ZEITUNG,ERKENNUNGSBEREICH,LIEFERSTATUS " +
                    "WHERE KUNDE.KnId = ZUGANG.KnId " +
                    "AND KUNDE.KnId = ZAHLUNG.KnId " +
                    "AND KUNDE.KnId = ZEITUNG.KnId " +
                    "AND KUNDE.KnId = LIEFERSTATUS.KnId " +
                    "AND KUNDE.KnId = ERKENNUNGSBEREICH.KnId " +
                    "ORDER BY Position";
        } else if (kunden.equals("inaktiv")) {
            abfrage = "SELECT DISTINCT * " +
                    "FROM KUNDE,ABGANG,ZAHLUNG,ZEITUNG,ERKENNUNGSBEREICH,LIEFERSTATUS " +
                    "WHERE KUNDE.KnId = ABGANG.KnId " +
                    "AND KUNDE.KnId = ZAHLUNG.KnId " +
                    "AND KUNDE.KnId = ZEITUNG.KnId " +
                    "AND KUNDE.KnId = LIEFERSTATUS.KnId " +
                    "AND KUNDE.KnId = ERKENNUNGSBEREICH.KnId " +
                    "ORDER BY Position";
        }


        Cursor cursor = db.getErgebnisVonAbfrage(abfrage, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                Kunde kunde = new Kunde();
                kunde = setKundenInformation(id, cursor);
                list.add(kunde);

            } while (cursor.moveToNext());
        }
        return list;
    }
*/
    public void loesche(String tabelle, String bedingung, String[] listArgument) {
        //Datenbank db = new Datenbank(context, nameDatenbank);
        db.loesche(tabelle, bedingung, listArgument);
    }


    //Anzahl Kunden zurückgeben
    public long ermittleAnzahlKunden(Boolean mitAbgaengen) {
        //   //Datenbank db = new Datenbank(context, nameDatenbank);
        int anzahlKunden = (int) db.ermittleAnzahlKunden(mitAbgaengen);

        return anzahlKunden;
    }

    //Anzahl Datensätze Bericht
    public long ermittleAnzahlDatensaetze() {
        //   //Datenbank db = new Datenbank(context, nameDatenbank);
        int anzahl = (int) db.ermittleAnzahlDatensaetze();

        return anzahl;
    }

        public void getListFromFb(final OnGetData listener){
            //   ArrayList<Kunde> listKunde = new ArrayList<Kunde>();
            FirebaseUser user = firebaseAuth.getCurrentUser();
            DatabaseReference refUser = FirebaseDatabase.getInstance().getReference(user.getUid());
            DatabaseReference refKunde = refUser.child("Kunde");
            Log.d("Firemire",user.getUid());
            listKundeFirebase = new ArrayList<Kunde>();

            refKunde.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot valuesRes: dataSnapshot.getChildren()){

                        Kunde kunde =  valuesRes.getValue(Kunde.class);
                        Log.d("Firemire","jm : " + valuesRes.getValue().toString());
                        listKundeFirebase.add(kunde);
                    }
                    listener.onSuccess(listKundeFirebase);
                    Log.d("Firemire","size : " +String.valueOf(listKundeFirebase.size()));

                    Log.d("Firemire","size: " + listKundeFirebase.size());


                    // listKunde =  (ArrayList<Kunde>) dataSnapshot.getValue();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                  //  Log.d("Firemire",String.valueOf(listKunde.size()));
                }
            });
            Log.d("Firemire","return");


        }

}