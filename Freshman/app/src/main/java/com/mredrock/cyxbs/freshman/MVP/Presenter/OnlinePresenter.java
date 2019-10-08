package com.mredrock.cyxbs.freshman.MVP.Presenter;

import com.mredrock.cyxbs.freshman.DataBean.OnlineBean;
import com.mredrock.cyxbs.freshman.MVP.Model.OnlineModel;
import com.mredrock.cyxbs.freshman.MVP.View.OnlineView;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/17 0017 23:54
 */
public class OnlinePresenter extends BasePresenter<OnlineView> implements BaseCallBack<List<OnlineBean.GroupBean>> {
    private OnlineModel mModel;

    public OnlinePresenter() {
        mModel = new OnlineModel();
    }

    public void requetData(String type, String key) {
        mModel.getData(this, type, key);
    }

    @Override
    public void onSuccess(List<OnlineBean.GroupBean> data) {
        mView.showData(data);
    }

    @Override
    public void onFailure(String msg) {
        mView.showToast(msg);
    }

    @Override
    public void onComplete() {

    }
}
