package com.example.timo.Zeiterfassung.Helfer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Datenbank extends SQLiteOpenHelper {

    public String tabelle;
    Context context;
    private SQLiteDatabase db;
    private String DEBUG_TAG = "protoApp";
    private ArrayList<String> tabelleList = new ArrayList<String>();


    public Datenbank(Context activity, String dbName, ArrayList<String> sqlList) {
        super(activity, dbName, null, 1);
        this.tabelleList = sqlList;
        int anzahlTabellen = sqlList.size();
        bestimmeTabelle(anzahlTabellen);
        db = this.getWritableDatabase();
        for (int i = 0; i < anzahlTabellen; i++) {
            //Tabelle erzeugen
            try {
                db.execSQL(tabelleList.get(i));


            } catch (Exception e) {
                String ff = "ff";

            }
        }
    }

    public Datenbank(Context activity, String dbName) {
        super(activity, dbName, null, 1);
        db = this.getWritableDatabase();
        this.context = activity;
    }


    private void bestimmeTabelle(int anzahlTabellen) {
        for (int i = 0; i < anzahlTabellen; i++) {

            String sql = tabelleList.get(i).toUpperCase();
            StringTokenizer tokenizer = new StringTokenizer(sql);

            //  den Tabellennamen suchen
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();

                if (token.equals("TABLE")) {
                    tabelle = tokenizer.nextToken();
                    break;
                }
            }
        }
    }


    public Cursor getErgebnisVonAbfrage(String sql, String[] listArgument) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(sql, listArgument);
        return cursor;
    }


    public void onCreate(SQLiteDatabase db) {
//        int anzahlTabellen = tabelle.length();
        try {
            // Tabelle anlegen

            //Tabelle erzeugen
            db.execSQL(tabelleList.get(0));


        } catch (Exception ex) {
            Log.e(DEBUG_TAG, ex.getMessage());
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tabelle);
        onCreate(db);
    }


    public void einfuegen(ContentValues daten, String tabelle) {


            long a = db.insert(tabelle, null, daten);
            Log.d("Firemire","a ist: " + String.valueOf(a));
            String gg = "";





    }

    public void loesche(String tabelle, String bedingung, String[] listArgument) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(tabelle, bedingung, listArgument);
    }



    //Update einer Kundeninformation
    public void update(long id, ContentValues daten, String spalte, String tabelle) {
        SQLiteDatabase db = getWritableDatabase();
        db.update(tabelle, daten, "KnId" + " = ?", new String[]{Long.toString(id)});

    }


    //Anzahl Kunden zurückgeben
    public long ermittleAnzahlKunden(Boolean mitAbgaengen) {
        SQLiteDatabase db = getReadableDatabase();
        long zaeler;

        zaeler = DatabaseUtils.queryNumEntries(db, "KUNDE");

        return zaeler;
    }

    //Anzahl Datensätze
    public long ermittleAnzahlDatensaetze () {
        SQLiteDatabase db = getReadableDatabase();
        long zaeler;

        zaeler = DatabaseUtils.queryNumEntries(db, "BERICHT");

        return zaeler;
    }


    public Integer getNaechsteId() {
        int id = 0;
        String sql = "SELECT KnId FROM KUNDE";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() == 0) {
            return 1;
        }
        cursor.moveToLast();
        id = cursor.getInt(0);

        return ++id;
    }
}