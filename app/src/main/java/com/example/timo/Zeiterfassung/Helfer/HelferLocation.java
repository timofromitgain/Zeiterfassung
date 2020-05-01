package com.example.timo.Zeiterfassung.Helfer;

import android.location.Location;

public class HelferLocation {
    double latAlt;
    double longAlt;



    String time;
    boolean hasAcc;
    float acc;
    boolean hasSpeedAcc;
    double distanceToLastPoint;
    String provider;

    public HelferLocation(double latAlt, double longAlt, String time, boolean hasAcc, float acc, boolean hasSpeedAcc, double distanceToLastPoint, String provider) {
        this.latAlt = latAlt;
        this.longAlt = longAlt;
        this.time = time;
        this.hasAcc = hasAcc;
        this.acc = acc;
        this.hasSpeedAcc = hasSpeedAcc;
        this.distanceToLastPoint = distanceToLastPoint;
        this.provider = provider;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public double getLatAlt() {
        return latAlt;
    }

    public void setLatAlt(double latAlt) {
        this.latAlt = latAlt;
    }

    public double getLongAlt() {
        return longAlt;
    }

    public void setLongAlt(double longAlt) {
        this.longAlt = longAlt;
    }





    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }



    public boolean isHasAcc() {
        return hasAcc;
    }

    public void setHasAcc(boolean hasAcc) {
        this.hasAcc = hasAcc;
    }

    public float getAcc() {
        return acc;
    }

    public void setAcc(float acc) {
        this.acc = acc;
    }

    public boolean isHasSpeedAcc() {
        return hasSpeedAcc;
    }

    public void setHasSpeedAcc(boolean hasSpeedAcc) {
        this.hasSpeedAcc = hasSpeedAcc;
    }

    public double getDistanceToLastPoint() {
        return distanceToLastPoint;
    }

    public void setDistanceToLastPoint(double distanceToLastPoint) {
        this.distanceToLastPoint = distanceToLastPoint;
    }
}
