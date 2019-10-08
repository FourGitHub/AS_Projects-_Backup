package com.example.four.activitylifecycletest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "-->> onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.normal_activity);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,NormalActivity.class);
                startActivity(intent1);
            }
        });

        Button button2 = (Button) findViewById(R.id.dialog_activity);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this,DialogActivity.class);
                startActivity(intent2);
            }
        });


    }
}
