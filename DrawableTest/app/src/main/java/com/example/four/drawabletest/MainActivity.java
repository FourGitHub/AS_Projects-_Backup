package com.example.four.drawabletest;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mLinearLayout = null;

    private ImageView img = null;
    private ImageView img_1 = null;
    private TextView tv = null;
    private static boolean change = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayout = findViewById(R.id.linear_layout);

        /*
        ImageView 测试LevalListDrawable
         */
        img = findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (change == true) {
                    img.setBackgroundResource(R.drawable.icon_bg_1); // 背景无法通过 LevelistDrawable 实现切换效果。这里采用的是硬设置资源id的方法
                    img.getDrawable().setLevel(2); // 前景
//                img.setImageLevel(2); // 前景,内部调用的其实就是上述方法
                    change = false;
                } else {
                    img.setBackgroundResource(R.drawable.icon_bg); // 背景无法通过 LevelistDrawable 实现切换效果。这里采用的是硬设置资源id的方法
                    img.getDrawable().setLevel(1); // 前景
//                img.setImageLevel(1); // 前景,内部调用的其实就是上述方法
                    change = true;
                }

            }
        });


        /*
        TextView 测试TransitionDrawable
         */
        tv = findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionDrawable drawable = (TransitionDrawable) tv.getBackground();
                drawable.startTransition(2000);

                drawable.reverseTransition(2000);
            }
        });

        /*
        mLinearLayout 测试 ScaleDrawable
         */
        ScaleDrawable scaleDrawable = (ScaleDrawable) mLinearLayout.getBackground();
        scaleDrawable.setLevel(5000);

        /*
        img_1 测试 ScaleDrawable
         */
        img_1 = findViewById(R.id.img_1);
        ClipDrawable clipDrawable = (ClipDrawable) img_1.getDrawable();
        clipDrawable.setLevel(8000);
    }
}
