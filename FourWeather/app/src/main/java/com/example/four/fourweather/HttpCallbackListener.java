package com.example.four.fourweather;

/**
 * Created by Administrator on 2018/2/22 0022.
 * 自定义的Http请求的回调接口，用于返回服务器的返回结果或者报错，模仿自Okhttp3的Okhttp3.callback接口
 */

public interface HttpCallbackListener {

    void onResponse(String response);
    void onFailure(Exception e);
}
