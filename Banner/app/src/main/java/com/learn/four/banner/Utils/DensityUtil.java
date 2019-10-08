package com.learn.four.banner.Utils;

import android.content.Context;

/**
 * Create on 2019/07/20
 *
 * @author Four
 * @description
 */
public class DensityUtil {

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
