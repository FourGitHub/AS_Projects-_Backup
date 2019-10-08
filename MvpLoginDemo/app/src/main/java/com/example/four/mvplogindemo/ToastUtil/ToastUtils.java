package com.example.four.mvplogindemo.ToastUtil;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/6/15 0015.
 */

public class ToastUtils {

    private static Toast mToast;

    public static void showToast(Context context, String content, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, content, duration);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }

}
