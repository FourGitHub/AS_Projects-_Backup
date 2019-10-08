package com.example.four.webviewtest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/2/7 0007.
 */

public class OkHttpUtils {
    public static void sendRequestWithOkHttp(final String address, final okhttp3.Callback callback) {
        Request request = new Request.Builder().url(address)
                .build();
        new OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()
                .newCall(request)
                .enqueue(callback);

    }

    public static void postResquestWithOkHttp(final String address, final RequestBody requestBody, final
    okhttp3.Callback callback) {
        Request request = new Request.Builder().url(address)
                .post(requestBody)
                .build();
        new OkHttpClient.Builder().connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build()
                .newCall(request)
                .enqueue(callback);
    }
}
