package com.example.timo.Zeiterfassung.Helfer;

import com.example.timo.Zeiterfassung.Beans.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static com.example.timo.Zeiterfassung.Beans.Position.comparatorPosition;

public class Dummy {



    public ArrayList<Position> getDummyList(){
        Position pos1 = new Position("Firma");
        Kunde dump = new Kunde();
        dump.setFirma("Firma");
        ArrayList<Position> listPosition = new ArrayList<Position>();
        pos1.setKunde(dump);
        Position pos2 = new Position("Sonstiges");
        Position pos3 = new Position("Kunde");
        Position pos4 = new Position("Firma");
        Date d1 = new Date();

        d1.setHours(8);
        d1.setMinutes(00);
        Date d1Ende = new Date();
        d1Ende.setHours(11);
        d1Ende.setMinutes(45);
        Date d2 = new Date();
        d2.setHours(11);
        d2.setMinutes(46);
        Date d2Ende = new Date();
        d2Ende.setHours(13);
        d2Ende.setMinutes(05);

        Date d3 = new Date();
        d3.setHours(13);
        d3.setMinutes(07);
        Date d3Ende = new Date();
        d3Ende.setHours(13);
        d3Ende.setMinutes(51);

        Date d4 = new Date();
        d4.setHours(17);
        d4.setMinutes(52);
        Date d4Ende = new Date();
        d4Ende.setHours(22);
        d4Ende.setMinutes(12);


        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        Calendar c4 = Calendar.getInstance();


        Calendar c5 = Calendar.getInstance();
        Calendar c6 = Calendar.getInstance();
        Calendar c7 = Calendar.getInstance();
        Calendar c8 = Calendar.getInstance();

        c1.setTime(d1);
        c2.setTime(d2);
        c3.setTime(d1Ende);
        c4.setTime(d2Ende);
        c5.setTime(d3);
        c6.setTime(d3Ende);
        c7.setTime(d4);
        c8.setTime(d4Ende);
        pos1.setStartTime(c1);
        pos1.setEndTime(c3);
        pos2.setStartTime(c2);
        pos2.setEndTime(c4);
        pos3.setStartTime(c5);
        pos3.setEndTime(c6);
        pos4.setStartTime(c7);
        pos4.setEndTime(c8);

        listPosition.clear();
        listPosition.add(pos1);
        listPosition.add(pos2);
        listPosition.add(pos3);
    //    listPosition.add(pos4);

        Position[] listPos = new Position[listPosition.size()];
        //Zeitliche sortierung
        for (int l  = 0;l<listPosition.size();l++){
            listPos[l] = listPosition.get(l);
        }

        Arrays.sort(listPos, comparatorPosition);
        listPosition = listToArraylist(listPos);

        return listPosition;
    }

    private ArrayList<Position> listToArraylist(Position[] listPos) {
        ArrayList<Position> listPosition = new ArrayList<Position>();

        for (int i = 0; i < listPos.length; i++) {
            listPosition.add(listPos[i]);
        }
        return listPosition;
    }
}
