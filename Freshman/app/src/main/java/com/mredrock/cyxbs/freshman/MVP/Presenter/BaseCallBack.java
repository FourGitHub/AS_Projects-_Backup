package com.mredrock.cyxbs.freshman.MVP.Presenter;

/**
 * Created by FengHaHa on2018/8/8 0008 23:21
 */
public interface BaseCallBack<T> {
    void onSuccess(T data);

    void onFailure(String msg);

    default void onError(Throwable e) {
        e.printStackTrace();
    }

    void onComplete();
}
