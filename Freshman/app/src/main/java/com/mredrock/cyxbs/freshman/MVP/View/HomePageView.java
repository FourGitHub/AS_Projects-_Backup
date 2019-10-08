package com.mredrock.cyxbs.freshman.MVP.View;

import android.view.View;

/**
 * Created by FengHaHa on2018/8/13 0013 17:13
 */
public interface HomePageView extends BaseView{
        void showCarAnim(int pos);
        void showNowCar(int pos);
        void showBuildings(int pos);
        void onBuildingClick(View view);
}
