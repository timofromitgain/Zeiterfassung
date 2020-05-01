package com.example.timo.Zeiterfassung.Helfer;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class Geo {
    double latitude;
    double longitude;
    private Context context;
    public LocationManager locationManager;

    public Geo(Context context) {
        this.context = context;
    }

    // Siehe erste Antwort https://stackoverflow.com/questions/3574644/how-can-i-find-the-latitude-and-longitude-from-address
    public LatLng setGeo(String adresse, String ort) {

        Geocoder coder = new Geocoder(context);
        try {

            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(
                    adresse + " " + ort, 1);

            for (Address add : adresses) {

                longitude = add.getLongitude();
                latitude = add.getLatitude();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LatLng(latitude, longitude);
    }

    public double getDistance(double lat1, double lon1, double lat2, double lon2) {
        Location startPoint = new Location("locationA");
        startPoint.setLatitude(lat1);
        startPoint.setLongitude(lon1);

        Location endPoint = new Location("locationB");
        endPoint.setLatitude(lat2);
        endPoint.setLongitude(lon2);
        double distance = startPoint.distanceTo(endPoint);

        return distance;
    }





    //Überprüfen ob sich der aktuelle Standort in einem Kundenradius befindet
    public Boolean inKreis(double distance, int radius) {
        if (distance > radius) {
            return false;
        } else {
            return true;
        }
    }



}