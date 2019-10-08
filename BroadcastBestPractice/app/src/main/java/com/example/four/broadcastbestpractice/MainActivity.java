package com.example.four.broadcastbestpractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.sendBroadcast(new Intent());
        Button sendForceOffline = (Button) findViewById(R.id.send_broadcast);
        Button sendPermissionBroadcast = (Button) findViewById(R.id.permission_b);
        sendForceOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.android.four.example.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });

        sendPermissionBroadcast.setOnClickListener((view) -> {
                Intent intent = new Intent("com.example.four.permissionbroadcast_Action");
                // Android 8.0 , 打破隐式广播限制 -> 发送显式广播，让静态注册的Receiver接收到广播。
                intent.setClassName("com.example.four.recyclerviewtest",
                        "com.example.four.recyclerviewtest.PermissionBroadcastReceiver");
                sendBroadcast(intent,"com.example.four.permissionbroadcast_Receiver");
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_in_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.harvest:
                Intent intent = new Intent(this, SummaryActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }
}
