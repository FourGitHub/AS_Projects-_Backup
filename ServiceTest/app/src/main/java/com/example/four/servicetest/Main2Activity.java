package com.example.four.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {

    private ServiceConnection conn;
    private MyService.DownloadBinder mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        init();
        startTask();
    }

    @Override
    protected void onDestroy() {
        unBindService();
        super.onDestroy();
    }

    private void init() {
        /*
        onServiceConnected() 和 onServiceDisconnected() 运行在客户端主线程，
        所以避免在里面直接调用服务端耗时方法
         */
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                // 强转，获取组件和服务通信的桥梁
                mBinder = (MyService.DownloadBinder) service;

                // 在这里调用 mBinder的方法就不会NPE，因为这里可以确定mBinder != null
                mBinder.startDownload();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    private void startTask() {
        Intent i = new Intent(this, MyService.class);
        bindService(i, conn, BIND_AUTO_CREATE);
        // 坑？如果在这里立马调用 mBinder的方法，会报NPE，难道是bind需要一定时间？
    }

    private void unBindService() {
        if (conn != null) {
            unbindService(conn);
        }
    }

}




