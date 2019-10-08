package com.example.four.materialtest;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by Administrator on 2018/1/14 0014.
 */

/**
 * 给 RecyclerView 注册 OnScrollListener可以在RecyclerView上发生滚动事件时接收消息。
 */
public abstract class EndLessOnScrollListener extends RecyclerView.OnScrollListener {
    private static final String TAG = "EndLessOnScrollListener";
    //声明一个GridLayoutManager，引用我的layoutmanager
    private GridLayoutManager mGridLayoutManager = null;

    //recyclerView中已经加载出来的Item的数量，包括屏幕外的Item
    private int totalItemCount = 0;

    //用来存储上一次加载时recyclerViewz的totalItemCount
    private int previousTotal = 0;

    //屏幕上可见的item的数量
    private int visibleItemCount = 0;

    //在屏幕可见的Item中，第一个Item的position
    private int firstVisibleItemPosition = 0;

    //totalItemCount - visibleItemCount <= firstVisibleItem
    //是否正在上拉数据
    private boolean loading = true;

    public EndLessOnScrollListener(GridLayoutManager gridLayoutManager) {
        this.mGridLayoutManager = gridLayoutManager;
    }

    /**
     * 一旦recyclerVirew发送滑动动作，这两个方法就会得到调用
     * @param dx 水平滚动的数量
     * @param dy 垂直滚动的数量
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        Log.i(TAG, "哈水平滚动的数量: " + dx);
        Log.i(TAG, "哈垂直滚动的数量: " + dy);
        // 返回当前屏幕内可见的item数，这个数的大小是不一定的，哪怕item只出现半个，count也 +1
        visibleItemCount = recyclerView.getChildCount();

        // 返回绑定到RecyclerView的adapter中的item数。
        totalItemCount = mGridLayoutManager.getItemCount();

        // 第一个可见item在适配器中的位置或RecyclerView.NO_POSITION（如果没有任何可见项目）。
        firstVisibleItemPosition = mGridLayoutManager.findFirstVisibleItemPosition();


        // 动态判断
        if (loading) {

            if (totalItemCount > previousTotal) {
                //说明数据已经加载结束
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        //开始加载图片，分析这个条件判断:
        /**
         * 启动应用程序时，第一个可见的item的位置（firstVisibleItemPosition）肯定是0， 随着屏幕往下滑动，
         * firstVisibleItemPosition会随之增加，当增加到 firstVisibleItemPosition + 当前屏幕内显示的item的总数时，
         * 表示数据之前加载的数据已经全部在屏幕上显示了，此时如果需要继续显示新的数据，就应该执行下一次加载操作。
         */
        if (!loading && totalItemCount  <= (firstVisibleItemPosition + visibleItemCount)) {
            onLoadMore();
            loading = true;
        }
    }

    /**
     * 回调方法在RecyclerView的滚动状态改变时被调用。
     * @param newState 可能是 SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING or SCROLL_STATE_SETTLING.
     *  SCROLL_STATE_SETTLING - RecyclerView目前正在动画到最终位置，但不受外界控制。
     * SCROLL_STATE_DRAGGING - RecyclerView当前正在被外部输入（例如用户触摸输入）拖动。
     * SCROLL_STATE_IDLE - RecyclerView当前不在滚动。
     */
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        Log.i(TAG, "哈onScrollStateChanged: " + newState);
        super.onScrollStateChanged(recyclerView, newState);
    }

    /**
     * 提供一个抽象方法，在Activity中监听到这个EndLessOnScrollListener，并且实现这个方法
     */
    public abstract void onLoadMore();
}


