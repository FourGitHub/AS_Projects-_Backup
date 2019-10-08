package com.example.four.customviewtest.MyCustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Create on 2019/03/27
 *
 * @author Four
 * @description
 */
public class InterceptButton extends android.support.v7.widget.AppCompatButton {
    private static final String TAG = "-->> ";
    public InterceptButton(Context context) {
        super(context);
    }

    public InterceptButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                Log.i(TAG, "子View在 dispatchTouchEvent 方法中收到了: " + "ACTION_DOWN");
//                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case 1:
                Log.i(TAG, "子View在 dispatchTouchEvent 方法中收到了: " + "ACTION_UP");
                break;
            case 2:
                Log.i(TAG, "子View在 dispatchTouchEvent 方法中收到了: " + "ACTION_MOVE");
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                Log.i(TAG, "子View在 onTouchEvent 方法中收到了: " + "ACTION_DOWN");
                break;
            case 1:
                Log.i(TAG, "子View在 onTouchEvent 方法中收到了: " + "ACTION_UP");
                break;
            case 2:
                Log.i(TAG, "子View在 onTouchEvent 方法中收到了: " + "ACTION_MOVE");
                break;
        }

        return super.onTouchEvent(event);
    }
}
