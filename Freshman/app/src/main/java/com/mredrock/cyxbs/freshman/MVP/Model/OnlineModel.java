package com.mredrock.cyxbs.freshman.MVP.Model;

import com.mredrock.cyxbs.freshman.DataBean.OnlineBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.BaseCallBack;
import com.mredrock.cyxbs.freshman.Network.Network;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by FengHaHa on2018/8/17 0017 23:47
 */
public class OnlineModel {
    public void getData(BaseCallBack<List<OnlineBean.GroupBean>> mCallBack,String type,String key) {

        Network.sendRequest(Network.getApi().getGroup(type,key), new Observer<OnlineBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(OnlineBean onlineBean) {
                mCallBack.onSuccess(onlineBean.getGroupList());
            }


            @Override
            public void onError(Throwable e) {
                mCallBack.onError(e);
            }

            @Override
            public void onComplete() {
                mCallBack.onComplete();
            }
        });
    }
}
