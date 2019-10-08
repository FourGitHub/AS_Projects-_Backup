package com.example.four.mvplogindemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.four.mvplogindemo.ToastUtil.ToastUtils;

public class LoginActivity extends AppCompatActivity implements MvpView {

    private EditText userEdt;
    private EditText psdEdt;

    private MvpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        userEdt = (EditText) findViewById(R.id.edit_userid);
        psdEdt = (EditText) findViewById(R.id.edit_user_psd);

        presenter = new MvpPresenter();
        presenter.attachToPresenter(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter.isAttached()) {
            presenter.detachPresenter();
        }
    }

    // 按钮点击事件
    public void onBack(View view) {
        finish();
    }

    // 按钮点击事件
    public void onLogin(View view) {
        presenter.checkAccountAndPsd(this, userEdt.getText().toString().trim(), psdEdt.getText().toString().trim());
    }

    @Override
    public void onCheckSuccess() {
        startActivity(new Intent(this, SuccessActivity.class));
        finish();
    }

    @Override
    public void onCheckFailed() {
        ToastUtils.showToast(this, "登录失败：学号或者密码错误，请检查输入", Toast.LENGTH_SHORT);
    }

    @Override
    public void onPasswordEmpty() {
        ToastUtils.showToast(this, "密码不能为空", Toast.LENGTH_SHORT);
    }

    @Override
    public void onAccountEmpty() {
        ToastUtils.showToast(this, "学号不能为空", Toast.LENGTH_SHORT);
    }

    @Override
    public void onNoSuchStuId() {
        ToastUtils.showToast(this, "登录失败：学号不存在，请检查输入", Toast.LENGTH_SHORT);
    }

    @Override
    public void onBadNetwork() {
        ToastUtils.showToast(this, "网络异常", Toast.LENGTH_SHORT);
    }

}
