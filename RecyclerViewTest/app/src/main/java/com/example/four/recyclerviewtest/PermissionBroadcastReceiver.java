package com.example.four.recyclerviewtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class PermissionBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "PermissionBroadcastRece";
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "收到权限广播", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "收到权限广播");
    }
}
