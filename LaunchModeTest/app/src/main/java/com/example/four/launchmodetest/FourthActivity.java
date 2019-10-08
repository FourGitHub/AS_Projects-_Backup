package com.example.four.launchmodetest;

import android.content.Intent;
import android.os.Bundle;

public class FourthActivity extends BaseActivity {

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FourthActivity.this, FifthActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_layout);

    }
}