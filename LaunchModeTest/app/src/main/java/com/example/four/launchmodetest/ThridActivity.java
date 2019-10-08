package com.example.four.launchmodetest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ThridActivity extends BaseActivity {
    private static final String TAG = "ThridActivity";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thridactivity_1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.QUIT:
                ActivityCollector.finishAll();
                android.os.Process.killProcess(android.os.Process.myPid());// 彻底杀掉当前进程
                break;
            default:
        }
        return true;
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent2 = new Intent(ThridActivity.this, SecondActivity.class);
//        String data = "这是ThridActivity返回的数据";
//        intent2.putExtra("data_back", data);
//        setResult(RESULT_OK);
//        finish();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thrid_layout);
        Button button7 = (Button) findViewById(R.id.button_7);
        Button button8 = (Button) findViewById(R.id.button_8);

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String data = intent.getStringExtra("extra_data");
                Log.d(TAG, data);
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();////************************
                String data = "这是ThridActivity返回的数据";
                intent2.putExtra("data_back", data);
                setResult(RESULT_OK, intent2);//*****************
                finish();

            }
        });

        Button button9 = (Button) findViewById(R.id.button_9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ThridActivity.this, "你将进入下一个活动", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ThridActivity.this, FourthActivity.class);
                startActivity(intent);
            }
        });

    }

    public static void actionStart(Context context,String data){
        Intent intent = new Intent(context,ThridActivity.class);
        intent.putExtra("extra_data",data);
        context.startActivity(intent);
    }

}
