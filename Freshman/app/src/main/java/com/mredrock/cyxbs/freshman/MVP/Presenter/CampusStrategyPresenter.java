package com.mredrock.cyxbs.freshman.MVP.Presenter;


import android.util.Log;

import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.MVP.Model.CampusStrategyModel;
import com.mredrock.cyxbs.freshman.MVP.View.CampusStrategyView;
import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.UI.Fragment.CampusFrg1;
import com.mredrock.cyxbs.freshman.UI.Fragment.CampusFrg2;
import com.mredrock.cyxbs.freshman.UI.Fragment.CampusFrg3;
import com.mredrock.cyxbs.freshman.UI.Fragment.DormitoryFrg;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/15 0015 17:38
 */
public class CampusStrategyPresenter extends BasePresenter<CampusStrategyView> implements CSCallBack {


    private CampusStrategyModel mModel;

    public CampusStrategyPresenter() {
        this.mModel = new CampusStrategyModel();
    }

    public void onItemClick(int id) {
        int index;
        switch (id) {
            case R.id.card_dining_hall:
                index = 0;
                break;
            case R.id.card_dormitory:
                mView.enterDetails(new DormitoryFrg());
                return;
            case R.id.card_food:
                index = 2;
                break;
            case R.id.card_view_spot:
                index = 3;
                break;
            case R.id.card_environment:
                index = 4;
                break;
            case R.id.card_data:
                index = 5;
                break;
            case R.id.card_bank:
                index = 6;
                break;
            case R.id.card_bus:
                index = 7;
                break;
            case R.id.card_express:
                index = 8;
                break;
            default:
                index = -1;
        }
        mModel.getData(this, index);
    }

    @Override
    public void onSuccess(List<CampusBean.DataBean> beanList, int index, String name) {
        mView.setTitle(name);
        Log.d("asdsad", "onSuccess: " + index + name);
        switch (index) {
            case 0:
            case 3:
            case 4:
            case 7:
                mView.enterDetails(new CampusFrg1(beanList, CampusFrg1.TYPE_OTHER_FRG));
                break;
            case 6:
            case 8:
                mView.enterDetails(new CampusFrg2(beanList));
                break;
            case 5:
                mView.enterDetails(new CampusFrg3());
                break;
            case 2:
                mView.enterDetails(new CampusFrg1(beanList, CampusFrg1.TYPE_FOOD_FRG));
                break;
        }
    }

    @Override
    public void onSuccess(List<CampusBean.DataBean> data) {
        //Just do nothing
    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void onComplete() {

    }


}
