package com.example.four.activitytest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private static final String TAG = "ThirdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_layout);
        Button button4 = (Button) findViewById(R.id.button_4);
        Button button5 = (Button) findViewById(R.id.button_5);
        Button button6 = (Button) findViewById(R.id.button_6);
        Button button7 = (Button) findViewById(R.id.button_7);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Intent.ACTION_VIEW);
                intent4.setData(Uri.parse("tel:10086"));
                startActivity(intent4);
            }
        });
        // 向下一个活动传递数据
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Hello FourthActivity, I'm from ThirdActivity by Intent.";
                Intent intent5 = new Intent(ThirdActivity.this, FourthActivity.class);
                intent5.putExtra("extra_data", msg);
                startActivity(intent5);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Hello FourthActivity,I'm from ThirdActivity by Intent & Bundle.";
                Intent intent = new Intent(ThirdActivity.this, FourthActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("bundle_data", msg);
                intent.putExtra("bundle_key", bundle);
                startActivity(intent);
            }
        });
        // 跳到下一个活动,并获得返回数据
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(ThirdActivity.this, FourthActivity.class);
                startActivityForResult(intent7, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(ThirdActivity.this, data.getStringExtra("data_return"), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
