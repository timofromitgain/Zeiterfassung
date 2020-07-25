package com.example.timo.Zeiterfassung.Interface;

import com.example.timo.Zeiterfassung.Helfer.TaetigkeitsberichtUtil;

import java.util.HashMap;

public interface ITaetigkeitsbericht {
    void onGetTaetigkeitsberichtData(TaetigkeitsberichtUtil taetigkeitsberichtUtil);
    void onSendTaetigkeitsbericht();
    void onRemoveTaetigkeitsbericht();
    void onDayOfTaetigkeitsberichtChange(TaetigkeitsberichtUtil taetigkeitsberichtUtil);
    void onGetWochenbericht(HashMap<String,TaetigkeitsberichtUtil> listWochenBericht);


}
