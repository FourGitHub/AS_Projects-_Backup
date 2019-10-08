package com.example.four.customviewtest.MyCustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.example.four.customviewtest.DensityUtil.DensityUtil;

/**
 * Created by Administrator on 2018/4/3 1:27.
 */

public class RotateTextView extends TextView {
    private static final String TAG = "RotateTextView";

    public RotateTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(-90,0,0);
        canvas.translate(-getWidth(),0);
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: (1080×1920)wrap_content = " + DensityUtil.px2dip(getContext(),getWidth()) + "dp");
    }
}
