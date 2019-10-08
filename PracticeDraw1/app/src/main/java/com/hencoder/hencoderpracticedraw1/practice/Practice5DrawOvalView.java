package com.hencoder.hencoderpracticedraw1.practice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice5DrawOvalView extends View {
    private Paint mPaint = new Paint();
    private Path mPath = new Path();

    public Practice5DrawOvalView(Context context) {
        super(context);
    }

    public Practice5DrawOvalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice5DrawOvalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 canvas.drawOval() 方法画椭圆
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        canvas.drawOval(new RectF(getMeasuredWidth() >> 2,
                                  getMeasuredHeight() >> 2,
                                  3 * getMeasuredWidth() >> 2,
                                  3 * getMeasuredHeight() >> 2), mPaint);

        // 弧线是基于椭圆的
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setColor(Color.RED);
        mPath.lineTo(getMeasuredWidth() >> 2, getMeasuredHeight() >> 2);
        mPath.arcTo(new RectF(getMeasuredWidth() >> 2,
                getMeasuredHeight() >> 2,
                3 * getMeasuredWidth() >> 2,
                3 * getMeasuredHeight() >> 2),
                -90, 90, true);
        canvas.drawPath(mPath,mPaint);

    }
}
