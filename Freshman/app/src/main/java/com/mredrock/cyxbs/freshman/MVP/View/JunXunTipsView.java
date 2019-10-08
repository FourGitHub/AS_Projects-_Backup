package com.mredrock.cyxbs.freshman.MVP.View;

import com.mredrock.cyxbs.freshman.DataBean.CampusBean;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/15 0015 22:24
 */
public interface JunXunTipsView extends BaseView{
    void setTips(List<CampusBean.DataBean> campusBeanList);
}
