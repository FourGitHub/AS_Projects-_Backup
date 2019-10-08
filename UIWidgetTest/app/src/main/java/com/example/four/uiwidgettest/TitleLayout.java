package com.example.four.uiwidgettest;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public class TitleLayout extends LinearLayout {

    // 重写了LinerLayout的含有两个参数的构造方法
    public TitleLayout(Context context, AttributeSet atts) {
        super(context, atts);
        View view = LayoutInflater.from(context).inflate(R.layout.title, this);
        View view1 = LayoutInflater.from(context).inflate(R.layout.title, null);
        // 看两种情况下Inflate方法返回的view对象，logcat输出，
        System.out.println("view是" + view.toString());// 是TitleLayout(把title.xml实例化的view对象加到了TitleLayout，并把TitleLayout返回)
        System.out.println("view1是" + view1.toString());// 是title.xml实例化的view对象，并返回
        Button titleBack = (Button) findViewById(R.id.title_back);

        titleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });

    }


}
