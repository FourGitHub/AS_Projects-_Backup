package com.hencoder.hencoderpracticedraw2.practice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.SumPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice12PathEffectView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path();

    Path pathShape = new Path();

    public Practice12PathEffectView(Context context) {
        super(context);
    }

    public Practice12PathEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12PathEffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setStyle(Paint.Style.STROKE);

        path.moveTo(50, 100);
        path.rLineTo(50, 100);
        path.rLineTo(80, -150);
        path.rLineTo(100, 100);
        path.rLineTo(70, -120);
        path.rLineTo(150, 80);


        pathShape.moveTo(15, 0);
        pathShape.rLineTo(15, 25);
        pathShape.rLineTo(-30, 0);
        pathShape.close();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 Paint.setPathEffect() 来设置不同的 PathEffect


        // 第一处：CornerPathEffect
        paint.setPathEffect(new CornerPathEffect(20));
        canvas.drawPath(path, paint);


        canvas.save();
        canvas.translate(500, 0);


        // 第二处：DiscretePathEffect
        paint.setPathEffect(
                new DiscretePathEffect(20, 5));
        canvas.drawPath(path, paint);


        canvas.restore();

        canvas.save();
        canvas.translate(0, 200);


        // 第三处：DashPathEffect
        paint.setPathEffect(
                new DashPathEffect(new float[]{20, 10, 5,10}, 0));
        canvas.drawPath(path, paint);


        canvas.restore();

        canvas.save();
        canvas.translate(500, 200);


        // 第四处：PathDashPathEffect, pathShape 为黑色实心等边三角形
        paint.setPathEffect(
                new PathDashPathEffect(pathShape,
                                       40,
                                       0,
                                       PathDashPathEffect.Style.MORPH));
        canvas.drawPath(path, paint);


        canvas.restore();

        canvas.save();
        canvas.translate(0, 400);


        // 第五处：SumPathEffect
        paint.setPathEffect(
                new SumPathEffect(
                        new DashPathEffect(new float[]{20, 10, 5, 10}, 0),
                        new DiscretePathEffect(20, 5)));
        canvas.drawPath(path, paint);


        canvas.restore();

        canvas.save();
        canvas.translate(500, 400);


        // 第六处：ComposePathEffect
        paint.setPathEffect(
                new ComposePathEffect (
                        new DashPathEffect(new float[]{20, 10, 5, 10}, 0),
                        new DiscretePathEffect(20, 5)));
        canvas.drawPath(path, paint);


        canvas.restore();
    }
}
