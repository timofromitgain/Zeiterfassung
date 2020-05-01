package com.example.timo.Zeiterfassung.Activity;

import android.util.Log;

import com.example.timo.Zeiterfassung.Helfer.Kunde;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseHandler {
    private  FirebaseAuth firebaseAuth;
    private String userId;
    private ArrayList<Kunde> listKunde;
    FirebaseUser user;

    public FirebaseHandler() {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

    }

    public String getUserId(){
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        return user.getUid();
    }

    public void insertDate(String child,Object object){
        Log.d("Firemire",String.valueOf(user.getUid()));
        DatabaseReference refUser = FirebaseDatabase.getInstance().getReference(user.getUid());
        DatabaseReference ref = refUser.child(child);
       // ref.push().setValue(object);
        ref.setValue(object);
    }
    public void insert(String ref,Object object){
        Log.d("Firemire",String.valueOf(user.getUid()));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(ref);
        //   DatabaseReference ref = refUser.child(child);
        // ref.push().setValue(object);
        databaseReference.setValue(object);
    }

    public ArrayList<Kunde> getListKunde() {
     //   ArrayList<Kunde> listKunde = new ArrayList<Kunde>();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference refUser = FirebaseDatabase.getInstance().getReference(user.getUid());
        DatabaseReference refKunde = refUser.child("Kunde");
        Log.d("Firemire",user.getUid());
        refUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null){

                }

                Log.d("Firemire","change");
             listKunde =  (ArrayList<Kunde>) dataSnapshot.getValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firemire",String.valueOf(listKunde.size()));
            }
        });
            return listKunde;
    }

    public String userId(){
       return firebaseAuth.getCurrentUser().getUid();
    }
}
