package com.example.timo.Zeiterfassung.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.timo.Zeiterfassung.Beans.Position;
import com.example.timo.Zeiterfassung.Helfer.ListViewPositionAdapter;
import com.example.timo.Zeiterfassung.R;

import java.util.ArrayList;

public class BerichtWoche extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_heute);


        final Bundle extras = getIntent().getExtras();
        ArrayList<Position> listPosition = listPosition = (ArrayList<Position>) extras.getSerializable("listPosition");
        String arbeitszeit = extras.getString("arbeitszeit");
        ListView lv = findViewById(R.id.lvTaetigkeitsbericht);
        TextView tvArbeitszeit = findViewById(R.id.tvArbeitszeit);
       tvArbeitszeit.setText("Arbeitszeit: " + arbeitszeit);

        ListViewPositionAdapter adapter = new ListViewPositionAdapter(getApplicationContext(), R.layout.item_position, listPosition);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);
    }
}
