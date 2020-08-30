package com.zeiterfassung.timo.Zeiterfassung.Interface;

import com.zeiterfassung.timo.Zeiterfassung.Helfer.TaetigkeitsberichtUtil;

import java.util.HashMap;

public interface ITaetigkeitsbericht {
    void onGetTaetigkeitsberichtData(TaetigkeitsberichtUtil taetigkeitsberichtUtil);
    void onSendTaetigkeitsbericht();
    void onRemoveTaetigkeitsbericht();
    void onDayOfTaetigkeitsberichtChange(TaetigkeitsberichtUtil taetigkeitsberichtUtil);
    void onGetWochenbericht(HashMap<String,TaetigkeitsberichtUtil> listWochenBericht);


}
