package com.mredrock.cyxbs.freshman.Adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mredrock.cyxbs.freshman.CustomView.CircleViewPager;
import com.mredrock.cyxbs.freshman.Utility.ImageUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/8/14 0014.
 */

public class ImagePagerAdapter1 extends CirclePagerAdapter<CircleViewPager> {

    private Context mContext;
    private List<String> mPictures;


    public ImagePagerAdapter1(Context context, CircleViewPager viewPager, List<String> mPictures) {
        super(viewPager);
        this.mPictures = mPictures;
        mContext = context;
    }

    @Override
    public Object instantiateRealItem(ViewGroup container, int position) {
        ImageView views = new ImageView(container.getContext());
        views.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageUtil.loadImage(mContext, mPictures.get(position), views);
        container.addView(views);
        return views;
    }

    @Override
    public int getRealDataCount() {
        return mPictures.size();
    }

}
