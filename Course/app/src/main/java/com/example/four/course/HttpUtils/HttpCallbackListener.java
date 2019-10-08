package com.example.four.course.HttpUtils;

/**
 * Created by Administrator on 2018/5/26 0026.
 */

public interface HttpCallbackListener {
    void onFailure(Exception e);
    void onResponse(final String response);
}