package com.mredrock.cyxbs.freshman.MVP.View;

import android.content.Context;

import com.mredrock.cyxbs.freshman.Utility.ToastUtil;

/**
 * Created by FengHaHa on2018/8/8 0008 23:22
 */
public interface BaseView {

    default void showToast(String msg) {
        ToastUtil.makeToast(msg);
    }

    Context getContext();
}
