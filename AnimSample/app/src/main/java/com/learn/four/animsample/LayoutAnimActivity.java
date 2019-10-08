package com.learn.four.animsample;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.learn.four.animsample.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LayoutAnimActivity extends AppCompatActivity {

    @BindView(R.id.tv_0)
    TextView tv0;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.tv_4)
    TextView tv4;
    @BindView(R.id.lin_container)
    LinearLayout linContainer;
    @BindView(R.id.tv_00)
    TextView tv00;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_remove)
    Button btnRemove;
    private List<String> colorList;
    private List<View> childs;
    private Random mRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_anim);
        ButterKnife.bind(this);
        init();

        /* 通过Java代码设置 LayoutAnimation */

        linContainer.post(() -> {
            Animation layout_item_anim = AnimationUtils.loadAnimation(
                    LayoutAnimActivity.this, R.anim.layout_item_anim);

            /*
            『 LayoutAnimationController 是个什么东西呢？ 』

                为布局或ViewGroup的子View设置动画，
                并且计算每个子View的动画延迟，控制每个子View动画开始时间
            */
            LayoutAnimationController controller =
                    new LayoutAnimationController(layout_item_anim);
            controller.setDelay(0.3f);
            controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
            controller.setInterpolator(new DecelerateInterpolator());
            linContainer.setLayoutAnimation(controller);

            /*
            这一句代码很关键，当 『 此ViewGroup所属的Activity不是LAUNCHER时 』
            只有加了这一句代码子View才会在启动Activity时立即执行入场动画，
                否则动画将延迟到ViewGroup的Appearence首次改变时执行

                那其实 .requestLayout（）也可以
            */
            linContainer.invalidate();
        });

    }


    private void init() {
        initColorList();
        initChildList();
        mRandom = new Random();
        setLayoutTransition();
    }

    private void initChildList() {
        colorList = new ArrayList<>();
        colorList.add("#ff8ab2");
        colorList.add("#ff7833");
        colorList.add("#c2ed00");
        colorList.add("#ae000e");
        colorList.add("#df270a");
        colorList.add("#bb241e");
        colorList.add("#7b2e39");
    }

    private void initColorList() {
        childs = new ArrayList<>();
        childs.add(tv0);
        childs.add(tv1);
        childs.add(tv2);
        childs.add(tv3);
        childs.add(tv4);
    }

    private void setLayoutTransition() {
        LayoutTransition transition = new LayoutTransition();

        /* LayoutTransition.APPEARING */
        PropertyValuesHolder pvhRorarionX = PropertyValuesHolder.ofFloat("rotationX", 90, 0);
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 0, 1);
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 0, 1);
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 0, 1);
        ObjectAnimator appearingAnim = ObjectAnimator
                .ofPropertyValuesHolder(linContainer, pvhRorarionX, pvhAlpha, pvhScaleX, pvhScaleY);
        transition.setAnimator(LayoutTransition.APPEARING, appearingAnim);
        transition.setDuration(LayoutTransition.APPEARING, 500);
        appearingAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                btnAdd.setEnabled(false);
                btnRemove.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                btnAdd.setEnabled(true);
                btnRemove.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        /* LayoutTransition.DISAPPEARING */
        PropertyValuesHolder pvhDisAlpha = PropertyValuesHolder.ofFloat("alpha", 1, 0);
        PropertyValuesHolder pvhDisRotationX = PropertyValuesHolder.ofFloat("rotationX", 0, 90);
        PropertyValuesHolder pvhDisScaleX = PropertyValuesHolder.ofFloat("scaleX", 1, 0);
        PropertyValuesHolder pvhDisScaleY = PropertyValuesHolder.ofFloat("scaleY", 1, 0);
        ObjectAnimator disappearingAnim = ObjectAnimator
                .ofPropertyValuesHolder(linContainer, pvhDisAlpha, pvhDisRotationX, pvhDisScaleX, pvhDisScaleY);
        transition.setDuration(LayoutTransition.DISAPPEARING, 500);
        transition.setAnimator(LayoutTransition.DISAPPEARING, disappearingAnim);


        /* LayoutTransition.CHANGE_APPEARING */
        PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofInt("left", 0, 0);
        PropertyValuesHolder pvhTop = PropertyValuesHolder.ofInt("top", 0, 0);
        PropertyValuesHolder pvhChTranslationY = PropertyValuesHolder.ofFloat("translationY", 0, 150, 0);
        ObjectAnimator changeAppearAnim = ObjectAnimator
                .ofPropertyValuesHolder(linContainer, pvhLeft, pvhTop, pvhChTranslationY);
        transition.setDuration(LayoutTransition.CHANGE_APPEARING, 500);
        transition.setAnimator(LayoutTransition.CHANGE_APPEARING, changeAppearAnim);


        /* LayoutTransition.CHANGE_DISAPPEARING */
        PropertyValuesHolder outLeft = PropertyValuesHolder.ofInt("left", 0, 0);
        PropertyValuesHolder outTop = PropertyValuesHolder.ofInt("top", 0, 0);
        PropertyValuesHolder pvhChDisTranslationX = PropertyValuesHolder.ofFloat("translationX", 0, -500, 0);
        ObjectAnimator changeDisAppearAnim = ObjectAnimator
                .ofPropertyValuesHolder(linContainer, outLeft, outTop, pvhChDisTranslationX);
        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeDisAppearAnim);
        // 这里我为CHANGE_DISAPPEARING设置了stagger,注意看效果（TMD模拟器测试没效果，白折腾了2个小时）
        transition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 50);
        changeDisAppearAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                btnAdd.setEnabled(false);
                btnRemove.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                btnAdd.setEnabled(true);
                btnRemove.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        linContainer.setLayoutTransition(transition);


    }


    public void addChild(View view) {
        if (childs.size() >= 7) {
            Toast.makeText(getApplicationContext(), "塞不下了！", Toast.LENGTH_SHORT).show();
            return;
        }
        TextView tv = new TextView(this);
        tv.setText("Hello World!");
        tv.setAllCaps(false);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
        tv.setTextColor(Color.parseColor(colorList.get(mRandom.nextInt(colorList.size()))));
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD_ITALIC);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = DisplayUtil.dpToPx(20);
        lp.gravity = Gravity.CENTER;
        tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER);
        childs.add(tv);
        linContainer.addView(tv);
    }


    public void removeChild(View view) {
        if (childs.size() < 1) {
            Toast.makeText(getApplicationContext(), "已经被掏空了", Toast.LENGTH_SHORT).show();
            return;
        }
        linContainer.removeView(childs.get(0));
        childs.remove(0);

    }

    public void visiableTv00(View view) {
        if (tv00.getVisibility() != View.VISIBLE) {
            tv00.setVisibility(View.VISIBLE);
            childs.add(tv00);
        }


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.acback_enter_trans_anim, R.anim.acback_exit_trans_anim);
    }
}
