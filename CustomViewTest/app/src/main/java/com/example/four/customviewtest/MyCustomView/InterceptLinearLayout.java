package com.example.four.customviewtest.MyCustomView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;


/**
 * Create on 2019/03/27
 *
 * @author Four
 * @description
 */
public class InterceptLinearLayout extends LinearLayout {
    private static final String TAG = "-->> ";
    public InterceptLinearLayout(Context context) {
        super(context);
    }

    public InterceptLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case 0:
                Log.i(TAG, "父容器在 dispatchTouchEvent 方法中收到了: " + "ACTION_DOWN");
                break;
            case 1:
                Log.i(TAG, "父容器在 dispatchTouchEvent 方法中收到了: " + "ACTION_UP");
                break;
            case 2:
                Log.i(TAG, "父容器在 dispatchTouchEvent 方法中收到了: " + "ACTION_MOVE");
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case 0:
                Log.i(TAG, "父容器在 onInterceptTouchEvent 方法中收到了: " + "ACTION_DOWN");
                break;
            case 1:
                Log.i(TAG, "父容器在 onInterceptTouchEvent 方法中收到了: " + "ACTION_UP");
                break;
            case 2:
                Log.i(TAG, "父容器在 onInterceptTouchEvent 方法中收到了: " + "ACTION_MOVE");
//                return true;
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                Log.i(TAG, "父容器在 onTouchEvent 方法中收到了: " + "ACTION_DOWN");
                break;
            case 1:
                Log.i(TAG, "父容器在 onTouchEvent 方法中收到了: " + "ACTION_UP");
                break;
            case 2:
                Log.i(TAG, "父容器在 onTouchEvent 方法中收到了: " + "ACTION_MOVE");
                break;
        }

        return super.onTouchEvent(event);
    }
}
