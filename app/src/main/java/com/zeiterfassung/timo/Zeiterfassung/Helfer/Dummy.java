package com.zeiterfassung.timo.Zeiterfassung.Helfer;

import com.zeiterfassung.timo.Zeiterfassung.Beans.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static com.zeiterfassung.timo.Zeiterfassung.Beans.Position.comparatorPosition;

public class Dummy {



    public ArrayList<Position> getDummyList(){
        Position pos1 = new Position("Firma");
        Kunde dump = new Kunde();
        dump.setFirma("Firma");
        ArrayList<Position> listPosition = new ArrayList<Position>();
        pos1.setKunde(dump);
        Position pos2 = new Position("Sonstiges");
        Position pos3 = new Position("Kunde");
        Position pos4 = new Position("AKTUELL");
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
    //    listPosition.add(pos1);
     //   listPosition.add(pos2);
        listPosition.add(pos3);
        listPosition.add(pos4);

        Position[] listPos = new Position[listPosition.size()];
        //Zeitliche sortierung
        for (int l  = 0;l<listPosition.size();l++){
            listPos[l] = listPosition.get(l);
        }

        Arrays.sort(listPos, comparatorPosition);
        listPosition = listToArraylist(listPos);

        return listPosition;
    }




    public ArrayList<Position> getDummyList2(){

        ArrayList<Position> listPosition = new ArrayList<Position>();

        Position pos1 = new Position("Kunde");
        Position pos2 = new Position("Kunde");
        Position pos3 = new Position("Sonstiges");
        Position pos4 = new Position("Sonstiges");
        Position pos5 = new Position("Sonstiges");
        Position pos6 = new Position("Kunde");
        Position pos7 = new Position("Kunde");
        Position pos8 = new Position("Sonstiges");
        Position pos9 = new Position("Sonstiges");
        Position pos10 = new Position("Kunde");
        Position pos11 = new Position("Kunde");

        Kunde k1 = new Kunde();
        Kunde k2 = new Kunde();
        Kunde k6 = new Kunde();
        Kunde k7 = new Kunde();
        Kunde k10 = new Kunde();
        Kunde k11 = new Kunde();

        k1.setFirma("A");
        k1.setStrasse("A");

        k2.setFirma("b");
        k2.setStrasse("b");

        k6.setFirma("c");
        k6.setStrasse("c");

        k7.setFirma("d");
        k7.setStrasse("d");

        k10.setFirma("e");
        k10.setStrasse("e");

        k11.setFirma("f");
        k11.setStrasse("f");

        pos1.setKunde(k1);
        pos2.setKunde(k2);
        pos6.setKunde(k6);
        pos7.setKunde(k7);
        pos10.setKunde(k10);
        pos11.setKunde(k11);








        Date d1 = new Date();

        d1.setHours(8);
        d1.setMinutes(00);
        Date d1Ende = new Date();
        d1Ende.setHours(9);
        d1Ende.setMinutes(30);

        Date d2 = new Date();
        d2.setHours(9);
        d2.setMinutes(30);
        Date d2Ende = new Date();
        d2Ende.setHours(11);
        d2Ende.setMinutes(00);

        Date d3 = new Date();
        d3.setHours(11);
        d3.setMinutes(00);
        Date d3Ende = new Date();
        d3Ende.setHours(12);
        d3Ende.setMinutes(30);

        Date d4 = new Date();
        d4.setHours(12);
        d4.setMinutes(30);
        Date d4Ende = new Date();
        d4Ende.setHours(14);
        d4Ende.setMinutes(00);

        Date d5 = new Date();
        d5.setHours(14);
        d5.setMinutes(00);
        Date d5Ende = new Date();
        d5Ende.setHours(14);
        d5Ende.setMinutes(30);

        Date d6 = new Date();
        d6.setHours(14);
        d6.setMinutes(30);
        Date d6Ende = new Date();
        d6Ende.setHours(15);
        d6Ende.setMinutes(00);

        Date d7 = new Date();
        d7.setHours(15);
        d7.setMinutes(00);
        Date d7Ende = new Date();
        d7Ende.setHours(15);
        d7Ende.setMinutes(30);

        Date d8 = new Date();
        d8.setHours(15);
        d8.setMinutes(30);
        Date d8Ende = new Date();
        d8Ende.setHours(16);
        d8Ende.setMinutes(00);

        Date d9 = new Date();
        d9.setHours(16);
        d9.setMinutes(00);
        Date d9Ende = new Date();
        d9Ende.setHours(16);
        d9Ende.setMinutes(30);

        Date d10 = new Date();
        d10.setHours(16);
        d10.setMinutes(30);
        Date d10Ende = new Date();
        d10Ende.setHours(17);
        d10Ende.setMinutes(00);

        Date d11 = new Date();
        d11.setHours(17);
        d11.setMinutes(00);
        Date d11Ende = new Date();
        d11Ende.setHours(17);
        d11Ende.setMinutes(30);




        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        Calendar c4 = Calendar.getInstance();

        Calendar c5 = Calendar.getInstance();
        Calendar c6 = Calendar.getInstance();
        Calendar c7 = Calendar.getInstance();
        Calendar c8 = Calendar.getInstance();

        Calendar c9 = Calendar.getInstance();
        Calendar c10 = Calendar.getInstance();
        Calendar c11 = Calendar.getInstance();
        Calendar c12 = Calendar.getInstance();
        Calendar c13 = Calendar.getInstance();
        Calendar c14 = Calendar.getInstance();
        Calendar c15 = Calendar.getInstance();

        Calendar c16 = Calendar.getInstance();
        Calendar c17 = Calendar.getInstance();
        Calendar c18 = Calendar.getInstance();
        Calendar c19 = Calendar.getInstance();

        Calendar c20 = Calendar.getInstance();
        Calendar c21 = Calendar.getInstance();
        Calendar c22 = Calendar.getInstance();





        c1.setTime(d1);
        c2.setTime(d1Ende);
        c3.setTime(d2);
        c4.setTime(d2Ende);
        c5.setTime(d3);
        c6.setTime(d3Ende);
        c7.setTime(d4);
        c8.setTime(d4Ende);
        c9.setTime(d5);
        c10.setTime(d5Ende);
        c11.setTime(d6);
        c12.setTime(d6Ende);
        c13.setTime(d7);
        c14.setTime(d7Ende);
        c15.setTime(d8);
        c16.setTime(d8Ende);
        c17.setTime(d9);
        c18.setTime(d9Ende);
        c19.setTime(d10);
        c20.setTime(d10Ende);
        c21.setTime(d11);
        c20.setTime(d11Ende);





        pos1.setStartTime(c1);
        pos1.setEndTime(c2);
        pos2.setStartTime(c3);
        pos2.setEndTime(c4);
        pos3.setStartTime(c5);
        pos3.setEndTime(c6);
        pos4.setStartTime(c7);
        pos4.setEndTime(c8);
        pos5.setStartTime(c9);
        pos5.setEndTime(c10);
        pos6.setStartTime(c11);
        pos6.setEndTime(c12);
        pos7.setStartTime(c13);
        pos7.setEndTime(c14);
        pos8.setStartTime(c15);
        pos8.setEndTime(c16);
        pos9.setStartTime(c17);
        pos9.setEndTime(c18);
        pos10.setStartTime(c19);
        pos10.setEndTime(c20);
        pos11.setStartTime(c21);
        pos11.setEndTime(c22);



        listPosition.clear();
        listPosition.add(pos1);
        listPosition.add(pos2);
        listPosition.add(pos3);
        listPosition.add(pos4);
        listPosition.add(pos5);
        listPosition.add(pos6);
        listPosition.add(pos7);
        listPosition.add(pos8);
        listPosition.add(pos9);
        listPosition.add(pos10);
        listPosition.add(pos11);

        //    listPosition.add(pos4);

        Position[] listPos = new Position[listPosition.size()];
        //Zeitliche sortierung
        for (int l  = 0;l<listPosition.size();l++){
            listPos[l] = listPosition.get(l);
        }

   //     Arrays.sort(listPos, comparatorPosition);
        listPosition = listToArraylist(listPos);

        return listPosition;
    }

