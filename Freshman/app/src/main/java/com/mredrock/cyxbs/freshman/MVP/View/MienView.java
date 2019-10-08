package com.mredrock.cyxbs.freshman.MVP.View;

import com.mredrock.cyxbs.freshman.DataBean.CampusBean;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/16 0016 15:14
 */
public interface MienView extends BaseView{
    void showData(List<CampusBean.DataBean> dataBeanList);
}
