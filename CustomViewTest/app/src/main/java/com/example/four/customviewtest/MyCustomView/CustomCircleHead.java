package com.example.four.customviewtest.MyCustomView;

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
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.Scroller;

import com.example.four.customviewtest.DensityUtil.DensityUtil;
import com.example.four.customviewtest.R;

import java.io.FileNotFoundException;
import java.util.Random;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class CustomCircleHead extends android.support.v7.widget.AppCompatImageView {
    private Paint mPaint;
    private Bitmap dstBmp, srcBmp, defaultBmp;
    private int mWidth;
    private int mHeight;
    private int cch_radius;
    private static final String TAG = "CustomCircleHead";

    private OnClickListener mClickListener;

    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;

    // 以下为了测试View动画（不是属性动画），实现自定义圆形头像内容的全屏滑动, 配合重写的onTouchEvent()方法实现！
    // 记录ACTION_MOVE过程中，不断刷新的触点坐标。
    private int mLastX;
    private int mLastY;

    // 记录ACTION_DOWN时触点的坐标
    private int downX;
    private int downY;



    // 以下变量为了测试 Scroller, 结合scrollBy()方法和重写的computeScroll()方法
    // 实现自定义头像的 全屏随机 相对当前内容 的滑动（响应双击事件）
    private Scroller mScrooler;
//    private GestureDetector mGestureDetector;

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
    初始化:
   // 1.View级别关闭硬件加速
    2.取出自定义属性(圆形头像半径)
    3.设置默认头像！
    4.成员变量初始化
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomCircleHead);
        cch_radius = array.getDimensionPixelSize(R.styleable.CustomCircleHead_cch_radius, DensityUtil.dip2px
                (getContext(), 30));

        array.recycle();
        defaultBmp = BitmapFactory.decodeResource(getResources(), R.drawable.image_default_head);

        mScrooler = new Scroller(context);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        // 一定要避免使用如下方法为自定义View设置监听，因为他将不起作用，因为onTouch—–>onTouchEvent—>onClick
//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
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
            float scale = Math.max(sx, sy);
            m.setScale(scale, scale);
        }
        return Bitmap.createBitmap(srcBmp, 0, 0, width, height, m, true);
    }

    /**
     * @param dstBmp 把这张图片切成圆的,使用自定义属性cch_radius
     * @return
     */
    public Bitmap createCircleBitmap(Bitmap dstBmp) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        // 先创建一个和View相同大小的mutable Bitmap, 所谓mutable就是说bitmap的像素能被修改
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(mWidth / 2, mHeight / 2, cch_radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(dstBmp, 0, 0, paint);
        return bitmap;
    }

    /*
     * 测试View动画，可以拖着头像全屏滑动
     * 优先级：onTouch() > onTouchEvent() > onclick()
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return mGestureDetector.onTouchEvent(event);

        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        int x1 = (int) event.getX();
        int y1 = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getRawX();
                downY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;

                int translationX = (int) (getTranslationX() + deltaX);
                int translationY = (int) (getTranslationY() + deltaY);

                // https://blog.csdn.net/yanzi1225627/article/details/47850471 总结
                // 1.setTranslationX/Y改变了view的位置，但没有改变view的LayoutParams里的margin属性；(这一点由运行结果可知)
                // 2.它改变的是android:translationX/Y 属性，也即这个参数级别是和margin平行的。
                setTranslationX(translationX);
                setTranslationY(translationY);

                int testX = getLeft();
                int testY = getTop();
                Log.i(TAG, "onTouchEvent: -->> " + "(" + testX + " , " + testY + ")");
                Log.i(TAG, "onTouchEvent: -->> " + "(" + x1 + " , " + y1 + ")");

                break;
            case MotionEvent.ACTION_UP:
                // 这个case判断单击事件，判断依据：
                // 判断所点击的坐标是否处于自定义view内部。如果处于自定义view内部且ACTION_DOWN 和 ACTION_UP之间的距离小于TouchSlop
                // 则回调mClickListener.onClick()，否则不予理会。
                if ((x1 + getLeft() < getRight()) && (y1 + getTop()) < getBottom() && Math.sqrt(Math.pow(x - downX,
                        2) + Math.pow(y - downY, 2)) < mTouchSlop) {
                    mClickListener.onClick(this);
                }
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }

    /*
     * 重写setOnClickListener()方法
     */
    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        this.mClickListener = l;
    }

    public void randomScrollBy() {
        Random random = new Random();
        int randomDeltaX = random.nextInt(100) * (Math.random() > 0.5 ? 1 : -1);
        int randomDeltaY = random.nextInt(100) * (Math.random() > 0.5 ? 1 : -1);
        Log.i(TAG, "-->> randomDeltaX = " + randomDeltaX + "  randomDeltaY = " + randomDeltaY);
        mScrooler.startScroll(getScrollX(), getScrollY(), randomDeltaX, randomDeltaY, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        Log.i(TAG, "computeScroll: -->>");
        if (mScrooler.computeScrollOffset()) {
            scrollTo(mScrooler.getCurrX(), mScrooler.getCurrY());
            postInvalidate();
        }
    }
}