    public ArrayList<Position> getDummyList3(){

        ArrayList<Position> listPosition = new ArrayList<Position>();

        Position pos1 = new Position("Kunde");
        Position pos2 = new Position("Kunde");
        Position pos3 = new Position("Sonstiges");
        Position pos4 = new Position("Sonstiges");
        Position pos5 = new Position("Sonstiges");
        Position pos6 = new Position("Kunde");
        Position pos7 = new Position("Kunde");
        Position pos8 = new Position("Sonstiges");
        Position pos9 = new Position("Sonstiges");
        Position pos10 = new Position("Kunde");
        Position pos11 = new Position("Kunde");

        Kunde k1 = new Kunde();
        Kunde k2 = new Kunde();
        Kunde k6 = new Kunde();
        Kunde k7 = new Kunde();
        Kunde k10 = new Kunde();
        Kunde k11 = new Kunde();

        k1.setFirma("Kobusch");
        k1.setStrasse("Walsroder Str 55");
        k1.setStadt("Langenhagen");
        k1.setAuswahlTaetigkeit("Heizung");

        k2.setFirma("Meier");
        k2.setStrasse("Walsroder Str 37");
        k1.setStadt("Langenhagen");
        k2.setAuswahlTaetigkeit("Boden");

        k6.setFirma("c");
        k6.setStrasse("c");

        k7.setFirma("d");
        k7.setStrasse("d");

        k10.setFirma("e");
        k10.setStrasse("e");

        k11.setFirma("f");
        k11.setStrasse("f");

        pos1.setKunde(k1);
        pos2.setKunde(k2);
        pos6.setKunde(k6);
        pos7.setKunde(k7);
        pos10.setKunde(k10);
        pos11.setKunde(k11);








        Date d1 = new Date();

        d1.setHours(8);
        d1.setMinutes(00);
        Date d1Ende = new Date();
        d1Ende.setHours(9);
        d1Ende.setMinutes(30);

        Date d2 = new Date();
        d2.setHours(9);
        d2.setMinutes(45);
        Date d2Ende = new Date();
        d2Ende.setHours(12);
        d2Ende.setMinutes(00);

        Date d3 = new Date();
        d3.setHours(9);
        d3.setMinutes(30);
        Date d3Ende = new Date();
        d3Ende.setHours(9);
        d3Ende.setMinutes(45);

        Date d4 = new Date();
        d4.setHours(12);
        d4.setMinutes(30);
        Date d4Ende = new Date();
        d4Ende.setHours(14);
        d4Ende.setMinutes(00);

        Date d5 = new Date();
        d5.setHours(14);
        d5.setMinutes(00);
        Date d5Ende = new Date();
        d5Ende.setHours(14);
        d5Ende.setMinutes(30);

        Date d6 = new Date();
        d6.setHours(14);
        d6.setMinutes(30);
        Date d6Ende = new Date();
        d6Ende.setHours(15);
        d6Ende.setMinutes(00);

        Date d7 = new Date();
        d7.setHours(15);
        d7.setMinutes(00);
        Date d7Ende = new Date();
        d7Ende.setHours(15);
        d7Ende.setMinutes(30);

        Date d8 = new Date();
        d8.setHours(15);
        d8.setMinutes(30);
        Date d8Ende = new Date();
        d8Ende.setHours(16);
        d8Ende.setMinutes(00);

        Date d9 = new Date();
        d9.setHours(16);
        d9.setMinutes(00);
        Date d9Ende = new Date();
        d9Ende.setHours(16);
        d9Ende.setMinutes(30);

        Date d10 = new Date();
        d10.setHours(16);
        d10.setMinutes(30);
        Date d10Ende = new Date();
        d10Ende.setHours(17);
        d10Ende.setMinutes(00);

        Date d11 = new Date();
        d11.setHours(17);
        d11.setMinutes(00);
        Date d11Ende = new Date();
        d11Ende.setHours(17);
        d11Ende.setMinutes(30);




        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        Calendar c4 = Calendar.getInstance();

        Calendar c5 = Calendar.getInstance();
        Calendar c6 = Calendar.getInstance();
        Calendar c7 = Calendar.getInstance();
        Calendar c8 = Calendar.getInstance();

        Calendar c9 = Calendar.getInstance();
        Calendar c10 = Calendar.getInstance();
        Calendar c11 = Calendar.getInstance();
        Calendar c12 = Calendar.getInstance();
        Calendar c13 = Calendar.getInstance();
        Calendar c14 = Calendar.getInstance();
        Calendar c15 = Calendar.getInstance();

        Calendar c16 = Calendar.getInstance();
        Calendar c17 = Calendar.getInstance();
        Calendar c18 = Calendar.getInstance();
        Calendar c19 = Calendar.getInstance();

        Calendar c20 = Calendar.getInstance();
        Calendar c21 = Calendar.getInstance();
        Calendar c22 = Calendar.getInstance();





        c1.setTime(d1);
        c2.setTime(d1Ende);
        c3.setTime(d2);
        c4.setTime(d2Ende);
        c5.setTime(d3);
        c6.setTime(d3Ende);
        c7.setTime(d4);
        c8.setTime(d4Ende);
        c9.setTime(d5);
        c10.setTime(d5Ende);
        c11.setTime(d6);
        c12.setTime(d6Ende);
        c13.setTime(d7);
        c14.setTime(d7Ende);
        c15.setTime(d8);
        c16.setTime(d8Ende);
        c17.setTime(d9);
        c18.setTime(d9Ende);
        c19.setTime(d10);
        c20.setTime(d10Ende);
        c21.setTime(d11);
        c20.setTime(d11Ende);





        pos1.setStartTime(c1);
        pos1.setEndTime(c2);
        pos2.setStartTime(c3);
        pos2.setEndTime(c4);
        pos3.setStartTime(c5);
        pos3.setEndTime(c6);
        pos4.setStartTime(c7);
        pos4.setEndTime(c8);
        pos5.setStartTime(c9);
        pos5.setEndTime(c10);
        pos6.setStartTime(c11);
        pos6.setEndTime(c12);
        pos7.setStartTime(c13);
        pos7.setEndTime(c14);
        pos8.setStartTime(c15);
        pos8.setEndTime(c16);
        pos9.setStartTime(c17);
        pos9.setEndTime(c18);
        pos10.setStartTime(c19);
        pos10.setEndTime(c20);
        pos11.setStartTime(c21);
        pos11.setEndTime(c22);



        listPosition.clear();
        listPosition.add(pos1);
        listPosition.add(pos3);
        listPosition.add(pos2);
     //   listPosition.add(pos4);

        //    listPosition.add(pos4);

        Position[] listPos = new Position[listPosition.size()];
        //Zeitliche sortierung
        for (int l  = 0;l<listPosition.size();l++){
            listPos[l] = listPosition.get(l);
        }

        //     Arrays.sort(listPos, comparatorPosition);
        listPosition = listToArraylist(listPos);

        return listPosition;
    }

