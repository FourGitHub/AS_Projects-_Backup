package com.hencoder.hencoderpracticedraw2.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw2.R;

public class Practice08XfermodeView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap1;
    Bitmap bitmap2;
    Xfermode xfermode1 = new PorterDuffXfermode(PorterDuff.Mode.SRC);
    Xfermode xfermode2 = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    Xfermode xfermode3 = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

    public Practice08XfermodeView(Context context) {
        super(context);
    }

    public Practice08XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice08XfermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.batman);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.batman_logo);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 调用 canvas.saveLayer() 开启 off-screen buffer
        int save = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(bitmap1, 0, 0, paint);
        paint.setXfermode(xfermode1);  // SRC
        canvas.drawBitmap(bitmap2, 0, 0, paint);
        paint.setXfermode(null);



        canvas.drawBitmap(bitmap1, bitmap1.getWidth() + 100, 0, paint);
        paint.setXfermode(xfermode2);  // DST_IN
        canvas.drawBitmap(bitmap2, bitmap1.getWidth() + 100, 0, paint);
        paint.setXfermode(null);



        canvas.drawBitmap(bitmap1, 0, bitmap1.getHeight() + 20, paint);
        paint.setXfermode(xfermode3);  // DST_OUT
        canvas.drawBitmap(bitmap2, 0, bitmap1.getHeight() + 20, paint);
        paint.setXfermode(null);

        // 用完之后恢复 off-screen buffer
        canvas.restoreToCount(save);
    }
}
