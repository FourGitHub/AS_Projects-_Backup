package com.example.four.materialtest;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/1/13 0013.
 */

// 一个自定义的snackbar工具类，防止snackbar多次点击，频繁弹出snackbar.
public class snackBarUtils {
    private static Snackbar snackbar;

    public static void showSnackbar(View view, String content) {
        if (snackbar == null) {
            snackbar = Snackbar.make(view, content, Snackbar.LENGTH_LONG)
                    .setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new CustomiseToast(view.getContext().getApplicationContext()).showToast(R.drawable.toast_image, Toast.LENGTH_SHORT);
                        }
                    });
        } else {
            snackbar.show();
        }
    }
}
