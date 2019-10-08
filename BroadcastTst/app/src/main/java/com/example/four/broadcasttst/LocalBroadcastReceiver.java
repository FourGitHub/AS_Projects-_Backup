package com.example.four.broadcasttst;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LocalBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"这是一条本地广播，发出的广播仅在本程序内部进行传递！",Toast.LENGTH_LONG).show();
    }
}
