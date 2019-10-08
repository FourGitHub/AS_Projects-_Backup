package com.example.four.intensiveservicetest;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/3/11 0011.
 */

public class MyUploadService extends IntentService {
    public static final String ACTION_UPLOAD_RESULT = "com.four.example.intentservicetest";
    private LocalBroadcastManager manager;
    private static final String TAG = "MyUploadService";

    public MyUploadService() {
        super("MyUploadService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "--->>> onHandleIntent: " + Thread.currentThread().getName());

        try {
            Thread.sleep(2000);
            Intent i = new Intent(ACTION_UPLOAD_RESULT);
            i.putExtra("path",intent.getStringExtra("path"));
            manager.sendBroadcast(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = LocalBroadcastManager.getInstance(getApplicationContext());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(),"onDestory()", Toast.LENGTH_SHORT).show();
    }
}
