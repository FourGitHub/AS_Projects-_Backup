package com.mredrock.cyxbs.freshman.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mredrock.cyxbs.freshman.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
//    public static final int RUXUE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void startRuXue(View view) {
        Intent startRuXue = new Intent(this, RuXueActivity.class);
        startActivity(startRuXue);
    }
}
