package com.example.four.uiwidgettest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LinearLayoutActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_layout);
        Button button1 = (Button) findViewById(R.id.button_1);
        //        Button button2 = (Button) findViewById(R.id.button_2);
        editText = (EditText) findViewById(R.id.edit_text);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button_1:
                        String string = editText.getText().toString();
                        editText.setText("");
                        if (string.equals("")) {
                            Toast.makeText(LinearLayoutActivity.this, "发送消息为空！", Toast.LENGTH_SHORT).show();
                        } else if (string.toLowerCase().equals("ok")) {
                            Intent intent = new Intent(LinearLayoutActivity.this, RelativeActivity.class);
                            startActivity(intent);
                        } else {
                            editText.setText("");
                            Toast.makeText(LinearLayoutActivity.this, string + "\n输入\"OK\"进入相对布局测试", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

    }
}
