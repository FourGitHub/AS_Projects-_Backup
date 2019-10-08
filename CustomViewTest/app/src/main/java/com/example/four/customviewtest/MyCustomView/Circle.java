package com.example.four.customviewtest.MyCustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.four.customviewtest.R;

/**
 * Create on 2019/03/06
 *
 * @author Four
 * @description
 */
public class Circle extends View {

    private Paint paint = new Paint();
    private int defaultValue;

    public Circle(Context context) {
        super(context);
    }

    public Circle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 第二个参数就是我们在attrs.xml文件中利用<declare-styleable>标签定义的“属性集合”
        // 在R文件里的名称为R.styleable+name
        TypedArray typedValue = context.obtainStyledAttributes(attrs, R.styleable.MyCircle);

        //第一个参数为“属性集合”里面的具体属性，在R文件里的名称：R.styleable+属性集合名称+下划线+属性名称
        //第二个参数为，如果用户没有设置这个属性，则使用硬编码的默认值
        defaultValue = typedValue.getDimensionPixelSize(R.styleable.MyCircle_default_value, 100);

        // 最后，回收typedValue
        typedValue.recycle();
    }

    private int getSize(int defaultSize, int measureSpec) {
        int mSize = defaultSize;

        // 解包
        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);

        switch (mode) {
            // 如果没有指定大小，就设置为默认大小
            case MeasureSpec.UNSPECIFIED:
                break;

            // 如果父View限制了最大尺寸，就取最大的尺寸
            // 当然也可以根据项目实际情况，选择其他 ≤ size 的值
            case MeasureSpec.AT_MOST:
                mSize = size;
                break;

            // 如果父View指定了我的尺寸，就奉命取父View给的尺寸
            case MeasureSpec.EXACTLY:
                mSize = size;
                break;
        }
        return mSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getSize(defaultValue, widthMeasureSpec);
        int height = getSize(defaultValue, heightMeasureSpec);
        width = height = Math.min(width, height);

        setMeasuredDimension(width, height);
    }

    /**
     * 注意不要在onDraw方法中创建局部对象，这是因为onDraw方法可能会被频繁的调用，这样就会在一瞬间
     * 产生大量的临时对象，这不仅会占用更多的内存而且还会导致系统更加频繁gc，降低了程序的执行效率
     *
     * onDraw方法中不要做耗时任务，也不要执行太多次的循环操作。这样会造成View的绘制不流畅
     *
     * 可以的话 使用硬件加速
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // 调用父View的onDraw函数，帮我们实现了一些其他绘制，比如背景颜色、背景图片等
        super.onDraw(canvas);

        // 也可以是getMeasuredWidth()/2,因为已经将宽高设置相等了
        float radius = getMeasuredHeight() / 2.0f;

        // 圆心的横坐标为当前的View的左边起始位置+半径
        float cx = getLeft() + radius;

        // 圆心的纵坐标为当前的View的顶部起始位置+半径
        float cy = getTop() + radius;

        // 设置画笔颜色，并开始绘制
        paint.setColor(Color.parseColor("#00ff00"));
        canvas.drawCircle(cx, cy, radius, paint);
    }

}
