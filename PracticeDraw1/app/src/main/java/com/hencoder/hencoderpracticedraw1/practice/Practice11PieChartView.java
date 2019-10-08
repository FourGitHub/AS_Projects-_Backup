package com.hencoder.hencoderpracticedraw1.practice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice11PieChartView extends View {
    private Paint mPiant = new Paint();

    public Practice11PieChartView(Context context) {
        super(context);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPiant.setColor(Color.YELLOW);
        canvas.drawArc(new RectF(250, 50, 1150, 850), -5, -40, true, mPiant);
        mPiant.setColor(Color.GREEN);
        canvas.drawArc(new RectF(250, 50, 1150, 850), 3, 5, true, mPiant);
        mPiant.setColor(Color.WHITE);
        canvas.drawArc(new RectF(250, 50, 1150, 850), 10, 5, true, mPiant);
        mPiant.setColor(Color.BLUE);
        canvas.drawArc(new RectF(250, 50, 1150, 850), 17, 53, true, mPiant);
        mPiant.setColor(Color.DKGRAY);
        canvas.drawArc(new RectF(250, 50, 1150, 850), 73, 107, true, mPiant);mPiant.setColor(Color.YELLOW);
        mPiant.setColor(Color.RED);
        canvas.drawArc(new RectF(220,20,1120,820),-48 , -128, true, mPiant);

    }
}
