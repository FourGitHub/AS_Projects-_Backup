package com.mredrock.cyxbs.freshman.MVP.View;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by FengHaHa on2018/8/15 0015 17:26
 */
public interface CampusStrategyView extends BaseView{
    void onItemClick(View view);
    void enterDetails(Fragment fragment);
    void setTitle(String title);
    void onBack();
}
