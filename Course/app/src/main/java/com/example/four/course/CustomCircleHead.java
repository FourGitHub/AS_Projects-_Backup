package com.example.four.course;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.four.course.DensityUtil.DensityUtil;

import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2018/5/27 0027.
 */

public class CustomCircleHead extends ImageView {
    private Paint mPaint;
    private Bitmap dstBmp, srcBmp, defaultBmp;
    private int mWidth;
    private int mHeight;
    private int cch_radius;
    private static final String TAG = "CustomCircleHead";

    public CustomCircleHead(Context context) {
        super(context);
    }

    public CustomCircleHead(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCircleHead(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /*
    初始化，View级别关闭硬件加速，取出自定义属性(圆形头像半径)，设置默认头像！
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomCircleHead);
        cch_radius = array.getDimensionPixelSize(R.styleable.CustomCircleHead_cch_radius, DensityUtil.dip2px(getContext(), 40));
        array.recycle();
        defaultBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_me_default_avatar);
    }

    /*
    测量View的宽高，若父View的Mode为 MeasureSpec.UNSPECIFIED,
    则View的矩形区域默认为2倍半径
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "--> onMeasure()");
        mWidth = getMeasuredSize(widthMeasureSpec, 2 * cch_radius);
        mHeight = getMeasuredSize(heightMeasureSpec, 2 * cch_radius);
        mWidth = Math.min(mWidth, mHeight);
        mHeight = mWidth;
        setMeasuredDimension(mWidth, mHeight);
    }

    public int getMeasuredSize(int measureSpec, int defaultSize) {
        int mySize = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            mySize = size;
        } else {
            mySize = defaultSize;
        }
        return mySize;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "--> onLayout()");

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "--> onDraw()");
        if (dstBmp != null) {
            canvas.drawBitmap(dstBmp, 0, 0, mPaint);
        } else {
            canvas.drawBitmap(createCircleBitmap(scaleHeadImage(defaultBmp)), 0, 0, mPaint);
        }
    }

    /**
     * @param Path 根据路径
     */
    public void setHeadImagePath(String Path) {
        srcBmp = BitmapFactory.decodeFile(Path);
        dstBmp = createCircleBitmap(scaleHeadImage(srcBmp));
        invalidate();
    }

    public void setHeadImageUri(Uri imageUri) {
        try {
            srcBmp = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(imageUri));
            dstBmp = createCircleBitmap(scaleHeadImage(srcBmp));
            invalidate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "--> onSizeChanged()");
    }

    /**
     * @param srcBmp 把这张图片压缩
     * @return
     */
    private Bitmap scaleHeadImage(Bitmap srcBmp) {
        Matrix m = new Matrix();
        final int width = srcBmp.getWidth();
        final int height = srcBmp.getHeight();
        if (width != mWidth || height != mHeight) {
            final float sx = mWidth / (float) width;
            final float sy = mHeight / (float) height;
            float scale =  Math.max(sx,sy);
            m.setScale(scale, scale);
        }
        return Bitmap.createBitmap(srcBmp, 0, 0, width, height, m, true);
    }

    /**
     * @param dstBmp 把这张图片切成圆的,使用自定义属性cch_radius
     * @return
     */
    public Bitmap createCircleBitmap(Bitmap dstBmp){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        // 先创建一个和View相同大小的mutable Bitmap, 所谓mutable就是说bitmap的像素能被修改
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(mWidth / 2, mHeight / 2, cch_radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(dstBmp, 0, 0, paint);
        return  bitmap;
    }

}
