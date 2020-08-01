package com.example.timo.Zeiterfassung.Beans;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.timo.Zeiterfassung.Helfer.Kunde;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class Position implements Serializable, Comparable<Position> {
    static int pause = 0;
    String namePosition, firma;
    Kunde kunde;
    Kunde kundeVorher, kundeAktuell;
    String arbeitsZeitBeginn, arbeitsZeitEnde;
    String arbeitsZeitBeginnStdGerundet, arbeitsZeitBeginnMinGerundet, arbeitsZeitEndeStdGerundet, arbeitsZeitEndeMinGerundet;


    Integer pause1 = 0, pause2 = 0;
    int minGesamt1;
    int minGesamt2;
    public static ArrayList<Position> listPosition;

    public static void setListPosition(ArrayList<Position> list) {
        listPosition = list;

    }


    public static ArrayList<Position> getListPosition() {
        return listPosition;
    }

    public void setKundeVorher(Kunde kundeVorher) {
        this.kundeVorher = kundeVorher;
    }

    public void setKundeAktuell(Kunde kundeAktuell) {
        this.kundeAktuell = kundeAktuell;
    }


    public String getArbeitsZeitBeginnStdGerundet() {
        return arbeitsZeitBeginnStdGerundet;
    }

    public String getArbeitsZeitBeginnMinGerundet() {
        return arbeitsZeitBeginnMinGerundet;
    }

    public String getArbeitsZeitEndeStdGerundet() {
        return arbeitsZeitEndeStdGerundet;
    }

    public String getArbeitsZeitEndeMinGerundet() {
        return arbeitsZeitEndeMinGerundet;
    }

    public Kunde getKundeVorher() {
        return kundeVorher;
    }

    public Integer getPause1() {
        return pause1;
    }

    public void setPause1(Integer pause1) {
        this.pause1 = pause1;
    }

    public Integer getPause2() {
        return pause2;
    }

    public void setPause2(Integer pause2) {
        this.pause2 = pause2;
    }
    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getFirma() {
        return firma;
    }

/*
    public void setArbeitsZeitBeginnStdGerundet(String std) {
        this.arbeitsZeitBeginnStdGerundet = arbeitsZeitBeginnStdGerundet;
    }
*/
/*
    public void setArbeitsZeitBeginnMinGerundet(Position position) {
      //  int min = position.getArbeitszeitMinuten();
        int min = position.getStartTime().getTime().getMinutes();
        int minGerundet;
        int std = position.getStartTime().getTime().getHours();
        if (min > 0 && min < 15) {
            minGerundet = 15;
            this.arbeitsZeitBeginnMinGerundet = String.valueOf(minGerundet);
            position.getStartTime().getTime().setMinutes(minGerundet);
        } else if (min > 15 && min < 30) {
            minGerundet = 30;
            this.arbeitsZeitBeginnMinGerundet = String.valueOf(minGerundet);
            position.getStartTime().getTime().setMinutes(minGerundet);
        } else if (min > 30 && min < 45) {
            minGerundet = 45;
            this.arbeitsZeitBeginnMinGerundet = String.valueOf(minGerundet);
            position.getStartTime().getTime().setMinutes(minGerundet);
        } else if (min > 45 && min < 60) {
            minGerundet = 0;
            this.arbeitsZeitBeginnMinGerundet = String.valueOf(minGerundet);
            position.getStartTime().getTime().setMinutes(minGerundet);
            setArbeitsZeitBeginnStdGerundet(String.valueOf(std + 1));
        }

    }
    */

    public Calendar getAzeitBegGerundet(Position position) {
        //  int min = position.getArbeitszeitMinuten();
        int min = position.getStartTime().getTime().getMinutes();
        int minGerundet;
        int std = position.getStartTime().getTime().getHours();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();


        if (min > 0 && min < 15) {
            minGerundet = 15;
            this.arbeitsZeitBeginnMinGerundet = String.valueOf(minGerundet);
            date.setMinutes(minGerundet);
            date.setHours(position.getStartTime().getTime().getHours());
        } else if (min > 15 && min < 30) {
            minGerundet = 30;
            this.arbeitsZeitBeginnMinGerundet = String.valueOf(minGerundet);
            date.setMinutes(minGerundet);
            date.setHours(position.getStartTime().getTime().getHours());
        } else if (min > 30 && min < 45) {
            minGerundet = 45;
            this.arbeitsZeitBeginnMinGerundet = String.valueOf(minGerundet);
            position.getStartTime().getTime().setMinutes(minGerundet);
            date.setMinutes(minGerundet);
            date.setHours(position.getStartTime().getTime().getHours());
        } else if (min > 45 && min < 60) {
            minGerundet = 0;
            this.arbeitsZeitBeginnMinGerundet = String.valueOf(minGerundet);
            position.getStartTime().getTime().setMinutes(minGerundet);
            //position.setArbeitsZeitBeginnStdGerundet(String.valueOf(std + 1));
            date.setMinutes(minGerundet);
            date.setHours(position.getStartTime().getTime().getHours() + 1);
        }
        calendar.setTime(date);
        return calendar;

    }

    public void setArbeitsZeitEndeStdGerundet(String std) {
        this.arbeitsZeitEndeStdGerundet = arbeitsZeitEndeStdGerundet;
    }

    public Calendar getArbeitszeitEndeGerundet(Position position) {
        int min = position.getEndTime().getTime().getMinutes();
        int minGerundet;
        int std = position.getStartTime().getTime().getHours();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        if (min > 0 && min < 15) {
            minGerundet = 0;
            this.arbeitsZeitBeginnMinGerundet = String.valueOf(minGerundet);
            date.setHours(position.getEndTime().getTime().getHours());
            date.setMinutes(minGerundet);
        } else if (min > 15 && min < 30) {
            minGerundet = 15;
            this.arbeitsZeitBeginnMinGerundet = String.valueOf(minGerundet);
            date.setHours(position.getEndTime().getTime().getHours());
            date.setMinutes(minGerundet);
        } else if (min > 30 && min < 45) {
            minGerundet = 30;
            this.arbeitsZeitBeginnMinGerundet = String.valueOf(minGerundet);
            date.setHours(position.getEndTime().getTime().getHours());
            date.setMinutes(minGerundet);
        } else if (min > 45 && min < 60) {
            minGerundet = 45;
            this.arbeitsZeitBeginnMinGerundet = String.valueOf(minGerundet);
            date.setHours(position.getEndTime().getTime().getHours());
            date.setMinutes(minGerundet);
        }
        calendar.setTime(date);
        return calendar;
    }

    public void getArbzEndeGerundet(Position position) {
        int min = position.getArbeitszeitMinuten();
        int minGerundet;
        int std = position.getStartTime().getTime().getHours();
        if (min > 0 && min < 15) {
            minGerundet = 0;
            this.arbeitsZeitBeginnMinGerundet = String.valueOf(minGerundet);
        } else if (min > 15 && min < 30) {
            minGerundet = 15;
            this.arbeitsZeitBeginnMinGerundet = String.valueOf(minGerundet);
        } else if (min > 30 && min < 45) {
            minGerundet = 30;
            this.arbeitsZeitBeginnMinGerundet = String.valueOf(minGerundet);
        } else if (min > 45 && min < 60) {
            minGerundet = 45;
            this.arbeitsZeitBeginnMinGerundet = String.valueOf(minGerundet);
        }
    }

    public Kunde getKundeAktuell() {
        return kundeAktuell;
    }

    int arbeitsZeitStunden, arbeitszeitMinuten, arbeitsZeitSekunden;
    Calendar startTime, endTime;
    Date zeit;
    int dauerSekunden;
    String dauerString;

    public void setArbeitsZeitBeginn(String arbeitsZeitBeginn) {
        this.arbeitsZeitBeginn = arbeitsZeitBeginn;
    }

    public void setArbeitsZeitStunden() {
        this.arbeitsZeitStunden = endTime.getTime().getHours() - startTime.getTime().getHours();
    }

    public void setArbeitsZeitStunden(int stunden) {
        this.arbeitsZeitStunden = stunden;
    }

    public int getArbeitsZeitStunden() {
        return arbeitsZeitStunden;
    }

    public void setArbeitsZeitEnde(String arbeitsZeitEnde) {
        this.arbeitsZeitEnde = arbeitsZeitEnde;
    }

    public String getArbeitsZeitBeginn() {
        String startStunde,
                startMinute,
                startSekunde;
        startStunde = String.valueOf(getStartTime().getTime().getHours());
        startMinute = String.valueOf(getStartTime().getTime().getMinutes());
        startSekunde = String.valueOf(getStartTime().getTime().getSeconds());
        if (startStunde.length() == 1) {
            startStunde = "0" + startStunde;
        }
        if (startMinute.length() == 1) {
            startMinute = "0" + startMinute;
        }
        if (startSekunde.length() == 1) {
            startSekunde = "0" + startSekunde;
        }


        String start = startStunde + ":" + startMinute;

        return start;

    }

    public String getArbeitsZeitEnde() {
        String
                endeStunde,
                endeMinute,
                endeSekunde;


        endeStunde = String.valueOf(getEndTime().getTime().getHours());
        endeMinute = String.valueOf(getEndTime().getTime().getMinutes());
        endeSekunde = String.valueOf(getEndTime().getTime().getSeconds());
        if (endeStunde.length() == 1) {
            endeStunde = "0" + endeStunde;
        }
        if (endeMinute.length() == 1) {
            endeMinute = "0" + endeMinute;
        }
        if (endeSekunde.length() == 1) {
            endeSekunde = "0" + endeSekunde;
        }
        ;
        String ende = endeStunde + ":" + endeMinute;
        return ende;

    }

    //Anfangszeit
    //Endzeit
    //Arbeitszeit
    public Position(
            String namePosition) {
        this.namePosition = namePosition;
    }

    public Position(String namePosition, String firma) {
        this.namePosition = namePosition;
        this.firma = firma;
    }

    public String getDauerString() {
        String startStunde,
                startMinute,
                startSekunde,
                endeStunde,
                endeMinute,
                endeSekunde;
        Double arbeitsZeitPos = 0.0;
        String arbeitsZeitStr;
        arbeitsZeitPos = ermittleArbeitsZeitPosition(getStartTime().getTime(), getEndTime().getTime());
        arbeitsZeitStr = formatArbeitszeit(arbeitsZeitPos);
        startStunde = String.valueOf(getStartTime().getTime().getHours());
        startMinute = String.valueOf(getStartTime().getTime().getMinutes());
        startSekunde = String.valueOf(getStartTime().getTime().getSeconds());

        endeStunde = String.valueOf(getEndTime().getTime().getHours());
        endeMinute = String.valueOf(getEndTime().getTime().getMinutes());
        endeSekunde = String.valueOf(getEndTime().getTime().getSeconds());
        if (startMinute.length() == 1) {
            startMinute = "0" + startMinute;
        }
        if (endeMinute.length() == 1) {
            endeMinute = "0" + endeMinute;
        }
        String start = startStunde + ":" + startMinute;
        int pauseGesamt = pause1 + pause2;
        String ende = endeStunde + ":" + endeMinute;
  //      return "Start: " + start + " Uhr" + "\n" + "Ende: " + ende + " Uhr" + "\n" + arbeitsZeitStr + "\nPause: " + pauseGesamt + " Minuten";
        return "Start: " + start + " Uhr" + "\n" + "Ende: " + ende + " Uhr" + "\n" + arbeitsZeitStr;
        //  return "Start: " + start + " Uhr" + "\n" + "Ende: " + ende + " Uhr" + "\n" + arbeitsZeitStr;

    }

    private String formatArbeitszeit(Double arbeitszeit) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        System.out.println(formatter.format(4.0));
        String roundAzeit;
        roundAzeit = formatter.format(arbeitszeit);
        String arbeitszeitStr = roundAzeit;
        String std, min;
        String[] arrOfStr = arbeitszeitStr.split(",");
        std = arrOfStr[0];
        min = arrOfStr[1];

        arbeitszeitStr = std + " Std, " + min + " Minuten";

        return arbeitszeitStr;


    }

    public String getDauerStringSeit() {
        String startStunde,
                startMinute,
                startSekunde,
                endeStunde,
                endeMinute,
                endeSekunde;
        Double arbeitsZeitPos = 0.0;
        String arbeitsZeitStr;
        arbeitsZeitPos = ermittleArbeitsZeitPosition(getStartTime().getTime(), getEndTime().getTime());
        arbeitsZeitStr = formatArbeitszeit(arbeitsZeitPos);
        startStunde = String.valueOf(getStartTime().getTime().getHours());
        startMinute = String.valueOf(getStartTime().getTime().getMinutes());
        startSekunde = String.valueOf(getStartTime().getTime().getSeconds());

        endeStunde = String.valueOf(getEndTime().getTime().getHours());
        endeMinute = String.valueOf(getEndTime().getTime().getMinutes());
        endeSekunde = String.valueOf(getEndTime().getTime().getSeconds());
        if (startMinute.length() == 1) {
            startMinute = "0" + startMinute;
        }
        if (endeMinute.length() == 1) {
            endeMinute = "0" + endeMinute;
        }
        String start = startStunde + ":" + startMinute;
        String ende = endeStunde + ":" + endeMinute;
        return "Seit: " + start + " Uhr" + "\n" + arbeitsZeitStr;


    }

    public void setDauerString(String dauerString) {

    }

    public int getArbeitszeitMinuten() {
        return arbeitszeitMinuten;
    }

    public void setArbeitszeitMinuten() {
        this.arbeitszeitMinuten = endTime.getTime().getMinutes() - startTime.getTime().getMinutes();
    }

    public void setArbeitszeitMinuten(
            int minuten) {
        this.arbeitszeitMinuten = minuten;
    }

    public void setArbeitsZeitSekunden() {
        this.arbeitsZeitSekunden = endTime.getTime().getSeconds() - startTime.getTime().getSeconds();
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public int getArbeitsZeitSekunden() {
        int zeit = this.endTime.getTime().getSeconds() - this.startTime.getTime().getSeconds();
        if (zeit < 0) {
            return zeit * (-1);
        } else {
            return zeit;
        }

    }

    public String getArbeitszeitGesamt(ArrayList<Position> listPosition, Date dPauseStart, Date dPauseEnde, Date dPauseStart2, Date dPauseEnde2) {

        if (listPosition.size() == 0){
            return "0";
        }

        Double arbeitszeit = 0.00;
        int abzug = 0;

        //Pause kalkurieren
        if (dPauseStart!= null){
            setPostionPause1(listPosition, dPauseStart, dPauseEnde, true);
            setPostionPause1(listPosition, dPauseStart2, dPauseEnde2, false);
        }



        setListPosition(listPosition);

        //Aufsummieren der Arbeitszeit
        /*
        listPosition.clear();
        Date dateStart = new Date();
        Date dateEnd = new Date();
        dateStart.setHours(8);
        dateStart.setMinutes(44);
        dateEnd.setHours(10);
        dateEnd.setMinutes(30);
        Position position = new Position("");
        Position pos2 = new Position("");
        //1
        Calendar cAnfang = Calendar.getInstance();
        Calendar cEnde = Calendar.getInstance();
        cAnfang.setTime(dateStart);
        cEnde.setTime(dateEnd);
        position.setStartTime(cAnfang);
        position.setEndTime(cEnde);
        listPosition.add(position);
        //2
        Date dateStart2 = new Date();
        Date dateEnd2 = new Date();
        dateStart2.setHours(10);
        dateStart2.setMinutes(31);
        dateEnd2.setHours(23);
        dateEnd2.setMinutes(56);
        Position position2 = new Position("");
        Calendar cAnfang2 = Calendar.getInstance();
        Calendar cEnde2 = Calendar.getInstance();
        cAnfang2.setTime(dateStart2);
        cEnde2.setTime(dateEnd2);
        position2.setStartTime(cAnfang2);
        position2.setEndTime(cEnde2);
        listPosition.add(position2);
*/
        //  DecimalFormat df = new DecimalFormat("#.00");
        //    Double d = df.format(2.4);
        long milTime = 0;
        long mil1 = 0;
        long mil2 = 0;
        Date d1 = new Date();
        long t = d1.getTime();
        arbeitszeit = ermittleArbeitsZeitPosition(listPosition.get(0).getStartTime().getTime(), listPosition.get(listPosition.size() - 1).getEndTime().getTime());
        milTime = listPosition.get(listPosition.size() - 1).getEndTime().getTime().getTime() - listPosition.get(0).getStartTime().getTime().getTime();
        milTime = milTime - (pause * 60000);
        /*
        for (int i = 0; i < listPosition.size(); i++) {

            //  stdGesamt = stdGesamt + listPosition.get(i).getArbeitsZeitStunden();
            arbeitszeit = arbeitszeit + ermittleArbeitsZeitPosition(listPosition.get(i).getStartTime().getTime(), listPosition.get(i).getEndTime().getTime());
            minGesamt = (listPosition.get(i).getArbeitsZeitStunden() * 60) + listPosition.get(i).getArbeitszeitMinuten();
            milTime = milTime + (listPosition.get(i).getEndTime().getTime().getTime() - listPosition.get(i).getStartTime().getTime().getTime());
            // milTime = milTime +  listPosition.get(i).getEndTime().getTimeInMillis() - listPosition.get(i).getStartTime().getTime().getTime();
            stdNeu = minGesamt / 60;
            minNeu = minGesamt % 60;
        }
*/

        //   arbeitszeit = Double.valueOf(String.valueOf(stdNeu)) + (Double.valueOf(minNeu) / Double.valueOf(100));
        String strAzeit = String.valueOf(arbeitszeit);
        String std, min;
        int iStd, iMin, iStdNeu, iMinNeu, dbIminNe, min2, std2;
        String[] arrOfStr = strAzeit.split("[.]");
        std = arrOfStr[0];
        min = arrOfStr[1];
        //  min = min.substring(0,2);
        iStd = Integer.parseInt(std);
        String strAzeitOutput = null;
        try {
            //Pausen abziehen
            abzug = 0;
            for (int i = 0; i < listPosition.size(); i++) {
                abzug = abzug + ((listPosition.get(i).getPause1() + listPosition.get(i).getPause2()) * 60000);
            }
            milTime = (milTime - abzug) / 60000;
            std2 = (int) milTime / 60;
            min2 = (int) milTime % 60;
            //      iMin = Integer.parseInt(min);
            //    iStdNeu = iStd + (iMin / 60);
            //   iMinNeu = iMin % 60;
            // dbIminNe = iMinNeu / 100;
            // arbeitszeit = Double.valueOf(iStdNeu) + (Double.valueOf(iMinNeu)/100);
            arbeitszeit = Double.valueOf(std2) + (Double.valueOf(min2) / 100);
            if (arbeitszeit<0){
                arbeitszeit = 0.0;
            }
            strAzeitOutput = formatArbeitszeit(arbeitszeit);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "-" +
                    "999";
        }

        return strAzeitOutput;
    }

    private Boolean pauseIstInPosition(Calendar pauseBegin,
                                       Calendar pauseEnde,
                                       Calendar positionAnfang,
                                       Calendar positionEnde) {
        if (pauseBegin.compareTo(positionEnde) > 0 || pauseEnde.compareTo(positionAnfang) < 0) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean istZeitüberschneidung(ArrayList<Position> listPosition) {
        ArrayList<Position> listPositionSortiert = new ArrayList<Position>();
        //   Collections.sort(listPosition, new Position(""));
        String ff = "ff";
        for (int i = 0; i < listPosition.size() - 1; i++) {
            int endzeit = listPosition.get(i).getEndTime().getTime().getHours() * 60 +
                    listPosition.get(i).getEndTime().getTime().getMinutes();

            int startzeit = listPosition.get(i + 1).getStartTime().getTime().getHours() * 60 +
                    listPosition.get(i + 1).getStartTime().getTime().getMinutes();
            if (startzeit > endzeit) {
                return true;
            }
        }
        return false;
    }


    public void setStartTime(Calendar start) {
        Date dateOhneSekundne = new Date();
        dateOhneSekundne.setHours(start.getTime().getHours());
        dateOhneSekundne.setMinutes(start.getTime().getMinutes());
        dateOhneSekundne.setSeconds(0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.setTime(dateOhneSekundne);
        this.startTime = calendar;
    }

    public void setEndTime(Calendar ende) {
        Date dateOhneSekundne = new Date();
        dateOhneSekundne.setHours(ende.getTime().getHours());
        dateOhneSekundne.setMinutes(ende.getTime().getMinutes());
        dateOhneSekundne.setSeconds(0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.setTime(dateOhneSekundne);
        this.endTime = calendar;
    }

    public String getNamePosition() {
        return namePosition;
    }

    public void setNamePosition(String namePosition) {
        this.namePosition = namePosition;
    }

    public Calendar getStartTime() {


        return this.startTime;
    }

    public Calendar getEndTime() {
        return this.endTime;
    }

    public Double ermittleArbeitsZeitPosition(Date dAnfang, Date dEnde) {
        Double arbeitsZeitPosition;
        int stdAnfang, stdEnde, minAnfang, minEnde, stdDif, minDif;

        stdAnfang = dAnfang.getHours();
        stdEnde = dEnde.getHours();
        minAnfang = dAnfang.getMinutes();
        minEnde = dEnde.getMinutes();
        arbeitsZeitPosition = 0.00;

        if (stdAnfang == stdEnde) {
            stdAnfang = 0;
            minDif = minEnde - minEnde;
            arbeitsZeitPosition = Double.valueOf(minEnde - minAnfang) / 100;
            Log.d("zeittest", "minend" + String.valueOf(Double.valueOf(minEnde)));
            Log.d("zeittest", "minanf" + String.valueOf(Double.valueOf(minAnfang)));
            Log.d("zeittest", "ges" + String.valueOf(Double.valueOf(minEnde - minAnfang) / 100));
            Log.d("zeittest", "ges" + String.valueOf(Double.valueOf(minEnde / 100) - Double.valueOf(minAnfang / 100)));
            Log.d("zeittest", String.valueOf(1.0 / 100));

        } else {
            if (minEnde > minAnfang) {
                stdDif = stdEnde - stdAnfang;
                minDif = minEnde - minAnfang;
                Log.d("zeittest", "b" + String.valueOf(Double.valueOf(minEnde)));
                Log.d("zeittest", "stdende" + String.valueOf(Double.valueOf(stdEnde)));
                Log.d("zeittest", "stdanf" + String.valueOf(Double.valueOf(stdAnfang)));
                Log.d("zeittest", "ges" + String.valueOf((Double.valueOf(minEnde) - Double.valueOf(minEnde)) / 100));

            } else {
                Log.d("zeittest", "a" + String.valueOf(Double.valueOf(minEnde)));
                stdDif = stdEnde - stdAnfang - 1;
                minDif = 60 - (minAnfang - minEnde);
            }
            arbeitsZeitPosition = Double.valueOf(stdDif) + (Double.valueOf(minDif) / 100);
        }
        return arbeitsZeitPosition;
    }

    public static Comparator<Position> comparatorPosition = new Comparator<Position>() {


        @Override
        public int compare(Position pos1, Position pos2) {
            long startPos1 = pos1.getStartTime().getTime().getTime();
            long startPos2 = pos2.getStartTime().getTime().getTime();
            int minGesamt1 = pos1.getStartTime().getTime().getHours() * 60 + pos1.getStartTime().getTime().getMinutes();
            int minGesamt2 = pos2.getStartTime().getTime().getHours() * 60 + pos2.getStartTime().getTime().getMinutes();
            if (startPos1 > startPos2) {
                return 1;
            } else {
                return -1;
            }
            /*if (pos1.getArbeitszeitMinuten() < pos2.getArbeitszeitMinuten()) {
                return 1;
            } else {
                return -1;
            }*/
        }
    };

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    public ArrayList<Position> getListPositionOhneAusreisser(ArrayList<Position> listPosition) {
        ArrayList<Position> listPositionOhneAusreisser = new ArrayList<Position>();
        Double zeitPosition;
        Log.d("zeiii", String.valueOf(listPosition.size()));
        int sizeListePosition = listPosition.size();

        for (int i = 0; i < listPosition.size(); i++) {
            Position position = listPosition.get(i);
            Log.d("catchfehler", "index: " + String.valueOf(i));
            zeitPosition = ermittleArbeitsZeitPosition(listPosition.get(i).getStartTime().getTime(), listPosition.get(i).getEndTime().getTime());

            if (zeitPosition < 0.02) {
                listPosition.get(i).setArbeitszeitMinuten(9999);
                Log.d("zeitmao", String.valueOf(zeitPosition));
            }
        }


        int ix = 0;
        boolean letzteElement = false;
        while (!letzteElement) {
            letzteElement = (ix == (listPosition.size() - 1));

            if (listPosition.get(ix).getArbeitszeitMinuten() == 9999) {
                Log.d("zeitmao", String.valueOf(ix));
                listPosition.remove(ix);
                ix = 0;
            } else {
                ix++;
            }

        }

        return listPosition;

    }

    public ArrayList<Position> getListPositonOhneDuplikate(ArrayList<Position> listPosition) {
        int index = 1;
        int index2;
        int index3 = 0;
        int count = 0;
        boolean duplikat = false;
        boolean endeDuplikat = false;
        boolean esWirdWasGeloescht = false;
        Boolean istKunde = false;
        Calendar endTime = Calendar.getInstance();
    if (listPosition.size() != 0){


        while (index != listPosition.size() || duplikat) {
            istKunde = listPosition.get(index - 1).getKunde() != null && listPosition.get(index).getKunde() != null;
            if (istKunde && listPosition.get(index - 1).getKunde().getStrasse()
                    .equals(listPosition.get(index).getKunde().getStrasse())) {
                index2 = index - 1;


                while (index2 != listPosition.size() - 1 && !endeDuplikat) {
                    istKunde = listPosition.get(index2).getKunde() != null && listPosition.get(index2 + 1).getKunde() != null;
                    if (istKunde && listPosition.get(index2).getKunde().getStrasse()
                            .equals(listPosition.get(index2 + 1).getKunde().getStrasse())) {
                        esWirdWasGeloescht = true;
                        count++;
                        endTime = listPosition.get(index2 + 1).getEndTime();
                        index2++;
                        index3 = index;


                    } else {
                        listPosition.get(index - 1).setEndTime(endTime);
                        endeDuplikat = true;
                        index3 = index;


                    }

                }
                endeDuplikat = false;
                listPosition.get(index - 1).setEndTime(endTime);

                for (int i = 0; i < count; i++) {

                    listPosition.remove(index3);
                    //index2++;
                }
                count = 0;

            } else {
                index++;
            }

        }}
        return listPosition;
    }

    public ArrayList<Position> getListPositonOhneDuplikateSonstiges(ArrayList<Position> listPosition) {
        int index = 0;
        int indexCurrent = 0;
        int indexEnde;
        int count = 0;
        Boolean posDuplikateFahrtzeitGefunden = false;
        Boolean posIsFahrtzeit;
        Boolean posNextIsFahrtzeit;
        Boolean endeDuplikat = false;
        Boolean isDuplikat = false;
        Calendar endTime = Calendar.getInstance();


     //   while (index != listPosition.size() -1 ||  !posDuplikateFahrtzeitGefunden) {
        if (listPosition.size() != 0){


        while (index != listPosition.size() -1 ) {
            posIsFahrtzeit = listPosition.get(index).getKunde() == null;
            posNextIsFahrtzeit = listPosition.get(index + 1).getKunde() == null;
            if (posIsFahrtzeit && posNextIsFahrtzeit) {
                indexCurrent = index + 1;
                count = 0;
                while (indexCurrent != listPosition.size()-1 && !endeDuplikat) {
                    isDuplikat = listPosition.get(indexCurrent).getKunde() == null;
                    if (isDuplikat) {
                        indexCurrent++;
                        count++;
                    } else {
                        endeDuplikat = true;
                  //      indexCurrent--;

                    }


                }
                indexCurrent--;
                endTime = listPosition.get(indexCurrent).getEndTime();
                listPosition.get(index).setEndTime(endTime);
                //Duplikate löschen
                indexCurrent = index + 1;
                for (int i = 0; i < count; i++) {
                    listPosition.remove(indexCurrent);
                }
                index++;
                endeDuplikat = false;
                posDuplikateFahrtzeitGefunden = true;
            } else {
                index++;
            }

        }

        }
        return listPosition;
    }

    public ArrayList<Position> getGueltigePositionen(ArrayList<Position> listPosition) {
        int index = 0;
        boolean posionFirma = false;
        boolean positionKunde = false;
        boolean posKunde = false;
        Kunde kunde;
        Log.d("changeevent", "evt: " + String.valueOf(listPosition.size()));

        if (listPosition.size() ==1){
            if (listPosition.get(0).getKunde() != null){
                return listPosition;
            }
        }




        //Lösche alle Positionen VOR Firma
        while (!posKunde && index < listPosition.size()) {
            kunde = listPosition.get(index).getKunde();
            if (kunde != null && !kunde.getFirma().equals("Zuhause")) {
                posKunde = true;

      //          if (kunde.getFirma().equals("Firma")) {
        //            Log.d("ix", String.valueOf(index));
          //          posionFirma = true;
            //    }

            }
            index++;
        }


        index--;

        for (int i = 0; i < index; i++) {
            listPosition.remove(0);
            Log.d("ix", "i: " + String.valueOf(i));
        }

        //Lösche alle Positionen NACH Firma
        index = listPosition.size() - 1;
        posionFirma = false;


        while (!posionFirma && !positionKunde && (index > -1 && index < listPosition.size())) {
            kunde = listPosition.get(index).getKunde();
            Log.d("filt", String.valueOf(kunde));
            //Wenn Kunde
            if (kunde != null && !kunde.getFirma().equals("Zuhause")) {
                Log.d("filt", String.valueOf(kunde.getFirma()));
                if (kunde.getFirma().equals("Firma")) {
                    posionFirma = true;
                } else {
                    positionKunde = true;
                }

            }


            index--;
        }
        index = index + 2;
        int size = listPosition.size();
        Log.d("ix", "ix ist: " + String.valueOf(index));
        Log.d("ix", "size ist: " + String.valueOf(listPosition.size()));
        for (int i = index; i < size; i++) {
            listPosition.remove(index);
        }


        return listPosition;
    }

    private void setPostionPause1(ArrayList<Position> listPosition, Date pAnfang, Date pEnde, Boolean pause_1) {

        int stdPosEnde;
        int minPosEnde;
        int stdPosAnfang;
        int minPosAnfang;
        int stdPauseEnde;
        int stdNeu = 0;
        int minNeu = 0;
        int minPauseEnde;
        int stdGesamt = 0;
        int minGesamt = 0;
        int minGesamtPause;
        int dif;
        int stdPauseAnfang;
        int minPauseAnfang;

        int abzug;
        Boolean pauseInZeit;
        Date pauseAnfang = pAnfang;
        Date pauseEnde = pEnde;
        Date pauseAnfang2 = new Date();
        Date pauseEnde2 = new Date();


        Calendar cPauseBegin = Calendar.getInstance();
        cPauseBegin.setTime(pauseAnfang);

        Calendar cPauseEnde = Calendar.getInstance();
        cPauseEnde.setTime(pauseEnde);

        Calendar cPauseBegin2 = Calendar.getInstance();
        cPauseBegin2.setTime(pauseAnfang2);

        Calendar cPauseEnde2 = Calendar.getInstance();
        cPauseEnde2.setTime(pauseEnde2);

        Calendar cPositionAnfang;
        Calendar cPositionEnde;
        for (int j = 0; j < listPosition.size(); j++) {
            listPosition.get(j).setArbeitsZeitStunden();
            cPositionAnfang = listPosition.get(j).getStartTime();
            cPositionEnde = listPosition.get(j).getEndTime();


            pauseInZeit = pauseIstInPosition(cPauseBegin, cPauseEnde, cPositionAnfang, cPositionEnde);
            Log.d("Pause: ", String.valueOf(pauseInZeit));
            if (pauseInZeit) {
                int abz = 0;
                stdPosEnde = listPosition.get(j).getArbeitsZeitStunden();
                minPosEnde = listPosition.get(j).getArbeitszeitMinuten();
                stdPosAnfang = listPosition.get(j).getArbeitsZeitStunden();
                minPosAnfang = listPosition.get(j).getArbeitszeitMinuten();
                stdPauseAnfang = cPauseBegin.getTime().getHours();
                minPauseAnfang = cPauseBegin.getTime().getMinutes();
                stdPauseEnde = cPauseEnde.getTime().getHours();
                minPauseEnde = cPauseEnde.getTime().getMinutes();
                minGesamt = listPosition.get(j).getArbeitsZeitStunden() * 60 + listPosition.get(j).getArbeitszeitMinuten();

                //Fall 1   1 -> 1
                if (cPositionAnfang.compareTo(cPauseBegin) > 0 && cPositionEnde.compareTo(cPauseEnde) < 0) {


                    abz = minPauseEnde - minPosAnfang;
                    Log.d("Pause", "1");
                    if (abz < 0) {
                        abz = abz * (-1);
                    }
                    //  minGesamt = minGesamt - abzug;


                }
                //Fall 2
                else if (cPositionAnfang.compareTo(cPauseBegin) > 0 && cPositionEnde.compareTo(cPauseEnde) > 0) {
                    //    minGesamt = 0;
                    long differenz;
                    long dif2;

                    differenz = cPositionAnfang.getTime().getTime() - cPauseBegin.getTime().getTime();
                    dif2 = (cPauseEnde.getTime().getTime() - cPauseBegin.getTime().getTime()) - differenz;
                    abz = (int) dif2 / 60000;
                    Log.d("Pause", "2");

                }
                //Fall 3
                else if (cPositionAnfang.compareTo(cPauseBegin) < 0 && cPositionEnde.compareTo(cPauseEnde) > 0) {
                    int difStd, difMin;
                    difStd = (stdPauseEnde - stdPauseAnfang) * 60;
                    difMin = minPauseEnde - minPauseAnfang;
                    abz = difStd + difMin;
                    if (abz < 0) {
                        abz = abz * (-1);
                        Log.d("Pause", "3");
                    }
                    //  minGesamt = minGesamt - abzug;

                }
                //Fall 4
                else if (cPositionAnfang.compareTo(cPauseBegin) < 0 && cPositionEnde.compareTo(cPauseEnde) < 0) {
                    long differenz;
                    long dif2;
                    Log.d("Pause", "4");
                    differenz = cPauseEnde.getTime().getTime() - cPositionEnde.getTime().getTime();
                    dif2 = (cPauseEnde.getTime().getTime() - cPauseBegin.getTime().getTime()) - differenz;
                    abz = (int) dif2 / 60000;

                }

                stdNeu = minGesamt / 60;
                minNeu = minGesamt % 60;
                listPosition.get(j).setArbeitsZeitStunden(stdNeu);
                listPosition.get(j).setArbeitszeitMinuten(minNeu);
                if (pause_1) {
                    listPosition.get(j).setPause1(abz);
                    Log.d("Pause", String.valueOf(abz));
                } else {
                    listPosition.get(j).setPause2(abz);
                }


            }


        }

    }

    public Boolean istKundeInListe(ArrayList<Position> listPosition){
        boolean istKunde = false;
        int index = 0;
        while (index != listPosition.size()){
            if (
                    listPosition.get(index).getFirma() != null &&
                    !listPosition.get(index).getFirma().equals("Zuhause") &&
                    listPosition.get(index).getKunde() != null){
                Log.d("probebr", "pr:" + String.valueOf(listPosition.size()));
                return true;
            }else{
                index++;
            }
        }
        return false;
    }


    private void setPostionPause2(ArrayList<Position> listPosition, Date pAnfang, Date pEnde) {

        int stdPosEnde;
        int minPosEnde;
        int stdPosAnfang;
        int minPosAnfang;
        int stdPauseEnde;
        int stdNeu = 0;
        int minNeu = 0;
        int minPauseEnde;
        int stdGesamt = 0;
        int minGesamt = 0;
        int minGesamtPause;
        int dif;
        int stdPauseAnfang;
        int minPauseAnfang;

        int abzug;
        Boolean pauseInZeit;
        Date pauseAnfang = pAnfang;
        Date pauseEnde = pEnde;
        Date pauseAnfang2 = new Date();
        Date pauseEnde2 = new Date();


        Calendar cPauseBegin = Calendar.getInstance();
        cPauseBegin.setTime(pauseAnfang);

        Calendar cPauseEnde = Calendar.getInstance();
        cPauseEnde.setTime(pauseEnde);


        Calendar cPositionAnfang;
        Calendar cPositionEnde;
        for (int j = 0; j < listPosition.size(); j++) {
            listPosition.get(j).setArbeitsZeitStunden();
            cPositionAnfang = listPosition.get(j).getStartTime();
            cPositionEnde = listPosition.get(j).getEndTime();


            pauseInZeit = pauseIstInPosition(cPauseBegin, cPauseEnde, cPositionAnfang, cPositionEnde);
            Log.d("Pause: ", String.valueOf(pauseInZeit));
            if (pauseInZeit) {
                int abz = 0;
                stdPosEnde = listPosition.get(j).getArbeitsZeitStunden();
                minPosEnde = listPosition.get(j).getArbeitszeitMinuten();
                stdPosAnfang = listPosition.get(j).getArbeitsZeitStunden();
                minPosAnfang = listPosition.get(j).getArbeitszeitMinuten();
                stdPauseAnfang = cPauseBegin.getTime().getHours();
                minPauseAnfang = cPauseBegin.getTime().getMinutes();
                stdPauseEnde = cPauseEnde.getTime().getHours();
                minPauseEnde = cPauseEnde.getTime().getMinutes();
                minGesamt = listPosition.get(j).getArbeitsZeitStunden() * 60 + listPosition.get(j).getArbeitszeitMinuten();

                //Fall 1   1 -> 1
                if (cPositionAnfang.compareTo(cPauseBegin) > 0 && cPositionEnde.compareTo(cPauseEnde) < 0) {


                    abz = minPauseEnde - minPosAnfang;

                    if (abz < 0) {
                        abz = abz * (-1);
                    }
                    //  minGesamt = minGesamt - abzug;


                }
                //Fall 2
                else if (cPositionAnfang.compareTo(cPauseBegin) > 0 && cPositionEnde.compareTo(cPauseEnde) > 0) {
                    //    minGesamt = 0;
                    long differenz;
                    long dif2;

                    differenz = cPositionAnfang.getTime().getTime() - cPauseBegin.getTime().getTime();
                    dif2 = (cPauseEnde.getTime().getTime() - cPauseBegin.getTime().getTime()) - differenz;
                    abz = (int) dif2 / 60000;

                }
                //Fall 3
                else if (cPositionAnfang.compareTo(cPauseBegin) < 0 && cPositionEnde.compareTo(cPauseEnde) > 0) {
                    int difStd, difMin;
                    difStd = (stdPauseEnde - stdPauseAnfang) * 60;
                    difMin = minPauseEnde - minPauseAnfang;
                    abzug = difStd + difMin;
                    if (abz < 0) {
                        abz = abz * (-1);
                    }
                    //  minGesamt = minGesamt - abzug;

                }
                //Fall 4
                else if (cPositionAnfang.compareTo(cPauseBegin) < 0 && cPositionEnde.compareTo(cPauseEnde) < 0) {
                    long differenz;
                    long dif2;

                    differenz = cPauseEnde.getTime().getTime() - cPositionEnde.getTime().getTime();
                    dif2 = (cPauseEnde.getTime().getTime() - cPauseBegin.getTime().getTime()) - differenz;
                    abz = (int) dif2 / 60000;

                }

                stdNeu = minGesamt / 60;
                minNeu = minGesamt % 60;
                listPosition.get(j).setArbeitsZeitStunden(stdNeu);
                listPosition.get(j).setArbeitszeitMinuten(minNeu);

                listPosition.get(j).setPause2(abz);


            }


        }

    }

    @Override
    public int compareTo(@NonNull Position o) {
        return 0;
    }
}
