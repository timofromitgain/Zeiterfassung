package com.zeiterfassung.timo.Zeiterfassung.Interface;

import com.google.android.gms.maps.model.LatLng;

public interface DialogLocationInterface {
    void listenerLocation(String strasse, String stadt, LatLng latLng);
}
