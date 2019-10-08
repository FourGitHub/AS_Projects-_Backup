package com.mredrock.cyxbs.freshman.MVP.View;

import com.mredrock.cyxbs.freshman.DataBean.SaveBean.DescribeBean;


import java.util.List;

/**
 * Created by FengHaHa on2018/8/9 0009 12:17
 */
public interface RuXueView extends BaseView {
    void exit();

    void vibrate();

    void showList(List<DescribeBean> list);

    void addNewItem(DescribeBean bean);

    void edit();

    void cancelEdit();
    void showAddMoreWindow();

    void showInformation();

    void saveData();

}
