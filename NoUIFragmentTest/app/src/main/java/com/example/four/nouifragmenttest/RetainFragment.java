package com.example.four.nouifragmenttest;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2018/5/3 0003.
 */

public class RetainFragment extends Fragment {

    private MyTask.MyCallback mCallback;
    private MyTask myTask;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mCallback = (MyTask.MyCallback) context;

        if (myTask != null) {
            /* 让MyTask的进度返回给最新关联的Activity */
            myTask.setMwCallback(mCallback);
        }
    }

    /*
    在onCreate()方法中 设置RetainFragment 和 启动异步任务，因为在设置
    "Retain"后"，onCreate()方法只会执行一次。
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        myTask = new MyTask(mCallback);
        myTask.execute();

//        myTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    /*
    在关联新的Activity之前，将mCallback 置为 null
    ◆ 避免无效的回调
    ◆ 让旧Activity内存得到及时回收，因为RetainFragment不会因Activity重建而销毁
    但是 RetainFragment 持有旧Activity 的引用，这里释放了这个引用
    */
    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

}

