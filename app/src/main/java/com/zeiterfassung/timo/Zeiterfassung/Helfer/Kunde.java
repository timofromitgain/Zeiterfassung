package com.zeiterfassung.timo.Zeiterfassung.Helfer;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

public class Kunde implements Serializable {
    static Context context;
    int id;
    ArrayList<String> listAuftrag;
    String auftragsId;
    String firma;
    String ansprechpartner;
    String strasse;
    String stadt;
    String taetigkeit_1;
    String taetigkeit_2;
    String taetigkeit_3;
    String auswahlTaetigkeit;
    String taetigkeitString;
    int radius, status;
    double latitude;
    double longitude;
    String anmerkung;
    Integer position;
    Boolean besucht = false;
    Boolean monteurBeimKunden = false;

    public void setMonteurBeimKunden(Boolean monteurBeimKunden) {
        this.monteurBeimKunden = monteurBeimKunden;
    }

    public Boolean getMonteurBeimKunden() {
        return monteurBeimKunden;
    }

    public String getTaetigkeitString() {
        if (this.taetigkeit_3 == null || this.taetigkeit_3.equals("")) {
            if (this.taetigkeit_2 == null || taetigkeit_2.equals("")) {
                return taetigkeit_1;
            } else {
                return taetigkeit_1 + "\n" + taetigkeit_2;
            }
        } else {
            return taetigkeit_1 + "\n" + taetigkeit_2 + "\n" + taetigkeit_3;
        }

    }

    public void setAuswahlTaetigkeit(String auswahlTaetigkeit) {
        this.auswahlTaetigkeit = auswahlTaetigkeit;
    }

    public String getAuswahlTaetigkeit() {
        return auswahlTaetigkeit;
    }

    public Kunde() {

    }

    public Kunde(Context context,
                 String firma,
                 String ansprechpartner,
                 String strasse,
                 String stadt,
                 String taetigkeit_1,
                 String taetigkeit_2,
                 String taetigkeit_3,
                 String anmerkung) {
        this.firma = firma;
        this.ansprechpartner = ansprechpartner;
        this.strasse = strasse;
        this.stadt = stadt;
        this.taetigkeit_1 = taetigkeit_1;
        this.taetigkeit_2 = taetigkeit_2;
        this.taetigkeit_3 = taetigkeit_3;
        this.anmerkung = anmerkung;

    }

    public void setAuftragsId(String auftragsId) {
        this.auftragsId = auftragsId;
    }

    public String getAuftragsId() {
        return auftragsId;
    }

    public ArrayList<String> getListAuftrag() {
        return listAuftrag;
    }

    public void setListAuftrag(ArrayList<String> listAuftrag) {
        this.listAuftrag = listAuftrag;
    }

    public Kunde(Context context,
                 String auftragsId,
                 String firma,
                 String ansprechpartner,
                 String strasse,
                 String stadt,
                 ArrayList<String> listAuftrag,
                 String anmerkung,
                 Integer radius,
                 double latitude,
                 double longitude,
                 Integer status) {
        this.auftragsId = auftragsId;
        this.firma = firma;
        this.ansprechpartner = ansprechpartner;
        this.strasse = strasse;
        this.stadt = stadt;
        this.listAuftrag = listAuftrag;
        this.anmerkung = anmerkung;
        this.radius = radius;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
    }

    public Kunde(
                 String firma,
                 String strasse,
                 String stadt,
                 String ansprechpartner,
                 String taetigkeit_1,
                 String anmerkung,
                 double latitude,
                 double longitude) {
        this.firma = firma;
        this.strasse = strasse;
        this.stadt = stadt;
        this.ansprechpartner = ansprechpartner;
        this.taetigkeit_1 = taetigkeit_1;
        this.anmerkung = anmerkung;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public void setBesucht(Boolean besucht) {
        this.besucht = besucht;
    }

    public Boolean getBesucht() {
        return besucht;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }



    public void setFirma(String firma) {
        this.firma = firma;
    }

    public void setAnsprechpartner(String ansprechpartner) {
        this.ansprechpartner = ansprechpartner;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    public void setTaetigkeit_1(String taetigkeit_1) {
        this.taetigkeit_1 = taetigkeit_1;
    }

    public void setTaetigkeit_2(String taetigkeit_2) {
        this.taetigkeit_2 = taetigkeit_2;
    }

    public void setTaetigkeit_3(String taetigkeit_3) {
        this.taetigkeit_3 = taetigkeit_3;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setAnmerkung(String anmerkung) {
        this.anmerkung = anmerkung;
    }

    public String getFirma() {
        return firma;
    }

    public String getAnsprechpartner() {
        return ansprechpartner;
    }

    public String getStrasse() {
        return strasse;
    }

    public String getStadt() {
        return stadt;
    }

    public String getTaetigkeit_1() {
        return taetigkeit_1;
    }

    public String getTaetigkeit_2() {
        return taetigkeit_2;
    }

    public String getTaetigkeit_3() {
        return taetigkeit_3;
    }

    public int getRadius() {
        return radius;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAnmerkung() {
        return anmerkung;
    }
}

