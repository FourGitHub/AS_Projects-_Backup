package com.example.four.broadcasttst;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private IntentFilter intentFilter1;
    private IntentFilter intentFilter2;

    private NetworkChangeReceiver receiver;
    private MyBroadcastReceiver receiver1;
    private LocalBroadcastReceiver localBroadcastReceiver;

    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        // 发送自定义广播
        Button button = (Button) findViewById(R.id.send_broadcast);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.android.four.MY_BROADCAST");
                //     sendBroadcast(intent);
                sendOrderedBroadcast(intent, null);
            }
        });

        // 发送有序广播
        Button button1 = (Button) findViewById(R.id.send_order_broadcast);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.android.four.MY_ORDERED_BROADCAST");
                intent.setClass(getApplicationContext(), MyOrderedBroadcastReceiver.class);
                sendBroadcast(intent, null); // 第二个参数是与权限有关的字符串
            }
        });

        // 发送本地广播
        Button button2 = (Button) findViewById(R.id.send_local_broadcast);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.android.four.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 动态注册了三个广播接收器
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, intentFilter);

        intentFilter1 = new IntentFilter();
        intentFilter1.addAction("com.android.four.MY_BROADCAST");
        receiver1 = new MyBroadcastReceiver();
        registerReceiver(receiver1, intentFilter1);

        intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.android.four.LOCAL_BROADCAST");
        localBroadcastReceiver = new LocalBroadcastReceiver();
        localBroadcastManager.registerReceiver(localBroadcastReceiver, intentFilter2);
    }

    @Override// 动态注册的广播接收器一定要取消注册才行。否则内存泄漏
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        unregisterReceiver(receiver1);
        localBroadcastManager.unregisterReceiver(localBroadcastReceiver);
    }

    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, "Network is available", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Network unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_in_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.boot_completed:
                Intent intent = new Intent(MainActivity.this, BootCompletedActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
}
