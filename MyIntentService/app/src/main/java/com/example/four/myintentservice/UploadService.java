package com.example.four.myintentservice;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2018/3/11 0011.
 */

public class UploadService extends IntentService {
    private static final String TAG = "UploadService";
    public UploadService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "哈Thread is " + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "哈onDestroy:() !!!!!!!!!!!");
    }
}
