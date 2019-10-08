package com.example.four.alertdialogtest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button_1 = (Button) findViewById(R.id.btn_1);
        Button button_2 = (Button) findViewById(R.id.btn_2);
        Button button_3 = (Button) findViewById(R.id.btn_3);
        Button button_4 = (Button) findViewById(R.id.btn_4);
        Button button_5 = (Button) findViewById(R.id.btn_5);

        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomalDialog();
            }
        });

        button_2.setOnClickListener(v -> listDialog());

        button_3.setOnClickListener(v -> singleSelectDialog());

        button_4.setOnClickListener(v -> multiSelectDialig());

        button_5.setOnClickListener(v -> customDialog());
    }

    public void nomalDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("一般对话框");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("忽略", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void listDialog() {
        final String[] items = {"One", "Two", "Three", "Four"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), items[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void singleSelectDialog() {
        final String[] items = {"One", "Two", "Three", "Four"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), items[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void multiSelectDialig() {
        final String[] items = {"One", "Two", "Three", "Four"};
        final boolean[] selected = {true, true, false, false};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMultiChoiceItems(items, selected, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), items[which] + " seleted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), items[which] + " cancle seleted.", Toast.LENGTH_SHORT).show();

                }
            }

        });
        builder.setCancelable(false);
        builder.setPositiveButton("确定", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    public void customDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GridLayout实现的登录框");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(true);
        View view = getLayoutInflater().inflate(R.layout.custom_dialog,null);
        EditText count = view.findViewById(R.id.count_text);
        EditText password = view.findViewById(R.id.password_text);
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void showProgressDialog(View view) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("ProgressDialog");
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setMessage("正在加载...");
        dialog.setCancelable(true);
        dialog.show();
    }




}
