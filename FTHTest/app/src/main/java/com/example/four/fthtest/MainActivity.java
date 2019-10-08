package com.example.four.fthtest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";
    private FragmentTabHost fragmentTabHost;

    // 以下主要是准备一些资源。
    private int[] imageIds = {R.mipmap.icon_home, R.mipmap.icon_mine};
    private String[] text = new String[]{"标签 1", "标签 2"};
    private Class<?>[] mFragmentaClasses = {Fragment_0.class, Fragment_1.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Fragment_1 fragment_1 = new Fragment_1();
        fragment_1.setRetainInstance(true);
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        // 初始化
        fragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        fragmentTabHost.getTabWidget().setDividerDrawable(null);

        // 添加标签
        for (int i = 0; i < imageIds.length; i++) {
            // Tab标签添加文字和图片
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(i + " ").setIndicator(getIndicatorView(i));

            // 添加Fragment,可以理解为一个Tab关联一个Fragment,点击Tab的时候，就会切换到相应的Fragment
            fragmentTabHost.addTab(tabSpec, mFragmentaClasses[i], null);
        }

        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.i(TAG, "onTabChanged: " + tabId);
            }
        });

    }

    private View getIndicatorView(int i) {
        View view = View.inflate(this, R.layout.layout_indicator_view, null);
        ImageView iv_tab = (ImageView) view.findViewById(R.id.iv_tab);
        TextView tv_tab = (TextView) view.findViewById(R.id.tv_tab);
        iv_tab.setImageResource(imageIds[i]);
        tv_tab.setText(text[i]);
        return view;
    }
}
