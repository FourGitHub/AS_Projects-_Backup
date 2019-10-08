package com.learn.four.animsample;

import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PropertyAnimActivity extends AppCompatActivity {
    private ImageView imgMe;
    private ImageView imgMe1;
    private PathInterpolator pathInterpolator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_anim);
        init();
    }

    private void init() {
        imgMe = findViewById(R.id.img_me);
        imgMe1 = findViewById(R.id.img_me_1);

        imgMe.setOnClickListener(v -> Toast.makeText(PropertyAnimActivity.this, "你点击了我", Toast.LENGTH_SHORT).show());
        imgMe1.setOnClickListener(v -> Toast.makeText(PropertyAnimActivity.this, "你点击了我", Toast.LENGTH_SHORT).show());

        Path path = new Path();
        path.lineTo(0.25f, 0.25f );
        path.moveTo(0.25f, 0.5f);
        path.lineTo(1f, 1f);
        pathInterpolator = new PathInterpolator(path);
    }

    /* 使用最简单的ViewPropertyAnimator */
    public void startAnim(View view) {
        imgMe.animate()
             .scaleX(1.5f)
             .scaleY(1.5f)
             .rotationXBy(360)
             .setInterpolator(pathInterpolator)
             .setDuration(2000);
    }

    /* 使用最简单的ViewPropertyAnimator */
    public void startAnim1(View view) {
        imgMe1.animate()
              .scaleX(0.7f)
              .scaleY(0.7f)
              .rotationYBy(180)
              .setDuration(2000);
    }

    /* 使用 ValueAnimator --- （上）按钮变宽*/
    public void beWiderAnim(View view) {
        performAnimByValueAnimator(view);
    }

    private void performAnimByValueAnimator(View target) {
        int startValue = target.getWidth();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            Interpolator interpolator = new DecelerateInterpolator();
            IntEvaluator evaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int process = (Integer) valueAnimator.getAnimatedValue();
                float fraction = interpolator.getInterpolation(process / 100f);
                target.getLayoutParams().width =
                        evaluator.evaluate(fraction, startValue, startValue+200);
                target.requestLayout();
            }
        });

        valueAnimator.setDuration(2000).start();
    }


    /*
    使用 ObjectAnimator + ViewWrapper --- （下）按钮变宽
    不管模拟器还是真机，这种方式在上一个变宽动画还未结束时又开始变宽动画有时候会“卡壳” --- 动画无响应
    */
    public void beWiderAnim1(View view) {
        performAnimByViewWrapper(view);
    }

    private void performAnimByViewWrapper(View target) {
        ViewWrapper viewWrapper = new ViewWrapper(target);
        ObjectAnimator.ofInt(viewWrapper, "width",  target.getWidth() + 200)
                      .setDuration(2000)
                      .start();
    }

    /* 使用 ViewWrapper 包装target，间接提供 getter、setter */
    private static class ViewWrapper {
        private View mTarget;

        ViewWrapper(View target) {
            this.mTarget = target;
        }

        public int getWidth() {
            return mTarget.getWidth();
        }

        @SuppressWarnings("unused")
        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.acback_enter_trans_anim, R.anim.acback_exit_trans_anim);
    }
}

