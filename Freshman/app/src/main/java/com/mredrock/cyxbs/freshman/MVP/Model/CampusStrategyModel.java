package com.mredrock.cyxbs.freshman.MVP.Model;

import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.CSCallBack;
import com.mredrock.cyxbs.freshman.Network.Network;
import com.mredrock.cyxbs.freshman.Utility.SPHelper;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by FengHaHa on2018/8/15 0015 17:39
 */
public class CampusStrategyModel {
    String[] indexs = new String[]{"学生食堂", "学生寝室", "周边美食", "附近景点", "校园环境", "数据揭秘", "附近银行", "公交线路", "快递收发"};

    public void getData(CSCallBack callBack, int index) {

        CampusBean bean = SPHelper.getBean(indexs[index], CampusBean.class);
        if (bean!=null){
            List<CampusBean.DataBean> beanList =bean.getData();
            callBack.onSuccess(beanList,index, indexs[index]);
        } else {
            Network.sendRequest(Network.getApi().getCampus(indexs[index], 1, 1000), new Observer<CampusBean>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(CampusBean campusBean) {
                    //if (campusBean.getData().size() > 0)
                    callBack.onSuccess(campusBean.getData(), index, indexs[index]);
                    saveData(campusBean, indexs[index]);
                    //  else callBack.onFailure(Const.FAILURE_MSG);
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

    private void saveData(CampusBean bean, String key) {
        SPHelper.saveBean(key, bean);
    }
}
