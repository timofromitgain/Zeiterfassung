package com.zeiterfassung.timo.Zeiterfassung.Activity;

import java.util.HashMap;

public class Speicher {
   public static HashMap<String,Integer> locIntervalls;

    public static void setLocIntervalls(HashMap<String, Integer> locIntervalls) {
        Speicher.locIntervalls = locIntervalls;
    }

    public static HashMap<String, Integer> getLocIntervalls() {
        return locIntervalls;
    }
}
