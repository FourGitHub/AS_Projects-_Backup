package com.example.four.launchmodetest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SixthActivity extends BaseActivity {
    private static final String TAG = "SixthActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sixth_layout);

        Log.d(TAG, "当前界面所属活动: " + this.toString());

        Button button12 = (Button) findViewById(R.id.button_12);
        Button button13 = (Button) findViewById(R.id.button_13);

        // standard launch mode test
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SixthActivity.this,SixthActivity.class);
                startActivity(intent);
            }
        });

        // 进入下一个活动
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SixthActivity.this,SeventhActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
        protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "活动 " + this.getClass().getSimpleName() + " onRestart, " + this.toString());
    }
}
