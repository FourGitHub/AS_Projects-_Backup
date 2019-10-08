package com.mredrock.cyxbs.freshman.MVP.Presenter;

import com.mredrock.cyxbs.freshman.DataBean.JunXunFCBean;
import com.mredrock.cyxbs.freshman.MVP.Model.JunXunFCModel;
import com.mredrock.cyxbs.freshman.MVP.View.JunXunFCView;

/**
 * Created by FengHaHa on2018/8/17 0017 3:21
 */
public class JunXunFCPresenter extends BasePresenter<JunXunFCView> implements BaseCallBack<JunXunFCBean>{
    private JunXunFCModel mModel;
    public JunXunFCPresenter() {
        mModel = new JunXunFCModel();
    }

    public void getData(){
        mModel.getData(this);
    }

    @Override
    public void onSuccess(JunXunFCBean data) {
        mView.setData(data);
    }

    @Override
    public void onFailure(String msg) {
        mView.showToast(msg);
    }


    @Override
    public void onComplete() {

    }
}
