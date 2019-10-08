package com.mredrock.cyxbs.freshman.UI.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mredrock.cyxbs.freshman.Adapter.FixedSpeedScroller;
import com.mredrock.cyxbs.freshman.Adapter.GalleryTransformer;
import com.mredrock.cyxbs.freshman.Adapter.ImagePagerAdapter;
import com.mredrock.cyxbs.freshman.CustomView.CircleViewPager;
import com.mredrock.cyxbs.freshman.DataBean.JunXunFCBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.JunXunFCPresenter;
import com.mredrock.cyxbs.freshman.MVP.View.JunXunFCView;
import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.Utility.Const;
import com.mredrock.cyxbs.freshman.Utility.DensityUtil;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2018/8/12 0012.
 */

public class JunXunFengCaiFrg extends BaseFragment implements JunXunFCView {
    private CircleViewPager viewPager;
    private LinearLayout junxunVedioLayout;
    private View parent;
    private JunXunFCPresenter mPresenter;

    // 在BaseFragment中调用onCreateView() 方法创建Fragment视图时，会调用此方法，BaseFragment相当于把创建视图的部分抽象出来
    // 针对不同的子类，只需要提供各自的根布局id就行了
    @Override
    public int getContentViewId() {
        mPresenter = new JunXunFCPresenter();
        mPresenter.attachView(this);
        return R.layout.freshman_h_jxfc_frg;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    // 视图创建完毕后，会立即调用此方法，动态添加5个军训视频的View(资源设置)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        parent = view;
        viewPager = view.findViewById(R.id.id_viewpager);
        junxunVedioLayout = view.findViewById(R.id.junxun_vedio_layout);
        setViewPagerScrollSpeed();
        mPresenter.getData();

    }

    // 通过反射修改ViewPager滑动速度
    private void setViewPagerScrollSpeed() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext());
            mScroller.set(viewPager, scroller);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setData(JunXunFCBean bean) {
        ImagePagerAdapter adapter = new ImagePagerAdapter(getActivity(),viewPager, bean.getPictureList());
        viewPager.setOffscreenPageLimit(bean.getPictureList().size());
        viewPager.setPageMargin((DensityUtil.getScreenWidth(getActivity()) / 10));
        viewPager.setAdapter(adapter);
        // 设置轮播时间
       viewPager.setTimeOut(4000);
        // 设置3d效果
        viewPager.setPageTransformer(true, new GalleryTransformer());
        // 用的资源文件，而且已经存在了
       viewPager.setHasData(true);
//        // 开启轮播
        viewPager.startTimer();

        for (int i = 0; i < bean.getVideoList().size(); i++) {
            JunXunFCBean.VideoBean videoBean = bean.getVideoList().get(i);
            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.freshman_h_junxun_lunbo_vedio, null);
            ImageView imageView = view1.findViewById(R.id.junxu_vidio_msg_imv);
            Glide.with(parent.getContext()).load(Const.IMG_PREFIX + videoBean.getVideoPicBean().getUrl()).into(imageView);
            imageView.setTag(videoBean.getVideoUrl());
            // 点击图片，播放视频....
            imageView.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse((String) imageView.getTag()));
                startActivity(intent);
            });
            TextView textView = view1.findViewById(R.id.junxu_vedio_tv);
            textView.setText(videoBean.getName());

            // 动态添加到容器中
            junxunVedioLayout.addView(view1);
        }
    }

}
