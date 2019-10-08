package com.example.four.launchmodetest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class NinthActivity extends BaseActivity {
    private static final String TAG = "NinthActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ninth_layout);
        Log.d(TAG, "当前活动所属返回栈的id: " + getTaskId());

        Button button17 = (Button) findViewById(R.id.button_17);
        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NinthActivity.this,TenthActivity.class);
                startActivity(intent);
            }
        });
    }
}
