package com.hencoder.hencoderpracticedraw6.practice.practice08;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static com.hencoder.hencoderpracticedraw6.Utils.dpToPixel;

public class Practice08ObjectAnimatorView extends View {
    final float radius = dpToPixel(80);

    RectF arcRectF = new RectF();
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // TODO 为 progress 添加 getter 和 setter 方法（setter 方法记得加 invalidate()）
    float progress = 0;
    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public Practice08ObjectAnimatorView(Context context) {
        super(context);
    }

    public Practice08ObjectAnimatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice08ObjectAnimatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setTextSize(dpToPixel(40));
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;


        paint.setColor(Color.parseColor("#E91E63"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(dpToPixel(15));
        arcRectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(arcRectF, 135, progress * 2.7f, false, paint);

        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.FILL);
        // 这里是怎么使得显示的 百分比位于圆弧形进度中心的？？？不是从中心点往右开始写吗？？ x方向文字内容怎么自动居中了？？
        canvas.drawText((int) progress + "%", centerX, centerY - (paint.ascent() + paint.descent()) / 2, paint);

        // 我打印出了（x，y）和 参考线，发现并不是位于文字内容的起始的左下角，为什么？？难道我把（x，y）理解错了？？？
        paint.setStrokeWidth(dpToPixel(10));
        paint.setColor(Color.BLACK);
        canvas.drawPoint(centerX,centerY - (paint.ascent() + paint.descent()) / 2, paint);

        paint.setStrokeWidth(dpToPixel(2));
        canvas.drawLine(centerX,0,centerX,getHeight(),paint);
        canvas.drawLine(0,centerY,getWidth(),centerY,paint);

        // 我操，居然是第46行设置了 paint.setTextAlign(Paint.Align.CENTER);
    }
}
