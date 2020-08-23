package com.example.timo.Zeiterfassung.Activity;

import android.Manifest;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.timo.Zeiterfassung.Beans.Position;
import com.example.timo.Zeiterfassung.Dialog.DialogOptimierung;
import com.example.timo.Zeiterfassung.Helfer.DatenbankHelfer;
import com.example.timo.Zeiterfassung.Helfer.Geo;
import com.example.timo.Zeiterfassung.Helfer.Kunde;
import com.example.timo.Zeiterfassung.Helfer.ListViewKundenAdapter;
import com.example.timo.Zeiterfassung.Helfer.LocationService;
import com.example.timo.Zeiterfassung.Interface.IFirebase;
import com.example.timo.Zeiterfassung.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 10/2/2017.
 */

public class KarteActivity extends AppCompatActivity implements OnMapReadyCallback, IFirebase,


        SearchView.OnQueryTextListener,
        TextWatcher {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final int REQUEST_PERMISSION_ACCESS_FINE_LOCATION = 123;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    public static KarteActivity karteActivity;
    public static Position position;

    public static HashMap<String, Integer> locIntervalls = new HashMap<>();
    public static HashMap<String, Integer> locIntervallsNewApi = new HashMap<>();
    ArrayList<LatLng> listGeo2 = new ArrayList<>();
    Polyline polyline = null;
    Polyline polyline2 = null;
    ListView lvKunden;
    FirebaseHandler firebaseHandler;
    ListViewKundenAdapter listViewKundenAdapter;
    View frMap;
    private GoogleMap googleMap;
    private DatenbankHelfer dbHelfer;
    private Binder binder;
    private Button btErledigtKreis,
            btReihenfolgeSpeichern,
            btReihenfolgeVerwerfen,
            btPolygon,
            btPolygonVerwerfen,
            btPolylineVerbinden,
            btPolygonSpeichern,
            btPolylineZurueck,
            btRadiuskreisSetzen,
            btPolygonNeu,
            btPolygonErledigt,
            btRadius,
            btReihenfolgeZurueck,
            btRadiusSpeichern;
    private Geo geo;
    private TableRow
            tr2,
            tr3,
            tr4,
            tr5,
            tr6;
    private SeekBar seekBar;
    private ListViewKundenAdapter adapterKunden;
    private ListView
            lvNummern,
            lvSuchvorschlaege;
    private SearchView svSuche;
    private String markerIDString;
    private Integer markerID,
            kundeRadiusAlt,
            radiusUmfang,
            optimierungZaehler = 0,
            anzahlPolygon = 0;
    private Double latitude_Kunde,
            longitude_Kunde,
            lati,
            longi;
    private Boolean kundeAbgang,
            kundeHattePolygon = false,
            adapterSucherIstInitialisert = false,
            zeichnePolygon = false,
            optimierungsProzess = false,
            activityBeenden = false;
    private TextView tvRadiusGrösse;
    private PolygonOptions polygonOption = new PolygonOptions();
    private ArrayList<LatLng> listGeo = new ArrayList<LatLng>();
    private ArrayList<String> listNummernReihenfolge = new ArrayList<String>();
    private ArrayList<Polyline> listPoly = new ArrayList<Polyline>();
    private ArrayList<Polyline> listPoly2 = new ArrayList<Polyline>();
    private ArrayList<Kunde> kundeListOhneAbgaenge = new ArrayList<Kunde>();
    private ArrayList<Kunde> listKundeSuche = new ArrayList<Kunde>();
    private Map<String, Kunde> listKunde = new HashMap<>();
    private ArrayList<LatLng> latLngPolyline = new ArrayList<LatLng>();
    private ArrayList<Circle> listKreis = new ArrayList<Circle>();
    // private ArrayList<Marker> listMarker = new ArrayList<Marker>();
    private Map<String, Marker> listMarker = new HashMap<>();
    private ArrayList<Polyline> listPolyline = new ArrayList<Polyline>();
    private HashMap<Integer, PolygonOptions> hashPolygonOptions = new HashMap<Integer, PolygonOptions>();
    private HashMap<Integer, Polygon> hashPolygon = new HashMap<Integer, Polygon>();
    private HashMap<String, Circle> hashKreis = new HashMap<String, Circle>();
    //Nachricht über belieferten Kunden durch den Location Service
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                //   Integer nachricht = intent.getIntExtra("markernr", 0);
                String uid = intent.getStringExtra("markernr");
                Boolean kundeHatKreis = intent.getBooleanExtra("kreis", true);
                Integer status = intent.getIntExtra("status", -1);
                Marker marker = listMarker.get((uid));
                int color;
                int farbeauswahl;
                if (status == 2) {
                    color = Color.GREEN;
                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else if (status == 1) {
                    color = Color.RED;
                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                } else {
                    color = Color.YELLOW;
                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                }

                listKunde.get(uid).setStatus(status);

                Circle kreis = hashKreis.get(uid);
                kreis.setStrokeColor(color);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };
    private ArrayList<Kunde> listKundeReihenfolge = new ArrayList<Kunde>();
    private ArrayList<Kunde> listKundeReihenfolgeKopie = new ArrayList<Kunde>();
    private ArrayList<PolygonOptions> polygonOptions = new ArrayList<PolygonOptions>();
    private ArrayList<PolygonOptions> polygonOptionList = new ArrayList<PolygonOptions>();
    private ArrayList<Polygon> PolygonListLoeschenTest = new ArrayList<Polygon>();
    private List<Kunde> listKundeFilter = new ArrayList<>();

    public static KarteActivity getInstance() {
        if (karteActivity == null) {
            karteActivity = new KarteActivity();
        }
        return karteActivity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.timo.Zeiterfassung.R.layout.activity_map);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getLocationBerechtigung();
        initialisiereKarte();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(com.example.timo.Zeiterfassung.R.menu.mapmenu, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        final SearchManager manager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        searchView.findViewById((android.support.v7.appcompat.R.id.search_src_text));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case com.example.timo.Zeiterfassung.R.id.itNaigation:
                lvKunden.setVisibility(View.VISIBLE);
                return true;
            case com.example.timo.Zeiterfassung.R.id.itRoutenoptimierung:
                Boolean serviceAktiviert = LocationService.getInstance().getAktiviert();
                DialogOptimierung dialogOptimierung = new DialogOptimierung();
                dialogOptimierung.show(getSupportFragmentManager(), "dialog");
                return true;
            case android.R.id.home:
                finish();
                return true;

            case R.id.itVerlauf:
                serviceAktiviert = LocationService.getInstance().getAktiviert();

                if (serviceAktiviert) {
                    LocationService locationService = new LocationService();


                    listGeo = locationService.getListGeo();
                    listGeo2 = LocationUpdatesService.listGeo;
                    locIntervalls = locationService.getListIntervalls();
                    locIntervallsNewApi = LocationUpdatesService.locIntervalls;

/*
                    DialogLocation dialogLocation = new DialogLocation();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("oldApi", locIntervalls);
                    bundle.putSerializable("newApi", locIntervallsNewApi);
                    dialogLocation.setArguments(bundle);
                    dialogLocation.show(getSupportFragmentManager(), "dialog");
                    //          Toast.makeText(KarteActivity.this, intervalls, Toast.LENGTH_SHORT).show();*/


                    zeigeStreckenverlauf(listGeo, getResources().getColor(R.color.gelb));
                   // zeigeStreckenverlauf2(listGeo2, getResources().getColor(R.color.rot));

                }

                return true;
            case com.example.timo.Zeiterfassung.R.id.itSubKarte:
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case com.example.timo.Zeiterfassung.R.id.itSubSatelite:
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Beim Drücken des Zurück-Buttons
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            activityBeenden = true;
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        int anzahlKunden;
        this.googleMap = googleMap;
        firebaseHandler = new FirebaseHandler();
        UiSettings mapUiSettings = googleMap.getUiSettings();
        mapUiSettings.setZoomControlsEnabled(true);
        binder = new Binder();
        dbHelfer = new DatenbankHelfer(this);
//        listKundeSuche = dbHelfer.getListKunde();
        listKunde = FirebaseHandler.listKunde;

        karteActivity = this;
        geo = new Geo(this);
        // anzahlKunden = (int) dbHelfer.ermittleAnzahlKunden(true);
        initialisiereXML();
        initialisiere();
        latLngPolyline.clear();

        //HIER
        // listViewKundenAdapter = new ListViewKundenAdapter(getApplicationContext(), R.layout.item_kunde, listKunde,true);
        lvKunden.setAdapter(listViewKundenAdapter);
        registerForContextMenu(lvKunden);


        try {
            //Berechtigung erteilt
            googleMap.setMyLocationEnabled(true);
        } catch (Exception e) {
            //Berechtigungen fehlen
            e.printStackTrace();
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
                new IntentFilter("kundeBeliefert"));



        lvSuchvorschlaege.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                double latitude = listKundeSuche.get(position).getLatitude();
                double longitude = listKundeSuche.get(position).getLongitude();
                lvSuchvorschlaege.setVisibility(View.GONE);

                //Fokus auf den gewählten Kunden setzen
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
                        longitude), 16.0f));

            }
        });

        lvKunden.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                double latitude = listKunde.get(position).getLatitude();
                double longitude = listKunde.get(position).getLongitude();

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
                        longitude), 18.0f));
                lvKunden.setVisibility(View.GONE);
            }
        });


        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng position) {
                // TODO Auto-generated method stub
                lvKunden.setVisibility(View.GONE);

                LatLng x;
                LatLng y;
                Marker marker;

                try {
                    Marker markerKunde = listMarker.get(markerID);
                    markerKunde.setDraggable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setZeilenUnsichtbar();

            }
        });


        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Boolean kundeBeliefert = false;
                int anzahAbgaenge = 0;
                int idLaenge;
                marker.showInfoWindow();
                String tit = marker.getTitle();
                /*
                int anzahlAllerKunden = (int) dbHelfer.ermittleAnzahlKunden(true);
                Marker markerKunde = null;
                if (markerID != null) {
                    markerKunde = listMarker.get(markerID);
                    markerKunde.setDraggable(false);
                }


                int anzahlMarker = 0;
                String meldungMarker = getString(R.string.meldungMarkerAuswahl);
                Boolean kundeAbgang;
                markerIDString = marker.getId();
                idLaenge = markerIDString.length();
                markerIDString = markerIDString.substring(1, idLaenge);


                anzahlMarker = listMarker.size();


                markerID = Integer.parseInt(markerIDString);
                //      markerID = String.valueOf(markerIdInt);

                if (listKunde.get(markerID).getStatus() == 1) {
                    kundeBeliefert = true;
                }


                radiusUmfang = listKunde.get(markerID).getRadius();


                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 18.0f));


                markerKunde = listMarker.get(markerID);
                markerKunde.setDraggable(true);
                setZeilenUnsichtbar();
                tr3.setVisibility(View.VISIBLE);
                seekBar.setProgress(radiusUmfang);


//                btErledigtKreis.setVisibility(View.VISIBLE);
            //    tr2.setWeightSum(3);

*/
                return true;

            }
        });
    }

    public void initialisiere() {
        String firma, kundeStrasse;
        Integer kundeStatus;

        for (Map.Entry<String, Kunde> entry : listKunde.entrySet()) {
            String key = entry.getKey();
            Kunde kunde = entry.getValue();
            if (!key.equals("Zuhause") && !key.equals("firma")) {
                addMarkerMitErkennungsbereich(key, kunde);
            } else {
                addMarkerMitErkennungsbereichCustom(kunde);
            }

        }
        //   User user = FirebaseHandler.monteur;
        // addMarkerMitErkennungsbereichZuhause(user);

    }

    public void addMarkerMitErkennungsbereichCustom(Kunde kunde) {

        int height = 150;
        int width = 150;
        String title;
        BitmapDrawable bitmapdraw;
        if (kunde.getFirma().equals("Firma")) {
            bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.firma);
            title = "Firma";
        } else {
            bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.zuhause);
            title = "Zuhause";
        }

        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        MarkerOptions markerOption = new MarkerOptions()
                .position(new LatLng(kunde.getLatitude(), kunde.getLongitude()))
                .title(title).draggable(false)
                //  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        Marker marker = googleMap.addMarker(markerOption);

        Circle kreis = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(kunde.getLatitude(), kunde.getLongitude()))
                .radius(75)
                .visible(false)
                .strokeColor((int) BitmapDescriptorFactory.HUE_ORANGE));


        kreis.setStrokeColor(Color.RED);
        kreis.setStrokeWidth(10);

    }

    //Kundenmarker + Erkennungsbereich hinzufügen
    public void addMarkerMitErkennungsbereich(String key, Kunde kunde) {
        Polygon polygon;
        Marker marker;
        MarkerOptions markerOption;
        Circle kreis;
        Gson gson = new Gson();
        int color, farbauswahl;
        int kundeStatus = kunde.getStatus();
        String markerTitel = kunde.getFirma();
        latitude_Kunde = kunde.getLatitude();
        longitude_Kunde = kunde.getLongitude();
        radiusUmfang = kunde.getRadius();
        if (kundeStatus == 2) {
            color = Color.GREEN;
            markerOption = new MarkerOptions()
                    .position(new LatLng(latitude_Kunde, longitude_Kunde))
                    .title(markerTitel).draggable(false)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        } else if (kundeStatus == 1) {
            color = Color.RED;
            markerOption = new MarkerOptions()
                    .position(new LatLng(latitude_Kunde, longitude_Kunde))
                    .title(markerTitel).draggable(false)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        } else {
            color = Color.YELLOW;
            markerOption = new MarkerOptions()
                    .position(new LatLng(latitude_Kunde, longitude_Kunde))
                    .title(markerTitel).draggable(false)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        }

        //Marker hinzufügen
        marker = googleMap.addMarker(markerOption);
        //  listKunde.remove(key);

        try {
            Marker m = listMarker.get(key);
            m.remove();
        } catch (Exception e) {
            e.printStackTrace();
        }
        listMarker.put(key, marker);


        //   listMarker.add(marker);

        //Falls Kunden einen Erkennungskreis besitzt
if (!kunde.getFirma().equals("Firma") && !kunde.getFirma().equals("Zuhause")){
    //Erkennungskreis erstellen
    kreis = googleMap.addCircle(new CircleOptions()
            .center(new LatLng(latitude_Kunde, longitude_Kunde))
            .radius(radiusUmfang)
            .strokeColor((int) BitmapDescriptorFactory.HUE_ORANGE));


    kreis.setStrokeColor(color);
    kreis.setStrokeWidth(10);

    //Erkennungskreis der Liste hinzufügen
    //  listKreis.add(kreis);
    //      hashKreis.remove(key);
    try {
        Circle c = hashKreis.get(key);
        c.remove();
    } catch (Exception e) {
        e.printStackTrace();
    }
    hashKreis.put(key, kreis);
}


        //Falls Kunden einen Polygon Erkennungsbereich besitzt

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude_Kunde,
                longitude_Kunde), 14.0f));
    }

    //Referenz zu den XML-Komponenten
    public void initialisiereXML() {
        btRadius = findViewById(R.id.btRadiusMapActivity);
        lvSuchvorschlaege = findViewById(R.id.lvSuchvorschlaegeMapActivity);
        seekBar = findViewById(R.id.seekBarMapsActivity);
        btRadiusSpeichern = findViewById(R.id.btRadiusOkMapActivity);
        tvRadiusGrösse = findViewById(R.id.tvRadiusMapActivity);
        lvKunden = findViewById(R.id.lvKundenmap);
        frMap = findViewById(R.id.map);
        tr2 = findViewById(com.example.timo.Zeiterfassung.R.id.tr2);
        tr3 = findViewById(com.example.timo.Zeiterfassung.R.id.tr3);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String text) {
        if (!activityBeenden) {


            if (!adapterSucherIstInitialisert) {
                adapterKunden = new ListViewKundenAdapter(this, com.example.timo.Zeiterfassung.R.layout.item_kunde, listKundeSuche, false);
                lvSuchvorschlaege.setAdapter(adapterKunden);
                registerForContextMenu(lvSuchvorschlaege);
                adapterSucherIstInitialisert = true;
            } else {
                lvSuchvorschlaege.setVisibility(View.VISIBLE);
            }
            tr2.setVisibility(View.GONE);
            tr3.setVisibility(View.GONE);
            tr4.setVisibility(View.GONE);
            tr5.setVisibility(View.GONE);
            tr6.setVisibility(View.GONE);
            listKundeFilter.clear();
            listKundeSuche.clear();
            if (!text.isEmpty()) {
                filterText(text.toLowerCase());
            } else {
                lvSuchvorschlaege.setVisibility(View.GONE);
            }
            adapterKunden.notifyDataSetChanged();


        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    //Kundensuche filtern
    private void filterText(String filterText) {
        String kundeStrasse;
        String firma;

        boolean textErhalten = false;
        for (int i = 0; i < kundeListOhneAbgaenge.size(); i++) {

            kundeStrasse = kundeListOhneAbgaenge.get(i).getStrasse().toLowerCase();
            firma = kundeListOhneAbgaenge.get(i).getFirma().toLowerCase();


            //Auf Übereinstimmungen überprüfen
            textErhalten = kundeStrasse.contains(filterText) ||
                    firma.contains(filterText);
            //Bei Übereinstimmung Kunde in Liste hinzufügen
            if (textErhalten) {
                listKundeFilter.add(kundeListOhneAbgaenge.get(i));
            }
        }
        listKundeSuche.clear();
        for (int i = 0; i < listKundeFilter.size(); i++) {
            listKundeSuche.add(listKundeFilter.get(i));
        }
        adapterKunden.notifyDataSetChanged();
    }

    private void getLocationBerechtigung() {
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }
                    googleMap.setMyLocationEnabled(true);
                }
            }
        }
        if ((requestCode == REQUEST_PERMISSION_ACCESS_FINE_LOCATION) &&
                (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED)) {
        }
    }

    private void initialisiereKarte() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void zeigeStreckenverlauf(ArrayList<LatLng> listGeo, Integer farbe) {

        if (polyline == null) {
            PolylineOptions polylineOptions = new PolylineOptions()
                    .width(8)
                    .color(farbe);
            //     .color(getResources().getColor(R.color.gelb));
            polylineOptions.addAll(listGeo);
            listPoly.add(polyline = googleMap.addPolyline(polylineOptions));

        } else {
            polyline.setPoints(listGeo);
        }
        //  Toast.makeText(KarteActivity.this, String.valueOf(listGeo.size()), Toast.LENGTH_SHORT).show();
    }

    private void zeigeStreckenverlauf2(ArrayList<LatLng> listGeo, Integer farbe) {

        if (polyline2 == null) {
            PolylineOptions polylineOptions = new PolylineOptions()
                    .width(8)
                    .color(farbe);
            //     .color(getResources().getColor(R.color.gelb));
            polylineOptions.addAll(listGeo);
            listPoly.add(polyline = googleMap.addPolyline(polylineOptions));

        } else {
            polyline.setPoints(listGeo);
        }
        //  Toast.makeText(KarteActivity.this, String.valueOf(listGeo.size()), Toast.LENGTH_SHORT).show();
    }

    private void setZeilenUnsichtbar() {
        tr2.setVisibility(View.GONE);
        tr3.setVisibility(View.GONE);
        //    tr4.setVisibility(View.GONE);
        //  tr5.setVisibility(View.GONE);
        // tr6.setVisibility(View.GONE);

    }

    @Override
    public void neuerKunde(HashMap<String, Kunde> listKunde) {

    }

    @Override
    public void neuerKunde(String key, Kunde kunde) {
        listKunde.put(key, kunde);
        addMarkerMitErkennungsbereich(key, kunde);


    }

    @Override
    public void loescheKunde(String key) {
        Marker marker;
        Circle circle;
        marker = listMarker.get(key);
        circle = hashKreis.get(key);

        listKunde.remove(key);
        listMarker.remove(key);
        hashKreis.remove(key);
        marker.remove();
        circle.remove();
    }

    public class Binder extends android.os.Binder {
        public KarteActivity getService() {
            return KarteActivity.this;
        }
    }

}


