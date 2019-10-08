package com.example.four.activitytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FourthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_layout);

        // 获得启动该活动的 含有数据的intent,并从中取出数据
        Button button8 = (Button) findViewById(R.id.button_8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = getIntent();
                String data = intent7.getStringExtra("extra_data");
                if (data != null)
                    Toast.makeText(FourthActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        });

        Button button9 = (Button) findViewById(R.id.button_9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = getIntent();
                Bundle bundle;
                bundle = intent7.getBundleExtra("bundle_key");
                if (bundle != null) {
                    String data = bundle.getString("bundle_data");
                    Toast.makeText(FourthActivity.this, data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 给按钮注册点击事件,返回数据给上一个活动
        Button button6 = (Button) findViewById(R.id.button_6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Hello ThirdActivity, I'm FourthActivity.";
                Intent intent6 = new Intent();
                intent6.putExtra("data_return", msg);
                setResult(RESULT_OK, intent6);
                finish();
            }

        });
    }

    private static final String TAG = "FourthActivity";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_1:
                Toast.makeText(FourthActivity.this, "还未给该选项注册活动", Toast.LENGTH_SHORT).show();
                break;
            case R.id.option_2:
                Toast.makeText(FourthActivity.this, "还未给该选项注册活动", Toast.LENGTH_SHORT).show();
                break;
            case R.id.option_3:
                Toast.makeText(FourthActivity.this, "还未给该选项注册活动", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

}
