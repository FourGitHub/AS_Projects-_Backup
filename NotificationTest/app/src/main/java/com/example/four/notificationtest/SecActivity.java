package com.example.four.notificationtest;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SecActivity extends AppCompatActivity {
    private static final String TAG = "SecActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("notificationId");
        if (bundle != null) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.cancel(bundle.getInt("notificationId"));
        }
    }
}
