package com.example.four.fthtest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/4/21 0021.
 */

public class Fragment_0 extends Fragment {

    /**
     * 当片段首次附加到其上下文时调用
     * @param context 碎片关联的上下文。
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * 被调用来做一个片段的初始创建。
     * 注意，在活动创建过程中(活动尚未创建好)调用片段的此方法。
     * 因此，我们还不能依赖于此时正在初始化的活动的内容视图层次结构。
     * 如果我们想在创建活动本身后进行工作，参看{@link #onActivityCreated（Bundle）}。
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @return Fragment实例化其用户界面视图。
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_0, container,false);
    }

    /**
     * 当 Fragment 关联的活动已经创建完成并且该片段的视图层次被实例化时调用。
     * 此回调告诉 Fragment 已经和新活动实例完全关联。
     * @param savedInstanceState 如果片段正在从先前保存的状态重新创建，则这是状态。
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 当 Fragment 对用户(后台)可见时调用。 这通常与Activity.onStart()关联。
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 当 Fragment 对用户(前台)可见时调用。 这通常与Activity.onResume()关联。
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 当 Fragment 对用户不再(后台)可见时调用。 这通常与Activity.onPause()关联。
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 当 Fragment 对用户不再(后台)可见时调用。 这通常与Activity.onStop()关联。
     */
    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * 当 Fragment 与活动取消关联的时候调用，紧接着或销毁这个 Fragment
     * 下次再使用它的时候需要重新创建它的View
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 当 Fragment 不再使用的时候调用
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 当 Fragment 不再和此活动关联的时候调用
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }
}

