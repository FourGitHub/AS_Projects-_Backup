package com.example.four.customviewtest.MyCustomView;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.four.customviewtest.R;

/**
 * Created by Administrator on 2018/4/1 0001.
 */

public class CuatomViewTest_1 extends View {

    private Paint paint;
    public CuatomViewTest_1(Context context) {
        super(context);
        paint = new Paint();
    }

    public CuatomViewTest_1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//                setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
    }

    public CuatomViewTest_1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CuatomViewTest_1(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.YELLOW);

        Bitmap dstBmp = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.image_dst,null);
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(bitmap);
        //画一个和图片大小相等的画布
        canvas1.drawCircle(getWidth() / 2, getHeight() / 2, getHeight() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas1.drawBitmap(dstBmp, 0, 0, paint);
        paint.setXfermode(null);

        canvas.drawBitmap(bitmap,getLeft(),getTop(),paint);

    }
}
