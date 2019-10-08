package com.mredrock.cyxbs.freshman.MVP.Model;

import com.mredrock.cyxbs.freshman.DataBean.SexRatioBean;
import com.mredrock.cyxbs.freshman.DataBean.SubjectBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.BaseCallBack;
import com.mredrock.cyxbs.freshman.Network.Network;
import com.mredrock.cyxbs.freshman.Utility.SPHelper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by FengHaHa on2018/8/18 0018 14:07
 */
public class DataDetailModel {
    public void getData(String name, BaseCallBack<Object> callBack) {
        SexRatioBean sexRatioBean = SPHelper.getBean(name + "性别比例", SexRatioBean.class);
        SubjectBean subjectBean = SPHelper.getBean(name + "最难科目", SubjectBean.class);
        if (sexRatioBean != null) callBack.onSuccess(sexRatioBean);
        else {
            Network.sendRequest(Network.getApi().getSexRatioByCollege(name), new Observer<SexRatioBean>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(SexRatioBean sexRatioBean) {
                    callBack.onSuccess(sexRatioBean);
                    saveData(sexRatioBean, name + "性别比例");
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }

        if (subjectBean != null) callBack.onSuccess(subjectBean);
        else {
            Network.sendRequest(Network.getApi().getMostDifficultSubject(name), new Observer<SubjectBean>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(SubjectBean subjectBean) {
                    callBack.onSuccess(subjectBean);
                    saveData(subjectBean,name + "最难科目");
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });

        }
    }

    private void saveData(SexRatioBean bean, String key) {
        SPHelper.saveBean(key, bean);
    }
    private void saveData(SubjectBean bean, String key) {
        SPHelper.saveBean(key, bean);
    }
}
