package com.mredrock.cyxbs.freshman.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.mredrock.cyxbs.freshman.R;

/**
 * Created by FengHaHa on2018/8/18 0018 2:06
 */
public class ImageUtil {

    public static void loadImage(Context context, String url, ImageView target) {
        Glide.with(context).load(Const.IMG_PREFIX+url).asBitmap().placeholder(R.drawable.freshman_h_zhanwei)
                .error(R.drawable.freshman_h_zhanwei).diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new BitmapImageViewTarget(target) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                                .create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(DensityUtil.dip2px(context, 5)); //设置圆角弧度
                        target.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void loadImage(Context context, @IdRes int resID, ImageView target) {
        Glide.with(context).load(resID).asBitmap().placeholder(R.drawable.freshman_h_zhanwei)
                .error(R.drawable.freshman_h_zhanwei).diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new BitmapImageViewTarget(target) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                                .create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(DensityUtil.dip2px(context, 5)); //设置圆角弧度
                        target.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }
}
