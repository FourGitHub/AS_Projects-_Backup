package com.example.four.broadcastbestpractice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private Button login;
    private Button register;
    private EditText account;
    private EditText password;
    private TextView forgot_passward;
    private SharedPreferences pre;
    private SharedPreferences.Editor editor;
    private CheckBox checkBox;
    private static int pressTime = 0;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate: 哈哈哈哈");

        pre = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pre.edit();
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.sign_in);
        account = (EditText) findViewById(R.id.account_text);
        password = (EditText) findViewById(R.id.password_text);
        forgot_passward = (TextView) findViewById(R.id.forgot_passward);
        checkBox = (CheckBox) findViewById(R.id.checkbox);

        //System.out.print(intent == null);
//        if(intent != null){
//            account.setText(intent.getStringExtra("user_account").toString());
//            password.setText(intent.getStringExtra("user_password").toString());
//        }

        Boolean isRemenber = pre.getBoolean("remenber_password", false);

        if (isRemenber) {
            account.setText(pre.getString("account", ""));
            account.setSelection(pre.getString("account", "").length());
            password.setText(pre.getString("password", ""));
            checkBox.setChecked(true);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 设计||···后的判断主要为了使four/666666在被临时创建的账号覆盖的情况下（临时创建的账号如果选择记住密码，在程序第二次启动时，会被加载到输入框）任然不影响它登录
                if (RegisterActivity.isContain(account.getText().toString(), password.getText().toString()) || account.getText().toString().equals("four") &&
                        password.getText().toString().equals("666666")) {

                    if (checkBox.isChecked()) {
                        editor.putBoolean("remenber_password", true);
                        editor.putString("account", account.getText().toString());
                        editor.putString("password", password.getText().toString());
                    } else {
                        editor.clear();
                    }
                    editor.apply(); // 效率高，线程不安全
                    Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent1);
                    finish();

                } else {
                    if (account.getText().toString().equals("") || password.getText().toString().equals("")) {
                        if (account.getText().toString().equals("") && !password.getText().toString().equals(""))
                            Toast.makeText(LoginActivity.this, "账号为空，请输入账号后重新登录", Toast.LENGTH_LONG).show();
                        else if (!account.getText().toString().equals("") && password.getText().toString().equals(""))
                            Toast.makeText(LoginActivity.this, "密码为空，请输入密码后重新登录", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(LoginActivity.this, "您未输入账号和密码，请输入后登录", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "账号或密码错误，请检测后重新登录", Toast.LENGTH_LONG).show();
                        account.setText("");
                        password.setText("");
                    }
                }
            }
        });

        forgot_passward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "自己在好好想想吧！！！", Toast.LENGTH_SHORT).show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (++LoginActivity.pressTime == 1) {
            Toast.makeText(LoginActivity.this, "再次点击将退出程序", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
            android.os.Process.killProcess(android.os.Process.myPid());//结束进程
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!checkBox.isChecked()) {
            editor.clear();
            editor.apply();
        }
    }

    // 设想的是回到登录界面会读取数据添加到输入框，但是没有实现。
    public static void startLogin(Context context, String account, String password) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("user_account", account);
        intent.putExtra("user_password", password);
        context.startActivity(intent);
    }

}
