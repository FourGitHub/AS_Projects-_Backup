package com.example.four.mvpdemo;


import android.os.Handler;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public class MvpModel {

    // 利用postDelayed()方法模拟网络请求耗时2s
    public static void getNetData(final String param, final MvpCallback callback) {
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               switch (param) {
                   case "normal":
                       callback.onSuccess("根据参数 " + param + "， 网络请求成功");
                       break;
                   case "failure":
                       callback.onFailure("请求失败：参数有误");
                       break;
                   case "error":
                       callback.onError();
                       break;
               }
               callback.onCompleted();

           }
       },2000);
    }

}
