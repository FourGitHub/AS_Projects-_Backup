package com.mredrock.cyxbs.freshman.MVP.Presenter;


import com.mredrock.cyxbs.freshman.DataBean.SaveBean.DescribeBean;
import com.mredrock.cyxbs.freshman.MVP.Model.RuXueModel;
import com.mredrock.cyxbs.freshman.MVP.View.RuXueView;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/11 0011 1:06
 */
public class RuXuePresenter extends BasePresenter<RuXueView> implements BaseCallBack<List<DescribeBean>>{
    private RuXueModel mModel;

    public RuXuePresenter() {
        mModel = new RuXueModel();
    }

    public void saveData(List<DescribeBean> saveBean) {
        mModel.saveData(saveBean);
    }
    public void showList(){
        mModel.getData(this);
    }



    @Override
    public void onSuccess(List<DescribeBean> data) {
        mView.showList(data);
    }

    @Override
    public void onFailure(String msg) {
        mView.showToast(msg);
    }

    @Override
    public void onComplete() {

    }
}
