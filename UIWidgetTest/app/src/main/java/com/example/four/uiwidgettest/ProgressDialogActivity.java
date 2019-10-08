package com.example.four.uiwidgettest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ProgressDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog_layout);
        Button button1 = (Button) findViewById(R.id.button_1);
        Button button2 = (Button) findViewById(R.id.button_2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button_1:
                        ProgressDialog progressDialog = new ProgressDialog(ProgressDialogActivity.this);
                        progressDialog.setTitle("This is a ProgressDialog");
//                        progressDialog.setIcon();
                        progressDialog.setMessage("6个基本控件测试已经完成！点击对话框外任意区域关闭该窗口!");
                        progressDialog.setCancelable(false);
                        progressDialog.setCanceledOnTouchOutside(true);
                        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                Toast.makeText(ProgressDialogActivity.this,"Hello Four!",Toast.LENGTH_LONG).show();
                            }
                        });
                        progressDialog.show();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgressDialogActivity.this,LinearLayoutActivity.class);
                startActivity(intent);
            }
        });
    }
}
