package com.zeiterfassung.timo.Zeiterfassung.Interface;

import com.zeiterfassung.timo.Zeiterfassung.Beans.Position;
import com.zeiterfassung.timo.Zeiterfassung.Fragment.Heute;

import java.util.ArrayList;

public interface IBericht {
    void onListPosFiltered(ArrayList<Position> listPosition);
    void onFragmetHeuteCreated (Heute contextFragmentHeute);
    void onCalculateArbeitszeit (String str);
}
