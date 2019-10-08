package com.mredrock.cyxbs.freshman.MVP.Model;

import android.util.Log;

import com.mredrock.cyxbs.freshman.Utility.SPHelper;

/**
 * Created by FengHaHa on2018/8/13 0013 21:21
 */
public class HomePageModel {
    private static final String TAG = "HomePageModel";

    public int getNowCarPos() {
        Log.d(TAG, "getNowCarPos: " + SPHelper.getInt("CarPos", 1));
        return SPHelper.getInt("CarPos", 1);

    }

    public void saveNowCarPos(int pos) {
        Log.d(TAG, "saveNowCarPos:  "+pos);
        SPHelper.saveInt("CarPos", pos);
    }
}
