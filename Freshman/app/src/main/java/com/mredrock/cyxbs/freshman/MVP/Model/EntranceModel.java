package com.mredrock.cyxbs.freshman.MVP.Model;

import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.BaseCallBack;
import com.mredrock.cyxbs.freshman.Network.Network;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by FengHaHa on2018/8/18 0018 2:19
 */
public class EntranceModel {
    public void getData(BaseCallBack<List<CampusBean.DataBean>> callBack) {
        Network.sendRequest(Network.getApi().getCampus("报道流程", 1, 100), new Observer<CampusBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CampusBean campusBean) {
                callBack.onSuccess(campusBean.getData());
            }

            @Override
            public void onError(Throwable e) {
                callBack.onError(e);
            }

            @Override
            public void onComplete() {
                callBack.onComplete();
            }
        });
    }
}
