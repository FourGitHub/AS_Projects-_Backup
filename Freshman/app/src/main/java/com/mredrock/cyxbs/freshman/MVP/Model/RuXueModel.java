package com.mredrock.cyxbs.freshman.MVP.Model;

import com.mredrock.cyxbs.freshman.DataBean.Description;
import com.mredrock.cyxbs.freshman.DataBean.SaveBean.DescribeBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.BaseCallBack;
import com.mredrock.cyxbs.freshman.Network.Network;
import com.mredrock.cyxbs.freshman.Utility.DataBaseUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by FengHaHa on2018/8/10 0010 12:46
 */
public class RuXueModel {
    private static final String TAG = "RuXueModel";

    public void getData(BaseCallBack<List<DescribeBean>> callBack) {
        List<DescribeBean> mRuXueBeanList = DataBaseUtil.getData(DescribeBean.class);
        //LitePal.findAll(DescribeBean.class);
        if (mRuXueBeanList.size() > 0) callBack.onSuccess(mRuXueBeanList);
        else
        {
            Network.sendRequest(Network.getApi().getDescribe("入学必备"), new Observer<Description>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Description description) {
                    if (description.getData().size()>0)
                    callBack.onSuccess(description.getData());
                    else callBack.onFailure("请求失败，请检查你的网络！");
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

    public void saveData(List<DescribeBean> saveBean) {
       DataBaseUtil.saveData(DescribeBean.class,saveBean);
    }
}
