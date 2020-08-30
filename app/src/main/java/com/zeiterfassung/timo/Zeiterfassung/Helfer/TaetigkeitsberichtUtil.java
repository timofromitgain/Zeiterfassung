package com.zeiterfassung.timo.Zeiterfassung.Helfer;

import com.zeiterfassung.timo.Zeiterfassung.Beans.Position;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import static com.zeiterfassung.timo.Zeiterfassung.Beans.Position.comparatorPosition;

public class TaetigkeitsberichtUtil implements Serializable {
String bericht;
String arbeitszeit;
Boolean abgeschlossen;

    public String getArbeitszeit() {
        return arbeitszeit;
    }

    public void setArbeitszeit(String arbeitszeit) {
        this.arbeitszeit = arbeitszeit;
    }

    public TaetigkeitsberichtUtil(String bericht, String arbeitszeit, Boolean abgeschlossen) {
        this.bericht = bericht;
        this.arbeitszeit = arbeitszeit;
        this.abgeschlossen = abgeschlossen;
    }

    public String getBericht() {
        return bericht;
    }

    public void setBericht(String bericht) {
        this.bericht = bericht;
    }

    public Boolean getAbgeschlossen() {
        return abgeschlossen;
    }

    public void setAbgeschlossen(Boolean abgeschlossen) {
        this.abgeschlossen = abgeschlossen;
    }

    public TaetigkeitsberichtUtil(String bericht, Boolean abgeschlossen) {
        this.bericht = bericht;
        this.abgeschlossen = abgeschlossen;
    }

    public TaetigkeitsberichtUtil(){

   }

    public String getTaetigkeitsbericht(ArrayList<Position> listPosition) {
        Gson gson = new Gson();
        String taetigkeitsbericht = gson.toJson(listPosition);
        return taetigkeitsbericht;
    }



    public ArrayList<Position> getTaetigkeitsbericht(String wochentag) {
        ArrayList<Position> listPosition = new ArrayList<Position>();
        DatenbankHelfer dbHelfer = new DatenbankHelfer();
        String jsonArray = dbHelfer.getBerichtWochentag(wochentag);
        Boolean isTaetigkeitsbericht = String.valueOf(jsonArray.charAt(0)).equals("[");

        if (isTaetigkeitsbericht) {
            Type listType = new TypeToken<ArrayList<Position>>() {}.getType();
            listPosition = new Gson().fromJson(jsonArray, listType);
            return listPosition;
        }else{
            return null;
        }
    }

    public ArrayList<Position> getTaetigkeitsbericht2(String bericht) {
        ArrayList<Position> listPosition = new ArrayList<Position>();
        DatenbankHelfer dbHelfer = new DatenbankHelfer();


            Type listType = new TypeToken<ArrayList<Position>>() {}.getType();
            listPosition = new Gson().fromJson(bericht, listType);
            return listPosition;

    }

    public ArrayList<Position> sortiereListe(ArrayList<Position> listPosition,ArrayList<Position>listPositionGesamt) {
        ArrayList<Position> listKunde = new ArrayList<Position>();
        ArrayList<Position> listSonstiges = new ArrayList<Position>();
        ArrayList<Position> listPositionenSortiert = new ArrayList<Position>();
        Position posAktuell;
        Boolean listForTaetigkeitsbericht = false;
/*
        Position posDump = new Position("");
        Calendar calendar;
        calendar = posDump.getAzeitBegGerundet(listPosition.get(0));
        listPosition.get(0).setStartTime(calendar);
       // //Log.d("zzz",String.valueOf(listPosition.get(0).getStartTime().getTime().getMinutes()));
*/


        for (int i = 0; i < listPosition.size(); i++) {
            if (listPosition.get(i).getNamePosition().equals("Kunde")) {
                listKunde.add(listPosition.get(i));
            } else if (listPosition.get(i).getNamePosition().equals("Sonstiges")) {
                listSonstiges.add(listPosition.get(i));
            } else {
                listForTaetigkeitsbericht = true;
                posAktuell = listPosition.get(i);
                listPositionenSortiert.add(posAktuell);
            }
        }

        // Überprüfe ob der Weg zurück von der Baustelle angerechnet werden kann
        //Log.d("anrechnung", "vorher");
        if (!listForTaetigkeitsbericht) {
            //Log.d("anrechnung", "nachher");
            Boolean letztePositionFirma;
            if (listKunde.size() >0){
                 letztePositionFirma = listKunde.get(listKunde.size() - 1).getKunde().getFirma().equals("Firma");
            }else{
                letztePositionFirma = false;
            }


            Boolean firmaInArbeitszeit;

            if (letztePositionFirma) {
                //Log.d("anrechnung", "letzePos");
                firmaInArbeitszeit = anrechnungFirma(listKunde.get(listKunde.size() - 1));
                //Log.d("anrechnung", String.valueOf(listKunde.size()));
                if (!firmaInArbeitszeit) {
                    //Log.d("anrechnung", "!firma");
                    //Letzte Positon Kunde UND Sonstiges nicht anrechnungsfähig
                    //  if (listKunde.size() != 0 || listKunde.size() != 1) {
                    if (listKunde.size() > 1) {
                        listKunde.remove(listKunde.size() - 1);
                    }

                    if (listSonstiges.size() > 0) {

                        listSonstiges.remove(listSonstiges.size() - 1);
                    }

                }

            } else {
                //Letzte Positon Sonstiges nicht anrechnungsfähig
                if (listPositionGesamt.size()>0){
                    Boolean letztePositionIstSonstiges = listPositionGesamt.get(listPositionGesamt.size() - 1).getNamePosition().equals("Sonstiges");
                    if (letztePositionIstSonstiges) {
                        listSonstiges.remove(listSonstiges.get(listSonstiges.size() - 1));
                    }
                }

            }

        }

/*
        calendar = posDump.getArbeitszeitEndeGerundet(listKunde.get(listKunde.size()-1));
        listKunde.get(listKunde.size()-1).setEndTime(calendar);
*/


        for (int j = 0; j < listKunde.size(); j++) {
            listPositionenSortiert.add(listKunde.get(j));
        }

        for (int k = 0; k < listSonstiges.size(); k++) {
            listPositionenSortiert.add(listSonstiges.get(k));
        }
        Position[] listPos = new Position[listPositionenSortiert.size()];
        //Zeitliche sortierung
        for (int l = 0; l < listPositionenSortiert.size(); l++) {
            listPos[l] = listPositionenSortiert.get(l);
        }

        Arrays.sort(listPos, comparatorPosition);
        listPositionenSortiert = listToArraylist(listPos);
        //  Collections.sort(listPositionenSortiert, new Position(""));
        return listPositionenSortiert;
    }

    private Boolean anrechnungFirma(Position position) {
        Position firma = position;

        if (firma.getKunde().getFirma().equals("Firma")) {
            int stdArbeitszeit = firma.getArbeitsZeitStunden();
            int minArbeitszeit = firma.getArbeitszeitMinuten();
            double az = firma.ermittleArbeitsZeitPosition(firma.getStartTime().getTime(), firma.getEndTime().getTime());
            if (az >= 0.2) {
                return true;
            }else{
                return false;
            }
        } else {
            return true;
        }

    }


    private ArrayList<Position> listToArraylist(Position[] listPos) {
        ArrayList<Position> listPosition = new ArrayList<Position>();

        for (int i = 0; i < listPos.length; i++) {
            listPosition.add(listPos[i]);
        }
        return listPosition;
    }

}



