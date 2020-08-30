package com.zeiterfassung.timo.Zeiterfassung.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.zeiterfassung.timo.Zeiterfassung.Fragment.Heute;
import com.zeiterfassung.timo.Zeiterfassung.Fragment.Woche;
import com.zeiterfassung.timo.Zeiterfassung.Helfer.FragmentAdapter;
import com.zeiterfassung.timo.Zeiterfassung.R;

public class Bericht extends AppCompatActivity {




        private ViewPager viewPager;
        private FragmentAdapter fragmentAdapter;

        private Heute fragmentHeute;
        private Woche fragmentWoche;





        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bericht);
        viewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        initialisiereViewPager(viewPager);
        /*
        berichtGesamtFragment = (BerichtGesamtFragment) fragmentAdapter.getItem(0);
        berichtNichtBeliefertFragment = (BerichtNichtBeliefertFragment) fragmentAdapter.getItem(1);
        berichtBeliefertFragment = (BerichtBeliefertFragment) fragmentAdapter.getItem(2);*/

    }



    private void initialisiereViewPager(ViewPager viewPager) {
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.addFragment(new Heute(), "Heute");
        fragmentAdapter.addFragment(new Woche(), "Wochenübersicht");
        viewPager.setAdapter(fragmentAdapter);
    }






    }
