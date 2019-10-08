package com.mredrock.cyxbs.freshman.MVP.Presenter;

import com.mredrock.cyxbs.freshman.DataBean.CampusBean;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/15 0015 20:12
 */
public interface CSCallBack extends BaseCallBack<List<CampusBean.DataBean>>{
    void onSuccess(List<CampusBean.DataBean> beanList,int type,String name);
}
