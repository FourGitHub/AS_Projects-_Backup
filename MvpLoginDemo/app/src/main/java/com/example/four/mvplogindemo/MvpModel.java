package com.example.four.mvplogindemo;

import android.util.Log;

import com.example.four.mvplogindemo.JavaBean.LoginBean;
import com.example.four.mvplogindemo.NetUtil.OkHttpUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/15 0015.
 */

public class MvpModel {
    private static final String TAG = "MvpModel";

    public static final String ADDRESS = "https://wx.idsbllp.cn/cyxbsMobile/index.php/Home/Person/search";

    public static void login(final String account, final String password, final MvpCallback callback) {
        OkHttpUtils.postResquestWithOkHttp(ADDRESS, new FormBody.Builder().add("stuNum", account).add("idNum",
                password).build(), new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                callback.checkFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.i(TAG, "onResponse: " + responseData);
                Gson gson = new Gson();
                LoginBean stuInfo = gson.fromJson(responseData, LoginBean.class);

                if (stuInfo.getInfo().equals("success")) {
                    if (stuInfo.getState() == 200 && stuInfo.getState() == 200) {
                        if (stuInfo.getData() != null) {
                            callback.checkSucccess();
                        } else {
                            callback.noSuchStuId();
                        }
                    } else {
                        callback.noSuchStuId();
                    }
                } else {
                    callback.checkFailed();
                }

            }
        });
    }
}
