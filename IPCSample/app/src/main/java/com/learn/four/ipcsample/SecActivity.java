package com.learn.four.ipcsample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.learn.four.ipcsample.aidl.Book;
import com.learn.four.ipcsample.aidl.BookManagerService;
import com.learn.four.ipcsample.aidl.IBookManager;

import java.util.List;

public class SecActivity extends AppCompatActivity {
    private static final String TAG = "SecActivity";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
        tv = findViewById(R.id.tv);
        tv.setText("User.sUserId = " + User.sUserId);
        Log.i(TAG, "-->> User.sUserId = " + User.sUserId);

    }

}
