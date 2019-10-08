package com.example.four.viewpagertest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class ButtonNavigationViewActivity extends AppCompatActivity {

    private BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_navigation_view);
        bnv = findViewById(R.id.navigation);

        bnv.setOnNavigationItemSelectedListener(item -> true);

    }
}
