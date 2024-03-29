package com.example.four.betternews.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018/4/16 0016.
 */

public class HttpUtil {
    private static final String TAG = "HttpUtil";

    /**
     * 请求方式 : GET
     *
     * @param address  请求地址
     * @param listener 自定义的回调接口，用于向主线程返回请求结果
     */
    public static void sendHttpUrlConnectionResquest(final String address, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                InputStream is = null;
                ByteArrayOutputStream os = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.setConnectTimeout(8 * 1000);
                    connection.setReadTimeout(8 * 1000);

                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        is = connection.getInputStream();
                        os = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = is.read(buffer)) != -1) {
                            os.write(buffer, 0, len);
                        }
                        String response = os.toString();
                        if (listener != null) {
                            listener.onResponse(response);
                        }
                    }

                } catch (Exception e) {
                    if (listener != null) {
                        listener.onFailure(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }


    /**
     * @return 发起网络请求之前检查网络状态是否可用
     */
    public static boolean networkStatus(Context context) {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            return true;
        }
        ToastUtil.showToast(context,"请检查您的网络连接...", Toast.LENGTH_SHORT);
        return false;
    }
}

