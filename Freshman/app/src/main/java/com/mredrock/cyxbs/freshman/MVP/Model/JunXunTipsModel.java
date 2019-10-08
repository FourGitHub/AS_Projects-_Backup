package com.mredrock.cyxbs.freshman.MVP.Model;

import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.BaseCallBack;
import com.mredrock.cyxbs.freshman.Network.Network;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by FengHaHa on2018/8/15 0015 22:20
 */
public class JunXunTipsModel {
    public void getData(BaseCallBack<List<CampusBean.DataBean>> mCallBack) {
        Network.sendRequest(Network.getApi().getCampus("军训小贴士", 1, 100), new Observer<CampusBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CampusBean campusBean) {
                if (campusBean.getData().size()>0)
                mCallBack.onSuccess(campusBean.getData());
                else mCallBack.onFailure("请求失败，请检查你的网络!");
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
