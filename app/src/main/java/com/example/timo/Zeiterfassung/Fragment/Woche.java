package com.example.timo.Zeiterfassung.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    public static Woche woche;
    LinearLayout linearMontag,
            linearDienstag,
            linearMittwoch,
            linearDonnerstag,
            linearFreitag,
            linearSamstag,
            linearSonntag;

    TextView tvAzMonatag,
            tvAzDienstag,
            tvAzMittwoch,
            tvAzDonnerstag,
            tvAzFreitag,
            tvAzSamstag,
            tvAzSonntag,
            tvAzGesamt;
    HashMap<String, TaetigkeitsberichtUtil> listWochenBericht = new HashMap<>();

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
                Intent intent = new Intent(getActivity(), BerichtWoche.class);
                intent.putExtra("listPosition",listWochenBericht.get("MONTAG").
                        getTaetigkeitsbericht2(listWochenBericht.get("MONTAG").getBericht()));
                intent.putExtra("arbeitszeit",listWochenBericht.get("MONTAG").getArbeitszeit());
                startActivity(intent);
            }
        });

        linearDienstag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BerichtWoche.class);
                intent.putExtra("listPosition",listWochenBericht.get("DIENSTAG").
                        getTaetigkeitsbericht2(listWochenBericht.get("DIENSTAG").getBericht()));
                intent.putExtra("arbeitszeit",listWochenBericht.get("DIENSTAG").getArbeitszeit());
                startActivity(intent);
            }
        });

        linearMittwoch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BerichtWoche.class);
                intent.putExtra("listPosition",listWochenBericht.get("MITTWOCH").
                        getTaetigkeitsbericht2(listWochenBericht.get("MITTWOCH").getBericht()));
                intent.putExtra("arbeitszeit",listWochenBericht.get("MITTWOCH").getArbeitszeit());
                startActivity(intent);
            }
        });

        linearDonnerstag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BerichtWoche.class);
                intent.putExtra("listPosition",listWochenBericht.get("DONNERSTAG").
                        getTaetigkeitsbericht2(listWochenBericht.get("DONNERSTAG").getBericht()));
                intent.putExtra("arbeitszeit",listWochenBericht.get("DONNERSTAG").getArbeitszeit());
                startActivity(intent);
            }
        });

        linearFreitag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BerichtWoche.class);
                intent.putExtra("listPosition",listWochenBericht.get("FREITAG").
                        getTaetigkeitsbericht2(listWochenBericht.get("FREITAG").getBericht()));
                intent.putExtra("arbeitszeit",listWochenBericht.get("FREITAG").getArbeitszeit());
                startActivity(intent);
            }
        });

        linearSamstag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BerichtWoche.class);
                intent.putExtra("listPosition",listWochenBericht.get("SAMSTAG").
                        getTaetigkeitsbericht2(listWochenBericht.get("SAMSTAG").getBericht()));
                intent.putExtra("arbeitszeit",listWochenBericht.get("SAMSTAG").getArbeitszeit());
                startActivity(intent);
            }
        });

        linearSonntag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BerichtWoche.class);
                intent.putExtra("listPosition",listWochenBericht.get("SONNTAG").
                        getTaetigkeitsbericht2(listWochenBericht.get("SONNTAG").getBericht()));
                intent.putExtra("arbeitszeit",listWochenBericht.get("SONNTAG").getArbeitszeit());
                startActivity(intent);
            }
        });

        tvAzMonatag = view.findViewById(R.id.azMontag);
        tvAzDienstag = view.findViewById(R.id.azDienstag);
        tvAzMittwoch = view.findViewById(R.id.azMittwoch);
        tvAzDonnerstag = view.findViewById(R.id.azDonnerstag);
        tvAzFreitag = view.findViewById(R.id.azFreitag);
        tvAzSamstag= view.findViewById(R.id.azSamstag);
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
        String startTime, endTime, arbeitsZeitVonBis;
        TaetigkeitsberichtUtil taetigkeitsberichtUtil = new TaetigkeitsberichtUtil();
        ArrayList<Position> listPos = new ArrayList<Position>();
        String arbeitszeit;
        int stdGes = 0, minGes = 0;
        String[] splited;
        //Montag

        if (listWochenBericht.get("MONTAG") != null && listPos != null) {
            listPos= taetigkeitsberichtUtil.getTaetigkeitsbericht2(listWochenBericht.get("MONTAG").getBericht());
            if (listPos != null){
                arbeitsZeitVonBis = getZeitString(listPos);
                arbeitszeit = listWochenBericht.get("MONTAG").getArbeitszeit();
                tvAzMonatag.setText(arbeitsZeitVonBis + "\n" + arbeitszeit);
                Position pos = new Position("");
                splited = arbeitszeit.split("\\s+");
                stdGes = Integer.parseInt(splited[0]);
                minGes = Integer.parseInt(splited[2]);
            }else{
                tvAzMonatag.setText("?");
            }

        } else {
            tvAzMonatag.setText("?");
        }
        //Dienstag

        if (listWochenBericht.get("DIENSTAG") != null && listPos != null) {
            listPos = taetigkeitsberichtUtil.getTaetigkeitsbericht2(listWochenBericht.get("DIENSTAG").getBericht());
            if (listPos != null){


            arbeitsZeitVonBis = getZeitString(listPos);
            arbeitszeit = listWochenBericht.get("DIENSTAG").getArbeitszeit();
            tvAzDienstag.setText(arbeitsZeitVonBis + "\n" + arbeitszeit);
            splited = arbeitszeit.split("\\s+");
            stdGes = stdGes + Integer.parseInt(splited[0]);
            minGes = minGes + Integer.parseInt(splited[2]);
            }else{
                tvAzDienstag.setText("?");
            }
        } else {
            tvAzDienstag.setText("?");
        }
        //Mittwoch

        if (listWochenBericht.get("MITTWOCH") != null && listPos != null) {
            listPos = taetigkeitsberichtUtil.getTaetigkeitsbericht2(listWochenBericht.get("MITTWOCH").getBericht());
            if (listPos != null){
                arbeitsZeitVonBis = getZeitString(listPos);
                arbeitszeit = listWochenBericht.get("MITTWOCH").getArbeitszeit();
                tvAzMittwoch.setText(arbeitsZeitVonBis + "\n" + arbeitszeit);
                splited = arbeitszeit.split("\\s+");
                stdGes = stdGes + Integer.parseInt(splited[0]);
                minGes = minGes + Integer.parseInt(splited[2]);
            }else{
                tvAzMittwoch.setText("?");
            }

        } else {
            tvAzMittwoch.setText("?");
        }
        //Donnerstag
        if (listWochenBericht.get("DONNERSTAG") != null && listPos != null) {
            listPos = taetigkeitsberichtUtil.getTaetigkeitsbericht2(listWochenBericht.get("DONNERSTAG").getBericht());
            if (listPos != null){
                arbeitsZeitVonBis = getZeitString(listPos);
                arbeitszeit = listWochenBericht.get("DONNERSTAG").getArbeitszeit();
                tvAzDonnerstag.setText(arbeitsZeitVonBis + "\n" + arbeitszeit);
                splited = arbeitszeit.split("\\s+");
                stdGes = stdGes + Integer.parseInt(splited[0]);
                minGes = minGes + Integer.parseInt(splited[2]);
            }else{
                tvAzDonnerstag.setText("?");
            }

        } else {
            tvAzDonnerstag.setText("?");
        }
        //Freitag
        if (listWochenBericht.get("FREITAG") != null && listPos != null) {
            listPos = taetigkeitsberichtUtil.getTaetigkeitsbericht2(listWochenBericht.get("FREITAG").getBericht());
            if (listPos != null){
                arbeitsZeitVonBis = getZeitString(listPos);
                arbeitszeit = listWochenBericht.get("FREITAG").getArbeitszeit();
                tvAzFreitag.setText(arbeitsZeitVonBis + "\n" + arbeitszeit);
                splited = arbeitszeit.split("\\s+");
                stdGes = stdGes + Integer.parseInt(splited[0]);
                minGes = minGes + Integer.parseInt(splited[2]);
            }else{
                tvAzFreitag.setText("?");
            }

        } else {
            tvAzFreitag.setText("?");
        }

        //Samstag
        if (listWochenBericht.get("SAMSTAG") != null && listPos != null) {
            listPos = taetigkeitsberichtUtil.getTaetigkeitsbericht2(listWochenBericht.get("SAMSTAG").getBericht());
            if (listPos != null){
                arbeitsZeitVonBis = getZeitString(listPos);
                arbeitszeit = listWochenBericht.get("SAMSTAG").getArbeitszeit();
                if (arbeitszeit!=null){
                    tvAzSamstag.setText(arbeitsZeitVonBis + "\n" + arbeitszeit);
                    splited = arbeitszeit.split("\\s+");
                    stdGes = stdGes + Integer.parseInt(splited[0]);
                    minGes = minGes + Integer.parseInt(splited[2]);
                }else{
                    tvAzSamstag.setText("?");
                    stdGes =0;
                    minGes = 0;
                }

            }else{
                tvAzSamstag.setText("?");
            }

        } else {
            tvAzSamstag.setText("?");
        }

        //Sonntag
        if (listWochenBericht.get("SONNTAG") != null && listPos != null) {
            listPos = taetigkeitsberichtUtil.getTaetigkeitsbericht2(listWochenBericht.get("SONNTAG").getBericht());
            if (listPos != null){
                arbeitsZeitVonBis = getZeitString(listPos);
                arbeitszeit = listWochenBericht.get("SONNTAG").getArbeitszeit();
                tvAzSonntag.setText(arbeitsZeitVonBis + "\n" + arbeitszeit);
                splited = arbeitszeit.split("\\s+");
                stdGes = stdGes + Integer.parseInt(splited[0]);
                minGes = minGes + Integer.parseInt(splited[2]);
            }else{
                tvAzSonntag.setText("?");
            }

        } else {
            tvAzSonntag.setText("?");
        }
        stdGes = stdGes + (minGes / 60);
        minGes = minGes % 60;

        tvAzGesamt.setText("Gesamt: " + String.valueOf(stdGes) + " Std, " + String.valueOf(minGes) + " Min");


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
