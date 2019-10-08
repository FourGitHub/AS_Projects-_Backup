package com.hencoder.hencoderpracticedraw6.practice;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hencoder.hencoderpracticedraw6.R;
import com.hencoder.hencoderpracticedraw6.Utils;

public class Practice05MultiProperties extends ConstraintLayout {
    Button animateBt;
    ImageView imageView;
    private boolean animationCase = false;
    private boolean animated = false;

    public Practice05MultiProperties(Context context) {
        super(context);
    }

    public Practice05MultiProperties(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice05MultiProperties(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        animateBt = (Button) findViewById(R.id.animateBt);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setScaleX(0);
        imageView.setScaleY(0);
        imageView.setAlpha(0f);

        PropertyValuesHolder scaleXHolder0_1 = PropertyValuesHolder.ofFloat("scaleX", imageView.getScaleX(), 1);
        PropertyValuesHolder scaleYHolder0_1 = PropertyValuesHolder.ofFloat("scaleY", imageView.getScaleY(), 1);
        PropertyValuesHolder alphaHolder0_1 = PropertyValuesHolder.ofFloat("alpha", imageView.getAlpha(), 1);
        PropertyValuesHolder translationXHolder0_1 = PropertyValuesHolder.ofFloat("translationX", imageView.getTranslationX(), Utils.dpToPixel(200));
        PropertyValuesHolder rotationHolder0_1 = PropertyValuesHolder.ofFloat("rotation", imageView.getRotation(), 360);
        final ObjectAnimator animator0_1 = ObjectAnimator.ofPropertyValuesHolder(imageView, scaleXHolder0_1, scaleYHolder0_1, alphaHolder0_1, translationXHolder0_1, rotationHolder0_1);

        PropertyValuesHolder scaleXHolder1_0 = PropertyValuesHolder.ofFloat("scaleX", imageView.getScaleX(), 0);
        PropertyValuesHolder scaleYHolder1_0 = PropertyValuesHolder.ofFloat("scaleY", imageView.getScaleY(), 0);
        PropertyValuesHolder alphaHolder1_0 = PropertyValuesHolder.ofFloat("alpha", imageView.getAlpha(), 0);
        PropertyValuesHolder translationXHolder1_0 = PropertyValuesHolder.ofFloat("translationX", imageView.getTranslationX(), 0);
        PropertyValuesHolder rotationHolder1_0 = PropertyValuesHolder.ofFloat("rotation", imageView.getRotation(), 0);

        final ObjectAnimator animator1_0 = ObjectAnimator.ofPropertyValuesHolder(imageView, scaleXHolder1_0, scaleYHolder1_0, alphaHolder1_0, translationXHolder1_0, rotationHolder1_0);


        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!animated) {
                    animator0_1.start();
                } else {
                    // animator1_0.start();  => 奇数次点击才有动画效果
                    animator0_1.reverse();
                }
                animated = !animated;

                //                if (!animated) {
                //                    imageView.animate()
                //                             .translationX(Utils.dpToPixel(200))
                //                             .rotation(360)
                //                             .scaleX(1)
                //                             .scaleY(1)
                //                             .alpha(1);
                //                } else {
                //                    imageView.animate()
                //                             .translationX(0)
                //                             .rotation(0)
                //                             .scaleX(0)
                //                             .scaleY(0)
                //                             .alpha(0);
                //                }
                //                animated = !animated;

            }
        });
    }
}
