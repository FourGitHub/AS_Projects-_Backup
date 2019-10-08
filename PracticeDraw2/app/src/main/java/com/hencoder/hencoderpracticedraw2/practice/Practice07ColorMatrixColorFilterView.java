package com.hencoder.hencoderpracticedraw2.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw2.R;

public class Practice07ColorMatrixColorFilterView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    ColorFilter colorFilter1;
    ColorFilter colorFilter2;

    public Practice07ColorMatrixColorFilterView(Context context) {
        super(context);
    }

    public Practice07ColorMatrixColorFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice07ColorMatrixColorFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 使用 setColorFilter() 设置一个 ColorMatrixColorFilter
    // 用 ColorMatrix.setSaturation() 把饱和度去掉
    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.batman);
        // 将 RGB 反转，在加上255以确保在颜色范围之内
        colorFilter1 = new ColorMatrixColorFilter(new float[]{
                 -1, 0, 0, 0, 255,
                 0, -1, 0, 0, 255,
                 0, 0, -1, 0, 255,
                 0, 0, 0, 1, 0
        });

        // 设置灰度 [0, 1] , 1 代表原色彩
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(1);
        colorFilter2 = new ColorMatrixColorFilter(matrix);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColorFilter(colorFilter1);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        paint.setColorFilter(colorFilter2);
        canvas.drawBitmap(bitmap, bitmap.getWidth() + 100, 0, paint);
    }
}
