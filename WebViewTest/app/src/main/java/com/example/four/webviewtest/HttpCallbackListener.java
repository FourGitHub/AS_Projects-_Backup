package com.example.four.webviewtest;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
