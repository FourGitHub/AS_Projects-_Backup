package com.mredrock.cyxbs.freshman.MVP.Presenter;

import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.MVP.Model.DormitoryModel;
import com.mredrock.cyxbs.freshman.MVP.View.DormitoryView;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/18 0018 3:54
 */
public class DormitoryPresenter extends BasePresenter<DormitoryView> implements BaseCallBack<List<CampusBean.DataBean>> {
    private DormitoryModel mModel;

    public DormitoryPresenter() {
        mModel = new DormitoryModel();
    }

    public void requestData(String dormitoryName) {
        mModel.getData(this, dormitoryName);
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
