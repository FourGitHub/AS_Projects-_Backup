package com.example.four.customviewtest.MyCustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/3/17 0017.
 */

public class CustomLayout extends ViewGroup {
    private static final String TAG = "CustomLayout";

    private int mPaddingLeft = getPaddingLeft();
    private int mPaddingTop = getPaddingTop();
    private int mPaddingRight = getPaddingRight();
    private int mPaddingBottom = getPaddingBottom();

    public CustomLayout(Context context) {
        super(context);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* ViewGroup 的测量过程 ，难点在于：计算子View的 MeasureSpec */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /* 内置方法，子View测量的简化版，
           对于所有子View，直接使用自己的 (MeasureSpec - Padding) 作为可用空间(size)
           而实际情况是：根据布局特征和子View的依次测量完成，可用空间是动态变化的 */
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int wideMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        /* 手动合并，计算和保存自己的尺寸 */
        int mWidth = 0;
        int mHeight = 0;
        int childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else {
            if (wideMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
                mWidth = getMaxWidth();
                mHeight = getTotalHeight();
            } else if (heightMode == MeasureSpec.AT_MOST) {
                mWidth = widthSize;
                mHeight = getTotalHeight();
            } else if (wideMode == MeasureSpec.AT_MOST) {
                mWidth = getMaxWidth();
                mHeight = heightSize;
            }

            mWidth = resolveSize(mWidth, widthMeasureSpec);
            mHeight = resolveSize(mHeight, heightMeasureSpec);
            setMeasuredDimension(mWidth, mHeight);
        }
    }

    private int getMaxWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (maxWidth < child.getMeasuredWidth()) {
                maxWidth = Math.max(maxWidth, child.getMeasuredWidth());
            }
        }
        return maxWidth + mPaddingLeft + mPaddingRight;
    }

    private int getTotalHeight() {
        int totalHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            totalHeight += child.getMeasuredHeight();
        }
        return totalHeight + mPaddingTop + mPaddingBottom;
    }


    /*
     * 此ViewGroup根据子View的测量尺寸 再结合自身的布局特征，将子View摆放到自己内部的合适位置
     * 布局特征模拟的是 Vertical LinearLayout
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();
        int curHeight = t + mPaddingTop;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int cMarginTop = lp.topMargin;
            int cMarginLeft = lp.leftMargin;
            int cMarginBottom = lp.bottomMargin;
            int cMarginRight = lp.rightMargin;

            int cHeight = child.getMeasuredHeight();
            int cWidth = child.getMeasuredWidth();
            child.layout(l + mPaddingLeft + cMarginLeft,
                         curHeight + cMarginTop,
                         l + mPaddingLeft + cWidth + cMarginRight,
                         curHeight + cMarginTop + cHeight + cMarginBottom);

            curHeight += cHeight + cMarginTop + cMarginBottom;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, getClass().getSimpleName() + " -> onDraw(): 执行了" + System.currentTimeMillis());
        super.onDraw(canvas);
    }



    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
