package com.example.timo.Zeiterfassung.Interface;

import com.example.timo.Zeiterfassung.Helfer.Kunde;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public interface OnGetData {
    void onSuccess(ArrayList<Kunde> listKunde);
}
