package com.example.timo.Zeiterfassung.Interface;

import com.example.timo.Zeiterfassung.Helfer.Kunde;

import java.util.HashMap;

public interface IFirebase {
    void neuerKunde (HashMap<String, Kunde> listKunde);
    void neuerKunde (String key,Kunde kunde);
    void loescheKunde (String key);

}
