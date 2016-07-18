package com.github.florent37.bubbletab.sample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FakeAdapter extends FragmentStatePagerAdapter {

    public FakeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            default:
                return FakeFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
