package com.mredrock.cyxbs.freshman.MVP.Model;

import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.BaseCallBack;
import com.mredrock.cyxbs.freshman.Network.Network;
import com.mredrock.cyxbs.freshman.Utility.Const;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by FengHaHa on2018/8/16 0016 15:13
 */
public class MienModel {
    public void getData(BaseCallBack<List<CampusBean.DataBean>> mCallBack, String index){
        Network.sendRequest(Network.getApi().getCampus(index, 1,100), new Observer<CampusBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CampusBean campusBean) {
               if (campusBean.getData().size()>0)
                   mCallBack.onSuccess(campusBean.getData());
               else mCallBack.onFailure(Const.FAILURE_MSG);
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
