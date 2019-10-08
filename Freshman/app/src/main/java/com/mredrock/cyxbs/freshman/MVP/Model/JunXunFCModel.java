package com.mredrock.cyxbs.freshman.MVP.Model;

import com.mredrock.cyxbs.freshman.DataBean.JunXunFCBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.BaseCallBack;
import com.mredrock.cyxbs.freshman.Network.Network;
import com.mredrock.cyxbs.freshman.Utility.Const;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by FengHaHa on2018/8/17 0017 3:31
 */
public class JunXunFCModel {
    public void getData(BaseCallBack<JunXunFCBean> callBack) {
        Network.sendRequest(Network.getApi().getJunxunFC(), new Observer<JunXunFCBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(JunXunFCBean junXunFCBean) {
                if (junXunFCBean.getPictureList().size() > 0)
                    callBack.onSuccess(junXunFCBean);
                else callBack.onFailure(Const.FAILURE_MSG);
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
