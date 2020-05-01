package com.example.timo.Zeiterfassung.Helfer;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class Datum {
    int monatID;
    int monat;
    int tag;
    int jahr;
    public static final String TAG_MONTAG = "Montag";
    public static final String TAG_DIENSTAG = "Montag";
    public static final String TAG_MITTWOCH = "Montag";
    public static final String TAG_DONNERSTAG = "Montag";
    public static final String TAG_FREITAG= "Montag";
    public static final String TAG_SAMSTAG = "Montag";
    public static final String TAG_SONNTAG = "Montag";
    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");


    String datum;

    public String getDatumHeute(){
        Date datumHeute = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(datumHeute);
        monat = datumHeute.getMonth();
        tag = cal.get(Calendar.DAY_OF_MONTH);
        jahr = datumHeute.getYear();
        datum = format.format(datumHeute);
        return datum;

    }
    public Date getDatumHeute2(){
        Date datumHeute = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(datumHeute);
        monat = datumHeute.getMonth();
        tag = cal.get(Calendar.DAY_OF_MONTH);
        datumHeute.setDate(tag);
        return new Date(getJahr(),monat,tag);

    }

    public boolean einMonatNachAbgang(Date datum){
        Date datumHeute = new Date();
        monat = datumHeute.getMonth();
        tag = datumHeute.getDay();
        jahr = datumHeute.getYear();
        return (datumHeute.getMonth() > datum.getMonth()) & datumHeute.getDay() >= datum.getDay();
}
    public String getNaechstenSonntag(){
        String naechsterSonntag="";
        SimpleDateFormat datumFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());  // Datum von heute
        int monat = calendar.get(Calendar.MONTH);
        int jahr = calendar.get(Calendar.YEAR);
        int wochentag = calendar.get(Calendar.DAY_OF_WEEK);
        int monatstag = calendar.get(Calendar.DAY_OF_MONTH);
        int tagSonntag;
        int difZumNaechsenSonntag;
        if (wochentag == 1) {
            difZumNaechsenSonntag = 0;
        } else if (wochentag == 2) {
            difZumNaechsenSonntag = 6;
        } else if (wochentag == 3) {
            difZumNaechsenSonntag = 5;
        } else if (wochentag == 4) {
            difZumNaechsenSonntag = 4;
        } else if (wochentag == 5) {
            difZumNaechsenSonntag = 3;
        } else if (wochentag == 6) {
            difZumNaechsenSonntag = 2;
        } else {
            difZumNaechsenSonntag = 1;
        }
        tagSonntag = monatstag + difZumNaechsenSonntag;
        Date datumNaechsterSonntag = new Date(jahr - 1900, monat, tagSonntag);
        naechsterSonntag= datumFormat.format(datumNaechsterSonntag);
        return naechsterSonntag;
    }

    public int getMonat(){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        monat = calendar.get(Calendar.MONTH);
        return monat;
    }
    public int getTag(){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        tag = calendar.get(Calendar.DAY_OF_MONTH);
        return tag;
    }

    public int getJahr(){
        int jahr;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        jahr = calendar.get(Calendar.YEAR);
        return jahr;
    }
    public boolean istSonntag(){
        boolean istSonntag;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        istSonntag = calendar.get(Calendar.DAY_OF_WEEK) == 1;
        return istSonntag;
    }

    public Integer getMonat(String datum){
        int monat;
        String [] datumInformation = datum.split(Pattern.quote("."));
        monat = Integer.parseInt(datumInformation[1]);
        return monat;
        }

    public Integer getTag(String datum){
        int tag;
        String [] datumInformation = datum.split(Pattern.quote("."));
        tag = Integer.parseInt(datumInformation[0]);
        return tag;

    }
    public Boolean ersterSonntag(Calendar calendar){
        int ersterTag;
        int ersterSonntagImMonat = 0;
       // int tagHeute = getTag();
        int tagHeute = calendar.get(Calendar.DAY_OF_MONTH);
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        cal.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        cal.set(Calendar.DAY_OF_MONTH, 1);

        ersterTag = cal.get(Calendar.DAY_OF_WEEK);
        if (ersterTag == 1) {
            ersterSonntagImMonat = ersterTag;
        } else if (ersterTag == 2) {
            ersterSonntagImMonat = 7;
        } else if (ersterTag == 3) {
            ersterSonntagImMonat = 6;
        } else if (ersterTag == 4) {
            ersterSonntagImMonat = 5;
        } else if (ersterTag == 5) {
            ersterSonntagImMonat = 4;
        } else if (ersterTag == 6) {
            ersterSonntagImMonat = 3;
        } else {
            ersterSonntagImMonat = 2;
        }
        return ersterSonntagImMonat == tagHeute;
    }

    public Boolean letzerTag(int Tag, int monat){
        int letzterTag;
        int anzahlTageImMonat = getAnzahlTageImMonat(monat, 2019-1900);
        int tagHeute = getTag();
        int letzterSonntagImMonat = 0;
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, getMonat());
        cal.set(Calendar.YEAR, getJahr());
        cal.set(Calendar.DAY_OF_MONTH, anzahlTageImMonat);
        int letzterMontagImMonat;
        letzterTag = cal.get(Calendar.DAY_OF_WEEK);
        letzterSonntagImMonat = (anzahlTageImMonat - letzterTag)+1;
        if (letzterTag == 1) {
            letzterSonntagImMonat = anzahlTageImMonat;
            letzterMontagImMonat = -6;
        } else if (letzterTag == 2) {
            letzterSonntagImMonat = anzahlTageImMonat - 1;
            letzterMontagImMonat = anzahlTageImMonat;
        } else if (letzterTag == 3) {
            letzterSonntagImMonat = anzahlTageImMonat - 2;
            letzterMontagImMonat = anzahlTageImMonat-1;
        } else if (letzterTag == 4) {
            letzterSonntagImMonat = anzahlTageImMonat - 3;
        } else if (letzterTag == 5) {
            letzterSonntagImMonat = anzahlTageImMonat - 4;
        } else if (letzterTag == 6) {
            letzterSonntagImMonat = anzahlTageImMonat - 5;
        } else {
            letzterSonntagImMonat = anzahlTageImMonat - 6;
        }
        return letzterSonntagImMonat == tagHeute;
    }
    public Boolean letzterSonntag(Calendar calendar){
        int letzterTag;

        int anzahlTageImMonat = getAnzahlTageImMonat(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
      //  int tagHeute = getTag();
        int tagHeute = calendar.get(Calendar.DAY_OF_MONTH);
        int letzterSonntagImMonat = 0;
        int letzterTagImMonat;
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        cal.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        cal.set(Calendar.DAY_OF_MONTH, anzahlTageImMonat);
        letzterTag = cal.get(Calendar.DAY_OF_WEEK);
        letzterSonntagImMonat = (anzahlTageImMonat - letzterTag)+1;

        if (letzterTag == 1) {
            letzterSonntagImMonat = anzahlTageImMonat;
        } else if (letzterTag == 2) {
            letzterSonntagImMonat = anzahlTageImMonat - 1;
        } else if (letzterTag == 3) {
            letzterSonntagImMonat = anzahlTageImMonat - 2;
        } else if (letzterTag == 4) {
            letzterSonntagImMonat = anzahlTageImMonat - 3;
        } else if (letzterTag == 5) {
            letzterSonntagImMonat = anzahlTageImMonat - 4;
        } else if (letzterTag == 6) {
            letzterSonntagImMonat = anzahlTageImMonat - 5;
        } else {
            letzterSonntagImMonat = anzahlTageImMonat - 6;
        }
        return letzterSonntagImMonat == tagHeute;
    }
    public Integer getAnzahlTageImMonat(int monat, int jahr) {
        int anzahlTage;
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.MONTH, monat);
        cal.set(Calendar.YEAR, jahr);
        anzahlTage = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return anzahlTage;
    }
    public Integer letzterTagImMonat(int monat,int jahr){
        int letzterTag;
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.MONTH, monat);
        cal.set(Calendar.YEAR, jahr);
        letzterTag = cal.get(Calendar.DAY_OF_WEEK);
        return letzterTag;
    }
    public Integer letzterSonntagImMonat(int monat, int jahr) {
        int letzterTag;
        int anzahlTageImMonat = getAnzahlTageImMonat(monat, jahr);
        Integer letzterSonntagImMonat;
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, monat);
        cal.set(Calendar.YEAR, jahr);
        cal.set(Calendar.DAY_OF_MONTH, anzahlTageImMonat);
        letzterTag = cal.get(Calendar.DAY_OF_WEEK);
        if (letzterTag == 1) {
            letzterSonntagImMonat = anzahlTageImMonat;
        } else if (letzterTag == 2) {
            letzterSonntagImMonat = anzahlTageImMonat - 1;
        } else if (letzterTag == 3) {
            letzterSonntagImMonat = anzahlTageImMonat - 2;
        } else if (letzterTag == 4) {
            letzterSonntagImMonat = anzahlTageImMonat - 3;
        } else if (letzterTag == 5) {
            letzterSonntagImMonat = anzahlTageImMonat - 4;
        } else if (letzterTag == 6) {
            letzterSonntagImMonat = anzahlTageImMonat - 5;
        } else {
            letzterSonntagImMonat = anzahlTageImMonat - 6;
        }
        return letzterSonntagImMonat;
    }

    public Integer ersterSonntagImMonat(int monat, int jahr) {
        int ersterTag;
        int ersterSonntagImMonat;
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, monat);
        cal.set(Calendar.YEAR, jahr);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        ersterTag = cal.get(Calendar.DAY_OF_WEEK);
        if (ersterTag == 1) {
            ersterSonntagImMonat = ersterTag;
        } else if (ersterTag == 2) {
            ersterSonntagImMonat = 7;
        } else if (ersterTag == 3) {
            ersterSonntagImMonat = 6;
        } else if (ersterTag == 4) {
            ersterSonntagImMonat = 5;
        } else if (ersterTag == 5) {
            ersterSonntagImMonat = 4;
        } else if (ersterTag == 6) {
            ersterSonntagImMonat = 3;
        } else {
            ersterSonntagImMonat = 2;
        }
        return ersterSonntagImMonat;
    }

    public String getNameMonat(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }
}
