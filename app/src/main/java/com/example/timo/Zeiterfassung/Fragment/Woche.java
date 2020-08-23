package com.example.timo.Zeiterfassung.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timo.Zeiterfassung.Activity.BerichtWoche;
import com.example.timo.Zeiterfassung.Beans.Position;
import com.example.timo.Zeiterfassung.Helfer.TaetigkeitsberichtUtil;
import com.example.timo.Zeiterfassung.Interface.ITaetigkeitsbericht;
import com.example.timo.Zeiterfassung.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Woche extends Fragment implements ITaetigkeitsbericht {
    View view;
    String azGesamt;
    public static Woche woche;
    LinearLayout linearMontag,
            linearDienstag,
            linearMittwoch,
            linearDonnerstag,
            linearFreitag,
            linearSamstag,
            linearSonntag;


    String startTime, endTime, arbeitsZeitVonBis;
    TaetigkeitsberichtUtil taetigkeitsberichtUtil = new TaetigkeitsberichtUtil();
    ArrayList<Position> listPos = new ArrayList<Position>();
    String arbeitszeit;
    int stdGes = 0, minGes = 0;
    String[] splited;

    TextView tvAzMonatag,
            tvAzDienstag,
            tvAzMittwoch,
            tvAzDonnerstag,
            tvAzFreitag,
            tvAzSamstag,
            tvAzSonntag,
            tvAzGesamt;
    HashMap<String, TaetigkeitsberichtUtil> listWochenBericht = new HashMap<>();

    private void clickBericht(String wochentag){
        if ( listWochenBericht.get(wochentag) == null){
            Toast.makeText(getContext(), "Keine Daten an dem Tag verfügbar!", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(getActivity(), BerichtWoche.class);
            intent.putExtra("listPosition", listWochenBericht.get(wochentag).
                    getTaetigkeitsbericht2(listWochenBericht.get(wochentag).getBericht()));
            azGesamt = listWochenBericht.get(wochentag).getArbeitszeit();
            if (azGesamt == null){
                azGesamt = "0 Std, 00 Minuten";
            }
            intent.putExtra("arbeitszeit", azGesamt);
            startActivity(intent);
        }
    }

    public void initialWochentag (TextView tv, String wochentag){
        if (listWochenBericht.get(wochentag) != null && listPos != null) {
            listPos = taetigkeitsberichtUtil.getTaetigkeitsbericht2(listWochenBericht.get(wochentag).getBericht());
            if (listPos != null) {
                arbeitsZeitVonBis = getZeitString(listPos);
                arbeitszeit = listWochenBericht.get(wochentag).getArbeitszeit();
                if (arbeitszeit != null) {
                    tv.setText(arbeitsZeitVonBis + "\n" + arbeitszeit);
                    splited = arbeitszeit.split("\\s+");
                    stdGes = stdGes + Integer.parseInt(splited[0]);
                    minGes = minGes + Integer.parseInt(splited[2]);
                    String gg = "f";
                } else {
                    tv.setText("?");
                }

            } else {
                tv.setText("?");
            }

        } else {
            tv.setText("?");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_woche, container, false);
        linearMontag = view.findViewById(R.id.linearMontag);
        linearDienstag = view.findViewById(R.id.linearDienstag);
        linearMittwoch = view.findViewById(R.id.linearMittwoch);
        linearDonnerstag = view.findViewById(R.id.linearDonnerstag);
        linearFreitag = view.findViewById(R.id.linearFreitag);
        linearSamstag = view.findViewById(R.id.linearSamstag);
        linearSonntag = view.findViewById(R.id.linearSonntag);


        linearMontag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBericht("MONTAG");
        }});

        linearDienstag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBericht("DIENSTAG");
            }});

        linearMittwoch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBericht("MITTWOCH");
            }});

        linearDonnerstag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBericht("DONNERSTAG");
            }});

        linearFreitag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBericht("FREITAG");
            }});

        linearSamstag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBericht("SAMSTAG");
            }});

        linearSonntag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBericht("SONNTAG");
            }});

        tvAzMonatag = view.findViewById(R.id.azMontag);
        tvAzDienstag = view.findViewById(R.id.azDienstag);
        tvAzMittwoch = view.findViewById(R.id.azMittwoch);
        tvAzDonnerstag = view.findViewById(R.id.azDonnerstag);
        tvAzFreitag = view.findViewById(R.id.azFreitag);
        tvAzSamstag = view.findViewById(R.id.azSamstag);
        tvAzSonntag = view.findViewById(R.id.azSonntag);
        tvAzGesamt = view.findViewById(R.id.azGesamt);


        woche = this;
        //     Taetigkeitsbericht taetigkeitsbericht = Taetigkeitsbericht.taetigkeitsbericht;
        //   taetigkeitsbericht.onFragmetHeuteCreated(heute);
        return view;
    }

    @Override
    public void onGetTaetigkeitsberichtData(TaetigkeitsberichtUtil taetigkeitsberichtUtil) {

    }

    @Override
    public void onSendTaetigkeitsbericht() {

    }

    @Override
    public void onRemoveTaetigkeitsbericht() {

    }

    @Override
    public void onDayOfTaetigkeitsberichtChange(TaetigkeitsberichtUtil taetigkeitsberichtUtil) {

    }

    @Override
    public void onGetWochenbericht(HashMap<String, TaetigkeitsberichtUtil> listWochenBericht) {
        this.listWochenBericht = listWochenBericht;
        //Montag
stdGes = 0;
minGes = 0;
        initialWochentag(tvAzMonatag,"MONTAG");
        initialWochentag(tvAzDienstag,"DIENSTAG");
        initialWochentag(tvAzMittwoch,"MITTWOCH");
        initialWochentag(tvAzDonnerstag,"DONNERSTAG");
        initialWochentag(tvAzFreitag,"FREITAG");
        initialWochentag(tvAzSamstag,"SAMSTAG");
        initialWochentag(tvAzSonntag,"SONNTAG");

        stdGes = stdGes + (minGes / 60);
        minGes = minGes % 60;

        tvAzGesamt.setText("Gesamt: " + String.valueOf(stdGes) + " Std, " + String.valueOf(minGes) + " Min");


    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    public String getZeitString(ArrayList<Position> listPosition) {
        String stdAnf, minAnf, startTime, stdEnde, minEnde, endTime, arbeitsZeitVonBis;
        stdAnf = String.valueOf(listPosition.get(0).getStartTime().get(Calendar.HOUR_OF_DAY));
        minAnf = String.valueOf(listPosition.get(0).getStartTime().get(Calendar.MINUTE));
        startTime = stdAnf + ":" + minAnf + " Uhr";
        stdEnde = String.valueOf(listPosition.get(listPosition.size() - 1).getEndTime().get(Calendar.HOUR_OF_DAY));
        minEnde = String.valueOf(listPosition.get(listPosition.size() - 1).getEndTime().get(Calendar.MINUTE));
        endTime = stdEnde + ":" + minEnde + " Uhr";
        arbeitsZeitVonBis = startTime + " - " + endTime;

        return arbeitsZeitVonBis;
    }
}
