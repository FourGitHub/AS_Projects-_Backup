package com.mredrock.cyxbs.freshman.MVP.Presenter;

import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.MVP.Model.MienModel;
import com.mredrock.cyxbs.freshman.MVP.View.MienView;
import com.mredrock.cyxbs.freshman.Network.Network;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/16 0016 15:14
 */
public class MienPresenter extends BasePresenter<MienView> implements BaseCallBack<List<CampusBean.DataBean>>{
    private MienModel mModel;

    public MienPresenter() {
        mModel = new MienModel();
    }
    public void getData(String index){
        mModel.getData(this,index);
    }

    @Override
    public void onSuccess(List<CampusBean.DataBean> data) {
        mView.showData(data);
    }

    @Override
    public void onFailure(String msg) {

    }


    @Override
    public void onComplete() {

    }
}
