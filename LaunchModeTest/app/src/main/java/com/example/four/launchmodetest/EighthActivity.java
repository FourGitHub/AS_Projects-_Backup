package com.example.four.launchmodetest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class EighthActivity extends BaseActivity {
    private static final String TAG = "EighthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eighth_layout);
        Log.d(TAG, "当前界面所属活动: " + this.toString());
        Log.d(TAG, "当前活动所属返回栈的id: " + getTaskId());

        Button button16 = (Button) findViewById(R.id.button_16);
        // 进入下一个活动
        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EighthActivity.this,NinthActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "活动 " + this.getClass().getSimpleName() + "onDestroy, " + this.toString());

    }
}
