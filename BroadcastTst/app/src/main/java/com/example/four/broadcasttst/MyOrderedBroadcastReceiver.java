package com.example.four.broadcasttst;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyOrderedBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"第一个接收到 有序广播，可在我这里截断！",Toast.LENGTH_SHORT).show();
       // abortBroadcast();
    }
}
