package com.mredrock.cyxbs.freshman.MVP.Presenter;

import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.MVP.Model.EntranceModel;
import com.mredrock.cyxbs.freshman.MVP.View.EntranceView;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/18 0018 2:19
 */
public class EntrancePresenter extends BasePresenter<EntranceView> implements BaseCallBack<List<CampusBean.DataBean>> {
    EntranceModel mModel;

    public EntrancePresenter() {
        mModel = new EntranceModel();
    }

    public void requestData() {
        mModel.getData(this);
    }

    @Override
    public void onSuccess(List<CampusBean.DataBean> data) {
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
