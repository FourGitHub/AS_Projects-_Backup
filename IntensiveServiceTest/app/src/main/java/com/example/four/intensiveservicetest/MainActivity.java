package com.example.four.intensiveservicetest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private LinearLayout addTextView;
    private UploadingBroadcastReceiver receiver;
    LocalBroadcastManager manager;
    private int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addTextView = (LinearLayout)findViewById(R.id.add_textView);
    }

    public void addTask(View view) {
        String path = "/sdcard/image/ " + (i++) + ".png";
        Intent intent = new Intent(this,MyUploadService.class);
        intent.putExtra("path",path);
        startService(intent);
        TextView textView = new TextView(this);
        addTextView.addView(textView);
        textView.setText(path+" is uploading......");
        textView.setTag(path);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerBroadcastReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiver != null) {
            manager.unregisterReceiver(receiver);
        }
    }

    private void registerBroadcastReceiver() {
        manager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(MyUploadService.ACTION_UPLOAD_RESULT);
        receiver = new UploadingBroadcastReceiver();
        manager.registerReceiver(receiver,filter);
    }

    class UploadingBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String path = intent.getStringExtra("path");
            TextView textView = (TextView) addTextView.findViewWithTag(path);
            textView.setText(path + " uploading is successful.");
        }
    }
}
