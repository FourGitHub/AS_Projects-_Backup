package com.mredrock.cyxbs.freshman.MVP.Presenter;

import android.content.Intent;
import android.util.Log;

import com.mredrock.cyxbs.freshman.MVP.Model.HomePageModel;
import com.mredrock.cyxbs.freshman.MVP.View.HomePageView;
import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.UI.Activity.CampusStrategyActivity;
import com.mredrock.cyxbs.freshman.UI.Activity.EntranceActivity;
import com.mredrock.cyxbs.freshman.UI.Activity.IWannaSayActivity;
import com.mredrock.cyxbs.freshman.UI.Activity.JunXunActivity;
import com.mredrock.cyxbs.freshman.UI.Activity.MienActivity;
import com.mredrock.cyxbs.freshman.UI.Activity.OnlineActivity;
import com.mredrock.cyxbs.freshman.UI.Activity.RuXueActivity;

/**
 * Created by FengHaHa on2018/8/13 0013 15:34
 */
public class HomePagePresenter extends BasePresenter<HomePageView> {


    private HomePageModel mModel;
    private boolean isPlaying = false;
    private Class[] classes = new Class[]{RuXueActivity.class, CampusStrategyActivity.class, OnlineActivity.class, EntranceActivity.class, IWannaSayActivity.class, JunXunActivity.class, MienActivity.class};

    public void initViews() {
        int pos = mModel.getNowCarPos();
        mView.showNowCar(pos);
        mView.showBuildings(pos);
    }

    public HomePagePresenter() {
        mModel = new HomePageModel();
    }

    public void onResume() {
        mView.showNowCar(mModel.getNowCarPos());
    }

    public void onBuildingClick(int id) {

        if (isPlaying) return;
        int index;
        switch (id) {
            case R.id.iv_home_ruxue:
                index = 1;
                break;
            case R.id.iv_home_gonglue:
                index = 2;
                break;
            case R.id.iv_home_jiaoliu:
                index = 3;
                break;
            case R.id.iv_home_process:
                index = 4;
                break;
            case R.id.iv_home_i_want_say:
                index = 5;
                break;
            case R.id.iv_home_junxun:
                index = 6;
                break;
            case R.id.iv_home_fengcai:
                index = 7;
                break;
            default:
                index = -1;
        }
        Log.d("on", "onBuildingClick:    click = " + index + "  no  pos = " + mModel.getNowCarPos() + "  isplaying = " + isPlaying);

        int next = mModel.getNowCarPos() + 1;
        if (index < next || index == 6 || index == 7) {
            startActivity(index);
        } else if (index == next) {
            isPlaying = true;
            mView.showCarAnim(index);
        }
    }

    public void onAnimEnd(int pos) {
        mModel.saveNowCarPos(pos);
        isPlaying = false;
        startActivity(pos);
    }

    public void startActivity(int index) {
        getView().getContext().startActivity(new Intent(getView().getContext(), classes[index - 1]));
    }
}
