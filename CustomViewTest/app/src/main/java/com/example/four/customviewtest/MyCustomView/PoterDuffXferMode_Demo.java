package com.example.four.customviewtest.MyCustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.four.customviewtest.R;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

public class PoterDuffXferMode_Demo extends View {
    private Paint mPaint;
    private Bitmap dstBmp, srcBmp;
    private RectF dstRect, srcRect;

    private Xfermode mXfermode;
    private PorterDuff.Mode mPorterDuffMode = PorterDuff.Mode.SRC;

    private static final String TAG = "PoterDuffXferMode_Demo";

    public PoterDuffXferMode_Demo(Context context) {
        super(context);
    }

    public PoterDuffXferMode_Demo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "PoterDuffXferMode_Demo: 哈哈哈");
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        dstBmp = BitmapFactory.decodeResource(getResources(), R.drawable.image_dst);
        srcBmp = BitmapFactory.decodeResource(getResources(), R.drawable.image_src);
        mXfermode = new PorterDuffXfermode(mPorterDuffMode);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: 哈哈哈");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout: 哈哈哈");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: 哈哈哈");
        //背景色设为白色，方便比较效果
        canvas.drawColor(Color.WHITE);
        //将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
        int saveCount = canvas.saveLayer(srcRect, mPaint, Canvas.ALL_SAVE_FLAG);
        //绘制目标图
        canvas.drawBitmap(dstBmp, null, dstRect, mPaint);
        //设置混合模式
        mPaint.setXfermode(mXfermode);
        //绘制源图
        canvas.drawBitmap(srcBmp, null, srcRect, mPaint);
        //清除混合模式
        mPaint.setXfermode(null);
        //还原画布
        canvas.restoreToCount(saveCount);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged: 哈哈哈");
        Log.i(TAG, "onSizeChanged->: oldw = " + oldw);
        Log.i(TAG, "onSizeChanged->: oldh = " + oldh);
        Log.i(TAG, "onSizeChanged->: w = " + w);
        Log.i(TAG, "onSizeChanged->: h = " + h);
        int width = w <= h ? w : h;
        int centerX = w/2;
        int centerY = h/2;
        int quarterWidth = width /4;
        srcRect = new RectF(centerX-quarterWidth, centerY-quarterWidth, centerX+quarterWidth, centerY+quarterWidth);
        dstRect = new RectF(centerX-quarterWidth, centerY-quarterWidth, centerX+quarterWidth, centerY+quarterWidth);
    }
}

    // 测试Canvas.save()/restore()/translate()/scale()/clipXxx()
    //    @Override
    //    protected void onDraw(Canvas canvas) {
    //        Log.i(TAG, "哈onDraw: Canvas" + canvas.getClass().getName());
    //        Log.i(TAG, "-->  onDraw() ");
    //        super.onDraw(canvas);
    //        paint.setColor(Color.RED);
    //        canvas.drawColor(Color.parseColor("#e5e5e5"));
    //        canvas.drawRect(new Rect(100, 100, 600, 300), paint);
    //        canvas.save(); // 调用save()方法保存当前画布状态到私有栈
    //        canvas.translate(-100, -100); // 把画布坐标原点往右上角平移(此时新坐标的坐标原点已经在屏幕外了)
    //        paint.setColor(Color.YELLOW);// 重置画笔为黄色
    //        canvas.drawRect(new Rect(100, 100, 600, 300), paint); // 绘制黄色矩形的时候以新坐标为参考
    //        canvas.restore(); // 这个方法将恢复canvas的坐标原点为(0,0), 即屏幕左上角，如果接下来继续canvas.drawXxx()的话，将以(0,0)为坐标原点参考绘制。
    //        paint.setColor(Color.GREEN);
    //        canvas.drawRect(new Rect(100, 100, 600, 300), paint);
    //        canvas.save();
    //        canvas.clipRect(new Rect(200, 200, 700, 400), Region.Op.INTERSECT);
    //        canvas.drawColor(Color.BLUE);
    //        canvas.restore();
    //        canvas.save();
    //        //        canvas.scale(2, 2);
    //        canvas.scale(2, 2, 100f, 100f);
    //        canvas.clipRect(new Rect(150, 150, 400, 250), Region.Op.INTERSECT);
    //        canvas.drawColor(Color.parseColor("#ffffa500"));
    //        canvas.restore();
    //        requestLayout();
    //    }


