package com.example.four.webviewtest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class HttpURLConnectionUtils {
    private static final String TAG = "HttpURLConnectionUtils";
    public static void sendHttpUrlConnectionRequest(final String address, final HttpCallbackListener callbackListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {

                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
//                    connection.setDoOutput(true);
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    int responseCode = connection.getResponseCode();
                    Log.d(TAG, "responseCode = " + responseCode);
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        if (callbackListener != null) {
                            callbackListener.onFinish(response.toString());
                            Log.d(TAG, "返回结果" + response.toString());
                        }
                    }
                } catch (Exception e) {
                    if (callbackListener != null) {
                        callbackListener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }


    public static void postHttpUrlConnectionRequest(final String address, final String post, final HttpCallbackListener callbackListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader reader = null;
                DataOutputStream outputStream = null;
                HttpURLConnection connection = null;
                try {
                    int i = 1;
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes(post);
                    // 提交数据后，获取输入流，读取数据，和之前的“GET”操作一样
                    int responseCode = connection.getResponseCode();
                    Log.d(TAG, "responseCode =" + responseCode);
                    if (responseCode == 200){
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        if (callbackListener != null) {
                            callbackListener.onFinish(response.toString());
                            Log.d(TAG, "返回结果" + response.toString());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

}
