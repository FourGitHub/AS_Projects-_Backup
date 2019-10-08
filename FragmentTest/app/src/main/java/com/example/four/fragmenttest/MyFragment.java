package com.example.four.fragmenttest;

import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.Fragment;

/**
 * Create on 2019/03/19
 *
 * @author Four
 * @description
 */
public class MyFragment extends Fragment {

    /* 如果实例化Fragment需要传递参数的话，这是比较好的方式*/
    public static MyFragment newInstance(int argument){
        MyFragment fragment = new MyFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("key", argument);

        /* 然后在 onCreate()方法中取出 */
        fragment.setArguments(arguments);
        return fragment;
    }


}
