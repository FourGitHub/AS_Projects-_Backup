package com.example.four.mvpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MvpView {

    private MvpPresenter presenter;
    private TextView tv;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.text);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        presenter = new MvpPresenter();
        presenter.attachPresenter(this);

    }

    public void getData(View view) {
        presenter.getData("normal");
    }

    public void getDataForFailure(View view) {
        presenter.getData("failure");
    }

    public void getDataForError(View view) {
        presenter.getData("error");
    }

    @Override
    public void showLoading() {
        if (progressBar.getVisibility() == View.GONE) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showData(String data) {
        tv.setText(data);
    }

    @Override
    public void showFailedMessage(String msg) {
        tv.setText(msg);
    }

    @Override
    public void showErrorMessage() {
        tv.setText("网络请求异常");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter.isViewAttached()) {
            presenter.detachPreaenter();
        }
    }
}
