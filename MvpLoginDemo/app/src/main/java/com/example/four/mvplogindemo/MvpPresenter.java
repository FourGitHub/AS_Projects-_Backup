package com.example.four.mvplogindemo;

import android.content.Context;
import android.text.TextUtils;

import com.example.four.mvplogindemo.NetUtil.OkHttpUtils;

/**
 * Created by Administrator on 2018/6/15 0015.
 */

public class MvpPresenter {

    private MvpView mView;

    public MvpPresenter() {

    }

    private MvpCallback mCallBack = new MvpCallback() {
        @Override
        public void checkSucccess() {
            if (mView != null) {
                LoginActivity activity = (LoginActivity)mView ;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                         mView.onCheckSuccess();
                    }
                });
            }
        }

        @Override
        public void checkFailed() {
            if (mView != null) {
                LoginActivity activity = (LoginActivity)mView ;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.onCheckFailed();
                    }
                });
            }

        }

        @Override
        public void noSuchStuId() {
            if (mView != null) {
                LoginActivity activity = (LoginActivity)mView ;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.onNoSuchStuId();
                    }
                });
            }
        }
    };

    public void attachToPresenter(MvpView mvpView) {
        this.mView = mvpView;
    }

    public void detachPresenter() {
        this.mView = null;
    }

    public boolean isAttached() {
        return mView != null;
    }

    public void checkAccountAndPsd(Context context, final String account, final String password) {
        if (!OkHttpUtils.isNetworkStatusOk(context)) {
            if (mView != null) {
                mView.onBadNetwork();
            }
            return;
        }

        if (TextUtils.isEmpty(account)) {
            if (mView != null) {
                mView.onAccountEmpty();
            }
            return;
        }

        if (TextUtils.isEmpty(password)) {
            if (mView != null) {
                mView.onPasswordEmpty();
            }
            return;
        }

        MvpModel.login(account, password, mCallBack);

    }

}
