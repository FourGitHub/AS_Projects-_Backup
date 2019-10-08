package com.hencoder.hencoderpracticedraw1.practice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.icu.text.MessagePattern;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice10HistogramView extends View {
    private Paint mPaint = new Paint();
    private Path mPath = new Path();

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绿色条柱
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(new RectF(110, 690, 240, 700), mPaint);
        canvas.drawRect(new RectF(260, 650, 390, 700), mPaint);
        canvas.drawRect(new RectF(410, 650, 540, 700), mPaint);
        canvas.drawRect(new RectF(560, 400, 690, 700), mPaint);
        canvas.drawRect(new RectF(710, 200, 840, 700), mPaint);
        canvas.drawRect(new RectF(860, 100, 990, 700), mPaint);
        canvas.drawRect(new RectF(1010, 350, 1140, 700), mPaint);

        // 坐标轴
        mPath.moveTo(100,100);
        mPath.lineTo(100,700);
        mPath.rLineTo(1200,0);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.WHITE);
        canvas.drawPath(mPath, mPaint);

        // 直方图
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(80);
        canvas.drawText("直方图", 600, 900, mPaint);

        // X轴取值
        mPaint.setTextSize(35);
        mPaint.setStrokeWidth(2);
        canvas.drawText("Froyo", 120, 750, mPaint);
        canvas.drawText("GB", 290, 750, mPaint);
        canvas.drawText("IC S", 440, 750, mPaint);
        canvas.drawText("JB", 590, 750, mPaint);
        canvas.drawText("KitKat", 720, 750, mPaint);
        canvas.drawText("L", 900, 750, mPaint);
        canvas.drawText("M", 1050, 750, mPaint);
    }
}
