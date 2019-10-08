package com.mredrock.cyxbs.freshman.MVP.Model;

import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.BaseCallBack;
import com.mredrock.cyxbs.freshman.Network.Network;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by FengHaHa on2018/8/18 0018 3:51
 */
public class DormitoryModel {
    public void getData(BaseCallBack<List<CampusBean.DataBean>> mCallback, String dormitoryName) {
        Network.sendRequest(Network.getApi().getDormitory(dormitoryName), new Observer<CampusBean>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CampusBean campusBean) {
                mCallback.onSuccess(campusBean.getData());
            }

            @Override
            public void onError(Throwable e) {
                mCallback.onError(e);
            }

            @Override
            public void onComplete() {
                mCallback.onComplete();
            }
        });
    }
}