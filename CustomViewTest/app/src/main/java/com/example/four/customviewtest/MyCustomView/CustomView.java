package com.example.four.customviewtest.MyCustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.four.customviewtest.DensityUtil.DensityUtil;
import com.example.four.customviewtest.R;

/**
 * Created by Administrator on 2018/3/14 0014.
 */

public class CustomView extends View{

    private int px_defaultSize;
    private int radius_dimention;
    private int padding_left;
    private int padding_right;
    private int padding_top;
    private int padding_buttom;
    private int width;
    private int height;

    private static final String TAG = "CustomView";

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FourCustomAttrs);
        // 如果布局文件中没有设置的话，默认为200个像素
        // 虽然布局文件中我们是以 dp 为单位的，但是这个方法返回的是当前设备屏幕下的与dp等值的px值
        px_defaultSize = a.getDimensionPixelSize(R.styleable.FourCustomAttrs_default_size, 200);
        radius_dimention = a.getDimensionPixelSize(R.styleable.FourCustomAttrs_radius_dimention,DensityUtil.dip2px(getContext(),30));
        a.recycle();
        Log.i(TAG, "CustomView: dp:default_size = " + DensityUtil.px2dip(getContext(), px_defaultSize));
        Log.i(TAG, "CustomView: px:default_size = " + px_defaultSize);
    }

    /**
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        padding_left = getPaddingLeft();
        padding_top = getPaddingTop();
        padding_right = getPaddingRight();
        padding_buttom = getPaddingBottom();
        width = getMySize(px_defaultSize, widthMeasureSpec);
        height = getMySize(px_defaultSize, heightMeasureSpec);
        Log.i(TAG, getClass().getSimpleName() + " --> onMeasure(): " + System.currentTimeMillis());
        Log.i(TAG, "CustomView的父View是: " + getParent().getClass().getName());
        Log.i(TAG, "CustomView的宽Mode是不是AT_MOST: " + (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST));
        Log.i(TAG, "CustomView的高Mode是不是EXACTLY: " + (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY));

        height = Math.max(height, width);
        width = height;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, getClass().getSimpleName() + " --> onDraw(): " + System.currentTimeMillis());
        float centerX = width/2;
        float centerY = height/2;
        Log.i(TAG, getClass().getSimpleName() + " -> 正方形centerX: " + DensityUtil.px2dip(getContext(),centerX));
        Log.i(TAG, getClass().getSimpleName() + " -> 正方形centerY: " + DensityUtil.px2dip(getContext(),centerY));
        Log.i(TAG, getClass().getSimpleName() + " -> 正方形的宽度: " + DensityUtil.px2dip(getContext(),width));
        Log.i(TAG, getClass().getSimpleName() + " -> 正方形的高度: " + DensityUtil.px2dip(getContext(),height));
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawCircle(centerX, centerY, radius_dimention-padding_top , paint);
    }

    /**
     * 这个方法的作用就类似于getDefault(int size, int measureSpec) 方法
     *
     * @param defaultSize
     * @param measureSpec
     * @return
     */
    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int measureMode = MeasureSpec.getMode(measureSpec);
        int measureSize = Math.max(0,MeasureSpec.getSize(measureSpec));

        switch (measureMode) {
            // 如果未指定就设置为默认的大小
            case MeasureSpec.UNSPECIFIED:
                mySize = defaultSize;
                break;

            // 如果指定了肯定就设置为指定大小啊
            case MeasureSpec.EXACTLY:
                mySize = measureSize;
                break;

            // 如果测量模式是最大取值为 measureSize, 我们就取View在布局文件中声明的尺寸，当然也可以是其他值，只要小于measureSize就行
            case MeasureSpec.AT_MOST:
                if (defaultSize < measureSize)
                    mySize = defaultSize;
                else
                    mySize = measureSize;
                break;
        }
        return mySize;
    }

}
