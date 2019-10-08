package com.example.four.launchmodetest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FifthActivity extends BaseActivity {
    private static final String TAG = "FifthActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fifth_layout);
        Toast.makeText(FifthActivity.this,"接下来的每个Launch Mode Test 需要结合logcat,并在logcat中添加输出限制\"活动\"",Toast.LENGTH_LONG).show();
        Log.d(TAG, "当前界面所属活动: " + this.toString());

        Button button10 = (Button) findViewById(R.id.button_10);
        Button button11 = (Button) findViewById(R.id.button_11);

        // standard launch mode test
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FifthActivity.this,FifthActivity.class);
                startActivity(intent);
            }
        });

        // 进入下一个活动
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FifthActivity.this,SixthActivity.class);
                startActivity(intent);
            }
        });


    }
}
