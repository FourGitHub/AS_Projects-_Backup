package com.mredrock.cyxbs.freshman.MVP.View;

import com.mredrock.cyxbs.freshman.DataBean.CampusBean;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/18 0018 2:19
 */
public interface EntranceView extends BaseView {
    void showData(List<CampusBean.DataBean> beanList);
}
