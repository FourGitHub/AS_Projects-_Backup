package com.mredrock.cyxbs.freshman.MVP.View;

import com.mredrock.cyxbs.freshman.DataBean.OnlineBean;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/17 0017 23:33
 */
public interface OnlineView extends BaseView{
    void showData(List<OnlineBean.GroupBean> groupBeanList);
}
