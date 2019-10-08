package com.example.four.customviewtest.DensityUtil;

import android.content.Context;

/**
 * Created by Four on 2018/3/26 0026.
 * px转dp公式 (int) (pxValue / scale + 0.5f)
 * dp转px公式 (int) (dipValue * scale + 0.5f)
 * 这里面唯一的参数就是scale，这个参数是由手机屏幕分辨率决定的，
 * 代码中可以用 scale = context.getResources().getDisplayMetrics().density; 得到。
 * 简单的说「scale就是dp转换为px需要乘的系数」：px = dp*scale。至于为什么加0.5f, 是为了保证每个转换结果的值都 ≥1
 *
 * dp 和 dip（Density Independent Pixels，密度无关像素）实际上是同一个概念，
 * 它是Android中的一个长度单位，『1dp = 1/160英寸』
 *
 * dp这个单位的意义在于：它和屏幕的分辨率无关，使得应用中设定的dp值在不同的设备上大小一致
 */

public class DensityUtil {

    /* px -> dp */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /* dp -> px */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /* px -> sp */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /* sp -> px */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}