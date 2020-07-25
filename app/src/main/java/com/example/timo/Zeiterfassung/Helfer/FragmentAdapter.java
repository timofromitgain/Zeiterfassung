package com.example.timo.Zeiterfassung.Helfer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter{
    private final List<Fragment> listFragment = new ArrayList<>();
    private final List<String> listFragmentTitle = new ArrayList<>();

    public void addFragment(Fragment fragment, String title) {
        listFragment.add(fragment);
        listFragmentTitle.add(title);

    }

    public FragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listFragmentTitle.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }
}

