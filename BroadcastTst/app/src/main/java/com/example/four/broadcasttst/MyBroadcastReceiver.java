package com.example.four.broadcasttst;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by Administrator on 2017/11/8 0008.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("My Broadcast Receiver");
                alertDialog.setMessage("已收到自定义广播");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();

    }
}
