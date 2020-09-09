package com.github.florent37.bubbletab.sample;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.github.florent37.bubbletab.BubbleTab;

public class BubbleTabMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bubbletab_activity_main);

        BubbleTab bubbleTab = findViewById(R.id.bubbleTab);
        ViewPager viewPager = findViewById(R.id.viewPager);

        viewPager.setAdapter(new FakeAdapter(getSupportFragmentManager(), 1));
        bubbleTab.setupWithViewPager(viewPager);
    }
}
