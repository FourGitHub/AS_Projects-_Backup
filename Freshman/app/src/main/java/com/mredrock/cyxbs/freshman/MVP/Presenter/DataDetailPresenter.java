package com.mredrock.cyxbs.freshman.MVP.Presenter;

import com.mredrock.cyxbs.freshman.DataBean.SexRatioBean;
import com.mredrock.cyxbs.freshman.DataBean.SubjectBean;
import com.mredrock.cyxbs.freshman.MVP.Model.DataDetailModel;
import com.mredrock.cyxbs.freshman.MVP.View.DataDetailView;

/**
 * Created by FengHaHa on2018/8/18 0018 14:08
 */
public class DataDetailPresenter extends  BasePresenter<DataDetailView>{
    private DataDetailModel mModel;

    public DataDetailPresenter() {
        mModel = new DataDetailModel();
    }
    public void requestData(String name){
        mModel.getData(name, new BaseCallBack<Object>() {
            @Override
            public void onSuccess(Object data) {
                if (data instanceof SexRatioBean)
                    mView.showData((SexRatioBean) data,null);
                else if (data instanceof SubjectBean)
                    mView.showData(null, (SubjectBean) data);
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
