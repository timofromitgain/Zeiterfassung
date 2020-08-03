package com.example.timo.Zeiterfassung.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.timo.Zeiterfassung.Beans.Position;
import com.example.timo.Zeiterfassung.Helfer.DatenbankHelfer;
import com.example.timo.Zeiterfassung.Helfer.Datum;
import com.example.timo.Zeiterfassung.Helfer.Kunde;
import com.example.timo.Zeiterfassung.Helfer.LocationService;
import com.example.timo.Zeiterfassung.Helfer.TaetigkeitsberichtUtil;
import com.example.timo.Zeiterfassung.Interface.IFirebase;
import com.example.timo.Zeiterfassung.Interface.ITaetigkeitsbericht;
import com.example.timo.Zeiterfassung.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements Serializable, IFirebase, ITaetigkeitsbericht {
    private static final int CODE = 1;
    public static MainActivity mainActivity;
    ArrayList<Position> listPosition;
    FirebaseAuth firebaseAuth;
    Bundle bundle;
    FirebaseHandler firebaseHandler;
    FirebaseUser user;
    private static ArrayList<Position> listPositionHeute = new ArrayList<Position>();
    private DatabaseReference refAuftrag, refUser, refFirma;
    private String
            dbNameImport,
            dbPfadZiel,
            status,
            nachname,
            vorname,
            dbPfadAktuell = "//data//com.example.timo.Zeiterfassung//databases//Kundenstamm_Neu.dat";
    private Button
            btnNeuerKunde,
            btnKundenliste,
            btnMaps,
            btnTaetigkeitsbericht,
            btnStartTracking,
            btnStopTracking,
            btnLieferstatusZuruecksetzen,
            btnDatenbankSpeichern,
            btnDatenbankImport,
            btnReg,
            btnLog,
            btnLogout;
    private FileChannel quelle = null, ziel = null;
    private File dbZiel, dbQuelle;
    private DatenbankHelfer dbHelfer;
    public static boolean firebaseDataLoaded = false;
    public static boolean istAbgeschlossen;
    public static boolean trackingStartenPresses = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        final Bundle extras = getIntent().getExtras();

        boolean changeUser = false;
        try {
            changeUser = extras.getBoolean("changeUser", false);
            status = extras.getString("status");
        } catch (Exception e) {
            e.printStackTrace();
        }

        dbHelfer = new DatenbankHelfer(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            starteIntent(Registration.class);
            return;
        }

        firebaseHandler = new FirebaseHandler(true);


        user = firebaseAuth.getCurrentUser();
        Log.i("userid ", user.getEmail());
        Toast.makeText(getApplicationContext(), "Hallo " + user.getEmail(), Toast.LENGTH_LONG).show();


        //TESTSZENARIO

        Position posTest = new Position("");
        Calendar c1 =  Calendar.getInstance();
        Date date = new Date();
        date.setHours(11);
        date.setMinutes(44);
        c1.setTime(date);
        Calendar cNew = posTest.getAzeitAbgerundet(c1);
       int hr =  cNew.get(Calendar.HOUR);
       int min = cNew.get(Calendar.MINUTE);

        String ff = "ff";
String g  ="d";

        //    dbHelfer.copyDataFromFirebaseToLocal();

        //  Toast.makeText(MainActivity.this, "Neuen Auftrag erhalten", Toast.LENGTH_SHORT).show();

        //   refAuftrag = FirebaseDatabase.getInstance().getReference().child("auftrag").child(user.getUid());
        //  refUser = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
        // refFirma = FirebaseDatabase.getInstance().getReference().child("auftrag").child("firma");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Toast.makeText(MainActivity.this, "Neuen Auftrag erhalten", Toast.LENGTH_SHORT).show();
                //           Kunde kunde = dataSnapshot.getValue(Kunde.class);
                String gg = "jhi";
                //   Post post = dataSnapshot.getValue(Post.class);
                // ...
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                //  Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };


        ChildEventListener newItemListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("userid", "add kunde");
                Kunde kunde = dataSnapshot.getValue(Kunde.class);
                String key = dataSnapshot.getKey();
                dbHelfer.datensatzEinfuegen(new Kunde(
                                getApplicationContext(),
                                key,
                                kunde.getFirma(),
                                "TEST",
                                kunde.getStrasse(),
                                kunde.getStadt(),
                                null,
                                kunde.getAnmerkung(),
                                50,
                                kunde.getLatitude(),
                                kunde.getLongitude(),
                                0),
                        "Kunde");

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ChildEventListener listenerFirma = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                try {
                    Kunde kunde = dataSnapshot.getValue(Kunde.class);
                    String key = dataSnapshot.getKey();
                    dbHelfer.datensatzEinfuegen(new Kunde(
                                    getApplicationContext(),
                                    key,
                                    kunde.getFirma(),
                                    "TEST",
                                    kunde.getStrasse(),
                                    kunde.getStadt(),
                                    null,
                                    kunde.getAnmerkung(),
                                    50,
                                    kunde.getLatitude(),
                                    kunde.getLongitude(),
                                    0),
                            "Kunde");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ChildEventListener listenerUser = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                /*
                Kunde kunde = dataSnapshot.getValue(Kunde.class);
                String key = dataSnapshot.getKey();
                dbHelfer.datensatzEinfuegen(new Kunde(
                                getApplicationContext(),
                                key,
                                kunde.getFirma(),
                                "TEST",
                                kunde.getStrasse(),
                                kunde.getStadt(),
                                kunde.getTaetigkeit_1(),
                                "",
                                "",
                                kunde.getAnmerkung(),
                                50,
                                kunde.getLatitude(),
                                kunde.getLongitude(),
                                0),
                        "Kunde");
*/
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        //    refAuftrag.addValueEventListener(postListener);
        //  refAuftrag.addChildEventListener(newItemListener);
        //  refUser.addChildEventListener(listenerUser);
        //  refFirma.addChildEventListener(listenerFirma);

        ArrayList<Position> Testliste = new ArrayList<Position>();
        Position posDump = new Position("Kunde");
        Position posDump2 = new Position("Kunde");
        Position posDump3 = new Position("Kunde");
        Position posDump8 = new Position("Sonstiges");
        Position posDump5 = new Position("Kunde");
        Position posDump6 = new Position("Kunde");
        Position posDump7 = new Position("Kunde");
        Kunde kunde = new Kunde();
        kunde.setFirma("Firma a");
        kunde.setStrasse("straße a");
        Kunde kunde2 = new Kunde();
        kunde2.setFirma("Firma b");
        kunde2.setStrasse("straße b");
        Kunde kunde3 = new Kunde();
        kunde3.setFirma("Firma c");
        kunde3.setStrasse("straße b");
        Kunde kunde4 = new Kunde();
        kunde4.setFirma("Firma d");
        kunde4.setStrasse("straße b");
        Kunde kunde5 = new Kunde();
        kunde5.setFirma("Firma y");
        kunde5.setStrasse("straße c");
        Kunde kunde6 = new Kunde();
        kunde6.setFirma("Firma X");
        kunde6.setStrasse("straße d");
        Kunde kunde7 = new Kunde();
        kunde7.setFirma("Firma z");
        kunde7.setStrasse("straße a");
        Date dateAnf = new Date();
        Date dateEnd = new Date();
        Date dateAnf2 = new Date();
        Date dateEnd2 = new Date();
        Date dateAnf3 = new Date();
        Date dateEnd3 = new Date();
        Date dateAnf5 = new Date();
        Date dateEnd5 = new Date();
        Date dateAnf6 = new Date();
        Date dateEnd6 = new Date();
        Calendar calendarAnf = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        Calendar calendarAnf2 = Calendar.getInstance();
        Calendar calendarEnd2 = Calendar.getInstance();
        Calendar calendarAnf3 = Calendar.getInstance();
        Calendar calendarEnd3 = Calendar.getInstance();
        Calendar calendarAnf5 = Calendar.getInstance();
        Calendar calendarEnd5 = Calendar.getInstance();
        Calendar calendarAnf6 = Calendar.getInstance();
        Calendar calendarEnd6 = Calendar.getInstance();

        posDump7.setKunde(kunde7);


        dateAnf6.setHours(11);
        dateAnf6.setMinutes(44);
        dateEnd6.setHours(12);
        dateEnd6.setMinutes(03);
        calendarAnf6.setTime(dateAnf6);
        calendarEnd6.setTime(dateEnd6);
        posDump6.setStartTime(calendarAnf6);
        posDump6.setEndTime(calendarEnd6);
        posDump6.setKunde(kunde6);


        dateAnf.setHours(11);
        dateAnf.setMinutes(23);

        dateEnd.setHours(11);
        dateEnd.setMinutes(44);
        calendarAnf.setTime(dateAnf);
        calendarEnd.setTime(dateEnd);

        posDump.setStartTime(calendarAnf);
        posDump.setEndTime(calendarEnd);
        posDump.setKunde(kunde);


        dateAnf2.setHours(11);
        dateAnf2.setMinutes(44);
        dateEnd2.setHours(12);
        dateEnd2.setMinutes(03);
        calendarAnf2.setTime(dateAnf2);
        calendarEnd2.setTime(dateEnd2);
        posDump2.setStartTime(calendarAnf2);
        posDump2.setEndTime(calendarEnd2);
        posDump2.setKunde(kunde2);


        dateAnf3.setHours(12);
        dateAnf3.setMinutes(15);
        dateEnd3.setHours(15);
        dateEnd3.setMinutes(55);
        calendarAnf3.setTime(dateAnf3);
        calendarEnd3.setTime(dateEnd3);
        posDump3.setStartTime(calendarAnf3);
        posDump3.setEndTime(calendarEnd3);
        posDump3.setKunde(kunde3);


        dateAnf5.setHours(12);
        dateAnf5.setMinutes(15);
        dateEnd5.setHours(15);
        dateEnd5.setMinutes(55);
        calendarAnf5.setTime(dateAnf5);
        calendarEnd5.setTime(dateEnd5);
        posDump5.setStartTime(calendarAnf5);
        posDump5.setEndTime(calendarEnd5);
        posDump5.setKunde(kunde4);


        //    Testliste.add(posDump);
        Testliste.add(posDump2);
        //  Testliste.add(posDump6);
        Testliste.add(posDump8);
        Testliste.add(posDump3);
        Testliste.add(posDump5);
        //   Testliste.add(posDump7);
        //  Testliste.add(posDump6);

        Position dummy = new Position("dummy");
        ArrayList<Position> outputliste = new ArrayList<Position>();
        outputliste = dummy.getListPositonOhneDuplikate(Testliste);

        listPosition = new ArrayList<Position>();
        listPosition = null;


        //Ab API 23 muss die Berechtiung eingeholt werden
        if (Build.VERSION.SDK_INT >= 23) {

            if (!berechtigungDatenSchreiben()) {
                berechtigungEinfordern();
            }


        }
        Double db = 0.00;
        db = Double.valueOf((2) / Double.valueOf(100));
        Log.d("zeittest", "hm :" + String.valueOf(db));
        initialisiere();
        String test = dbHelfer.getWochentag();
        //Time spielerei
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Calendar zeit1 = Calendar.getInstance();
        Calendar zeit2 = Calendar.getInstance();
        Date time = new Date();
        time.setHours(13);
        time.setMinutes(55);
        time.setSeconds(0);
        zeit2.setTime(time);


// textView is the TextView view that should display it
        Log.d("zeittest ", "Aktuelle Zeit ist: " + zeit1.getTime());
        Log.d("zeittest ", "selbst definierte Zeit ist: " + zeit2.getTime());
        Log.d("zeittest ", "Differenz: " + String.valueOf(zeit2.getTime().getMinutes() - zeit1.getTime().getMinutes()));


        btnNeuerKunde.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 starteIntent(Bericht.class);

                                             }
                                         }
        );
        btnKundenliste.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  starteIntent(Auftragsliste.class);

                                              }
                                          }
        );
        btnMaps.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           starteIntent(KarteActivity.class);
                                       }
                                   }
        );


        btnStartTracking.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    trackingStartenPresses = true;
                                                    if (firebaseDataLoaded) {
                                                        startTracking();
                                                    } else {
                                                        //    String tagHeute = "23-05-2020";
                                                        Datum datum = new Datum();
                                                        String tagHeute = datum.getFormatedTagHeute();
                                                        firebaseHandler.getSingleData("taetigkeitsbericht/" + firebaseHandler.getUserId() + "/" + tagHeute);
                                                    }


                                                }
                                            }
        );
        btnStopTracking.setOnClickListener(new View.OnClickListener() {
                                               TaetigkeitsberichtUtil taetigkeitsberichtUtil = new TaetigkeitsberichtUtil();
                                               String taetigkeitsbericht;
                                               Datum datumHeute = new Datum();
                                               String tagHeute = datumHeute.getDatumHeute();
                                               String tagHeuteFormated = tagHeute.replace(".", "-");


                                               @Override
                                               public void onClick(View v) {

                                                   try {
                                                       if (LocationService.getInstance().getAktiviert()) {


                                                           if (listPosition == null) {
                                                               Log.d("zeittest", "pos isSSSt null");
                                                           } else {
                                                               Log.d("zeittest", "pos isSS not null");
                                                           }
                                                           //    if (listPosition == null) {

                                                           listPosition = LocationService.getInstance().getListPosition(true);
                                                           Log.d("listSize", String.valueOf("size: " + listPosition.size()));
                                                           //    }
                                                           //      starteIntent(Taetigkeitsbericht.class, listPosition,false);
                                                           ArrayList<Position> listPostionGesamt = new ArrayList<Position>();
                                                           listPostionGesamt.addAll(listPosition);
                                                           Position posGesamt = new Position("");
                                                           listPosition = posGesamt.getListPositionOhneAusreisser(listPosition);
                                                           Log.d("listSize", String.valueOf("size2: " + listPosition.size()));
                                                           listPosition = taetigkeitsberichtUtil.sortiereListe(listPosition, listPostionGesamt);
                                                           Log.d("listSize", String.valueOf("size3: " + listPosition.size()));
                                                           listPosition = posGesamt.getListPositonOhneDuplikate(listPosition);
                                                           Log.d("listSize", String.valueOf("size4: " + listPosition.size()));
                                                           listPosition = posGesamt.getListPositonOhneDuplikateSonstiges(listPosition);
                                                           Log.d("listSize", String.valueOf("size5: " + listPosition.size()));
                                                           listPosition = posGesamt.getGueltigePositionen(listPosition);
                                                           Log.d("listSize", String.valueOf("size6: " + listPosition.size()));
                                                           String taetigkeitsbericht = taetigkeitsberichtUtil.getTaetigkeitsbericht(listPosition);
                                                           taetigkeitsbericht = taetigkeitsberichtUtil.getTaetigkeitsbericht(listPosition);
                                                           String arbeitszeit = posGesamt.getArbeitszeitGesamt(listPosition, null, null, null, null);
                                                           // tagHeuteFormated = "14-07-2020";
                                                           if (listPosition.size() != 0) {
                                                               firebaseHandler.insert("taetigkeitsbericht/" + firebaseHandler.getUserId() + "/" + tagHeuteFormated + "/bericht", taetigkeitsbericht, mainActivity, false);
                                                               firebaseHandler.insert("taetigkeitsbericht/" + firebaseHandler.getUserId() + "/" + tagHeuteFormated + "/abgeschlossen", false, mainActivity, false);
                                                               firebaseHandler.insert("taetigkeitsbericht/" + firebaseHandler.getUserId() + "/" + tagHeuteFormated + "/arbeitszeit", arbeitszeit, mainActivity, true);
                                                           } else {
                                                               Toast.makeText(MainActivity.this, "Es wurde keine Arbeitszeit erfasst", Toast.LENGTH_LONG).show();
                                                           }

                                                           listPosition.clear();
                                                           Position.setListPosition(listPosition);
                                                           String gg = "iu";
                                                           stopService(new Intent(MainActivity.this, LocationService.class));

                                                       } else {
                                                           Position position = new Position("");
                                                           String a = position.getDauerString();
                                                           Toast.makeText(MainActivity.this, "Es findet zurzeit kein Tracking statt", Toast.LENGTH_SHORT).show();
                                                       }
                                                   } catch (Exception e) {
                                                       Toast.makeText(MainActivity.this, "Ein Fehler ist aufgetreten", Toast.LENGTH_LONG).show();
                                                       e.printStackTrace();
                                                       //   starteIntent(Taetigkeitsbericht.class, listPosition, false);
                                                       if (listPosition.size() != 0) {
                                                           taetigkeitsbericht = taetigkeitsberichtUtil.getTaetigkeitsbericht(listPosition);
                                                           //    tagHeuteFormated = "23-05-2020";
                                                           firebaseHandler.insert("taetigkeitsbericht/" + firebaseHandler.getUserId() + "/" + tagHeuteFormated + "/bericht", taetigkeitsbericht, mainActivity, false);
                                                           firebaseHandler.insert("taetigkeitsbericht/" + firebaseHandler.getUserId() + "/" + tagHeuteFormated + "/abgeschlossen", false, mainActivity, true);
                                                       } else {
                                                           Toast.makeText(MainActivity.this, "Es wurde keine Arbeitszeit erfasst", Toast.LENGTH_LONG).show();
                                                       }

                                                       listPosition.clear();
                                                       Position.setListPosition(listPosition);
                                                       String gg = "iu";
                                                       Log.d("catchfehler", "Fehler: ");

                                                       stopService(new Intent(MainActivity.this, LocationService.class));
                                                   }

                                               }
                                           }
        );
        btnLieferstatusZuruecksetzen.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                                                DatabaseReference refEmail = FirebaseDatabase.getInstance().getReference(user.getUid());
                                                                DatabaseReference refAufrag = refEmail.child("Kunde");
                                                                refEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                        String gg = "dd";
                                                                        Kunde kunde1 = (Kunde) dataSnapshot.getValue();

                                                                        String gg3 = "dd";
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(DatabaseError databaseError) {
                                                                        String gg = "dd";
                                                                    }
                                                                });
                                                            }
                                                        }
        );

        btnDatenbankSpeichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File sdcard = Environment.getExternalStorageDirectory();
                    File intern = Environment.getDataDirectory();
                    dbZiel = new File(sdcard, "neuuz");
                    dbQuelle = new File(intern, dbPfadAktuell);
                    quelle = new FileInputStream(dbQuelle).getChannel();
                    ziel = new FileOutputStream(dbZiel).getChannel();
                    ziel.transferFrom(quelle, 0, quelle.size());
                    quelle.close();
                    ziel.close();
                    Toast.makeText(MainActivity.this, "passt", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnTaetigkeitsbericht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trackingStartenPresses = false;
                //      String tagHeute = "23-05-2020";

                if (LocationService.isLocation()) {
                    listPosition = LocationService.getInstance().getListPosition(false);
                    Log.d("changeevent", "jauu " + String.valueOf(listPosition.size()));
                    starteIntent(Taetigkeitsbericht.class, listPosition, false);
                } else {
                    if (firebaseDataLoaded) {
                        startTaetigkeitsbericht();
                    } else {
                        //  String tagHeute = "23-05-2020";
                        Datum datum = new Datum();
                        String tagHeute = datum.getFormatedTagHeute();
                        firebaseHandler.getSingleData("taetigkeitsbericht/" + firebaseHandler.getUserId() + "/" + tagHeute);
                    }
                }




           /*
                try {
                    if (LocationService.getInstance().getAktiviert()) {


                        try {
                            listPosition.clear();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            listPosition = LocationService.getInstance().getListPosition(false);
                            starteIntent(Taetigkeitsbericht.class, listPosition, true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        ArrayList<Position> listDummy = new ArrayList<Position>();
                        starteIntent(Taetigkeitsbericht.class, listPosition, true);
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Ein Fehler ist aufgetreten", Toast.LENGTH_LONG).show();
                    starteIntent(Taetigkeitsbericht.class, listPosition, true);
                    listPosition.clear();
                    String gg = "iu";

                    //     stopService(new Intent(MainActivity.this, LocationService.class));
                }
                */
            }
        });

        btnDatenbankImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Position> listPosition = new ArrayList<Position>();
                Gson gson = new Gson();
                Position dumpPos = new Position("Firma");
                Kunde k = new Kunde();
                k.setFirma("fkfof");
                Calendar calendar = Calendar.getInstance();
                dumpPos.setStartTime(calendar);
                listPosition.add(dumpPos);
                listPosition.add(dumpPos);
                String jsonArray = gson.toJson(listPosition);
                //    dbHelfer.datensatzEinfuegen(null,"BERICHT",jsonArray);
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                starteIntent(Testactivity.class);
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                starteIntent(Registration.class);
            }
        });
        try {
            checkDb();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void statusZuruecksetzen() {
        int kundeId;

        int anzahlAllerKunden = (int) dbHelfer.ermittleAnzahlKunden(true);
        for (int i = 1; i <= anzahlAllerKunden; i++) {
            dbHelfer.update(i, "1", "Status");
        }

    }

    /* Prüfung der Berechtigung übernommen von
        https://stackoverflow.com/questions/33162152/storage-permission-error-in-marshmallow
     */
    private boolean berechtigungDatenSchreiben() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void berechtigungEinfordern() {

        if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE);
        }
    }


    private void initialisiere() {
        btnNeuerKunde = findViewById(R.id.btnNeuerKunde_MainActivity);
        btnKundenliste = findViewById(R.id.btnKundenliste_MainActivity);
        btnMaps = findViewById(R.id.btnMaps_MainActivity);
        btnStartTracking = findViewById(R.id.btnTrackingOn_MainActivity);
        btnStopTracking = findViewById(R.id.btnTrackingOff_MainActivity);
        btnDatenbankSpeichern = findViewById(R.id.btnDatenbankSpeichern_MainActivity);
        btnDatenbankImport = findViewById(R.id.btnDatenbankImport_MainActivity);
        btnLieferstatusZuruecksetzen = findViewById(R.id.btnLieferstatusZuruecksetzen_MainActivity);
        btnLieferstatusZuruecksetzen.setVisibility(View.VISIBLE);
        btnTaetigkeitsbericht = findViewById(R.id.btnTaetigkeitsbericht);
        btnReg = findViewById(R.id.btnRegistrieren);
        btnLog = findViewById(R.id.btnLogin);
        btnLogout = findViewById(R.id.btnLogout);
        btnDatenbankImport.setVisibility(View.VISIBLE);
        btnDatenbankSpeichern.setVisibility(View.VISIBLE);


        try {
            String sql;
            boolean leereTabelle;
            ArrayList<String> tabelle = new ArrayList<String>();
            sql = "CREATE TABLE KUNDE (" +
                    "KnId VARCHAR(255) PRIMARY KEY DESC, " +
                    "Firma VARCHAR(255) NOT NULL, " +
                    "Ansprechpartner VARCHAR(255) NOT NULL, " +
                    "Strasse VARCHAR(255) NOT NULL, " +
                    "Stadt VARCHAR(255) NOT NULL, " +
                    "Taetigkeit1 VARCHAR(255) NOT NULL, " +
                    "Taetigkeit2 VARCHAR(255), " +
                    "Taetigkeit3 VARCHAR(255), " +
                    "Anmerkung VARCHAR(255)," +
                    "Status INTEGER NOT NULL," +
                    "Radius INTEGER NOT NULL," +
                    "Latitude FLOAT NOT NULL, " +
                    "Longitude FLOAT NOT NULL)";
            tabelle.add(sql);

            sql = "CREATE TABLE BERICHT (" +
                    "KnId INTEGER, " +
                    "KWOCHE INTEGER, " +
                    "MONTAG TEXT, " +
                    "DIENSTAG TEXT, " +
                    "MITTWOCH TEXT, " +
                    "DONNERSTAG TEXT, " +
                    "FREITAG TEXT, " +
                    "SAMSTAG TEXT) ";
            tabelle.add(sql);

            sql = "CREATE TABLE Benutzer (" +
                    "BenutzerId String NOT NULL, Status VARCHAR(25) NOT NULL, Nachname VARCHAR(255), Vorname VARCHAR(255))";

            tabelle.add(sql);


            dbHelfer = new DatenbankHelfer(this, "Kundenstamm_Neu.dat", tabelle);
            leereTabelle = dbHelfer.ermittleAnzahlKunden(false) == 0;
            if (leereTabelle) {
                Kunde kunde = new Kunde(getApplicationContext(),
                        "1",
                        "Firma",
                        "Harbecke",
                        "Hanseatenstraße 41",
                        "Langenhagen",
                        null,
                        "",
                        75,
                        52.454922,
                        9.733890,
                        0);


                //         dbHelfer.datensatzEinfuegen(kunde, "Kunde");

                //       firebaseHandler.insert("user/"+firebaseHandler.getUserId(),kunde);
                Log.d("Firemire", "Benutzer anlegen");

                //   dbHelfer.datensatzEinfuegenFirebase(firebaseAuth.getCurrentUser().getUid(),status);
            }
            leereTabelle = dbHelfer.ermittleAnzahlDatensaetze() == 0;
            if (leereTabelle) {
                dbHelfer.datensatzEinfuegen(null, "BERICHT");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void starteIntent(Class klasse) {
        Intent intent = new Intent(this, klasse);
        startActivity(intent);
    }

    private void starteIntent(Class klasse, ArrayList<Position> listPosition, Boolean berichtHeute) {
        Intent intent = new Intent(this, klasse);
        intent.putExtra("listPosition", listPosition);
        intent.putExtra("berichtHeute", berichtHeute);

        startActivity(intent);
    }

    private void checkDb() {

        Calendar dummyCalendar = Calendar.getInstance();
        int aktuelleWoche = dummyCalendar.get(Calendar.WEEK_OF_YEAR);
        int wocheDb = Integer.parseInt(dbHelfer.getBerichtWochentag("KWOCHE"));
        if (aktuelleWoche != wocheDb) {
            dbHelfer.update(1, String.valueOf(aktuelleWoche), "KWOCHE");
            dbHelfer.update(1, "MONTAG", "MONTAG");
            dbHelfer.update(1, "DIENSTAG", "DIENSTAG");
            dbHelfer.update(1, "MITTWOCH", "MITTWOCH");
            dbHelfer.update(1, "DONNERSTAG", "DONNERSTAG");
            dbHelfer.update(1, "FREITAG", "FREITAG");
            dbHelfer.update(1, "SAMSTAG", "SAMSTAG");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.beliefert_menu, menu);
        return true;
    }

    @Override
    public void neuerKunde(HashMap<String, Kunde> listKunde) {
        Log.i("555nase", "message from firebase");
    }

    @Override
    public void neuerKunde(String key, Kunde kunde) {
        Log.i("listenerfb", "message from firebase, neuer Kunde");
    }

    @Override
    public void loescheKunde(String key) {

    }

    @Override
    public void onGetTaetigkeitsberichtData(TaetigkeitsberichtUtil taetigkeitsberichtUtil) {
        firebaseDataLoaded = true;
        if (taetigkeitsberichtUtil == null) {
            istAbgeschlossen = true;
            listPositionHeute = null;
        } else {
            istAbgeschlossen = taetigkeitsberichtUtil != null && taetigkeitsberichtUtil.getAbgeschlossen();
            if (taetigkeitsberichtUtil != null) {
                listPositionHeute = taetigkeitsberichtUtil.getTaetigkeitsbericht2(taetigkeitsberichtUtil.getBericht());
            }

        }

        if (trackingStartenPresses) {
            startTracking();
        } else {
            startTaetigkeitsbericht();
        }
    }

    @Override
    public void onSendTaetigkeitsbericht() {
        istAbgeschlossen = true;
        firebaseDataLoaded = false;
    }

    @Override
    public void onRemoveTaetigkeitsbericht() {
        istAbgeschlossen = false;
        firebaseDataLoaded = false;

    }

    @Override
    public void onDayOfTaetigkeitsberichtChange(TaetigkeitsberichtUtil taetigkeitsberichtUtil) {

    }

    @Override
    public void onGetWochenbericht(HashMap<String, TaetigkeitsberichtUtil> listWochenBericht) {

    }

    private void startTracking() {
        if (listPositionHeute == null) {

            if (!LocationService.getInstance().getAktiviert()) {
                istAbgeschlossen = false;
                listPosition = null;
                statusZuruecksetzen();
                String meldungTrackingGestartet = getString(R.string.meldungTrackingGestartet);
                Toast.makeText(MainActivity.this, meldungTrackingGestartet, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LocationService.class);
                ContextCompat.startForegroundService(MainActivity.this, intent);
                LocationService.getInstance().setAktiviert(true);

            } else {
                Toast.makeText(MainActivity.this, "Tracking läuft bereits", Toast.LENGTH_SHORT).show();
            }
        } else {


            if (istAbgeschlossen) {
                Toast.makeText(getApplicationContext(), "Tracking kann heute nicht mehr gestartet werden", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Bericht bereits vorhanden", Toast.LENGTH_LONG).show();
                firebaseDataLoaded = true;
            }
        }
/*
        if (istAbgeschlossen) {

                if (!LocationService.getInstance().getAktiviert()) {
                    istAbgeschlossen = false;
                    listPosition = null;
                    statusZuruecksetzen();
                    String meldungTrackingGestartet = getString(R.string.meldungTrackingGestartet);
                    Toast.makeText(MainActivity.this, meldungTrackingGestartet, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LocationService.class);
                    ContextCompat.startForegroundService(MainActivity.this, intent);
                    LocationService.getInstance().setAktiviert(true);

                } else {
                    Toast.makeText(MainActivity.this, "Tracking läuft bereits", Toast.LENGTH_SHORT).show();
                }

        } else {
         //   listPositionHeute = taetigkeitsberichtUtil.getTaetigkeitsbericht2(taetigkeitsberichtUtil.getBericht());
            Toast.makeText(getApplicationContext(), "Bericht bereits vorhanden", Toast.LENGTH_LONG).show();
            firebaseDataLoaded = true;
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startTaetigkeitsbericht() {
        if (istAbgeschlossen) {
            starteIntent(Taetigkeitsbericht.class, null, false);
        } else {
            starteIntent(Taetigkeitsbericht.class, listPositionHeute, true);
        }

    }
}


