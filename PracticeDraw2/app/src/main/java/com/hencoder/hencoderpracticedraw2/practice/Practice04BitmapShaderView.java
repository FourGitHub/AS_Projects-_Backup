package com.hencoder.hencoderpracticedraw2.practice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw2.R;

public class Practice04BitmapShaderView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Practice04BitmapShaderView(Context context) {
        super(context);
    }

    public Practice04BitmapShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice04BitmapShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        // 用 Paint.setShader(shader) 设置一个 BitmapShader
        // Bitmap: R.drawable.batman
    }

    // 避免在 onDraw 方法中创建对象
    Shader shader = new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.batman),
            Shader.TileMode.MIRROR,
            Shader.TileMode.MIRROR);

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // NOTE：Bitmap是从PracticeView(下半截屏幕)的左上角开始绘制的
        int measuredHeight = getMeasuredHeight();

        paint.setShader(shader);
        canvas.drawCircle(measuredHeight>>1,
                measuredHeight>>1,
                measuredHeight>>1,
                paint);
    }
}
