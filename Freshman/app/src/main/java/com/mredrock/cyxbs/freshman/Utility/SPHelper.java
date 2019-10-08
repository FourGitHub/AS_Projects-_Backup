package com.mredrock.cyxbs.freshman.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.litepal.LitePalApplication;

/**
 * Created by FengHaHa on2018/8/13 0013 15:40
 */
public class SPHelper {
    private static Gson gson = new Gson();
    private static SharedPreferences mSp = LitePalApplication.getContext().getSharedPreferences("FreshMan", Context.MODE_PRIVATE);
    private static final String TAG = "SPHelper";

    public static <T> void saveBean(String key, T bean) {
        Log.d(TAG, "saveBean: " + gson.toJson(bean));
        new Thread(() -> mSp.edit().putString(key, gson.toJson(bean)).apply()).start();
    }

    public static <T> T getBean(String key, Class<T> clazz) {
        Log.d(TAG, "getBean: " + mSp.getString(key, null));
        return gson.fromJson(mSp.getString(key, null), clazz);
    }

    public static void saveInt(String key, int value) {
        mSp.edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defValue) {
        return mSp.getInt(key, defValue);
    }

}
