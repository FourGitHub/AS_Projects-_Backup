package com.example.four.viewpagertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: -->" + context.toString());
        Log.i(TAG, "onReceive: -->" + context.getPackageName());
        Log.i(TAG, "onReceive: -->" + context.getClass());
    }
}
