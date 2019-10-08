package com.example.four.recyclerviewtest;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class BasicActivity extends AppCompatActivity {
    private static final String TAG = "BasicActivity";

    /*
    两个辅助变量，两种思路
     */
    private long mLastPressedTime = 0;
    private boolean isExit = false;
    private IntentFilter intentFilter;
    private PermissionBroadcastReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, getClass().getSimpleName());
        ActivityCollector.addActivity(this);
//
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("com.example.four.permissionbroadcast_Action");
//        receiver = new PermissionBroadcastReceiver();
//        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
//        if (receiver != null) {
//            unregisterReceiver(receiver);
//        }
    }

    @Override
    public void onBackPressed() {
        /*
         连续双击back退出程序
         */
        //        long pressedTime = System.currentTimeMillis();
        //        if (pressedTime - mLastPressedTime > 1500) {
        //            super.onBackPressed();
        //            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        //            mLastPressedTime = pressedTime;
        //        } else {
        //            this.finish();
        //            System.exit(0);
        //        }

        Timer tExit = null;
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            finish();
            System.exit(0);
        }

        /*
        进入后台而不是退出程序
         */
        //        moveTaskToBack(true);
    }
}
