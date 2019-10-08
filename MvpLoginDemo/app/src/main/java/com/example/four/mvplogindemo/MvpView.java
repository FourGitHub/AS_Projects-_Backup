package com.example.four.mvplogindemo;

/**
 * Created by Administrator on 2018/6/15 0015.
 */

public interface MvpView {

    /**
     * 账号密码验证通过，可登陆
     */
    void onCheckSuccess();

    /**
     * 验证失败
     */
    void onCheckFailed();

    /**
     * 密码为空
     */
    void onPasswordEmpty();

    /**
     * 账号为空
     */
    void onAccountEmpty();

    void onNoSuchStuId();

    void onBadNetwork();

}
