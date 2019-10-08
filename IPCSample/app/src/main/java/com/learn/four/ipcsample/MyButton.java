package com.learn.four.ipcsample;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Create on 2019/05/22
 *
 * @author Four
 * @description
 */
public class MyButton extends android.support.v7.widget.AppCompatButton {
    private static final String TAG = "MyButton";
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent, ev=" + event.getAction());
        Thread.dumpStack();
        return true;
    }

}
