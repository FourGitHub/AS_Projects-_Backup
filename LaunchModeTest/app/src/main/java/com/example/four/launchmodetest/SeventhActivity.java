package com.example.four.launchmodetest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SeventhActivity extends BaseActivity {

    private static final String TAG = "SeventhActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seventh_layout);
        Log.d(TAG, "当前界面所属活动: " + this.toString());

        Button button14 = (Button) findViewById(R.id.button_14);
        Button button15 = (Button) findViewById(R.id.button_15);

        // standard launch mode test
        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeventhActivity.this, EighthActivity.class);
                startActivity(intent);
            }
        });

        // 进入下一个活动
        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeventhActivity.this, EighthActivity.class);
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
