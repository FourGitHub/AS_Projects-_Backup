package com.example.four.mvplogindemo.NetUtil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.four.mvplogindemo.ToastUtil.ToastUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/6/15 0015.
 */
public class OkHttpUtils {

    public static boolean isNetworkStatusOk(Context context) {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            return true;
        }
        ToastUtils.showToast(context,"网络连接异常", Toast.LENGTH_SHORT);
        return false;
    }

    public static void postResquestWithOkHttp(final String address, final RequestBody requestBody, final okhttp3.Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(address)
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(callback);
            }
        }).start();
    }
}
