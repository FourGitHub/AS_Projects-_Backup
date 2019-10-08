package com.learn.four.banner;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import javax.security.auth.PrivateCredentialPermission;

/**
 * Create on 2019/07/20
 *
 * @author Four
 * @description
 */
public class MyPagerFrgTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.7f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
        page.setScaleX(scaleFactor);
        page.setScaleY(scaleFactor);
//        if (position <= -1) {
//
//        } else if (position < 0) {
//        } else if (position >= 0 & position < 1) {
//        } else if (position >= 1) {
//
//        }

    }
}
