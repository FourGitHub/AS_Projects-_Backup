package com.example.four.launchmodetest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import javax.xml.validation.Validator;

public class SecondActivity extends BaseActivity {
    private static final String TAG = "SecondActivity";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch (requestCode){
           case 1:
               if (resultCode == RESULT_OK){
                   String msg = data.getStringExtra("data_back");
                   Log.d(TAG, msg);
               }
               break;
           default:
       }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        Button button3 = (Button)findViewById(R.id.button_3);
        Button button4 = (Button)findViewById(R.id.button_4);
        Button button5 = (Button)findViewById(R.id.Button_5);
        Button button6 = (Button)findViewById(R.id.button_6);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this,"你将进入下一个活动",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SecondActivity.this,ThridActivity.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse("tel:10086"));
                startActivity(intent1);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "这是SecondActivity传来的数据";
                ThridActivity.actionStart(SecondActivity.this,data);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(SecondActivity.this,ThridActivity.class);
                startActivityForResult(intent3,1);
            }
        });

    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context,SecondActivity.class);
        context.startActivity(intent);
    }

}
