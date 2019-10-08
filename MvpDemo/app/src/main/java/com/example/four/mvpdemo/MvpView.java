package com.example.four.mvpdemo;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public interface MvpView {

    /**
     * 显示“正在加载”控件
     */
    void showLoading();

    /**
     * 隐藏“正在加载”控件
     */
    void hideLoading();

    /**
     * 当数据请求成功后，调用此方法显示接口数据
     * @param data 数据源
     */
    void showData(String data);

    /**
     * 当数据请求失败后，调用次接口提示
     * @param msg 失败的原因
     */
    void showFailedMessage(String msg);

    /**
     * 当数据请求异常调用此方法
     */
    void showErrorMessage();
}
