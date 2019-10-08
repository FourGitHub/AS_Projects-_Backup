package com.mredrock.cyxbs.freshman.MVP.Presenter;

import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.MVP.Model.JunXunTipsModel;
import com.mredrock.cyxbs.freshman.MVP.View.JunXunTipsView;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/15 0015 22:21
 */
public class JunXunTipsPresenter extends BasePresenter<JunXunTipsView> implements BaseCallBack<List<CampusBean.DataBean>> {
    JunXunTipsModel mModel;

    public JunXunTipsPresenter() {
        mModel = new JunXunTipsModel();
    }

    public void getTips() {
        mModel.getData(this);
    }

    @Override
    public void onSuccess(List<CampusBean.DataBean> data) {
        mView.setTips(data);
    }

    @Override
    public void onFailure(String msg) {
        mView.showToast(msg);
    }



    @Override
    public void onComplete() {

    }
}
