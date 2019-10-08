package com.example.four.mvpdemo;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public class MvpPresenter {
    private MvpView mView;

    private MvpCallback callback = new MvpCallback() {
        @Override
        public void onSuccess(String data) {
            if (mView != null) {
                mView.showData(data);
            }
        }

        @Override
        public void onFailure(String msg) {
            if (mView != null) {
                mView.showFailedMessage(msg);
            }
        }

        @Override
        public void onError() {
            if (mView != null) {
                mView.showErrorMessage();
            }
        }

        @Override
        public void onCompleted() {
            if (mView != null) {
                mView.hideLoading();
            }
        }
    };

    public void attachPresenter(MvpView mvpView) {
        this.mView = mvpView;
    }

    public void detachPreaenter() {
        this.mView = null;
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public MvpPresenter() {

    }

    public void getData(String params) {
        if (mView != null) {
            mView.showLoading();
        }
        MvpModel.getNetData(params, callback);
    }
}
