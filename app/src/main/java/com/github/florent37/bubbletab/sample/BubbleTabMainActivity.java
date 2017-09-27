package com.github.florent37.bubbletab.sample;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.github.florent37.bubbletab.BubbleTab;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BubbleTabMainActivity extends AppCompatActivity {

    @Bind(R.id.bubbleTab) BubbleTab bubbleTab;
    @Bind(R.id.viewPager) ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bubbletab_activity_main);
        ButterKnife.bind(this);

        viewPager.setAdapter(new FakeAdapter(getSupportFragmentManager()));
        bubbleTab.setupWithViewPager(viewPager);
    }
}
