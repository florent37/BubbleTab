package com.github.florent37.bubbletab.sample;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FakeAdapter extends FragmentStatePagerAdapter {

    public FakeAdapter(FragmentManager fm, int behavior) {
        super(fm, behavior);
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
