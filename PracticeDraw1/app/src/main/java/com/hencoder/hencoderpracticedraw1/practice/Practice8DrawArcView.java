package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice8DrawArcView extends View {
    private Paint mPaint = new Paint();

    public Practice8DrawArcView(Context context) {
        super(context);
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);

        canvas.drawArc(new RectF(getMeasuredWidth() / 4, getMeasuredHeight() / 4, 3 * getMeasuredWidth() >> 2, 3 * getMeasuredHeight() >> 2), -30, -100, true, mPaint);
        canvas.drawArc(new RectF(getMeasuredWidth() / 4, getMeasuredHeight() / 4, 3 * getMeasuredWidth() >> 2, 3 * getMeasuredHeight() >> 2), 20, 140, false, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(new RectF(getMeasuredWidth() / 4, getMeasuredHeight() / 4, 3 * getMeasuredWidth() >> 2, 3 * getMeasuredHeight() >> 2), 170, 40, false, mPaint);

        //        练习内容：使用 canvas.drawArc() 方法画弧形和扇形
    }
}
