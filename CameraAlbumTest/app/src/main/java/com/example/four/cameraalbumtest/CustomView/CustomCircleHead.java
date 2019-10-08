package com.example.four.cameraalbumtest.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.example.four.cameraalbumtest.R;


/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class CustomCircleHead extends ImageView {
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int cch_radius;
    private int cch_padding;
    private Bitmap mBitmap;
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

    public CustomCircleHead(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomCircleHead);
        //        cch_radius = array.getDimensionPixelSize(R.styleable.CustomCircleHead_cch_radius, DensityUtil.dip2px(getContext(), 30));
        //        cch_padding = array.getDimensionPixelSize(R.styleable.CustomCircleHead_cch_padding, 0);
        //        array.recycle();
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_launcher_background);
        //        mBitmap = DrawableToBitmap(drawable);
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 默认为两倍半径宽/高
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
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null)
            canvas.drawBitmap(CreateCircleBitmap(mBitmap), getLeft(), getTop(), mPaint);
    }


    public Bitmap DrawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 :
                        Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private Bitmap CreateCircleBitmap(Bitmap bitmap) {

        bitmap = Bitmap.createScaledBitmap(bitmap, mWidth, mHeight, false);
        Bitmap circleBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mHeight / 2, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);
        return circleBitmap;
    }

//    @Override
//    public void setImageBitmap(Bitmap bm) {
//        mBitmap = bm;
//        requestLayout();
//    }
}