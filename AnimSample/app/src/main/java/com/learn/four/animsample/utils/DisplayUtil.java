package com.learn.four.animsample.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Create on 2019/03/21
 *
 * @author Four
 * @description
 */
public class DisplayUtil {
    public static int dpToPx(int dpValue) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return (int)(dpValue * displayMetrics.density);
    }
}
