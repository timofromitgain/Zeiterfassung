package com.example.timo.Zeiterfassung.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.timo.Zeiterfassung.Helfer.Geo;
import com.example.timo.Zeiterfassung.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {
    Button buttonRegistration, buttonLogin;
    EditText editNachname, editVorname,editStrasse,editStadt, editEmail, editPasswort, editEmailLogin, editPasswortLogin, editVerifycode;
    RadioGroup rg;
    RadioButton rbMonteur, rbProjektleiter;
    String nachname, vorname, strasse,stadt, email, passwort, strStatus, verifyCodeEingabe;
    String verifyCode = "123";
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    boolean istMonteur = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth = FirebaseAuth.getInstance();

        buttonRegistration = findViewById(R.id.btnReg);
        buttonLogin = findViewById(R.id.btnLogin);
        editNachname = findViewById(R.id.regNachname);
        editVorname = findViewById(R.id.regVorname);
        editStrasse = findViewById(R.id.regStrasse);
        editStadt = findViewById(R.id.regStadt);
        editEmail = findViewById(R.id.regEmail);
        editPasswort = findViewById(R.id.regPassword);
        editEmailLogin = findViewById(R.id.loginUsername);
        editPasswortLogin = findViewById(R.id.loginPasswort);
        editVerifycode = findViewById(R.id.regVerifycode);
        rg = findViewById(R.id.rgStatus);
        progressDialog = new ProgressDialog(this);


        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nachname = editNachname.getEditableText().toString();
                vorname = editVorname.getEditableText().toString();
                strasse = editStrasse.getEditableText().toString();
                stadt = editStadt.getEditableText().toString();
                email = editEmail.getEditableText().toString();
                passwort = editPasswort.getEditableText().toString();
                verifyCode = editVerifycode.getEditableText().toString();
                //     FirebaseHandler firebaseHandler = new FirebaseHandler();

                if (!istMonteur) {
                    if (verifyCodeEingabe.equals(verifyCode)) {
                        registriereUser(email, passwort, nachname, vorname,strasse,stadt, "Projektleiter");
                        //   signin(email,passwort);
                        // firebaseHandler.insertDate("Status","Projektleiter");
                    } else {
                        Toast.makeText(Registration.this, "Code ungültig", Toast.LENGTH_LONG).show();
                    }
                } else {
                    registriereUser(email, passwort, nachname, vorname,strasse,stadt, "Monteur");
                    //     signin(email,passwort);
                    Log.d("Firemire", "2");
                    //     firebaseHandler.insertDate("Status","Monteur");
                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editEmailLogin.getEditableText().toString();
                passwort = editPasswortLogin.getEditableText().toString();
                signin(email, passwort);
            }
        });


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (istMonteur) {
                    istMonteur = false;
                    editVerifycode.setVisibility(View.VISIBLE);
                } else {
                    istMonteur = true;
                    editVerifycode.setVisibility(View.GONE);
                }

            }
        });

    }

    private void registriereUser(String email, String passwort, String nachname, String vorname,String strasse,String stadt, String status) {
        progressDialog.setMessage("Registrieren...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, passwort).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Geo geo = new Geo(getApplicationContext());
                    LatLng latLng = geo.setGeo(strasse,stadt);

                    Toast.makeText(Registration.this, "User erfolgreich regisritert", Toast.LENGTH_LONG).show();
                    FirebaseUser user = task.getResult().getUser();
                    FirebaseHandler firebaseHandler = new FirebaseHandler();
                    firebaseHandler.insert("user/" + firebaseHandler.getUserId() + "/status", status);
                    firebaseHandler.insert("user/" + firebaseHandler.getUserId() + "/nachname", nachname);
                    firebaseHandler.insert("user/" + firebaseHandler.getUserId() + "/vorname", vorname);
                    firebaseHandler.insert("user/" + firebaseHandler.getUserId() + "/strasse", strasse);
                    firebaseHandler.insert("user/" + firebaseHandler.getUserId() + "/stadt", stadt);
                    firebaseHandler.insert("user/" + firebaseHandler.getUserId() + "/latitude", latLng.latitude);
                    firebaseHandler.insert("user/" + firebaseHandler.getUserId() + "/longitude", latLng.longitude);


                /*    Kunde kunde = new Kunde(getApplicationContext(),
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
                    firebaseHandler.insert("kunde/firma",kunde);*/




                    Log.d("Firemire", "reg: " + user);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("changeUser", true);
                    intent.putExtra("status", status);
                    intent.putExtra("nachname", nachname);
                    intent.putExtra("vorname", vorname);
                    startActivity(intent);
                } else {
                    Toast.makeText(Registration.this, "Fehlgeschlagen", Toast.LENGTH_LONG).show();

                }
            }
        });
        Log.d("Firemire", "1");
    }

    private void signin(String email, String passwort) {
        progressDialog.setMessage("Registrieren...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, passwort).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(Registration.this, "Erfolgreich eingeloggt", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("changeUser", true);
                    Log.d("callonme","erfolgreich eingeloggt");
                    startActivity(intent);
                } else {
                    Log.d("callonme","Fehler beim anmelden");
                    Toast.makeText(Registration.this, "Anmeldung fehlgeschlagen", Toast.LENGTH_LONG).show();

                }
            }
        });
        Log.d("Firemire", "5");
    }

}
