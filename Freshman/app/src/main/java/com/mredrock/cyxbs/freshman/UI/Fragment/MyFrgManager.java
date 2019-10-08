package com.mredrock.cyxbs.freshman.UI.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/14 0014.
 */

public class MyFrgManager {

    private AppCompatActivity mContext;
    private FragmentManager mManager;


    // 装Fragment的容器（用于动态替换Frg的容器的id）
    private int mContainerId;


    //该Activity所有fragment的集合
    private List<Fragment> fragments;

    /**
     * @param context      FragmentActivity 实例
     * @param mContainerId 容器Id
     */
    public MyFrgManager(AppCompatActivity context, int mContainerId) {
        this.mContext = context;
        this.mContainerId = mContainerId;
        mManager = this.mContext.getSupportFragmentManager();
        fragments = new ArrayList<>();
    }

    /**
     * @param fragment    要替换的 fragment
     * @param tag         fragment 标签
     * @param isBackStack 是否要添加到返回栈，if ture, tag != null
     */
    public void replaceFrag(Fragment fragment, String tag, boolean isBackStack) {
        fragments.add(fragment);
        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.replace(mContainerId, fragment, tag);
        if (isBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    /**
     * @param fragment    要替换的 fragment
     * @param tag         fragment 标签
     * @param isBackStack 是否添加到返回栈
     */
    public void addFrag(Fragment fragment, String tag, boolean isBackStack) {
        fragments.add(fragment);
        FragmentTransaction fTransaction = mManager.beginTransaction();
        fTransaction.add(mContainerId, fragment, tag);
        if (isBackStack) {
            fTransaction.addToBackStack(tag);
        }
        fTransaction.commit();
    }

    /**
     * 通过tag获取到某个Fragment
     */
    public Fragment getFragmentByTag(String tag) {
        return mManager.findFragmentByTag(tag);
    }

    /**
     * 删除某个Fragment
     */
    public void removeFrag(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
    }

    /**
     * 隐藏Fragment 没有删除view
     *
     * @param fragment
     */
    public void hideFrag(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.hide(fragment);
            transaction.commit();
        }
    }

    /**
     * 显示Fragment
     *
     * @param fragment
     */
    public void showFrag(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.show(fragment);
            transaction.commit();
        }
    }

    public List<Fragment> getFragList() {
        return fragments;
    }

    public FragmentManager getFragManager() {
        return mManager;
    }

    public void setmContainerId(int mContainerId) {
        this.mContainerId = mContainerId;
    }

}
