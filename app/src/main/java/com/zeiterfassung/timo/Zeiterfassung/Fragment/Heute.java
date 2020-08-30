package com.zeiterfassung.timo.Zeiterfassung.Fragment;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.zeiterfassung.timo.Zeiterfassung.Activity.FirebaseHandler;
import com.zeiterfassung.timo.Zeiterfassung.Activity.MainActivity;
import com.zeiterfassung.timo.Zeiterfassung.Activity.Taetigkeitsbericht;
import com.zeiterfassung.timo.Zeiterfassung.Beans.Position;
import com.zeiterfassung.timo.Zeiterfassung.Helfer.DatenbankHelfer;
import com.zeiterfassung.timo.Zeiterfassung.Helfer.Datum;
import com.zeiterfassung.timo.Zeiterfassung.Helfer.Dummy;
import com.zeiterfassung.timo.Zeiterfassung.Helfer.Kunde;
import com.zeiterfassung.timo.Zeiterfassung.Helfer.ListViewPositionAdapter;
import com.zeiterfassung.timo.Zeiterfassung.Helfer.ListViewPositionSonstiges;
import com.zeiterfassung.timo.Zeiterfassung.Interface.IBericht;
import com.zeiterfassung.timo.Zeiterfassung.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;



public class Heute extends Fragment implements IBericht {
    private View view;
    boolean taetigkeitsberichtNichtAbgeschlossen;
    int adapterPosition;

    MainActivity mainActivity = MainActivity.mainActivity;
    String wochentag;
    ListView lv, lv2;
    Spinner spinner;
    TextView tvArbeitszeit, tvPause;
    ListViewPositionAdapter adapter;
    ListViewPositionSonstiges adapterSonstges;
    ArrayList<Kunde> listAlleKunden;
    ArrayList<Position> listPositionGesamt;
    ArrayList<Position> listPosition;
    ArrayList<Position> listPositionGefiltert;
    ArrayList<Position> listPositionGueltig;
    ArrayList<Position> listPositionSonstiges;
    ArrayList<Position> listPositionWochentag;
    ArrayList<Position> listPositionAktuell;
    ArrayList<Position> listPositionOhneDuplikate;
    ArrayList<Position> listPos2;
    ArrayList<ArrayList<Position>> listSplit;
    String arbeitszeit;
    boolean isTaetigkeitsbericht;
    int posClickId;
    Date dPauseBeginn, dPauseEnde, dPauseBegin2, dPauseEnde2;
    DatenbankHelfer dbHelfer;
    FirebaseHandler firebaseHandler;
    public static Taetigkeitsbericht taetigkeitsbericht;
    private NavigationView bottomNavigationView;
    Button btnSenden;
    private Boolean berichtHeute = false;
   public static Heute heute;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_heute, container, false);
        dbHelfer = new DatenbankHelfer(getContext());
        firebaseHandler = new FirebaseHandler();


        listPosition = new ArrayList<Position>();

        lv = view.findViewById(R.id.lvTaetigkeitsbericht);
        btnSenden = (Button) view.findViewById(R.id.btnsendn);
        listPositionAktuell = new ArrayList<Position>();
        //    listAlleKunden = new ArrayList<Kunde>();
        //      listAlleKunden = dbHelfer.getListKunde();
        adapter = new ListViewPositionAdapter(getActivity(), R.layout.item_position, listPosition);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);


        final Position posGesamt = new Position("");

        tvArbeitszeit = view.findViewById(R.id.tvArbeitszeit);


        tvPause = view.findViewById(R.id.tvPause);
        listPos2 = new ArrayList<Position>();
        listSplit = new ArrayList<ArrayList<Position>>();
        //DUMMY
        Dummy dummy = new Dummy();


            //ARBEITSZEIT
          //  String arbeitszeit = posGesamt.getArbeitszeitGesamt(listPosition, dPauseBeginn, dPauseEnde, dPauseBegin2, dPauseEnde2);






        //   tvPause.setText("Pause: 09:00 Uhr - 09:15 Uhr\n12:00 Uhr - 12:30");


        btnSenden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Position position = new Position("");
                Boolean istZeitüberschneidung = position.istZeitüberschneidung(listPosition);
                istZeitüberschneidung = false;

                if (istZeitüberschneidung) {
                } else {
                    String wochentag = dbHelfer.getWochentag();
                    setNewTaetigkeitsbericht();

                }

            }


        });
        heute = this;
        Taetigkeitsbericht taetigkeitsbericht = Taetigkeitsbericht.taetigkeitsbericht;
        taetigkeitsbericht.onFragmetHeuteCreated(heute);
        return view;
    }

    private void testF(View view) {

        Position position = new Position("");
        Boolean zeitUeberschneidung = position.istZeitüberschneidung(listPosition);
    }

    private void setNewTaetigkeitsbericht() {
        Gson gson = new Gson();
        Datum datumHeute = new Datum();
        String tagHeute = datumHeute.getDatumHeute();
        tagHeute = tagHeute.replace(".", "-");
        //   tagHeute = "27-04-2020";
        String jsonArray = gson.toJson(listPosition);
        firebaseHandler.insert("taetigkeitsbericht/" + firebaseHandler.getUserId() + "/" + tagHeute, jsonArray);

    }


    @Override
    public void onListPosFiltered(ArrayList<Position> listPositionNeu) {
        if (listPositionNeu != null && listPositionNeu.size() >0){
            //Log.d("listenerPos","onListPosFiltered");
            listPosition.addAll(listPositionNeu);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onCalculateArbeitszeit(String str) {
tvArbeitszeit.setText(str + " (abzgl. 45 Min Pausenzeit)");

    }

    @Override
    public void onFragmetHeuteCreated(Heute contextFragmentHeute) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

}
