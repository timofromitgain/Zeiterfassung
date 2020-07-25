package com.example.timo.Zeiterfassung.Helfer;

public class Auftrag {
    private String taetigkeit;

    public String getTaetigkeit() {
        return taetigkeit;
    }

    public void setTaetigkeit(String taetigkeit) {
        this.taetigkeit = taetigkeit;
    }

    public Auftrag(String taetigkeit) {
        this.taetigkeit = taetigkeit;
    }
}
