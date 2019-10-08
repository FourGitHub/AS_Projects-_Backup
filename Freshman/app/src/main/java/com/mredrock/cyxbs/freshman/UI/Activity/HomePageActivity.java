package com.mredrock.cyxbs.freshman.UI.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.mredrock.cyxbs.freshman.MVP.Presenter.HomePagePresenter;
import com.mredrock.cyxbs.freshman.MVP.View.HomePageView;
import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.Utility.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomePageActivity extends BaseActivity implements HomePageView {

    @BindView(R.id.car1)
    ImageView car1;
    @BindView(R.id.car2)
    ImageView car2;
    @BindView(R.id.car3)
    ImageView car3;
    @BindView(R.id.car4)
    ImageView car4;
    @BindView(R.id.car5)
    ImageView car5;
    @BindView(R.id.car_in_1_2)
    ImageView carIn12;
    @BindView(R.id.car_in_2_3)
    ImageView carIn23;
    @BindView(R.id.car_in_3_4)
    ImageView carIn34;
    @BindView(R.id.car_in_4_5)
    ImageView carIn45;
    @BindView(R.id.iv_home_ruxue)
    ImageView ivRuxue;
    @BindView(R.id.iv_home_gonglue)
    ImageView ivGonglue;
    @BindView(R.id.iv_home_jiaoliu)
    ImageView ivJiaoliu;
    @BindView(R.id.iv_home_process)
    ImageView ivProcess;
    @BindView(R.id.iv_home_i_want_say)
    ImageView ivWantSay;
   @BindView(R.id.home_page_padding_view)
    View homePagePaddingView;


    private ImageView[] mBaseCars;
    private ImageView[] mMiddleCars;
    private ImageView[] mBulidings;
    private int[] lockedId;
    private int[] unlockedId;

    private HomePagePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freshman_home_page_f_activity);
        ButterKnife.bind(this);
        initData();
        mPresenter = new HomePagePresenter();
        mPresenter.attachView(this);
        mPresenter.initViews();
       fusionStatusBar(homePagePaddingView);
    }

    /*
    沉浸式效果，先留在这里吧！
    要恢复之前的效果，把上面注释掉的代码，和xml文件里注释掉的代码取消注释就行了
    */
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }

    private void initData() {
        mBaseCars = new ImageView[]{car1, car2, car3, car4, car5};
        mMiddleCars = new ImageView[]{carIn12, carIn23, carIn34, carIn45};
        mBulidings = new ImageView[]{ivRuxue, ivGonglue, ivJiaoliu, ivProcess, ivWantSay};
        lockedId = new int[]{
                R.drawable.freshman_home_f_building_ruxue,
                R.drawable.freshman_home_f_building_campus_locked,
                R.drawable.freshman_home_f_building_jiaoliu_locked,
                R.drawable.freshman_home_f_building_process_locked,
                R.drawable.freshman_home_f_building_words_locked
        };
        unlockedId = new int[]{
                R.drawable.freshman_home_f_building_ruxue,
                R.drawable.freshman_home_f_building_campus_unlocked,
                R.drawable.freshman_home_f_building_jiaoliu_unlocked,
                R.drawable.freshman_home_f_building_process_unlocked,
                R.drawable.freshman_home_f_building_words_unlocked
        };
    }

    @Override
    public void showCarAnim(int destination) {

        ImageView now = mBaseCars[destination - 2];
        ImageView middle = mMiddleCars[destination - 2];
        ImageView dest = mBaseCars[destination - 1];
        ValueAnimator tx = ObjectAnimator.ofFloat(now, "translationX", 0f, middle.getX() - now.getX(), dest.getX() - now.getX());
        ValueAnimator ty = ObjectAnimator.ofFloat(now, "translationY", 0f, middle.getY() - now.getY(), dest.getY() - now.getY());
        ValueAnimator sx = ObjectAnimator.ofFloat(now, "ScaleX", now.getScaleX(), middle.getScaleX(), dest.getScaleX());
        ValueAnimator sy = ObjectAnimator.ofFloat(now, "ScaleY", now.getScaleY(), middle.getScaleY(), dest.getScaleY());
        float middleY = middle.getY() - now.getY();
        if (destination > 2) ty.addUpdateListener(animation -> {

            if ((float) animation.getAnimatedValue() > middleY) {
                if (destination == 4) {
                    now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.freshman_home_f_car_left));

                } else {
                    now.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.freshman_home_f_car_right));
                }
                ty.removeAllUpdateListeners();
            }


        });
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.setDuration(1000);
        set.playTogether(tx, ty, sx, sy);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mPresenter.onAnimEnd(destination);
                unlockBuilding(destination);
            }
        });
        set.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    private void unlockBuilding(int pos) {
        mBulidings[pos - 1].setImageResource(unlockedId[pos - 1]);
    }

    @Override
    public void showNowCar(int pos) {
        int index = pos - 1;
        for (int i = 0; i < mBaseCars.length; i++) {
            if (i == index) mBaseCars[i].setVisibility(View.VISIBLE);
            else mBaseCars[i].setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showBuildings(int pos) {
        int index = pos - 1;
        for (int i = 0; i < mBulidings.length; i++) {
            if (i <= index) mBulidings[i].setImageResource(unlockedId[i]);
            else mBulidings[i].setImageResource(lockedId[i]);
        }
    }

    @Override
    public void onBuildingClick(View view) {
        mPresenter.onBuildingClick(view.getId());
    }




    @Override
    public Context getContext() {
        return this;
    }

}
