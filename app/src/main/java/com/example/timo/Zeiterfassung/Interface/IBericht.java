package com.example.timo.Zeiterfassung.Interface;

import com.example.timo.Zeiterfassung.Beans.Position;
import com.example.timo.Zeiterfassung.Fragment.Heute;

import java.util.ArrayList;

public interface IBericht {
    void onListPosFiltered(ArrayList<Position> listPosition);
    void onFragmetHeuteCreated (Heute contextFragmentHeute);
    void onCalculateArbeitszeit (String str);
}
