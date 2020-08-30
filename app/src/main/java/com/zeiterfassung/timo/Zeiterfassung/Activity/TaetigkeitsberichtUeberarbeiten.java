package com.zeiterfassung.timo.Zeiterfassung.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zeiterfassung.timo.Zeiterfassung.Beans.Position;
import com.zeiterfassung.timo.Zeiterfassung.Dialog.DialogZeit;
import com.zeiterfassung.timo.Zeiterfassung.Interface.IdialogZeit;
import com.zeiterfassung.timo.Zeiterfassung.Interface.Iposition;
import com.zeiterfassung.timo.Zeiterfassung.R;

import java.util.Calendar;


public class TaetigkeitsberichtUeberarbeiten extends AppCompatActivity implements IdialogZeit {
EditText editFirma,editAnsprechpartner, editStrasse,editStadt,editTaetigkeit;
TextView tvUhrzeitBeginn,tvUhrzeitEnde;
Button btUebernehmen;
ImageButton btnStart,btnEnde;
Intent intent;
Boolean startTime = false;
    public static Binder binder;
    private Iposition listenerPosition;
    Position position;
    Position positionNeu;




    @Override
    public void onBackPressed() {
  /*      position.getStartTime().set(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,12,22,22);
        position.getEndTime().set(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,22,22,22);
        //  Position position1 = new Position("Kunde");
        position.getKunde().setFirma("");
        position.getKunde().setAnsprechpartner("");
        position.getKunde().setStrasse("");
        position.getKunde().setStadt("");
        finish();
        Intent intent2 = new Intent();
        intent2.putExtra("Position",position);
        intent2.putExtra("test","blub");
        setResult(RESULT_OK,intent2);
        */
     super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle extras = getIntent().getExtras();
         intent = this.getIntent();
         binder = new Binder();
        Bundle bundle = intent.getExtras();
        setContentView(R.layout.activity_taetigkeitsbericht_ueberarbeiten);
         position = (Position) bundle.getSerializable("Position");
         positionNeu = new Position(position.getNamePosition());

         btnStart = findViewById(R.id.imtBtnAnfangEdit);
         btnEnde = findViewById(R.id.imtBtnEndeEdit);
        tvUhrzeitBeginn = findViewById(R.id.tvUhrzeitBeginn);
        tvUhrzeitEnde = findViewById(R.id.tvUhrzeitEnde);



        editFirma = findViewById(R.id.TeditFirma);
        editAnsprechpartner = findViewById(R.id.TeditAnsprechpartner);
        editStrasse = findViewById(R.id.TeditStraße);
        editStadt = findViewById(R.id.TeditStadt);
        editTaetigkeit = findViewById(R.id.TeditTaetigkeit);
        if (position.getKunde() != null){
        editFirma.setText(position.getKunde().getFirma());
        editAnsprechpartner.setText(position.getKunde().getAnsprechpartner());
        editStadt.setText(position.getKunde().getStadt());
        editStrasse.setText(position.getKunde().getStrasse());
        editTaetigkeit.setText(position.getKunde().getAuswahlTaetigkeit());
         }else{
             editFirma.setVisibility(View.GONE);
             editTaetigkeit.setVisibility(View.GONE);
             editAnsprechpartner.setVisibility(View.GONE);
             editStadt.setVisibility(View.GONE);
             editStrasse.setVisibility(View.GONE);
         }
        tvUhrzeitBeginn.setText("Beginn: " + position.getArbeitsZeitBeginn());
        tvUhrzeitEnde.setText("Ende: " + position.getArbeitsZeitEnde());

        btUebernehmen = findViewById(R.id.btnUebernehmen);

        btUebernehmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firma,ansprechpartner,strasse,stadt,strUhrzeitBegin,strUhrzeitEnde,taetigkeit;
                int stdBeginn,minBeginn,sekBeginn,stdEnde,minEnde,sekEnde;
                Calendar uhrzeitStart,uhrzeitEnde;
              if (position.getKunde() != null){
                  firma = editFirma.getEditableText().toString();
                  ansprechpartner = editAnsprechpartner.getEditableText().toString();
                  strasse = editStrasse.getEditableText().toString();
                  stadt = editStadt.getEditableText().toString();
                  taetigkeit = editTaetigkeit.getEditableText().toString();
                  if (firma.isEmpty() || strasse.isEmpty() || stadt.isEmpty() || taetigkeit.isEmpty()) {
                      if (firma.isEmpty()) {
                          editFirma.setBackgroundResource(R.drawable.edittext_fehler);
                      } else {
                          editFirma.setBackgroundResource(R.drawable.edittext_normal);
                      }
                      if (strasse.isEmpty()) {
                          editStrasse.setBackgroundResource(R.drawable.edittext_fehler);
                      } else {
                          editStrasse.setBackgroundResource(R.drawable.edittext_normal);
                      }
                      if (stadt.isEmpty()) {
                          editStadt.setBackgroundResource(R.drawable.edittext_fehler);
                      } else {
                          editStadt.setBackgroundResource(R.drawable.edittext_normal);
                      }
                      if (taetigkeit.isEmpty()) {
                          editTaetigkeit.setBackgroundResource(R.drawable.edittext_fehler);
                      } else {
                          editTaetigkeit.setBackgroundResource(R.drawable.edittext_normal);
                      }
                      firma = "";
                      strasse = "";
                      stadt = "";
                      taetigkeit = "";
                      String medlungFehlerEingabe = getString(R.string.meldungFehlerBeiDerEingabe);
                      Toast.makeText(getApplicationContext(), medlungFehlerEingabe, Toast.LENGTH_SHORT).show();
                      return;
                  }



                  position.getKunde().setAuswahlTaetigkeit(taetigkeit);
                  position.getKunde().setFirma(firma);
                  position.getKunde().setAnsprechpartner(ansprechpartner);
                  position.getKunde().setStrasse(strasse);
                  position.getKunde().setStadt(stadt);
              }

                strUhrzeitBegin = tvUhrzeitBeginn.getEditableText().toString();
                strUhrzeitEnde = tvUhrzeitEnde.getEditableText().toString();
                String test =strUhrzeitBegin.substring(8,10);

                stdBeginn = Integer.parseInt(strUhrzeitBegin.substring(8,10));
                minBeginn = Integer.parseInt(strUhrzeitBegin.substring(11,13));
//                sekBeginn = Integer.parseInt(strUhrzeitBegin.substring(14,16));
                stdEnde = Integer.parseInt(strUhrzeitEnde.substring(6,8));
                minEnde = Integer.parseInt(strUhrzeitEnde.substring(9,11));
  //              sekEnde = Integer.parseInt(strUhrzeitEnde.substring(12,14));

           //     position.getStartTime().set(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,stdBeginn,minBeginn);
             //   position.getEndTime().set(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,stdEnde,minEnde);
              //  Position position1 = new Position("Kunde");

               // finish();
                Intent intent2 = new Intent("rPosition");
                intent2.putExtra("Position",position);
                LocalBroadcastManager.getInstance(binder.getService()).sendBroadcast(intent2);
             //   setResult(RESULT_OK,intent2);
                finish();
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = true;
                DialogZeit dialogZeit = new DialogZeit();
                dialogZeit.show(getSupportFragmentManager(), "dia");
            }
        });

        btnEnde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = false;
                DialogZeit dialogZeit = new DialogZeit();
                dialogZeit.show(getSupportFragmentManager(), "dia");
            }
        });

    }


    @Override
    public void listenerZeit(Calendar calendar) {
        if (startTime){
            position.setStartTime(calendar);
            tvUhrzeitBeginn.setText("Beginn: " + position.getArbeitsZeitBeginn());
        }else{
            position.setEndTime(calendar);
            tvUhrzeitEnde.setText("Ende: " + position.getArbeitsZeitEnde());
        }
    }
    public class Binder extends android.os.Binder {
        public TaetigkeitsberichtUeberarbeiten getService() {
            return TaetigkeitsberichtUeberarbeiten.this;
        }
    }

}
