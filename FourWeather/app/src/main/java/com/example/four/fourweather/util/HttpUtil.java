package com.example.four.fourweather.util;

import android.util.Log;

import com.example.four.fourweather.HttpCallbackListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/2/19 0019.
 *
 * @author Four
 *         自定义工具类，用于发送网络请求
 */

public class HttpUtil {
    private static final String TAG = "HttpUtil";

    /**
     * 请求方式 : GET
     *
     * @param address  请求地址
     * @param listener 自定义的回调接口，用于向主线程返回请求结果
     */
    public static void sendHttpUrlConnectionResquest(String address, HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                InputStream is = null;
                ByteArrayOutputStream os = null;
                try {
                    URL url = new URL(address);
                    Log.i(TAG, "哈哈哈address: " + address);
                    Log.i(TAG, "哈哈哈url: " + url.toString());
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
                            Log.i(TAG, "哈哈哈response:" + response);
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
     * 请求方式 : POST
     *
     * @param address  请求地址
     * @param listener 自定义的回调接口，用于向主线程返回请求结果
     */
    public static void postHttpUrlConnectionResquest(String address, String request, HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                InputStream is = null;
                ByteArrayOutputStream os = null;
                DataOutputStream out = null;
                try {
                    URL url = new URL(address);
                    Log.i(TAG, "post哈哈哈address: " + address);
                    Log.i(TAG, "post哈哈哈url: " + url.toString());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setConnectTimeout(8 * 1000);
                    connection.setReadTimeout(8 * 1000);
                    out = new DataOutputStream(connection.getOutputStream());
                    out.writeBytes(request);

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
                            Log.i(TAG, "post哈哈哈response:" + response);
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
     * 请求方式 : GET
     *
     * @param address  请求地址
     * @param callback 注册一个回调接口
     *                 项目中未使用这个方法，之前的项目是用Okhttp3库实现的网络请求，之后改为HttpUrlConnection去实现
     */
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 请求方式 : POST
     *
     * @param address     请求地址
     * @param requestBody 构造好的请求体
     * @param callback    Okhttp3 的一个回调接口
     *                    项目中未使用这个方法，之前的项目是用Okhttp3库实现的网络请求，之后改为HttpUrlConnection去实现
     */
    public static void postOkHttpResquest(String address, RequestBody requestBody, okhttp3.Callback callback) {
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

