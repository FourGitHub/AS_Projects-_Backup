package com.example.four.storagetest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    private TextView textView_1;
    private TextView textView_2;
    private TextView textView_3;
    private TextView textView_4;
    private TextView textView_5;
    private TextView textView_6;
    private TextView textView_7;
    private TextView textView_8;
    private TextView textView_9;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView_1 = (TextView) findViewById(R.id.text_1);
        textView_2 = (TextView) findViewById(R.id.text_2);
        textView_3 = (TextView) findViewById(R.id.text_3);
        textView_4 = (TextView) findViewById(R.id.text_4);
        textView_5 = (TextView) findViewById(R.id.text_5);
        textView_6 = (TextView) findViewById(R.id.text_6);
        textView_7 = (TextView) findViewById(R.id.text_7);
//        textView_8 = (TextView) findViewById(R.id.text_8);
//        textView_9 = (TextView) findViewById(R.id.text_9);

        SharedPreferences prefs1 = getSharedPreferences("four", MODE_PRIVATE);
        SharedPreferences prefs2 =  getSharedPreferences("five", MODE_PRIVATE);
        Log.i(TAG, "prefs1 == prefs2 ---> " + (prefs1 == prefs2)); // flase

        SharedPreferences prefs3 = getSharedPreferences("four", MODE_PRIVATE);
        SharedPreferences prefs4 =  getSharedPreferences("four", MODE_PRIVATE);
        Log.i(TAG, "prefs3 == prefs4 ---> " + (prefs3 == prefs4)); // true
    }

    public void button_1(View view) {
        textView_1.setText(Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    public void button_2(View view) {
        textView_2.setText(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath
                ());
        Environment.getExternalStorageState();
    }

    public void button_3(View view) {
        textView_3.setText(getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath());
    }

    public void button_4(View view) {
        textView_4.setText(getExternalFilesDir(null).getAbsolutePath());
    }

    public void button_7(View view) {
        textView_7.setText(getExternalCacheDir().getAbsolutePath());
    }

    public void button_5(View view) {
        textView_5.setText(getFilesDir().getPath());
    }

    public void button_6(View view) {
        textView_6.setText(getCacheDir().getAbsolutePath());
    }


//
//    public void button_8(View view) {
//        textView_8.setText(getExternalFilesDir(null).getPath());
//    }
//
//    public void button_9(View view) {
//        textView_9.setText(getExternalFilesDir(null).getPath());
//    }
}
