package com.mredrock.cyxbs.freshman.UI.Activity;

import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.mredrock.cyxbs.freshman.Utility.DensityUtil;

/**
 * Created by FengHaHa on2018/8/15 0015 21:55
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    protected <V extends View> V $(@IdRes int id) {
        return (V) findViewById(id);
    }

    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    protected void fusionStatusBar(View paddingView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        ViewGroup.LayoutParams params = paddingView.getLayoutParams();
        params.height = getStatusBarHeight();
        Log.w(TAG, "-->> StatusBarHeight = " + DensityUtil.px2dip(this, getStatusBarHeight()));
    }
}
