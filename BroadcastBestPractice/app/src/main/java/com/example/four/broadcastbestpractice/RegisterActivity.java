package com.example.four.broadcastbestpractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private static HashMap<String, String> users = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button sign_in = (Button) findViewById(R.id.register_user);
        final EditText accountText = (EditText) findViewById(R.id.register_account);
        final EditText passwordText = (EditText) findViewById(R.id.register_password);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = accountText.getText().toString();
                String password = passwordText.getText().toString();
                if( !TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)){;
                    if(addUser(account,password)){
                        Toast.makeText(RegisterActivity.this,"创建成功",Toast.LENGTH_SHORT).show();
                        LoginActivity.startLogin(RegisterActivity.this,account,password);
                        finish();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this,"账号和密码均不能为空，请检查格式后重新输入",Toast.LENGTH_SHORT).show();
                    accountText.setText("");
                    passwordText.setText("");
                }
            }
        });

    }

    public  boolean addUser(String account, String password){
        boolean isAdded;
        if(users.containsKey(account) || account.equals("four")){
            Toast.makeText(RegisterActivity.this,"该用户已经存在",Toast.LENGTH_SHORT).show();
            isAdded = false;
        }else{
            users.put(account,password);
            isAdded = true;
        }
        return isAdded;
    }

    public static boolean isContain(String account,String password){
        boolean isContain;
        if(users.containsKey(account) && password.equals(users.get(account))){
            isContain = true;
        } else{
            isContain = false;
        }
        return isContain;

    }
}
