package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice2DrawCircleView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Practice2DrawCircleView(Context context) {
        super(context);
    }

    public Practice2DrawCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice2DrawCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 练习内容：使用 canvas.drawCircle() 方法画圆

        int quarterHeight = getMeasuredHeight() / 4;
        int quarterWidth = getMeasuredWidth() / 4;
        int radius = getMeasuredHeight() / 5;

        // 黑色实心圆
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(quarterWidth, quarterHeight, radius, mPaint);

        // 蓝色实心圆
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(quarterWidth, quarterHeight * 3, radius, mPaint);

        // 线宽为20的空心圆
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(50);
        canvas.drawCircle(quarterWidth * 3, quarterHeight * 3, radius, mPaint);

        // 线宽为5空心圆
        mPaint.setStrokeWidth(5);
        canvas.drawCircle(quarterWidth * 3, quarterHeight, radius, mPaint);


    }
}
