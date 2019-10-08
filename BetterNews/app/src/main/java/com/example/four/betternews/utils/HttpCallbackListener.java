package com.example.four.betternews.utils;

/**
 * Created by Administrator on 2018/4/16 0016.
 */

public interface HttpCallbackListener {

    void onFailure(Exception e);
    void onResponse(final String response);
}

