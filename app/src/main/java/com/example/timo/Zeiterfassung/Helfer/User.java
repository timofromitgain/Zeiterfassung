package com.example.timo.Zeiterfassung.Helfer;

import java.io.Serializable;

public class User implements Serializable {
    String nachname, vorname, strasse, stadt, status;
    Double latitude, longitude;

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getStadt() {
        return stadt;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public User(String nachname, String vorname, String strasse, String stadt, String status, Double latitude, Double longitude) {
        this.nachname = nachname;
        this.vorname = vorname;
        this.strasse = strasse;
        this.stadt = stadt;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public User(){

    }
}