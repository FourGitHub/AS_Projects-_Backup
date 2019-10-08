package com.example.four.uiwidgettest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        Button button = (Button) findViewById(R.id.button);

        editText = (EditText) findViewById(R.id.edit_text);
        button.setOnClickListener(new View.OnClickListener() {
            // 点击按钮，将EditText中的输入通过Toast显示出来
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button:
                        String msg = editText.getText().toString();
                        if (msg.equals("")) {
                            Toast.makeText(MainActivity.this, "请在输入框中输入信息后再点击", Toast.LENGTH_SHORT).show();
                        } else {
                            editText.setText("");
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                }
            }
        });

        Button button1 = (Button) findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            // 点击按钮，将EditText中的输入通过Toast显示出来
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageViewTest.class);
                startActivity(intent);
            }
        });
    }
}