    public ArrayList<Position> getDummyList4(){

        ArrayList<Position> listPosition = new ArrayList<Position>();

        Position pos1 = new Position("Kunde");
        Position pos2 = new Position("AKTUELL");
        Position pos3 = new Position("Sonstiges");
        Position pos4 = new Position("Sonstiges");
        Position pos5 = new Position("Sonstiges");
        Position pos6 = new Position("Kunde");
        Position pos7 = new Position("Kunde");
        Position pos8 = new Position("Sonstiges");
        Position pos9 = new Position("Sonstiges");
        Position pos10 = new Position("Kunde");
        Position pos11 = new Position("Kunde");

        Kunde k1 = new Kunde();
        Kunde k2 = new Kunde();
        Kunde k6 = new Kunde();
        Kunde k7 = new Kunde();
        Kunde k10 = new Kunde();
        Kunde k11 = new Kunde();

        k1.setFirma("Kobusch");
        k1.setStrasse("Walsroder Str 55");
        k1.setStadt("Langenhagen");
        k1.setAuswahlTaetigkeit("Heizung");

        k2.setFirma("Meier");
        k2.setStrasse("Walsroder Str 37");
        k1.setStadt("Langenhagen");
        k2.setAuswahlTaetigkeit("Boden");

        k6.setFirma("c");
        k6.setStrasse("c");

        k7.setFirma("d");
        k7.setStrasse("d");

        k10.setFirma("e");
        k10.setStrasse("e");

        k11.setFirma("f");
        k11.setStrasse("f");

        pos1.setKunde(k1);
        pos2.setKunde(k2);
        pos6.setKunde(k6);
        pos7.setKunde(k7);
        pos10.setKunde(k10);
        pos11.setKunde(k11);








        Date d1 = new Date();

        d1.setHours(8);
        d1.setMinutes(03);
        Date d1Ende = new Date();
        d1Ende.setHours(9);
        d1Ende.setMinutes(27);

        Date d2 = new Date();
        d2.setHours(9);
        d2.setMinutes(36);
        Date d2Ende = new Date();
        d2Ende.setHours(12);
        d2Ende.setMinutes(44);

        Date d3 = new Date();
        d3.setHours(13);
        d3.setMinutes(01);
        Date d3Ende = new Date();
        d3Ende.setHours(13);
        d3Ende.setMinutes(22);

        Date d4 = new Date();
        d4.setHours(12);
        d4.setMinutes(30);
        Date d4Ende = new Date();
        d4Ende.setHours(14);
        d4Ende.setMinutes(00);

        Date d5 = new Date();
        d5.setHours(14);
        d5.setMinutes(00);
        Date d5Ende = new Date();
        d5Ende.setHours(14);
        d5Ende.setMinutes(30);

        Date d6 = new Date();
        d6.setHours(14);
        d6.setMinutes(30);
        Date d6Ende = new Date();
        d6Ende.setHours(15);
        d6Ende.setMinutes(00);

        Date d7 = new Date();
        d7.setHours(15);
        d7.setMinutes(00);
        Date d7Ende = new Date();
        d7Ende.setHours(15);
        d7Ende.setMinutes(30);

        Date d8 = new Date();
        d8.setHours(15);
        d8.setMinutes(30);
        Date d8Ende = new Date();
        d8Ende.setHours(16);
        d8Ende.setMinutes(00);

        Date d9 = new Date();
        d9.setHours(16);
        d9.setMinutes(00);
        Date d9Ende = new Date();
        d9Ende.setHours(16);
        d9Ende.setMinutes(30);

        Date d10 = new Date();
        d10.setHours(16);
        d10.setMinutes(30);
        Date d10Ende = new Date();
        d10Ende.setHours(17);
        d10Ende.setMinutes(00);

        Date d11 = new Date();
        d11.setHours(17);
        d11.setMinutes(00);
        Date d11Ende = new Date();
        d11Ende.setHours(17);
        d11Ende.setMinutes(30);




        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        Calendar c4 = Calendar.getInstance();

        Calendar c5 = Calendar.getInstance();
        Calendar c6 = Calendar.getInstance();
        Calendar c7 = Calendar.getInstance();
        Calendar c8 = Calendar.getInstance();

        Calendar c9 = Calendar.getInstance();
        Calendar c10 = Calendar.getInstance();
        Calendar c11 = Calendar.getInstance();
        Calendar c12 = Calendar.getInstance();
        Calendar c13 = Calendar.getInstance();
        Calendar c14 = Calendar.getInstance();
        Calendar c15 = Calendar.getInstance();

        Calendar c16 = Calendar.getInstance();
        Calendar c17 = Calendar.getInstance();
        Calendar c18 = Calendar.getInstance();
        Calendar c19 = Calendar.getInstance();

        Calendar c20 = Calendar.getInstance();
        Calendar c21 = Calendar.getInstance();
        Calendar c22 = Calendar.getInstance();





        c1.setTime(d1);
        c2.setTime(d1Ende);
        c3.setTime(d2);
        c4.setTime(d2Ende);
        c5.setTime(d3);
        c6.setTime(d3Ende);
        c7.setTime(d4);
        c8.setTime(d4Ende);
        c9.setTime(d5);
        c10.setTime(d5Ende);
        c11.setTime(d6);
        c12.setTime(d6Ende);
        c13.setTime(d7);
        c14.setTime(d7Ende);
        c15.setTime(d8);
        c16.setTime(d8Ende);
        c17.setTime(d9);
        c18.setTime(d9Ende);
        c19.setTime(d10);
        c20.setTime(d10Ende);
        c21.setTime(d11);
        c20.setTime(d11Ende);





        pos1.setStartTime( pos1.getAzeitAbgerundet(c1));
        pos1.setEndTime(pos1.getAzeitAbgerundet(c2));
        pos2.setStartTime(pos2.getAzeitAbgerundet(c3));
        pos2.setEndTime(pos2.getAzeitAbgerundet(c4));
        pos3.setStartTime(pos3.getAzeitAbgerundet(c5));
        pos3.setEndTime(pos3.getAzeitAbgerundet(c6));
        pos4.setStartTime(c7);
        pos4.setEndTime(c8);
        pos5.setStartTime(c9);
        pos5.setEndTime(c10);
        pos6.setStartTime(c11);
        pos6.setEndTime(c12);
        pos7.setStartTime(c13);
        pos7.setEndTime(c14);
        pos8.setStartTime(c15);
        pos8.setEndTime(c16);
        pos9.setStartTime(c17);
        pos9.setEndTime(c18);
        pos10.setStartTime(c19);
        pos10.setEndTime(c20);
        pos11.setStartTime(c21);
        pos11.setEndTime(c22);



        listPosition.clear();
        listPosition.add(pos1);
      //  listPosition.add(pos3);
        listPosition.add(pos2);
        //   listPosition.add(pos4);

        //    listPosition.add(pos4);

        Position[] listPos = new Position[listPosition.size()];
        //Zeitliche sortierung
        for (int l  = 0;l<listPosition.size();l++){
            listPos[l] = listPosition.get(l);
        }

        //     Arrays.sort(listPos, comparatorPosition);
        listPosition = listToArraylist(listPos);

        return listPosition;
    }

