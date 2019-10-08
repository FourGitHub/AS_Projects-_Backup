package com.example.four.mvpdemo;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public interface MvpCallback {

    /**
     * 请求成功
     * @param data 请求到的数据
     */
    void onSuccess(String data);

    /**
     * 请求成功，但是无法返回数据。
     * @param msg
     */
    void onFailure(String msg);

    /**
     * 请求数据失败，指在请求网络时，出现无法联网、缺少权限内存溢出等原因无法正常返回数据
     */
    void onError();

    /**
     * 当请求数据结束时，无论请求结果是成功、失败、或是抛出异常都会执行此方法给用户做处理
     * 通常做网络请求时可以在此处隐藏 “正在加载” 等控件
     */
    void onCompleted();

}
