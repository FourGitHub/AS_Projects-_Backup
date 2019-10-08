package com.mredrock.cyxbs.freshman.Utility;

import android.widget.Toast;

import org.litepal.LitePalApplication;

/**
 * Created by Administrator on 2018/8/10 0010.
 */
public class ToastUtil {
    public static void makeToast(String content) {
        Toast.makeText(LitePalApplication.getContext(), content, Toast.LENGTH_SHORT).show();
    }
}
