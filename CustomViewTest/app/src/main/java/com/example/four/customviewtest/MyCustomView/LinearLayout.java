package com.example.four.customviewtest.MyCustomView;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import static android.content.ContentValues.TAG;

/**
 * Create on 2019/03/07
 *
 * @author Four
 * @description
 */
public class LinearLayout extends ViewGroup {

    public LinearLayout(Context context) {
        super(context);
    }

    public LinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 重写此方法时，必须调用setMeasuredDimension（int，int）来存储此视图的测量宽度和高度
     * @param widthMeasureSpec 父级强加给（限制/不能超过）此View的水平空间要求
     * @param heightMeasureSpec 父级强加给（限制/不能超过）此View的垂直空间要求
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 调用父级的onMeasure方法，帮我们测量背景的默认大小
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 对所有的子View进行测量，这将触发每个子View的onMeasure方法
        // 注意要与measureChild方法区分，measureChild是对单个view进行测量
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            // 宽高都是包裹内容,将宽度设为子View中最大的宽度,高度设置为所有子View的高度相加.
            setMeasuredDimension(getMaxChildWidth(), getChildTotalHeight());
        } else if (heightMode == MeasureSpec.AT_MOST) {
            // 只有高度是包裹内容,宽度设置为ViewGroup自己的测量宽度，高度设置为所有子View的高度总和
            setMeasuredDimension(width, getChildTotalHeight());
        } else if(widthMode == MeasureSpec.AT_MOST){
            // 只有宽度是包裹内容,宽度设置为子View中宽度最大的值，高度设置为ViewGroup自己的测量高度
            setMeasuredDimension(getMaxChildWidth(), height);
        }
    }

    /* 获取子View的最大宽度 */
    private int getMaxChildWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        while (childCount > 0) {
            View childView = getChildAt(--childCount);
            if (maxWidth < childView.getMeasuredWidth()) {
                maxWidth = childView.getMeasuredWidth();
            }
        }
        return maxWidth;
    }

    /* 获取子View的总高度 */
    private int getChildTotalHeight() {
        int childCount = getChildCount();
        int totalHeight = 0;
        while (childCount > 0) {
            View childView = getChildAt(--childCount);
            totalHeight += childView.getMeasuredHeight();
        }
        return totalHeight;
    }

    /**
     * 从父级的layout()方法中被调用，ViewGroup应当重写此方法，并在方法内调用子View的layout()方法，
     * 自顶向下的完成 “布局”
     * @param changed 视图是否已更改，如果为true，表示这是此ViewGroup的新大小或新位置，
     *                需要按照这个新大小或新位置从新摆放子View
     * @param l 此ViewGroup相对于其父级左侧的位置
     * @param t 此ViewGroup相对于其父级顶部的位置
     * @param r 此ViewGroup相对于其父级右侧的位置
     * @param b 此ViewGroup相对于其父级底部的位置
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 记录此ViewGroup可分配给子View的高度位置
        int currentHeight = t;

        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }

        // 依次摆放子View
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.layout(l, currentHeight, l + childView.getMeasuredWidth(),
                    currentHeight + childView.getMeasuredHeight());
            currentHeight += childView.getMeasuredHeight();
        }
    }


}