    public ArrayList<Position> getDummyList5(){

        ArrayList<Position> listPosition = new ArrayList<Position>();

        Position pos1 = new Position("Kunde");
        Position pos2 = new Position("Kunde");
        Position pos3 = new Position("Sonstiges");
        Position pos4 = new Position("Sonstiges");
        Position pos5 = new Position("Sonstiges");
        Position pos6 = new Position("Kunde");
        pos6.setFirma("Firma");
        Position pos7 = new Position("Kunde");
        Position pos8 = new Position("Sonstiges");
        Position pos9 = new Position("Sonstiges");
        Position pos10 = new Position("Kunde");
        Position pos11 = new Position("Kunde");

        Kunde k1 = new Kunde();
        Kunde k2 = new Kunde();
        Kunde k6 = new Kunde();
        Kunde k7 = new Kunde();
        Kunde k10 = new Kunde();
        Kunde k11 = new Kunde();

        k1.setFirma("Kobusch");
        k1.setStrasse("Walsroder Str 55");
        k1.setStadt("Langenhagen");
        k1.setAuswahlTaetigkeit("Heizung");

        k2.setFirma("Meier");
        k2.setStrasse("Walsroder Str 37");
        k1.setStadt("Langenhagen");
        k2.setAuswahlTaetigkeit("Boden");

        k6.setFirma("Firma");
        k6.setStrasse("c");

        k7.setFirma("d");
        k7.setStrasse("d");

        k10.setFirma("e");
        k10.setStrasse("e");

        k11.setFirma("f");
        k11.setStrasse("f");

        pos1.setKunde(k1);
        pos2.setKunde(k2);
        pos6.setKunde(k6);
        pos7.setKunde(k7);
        pos10.setKunde(k10);
        pos11.setKunde(k11);








        Date d1 = new Date();

        d1.setHours(7);
        d1.setMinutes(57);
        Date d1Ende = new Date();
        d1Ende.setHours(9);
        d1Ende.setMinutes(30);

        Date d2 = new Date();
        d2.setHours(9);
        d2.setMinutes(45);
        Date d2Ende = new Date();
        d2Ende.setHours(12);
        d2Ende.setMinutes(10);

        Date d3 = new Date();
        d3.setHours(16);
        d3.setMinutes(30);
        Date d3Ende = new Date();
        d3Ende.setHours(17);
        d3Ende.setMinutes(45);

        Date d4 = new Date();
        d4.setHours(12);
        d4.setMinutes(30);
        Date d4Ende = new Date();
        d4Ende.setHours(14);
        d4Ende.setMinutes(00);

        Date d5 = new Date();
        d5.setHours(14);
        d5.setMinutes(00);
        Date d5Ende = new Date();
        d5Ende.setHours(14);
        d5Ende.setMinutes(30);

        Date d6 = new Date();
        d6.setHours(14);
        d6.setMinutes(30);
        Date d6Ende = new Date();
        d6Ende.setHours(14);
        d6Ende.setMinutes(32);

        Date d7 = new Date();
        d7.setHours(15);
        d7.setMinutes(00);
        Date d7Ende = new Date();
        d7Ende.setHours(15);
        d7Ende.setMinutes(30);

        Date d8 = new Date();
        d8.setHours(15);
        d8.setMinutes(30);
        Date d8Ende = new Date();
        d8Ende.setHours(16);
        d8Ende.setMinutes(00);

        Date d9 = new Date();
        d9.setHours(16);
        d9.setMinutes(00);
        Date d9Ende = new Date();
        d9Ende.setHours(16);
        d9Ende.setMinutes(30);

        Date d10 = new Date();
        d10.setHours(16);
        d10.setMinutes(30);
        Date d10Ende = new Date();
        d10Ende.setHours(17);
        d10Ende.setMinutes(00);

        Date d11 = new Date();
        d11.setHours(17);
        d11.setMinutes(00);
        Date d11Ende = new Date();
        d11Ende.setHours(17);
        d11Ende.setMinutes(30);




        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        Calendar c4 = Calendar.getInstance();

        Calendar c5 = Calendar.getInstance();
        Calendar c6 = Calendar.getInstance();
        Calendar c7 = Calendar.getInstance();
        Calendar c8 = Calendar.getInstance();

        Calendar c9 = Calendar.getInstance();
        Calendar c10 = Calendar.getInstance();
        Calendar c11 = Calendar.getInstance();
        Calendar c12 = Calendar.getInstance();
        Calendar c13 = Calendar.getInstance();
        Calendar c14 = Calendar.getInstance();
        Calendar c15 = Calendar.getInstance();

        Calendar c16 = Calendar.getInstance();
        Calendar c17 = Calendar.getInstance();
        Calendar c18 = Calendar.getInstance();
        Calendar c19 = Calendar.getInstance();

        Calendar c20 = Calendar.getInstance();
        Calendar c21 = Calendar.getInstance();
        Calendar c22 = Calendar.getInstance();





        c1.setTime(d1);
        c2.setTime(d1Ende);
        c3.setTime(d2);
        c4.setTime(d2Ende);
        c5.setTime(d3);
        c6.setTime(d3Ende);
        c7.setTime(d4);
        c8.setTime(d4Ende);
        c9.setTime(d5);
        c10.setTime(d5Ende);
        c11.setTime(d6);
        c12.setTime(d6Ende);
        c13.setTime(d7);
        c14.setTime(d7Ende);
        c15.setTime(d8);
        c16.setTime(d8Ende);
        c17.setTime(d9);
        c18.setTime(d9Ende);
        c19.setTime(d10);
        c20.setTime(d10Ende);
        c21.setTime(d11);
        c20.setTime(d11Ende);





        pos1.setStartTime(c1);
        pos1.setEndTime(c2);
        pos2.setStartTime(c3);
        pos2.setEndTime(c4);
        pos3.setStartTime(c5);
        pos3.setEndTime(c6);
        pos4.setStartTime(c7);
        pos4.setEndTime(c8);
        pos5.setStartTime(c9);
        pos5.setEndTime(c10);
        pos6.setStartTime(c11);
        pos6.setEndTime(c12);
        pos7.setStartTime(c13);
        pos7.setEndTime(c14);
        pos8.setStartTime(c15);
        pos8.setEndTime(c16);
        pos9.setStartTime(c17);
        pos9.setEndTime(c18);
        pos10.setStartTime(c19);
        pos10.setEndTime(c20);
        pos11.setStartTime(c21);
        pos11.setEndTime(c22);



        listPosition.clear();
        listPosition.add(pos1);
        listPosition.add(pos2);
        listPosition.add(pos6);
        listPosition.add(pos3);
        //   listPosition.add(pos4);

        //    listPosition.add(pos4);

        Position[] listPos = new Position[listPosition.size()];
        //Zeitliche sortierung
        for (int l  = 0;l<listPosition.size();l++){
            listPos[l] = listPosition.get(l);
        }

        //     Arrays.sort(listPos, comparatorPosition);
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
