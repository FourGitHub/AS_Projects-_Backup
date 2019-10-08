package com.example.four.a2broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyOrederdBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"2BroadcastTest也收到了 BroadcastTest 发出的有序 广播",Toast.LENGTH_LONG).show();
    }
}
