package com.example.timo.Zeiterfassung.Helfer;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
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

    public String getFormatedTagHeute(){
        Datum datum = new Datum();
        String wochentag = datum.getWochentag();
        Datum datumHeute = new Datum();
        String tagHeute = datumHeute.getDatumHeute();
        tagHeute = tagHeute.replace(".", "-");
        return tagHeute;
    }

    public String getFormatedTag(String tag){
        String formatedTag;
        Calendar c = Calendar.getInstance();
        int tagImMonat = c.get(Calendar.DAY_OF_WEEK);
        int tagImMonatAuswahl=0;
        int tagDif;
        //Wenn es heute Sonntag ist
        if (tagImMonat == 1){
            tagImMonat = 7;
        }else{
            tagImMonat--;
        }
        String day,month,year;

        if (tag.equals("MONTAG")){
          //  c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            tagImMonatAuswahl = 1;
        }else if (tag.equals("DIENSTAG")){
            tagImMonatAuswahl = 2;
        }else if (tag.equals("MITTWOCH")){
            tagImMonatAuswahl = 3;
        }else if (tag.equals("DONNERSTAG")){
            tagImMonatAuswahl = 4;
        }else if (tag.equals("FREITAG")){
            tagImMonatAuswahl = 5;
        }
        else if (tag.equals("SAMSTAG")){
            tagImMonatAuswahl = 6;
        }
        else if (tag.equals("SONNTAG")){
            tagImMonatAuswahl = 7;
        }

        tagDif = tagImMonat - tagImMonatAuswahl;

        c.add(Calendar.DAY_OF_MONTH,-tagDif);
        day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        month = String.valueOf(c.getTime().getMonth()+1);
        year = String.valueOf(c.getTime().getYear() + 1900);
        if (day.length() == 1){
            day = "0" + day;
        }
        if (month.length() == 1){
            month = "0" + month;
        }
        formatedTag = day + "-" + month + "-" + year;

       return formatedTag;
    }

    public String getWochentag(){
        Calendar calendar = Calendar.getInstance();
        Date dateHeute = new Date();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) +1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

// First convert to Date. This is one of the many ways.
        String dateString = String.format("%d-%d-%d", year, month, day);
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

// Then get the day of week from the Date based on specific locale.
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.GERMAN).format(date);

        return dayOfWeek; // Friday
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
