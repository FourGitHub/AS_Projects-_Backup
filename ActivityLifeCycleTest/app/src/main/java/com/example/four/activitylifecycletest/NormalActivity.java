package com.example.four.activitylifecycletest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class NormalActivity extends AppCompatActivity {

    private static final String TAG = "NormalActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "-->> onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_layout);
    }

    @Override
    protected void onStart() {
        Log.i(TAG,"-->> onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"-->> onResume");
    }

    @Override
    protected void onPause() {
        Log.i(TAG,"-->> onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG,"-->> onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG,"-->> onDestroy");
        super.onDestroy();
    }
    @Override
    protected void onRestart() {
        Log.i(TAG,"-->> onRestart");
        super.onRestart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(TAG, "-->> onNewIntent: ");
        super.onNewIntent(intent);
    }

    public void StartActivity1(View view) {
        Intent i = new Intent(this, NormalActivity.class);
        startActivity(i);
    }

    public void StartActivity2(View view) {
        Intent i = new Intent(this, Main2Activity.class);
        startActivity(i);
    }
}
