package com.mredrock.cyxbs.freshman.UI.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mredrock.cyxbs.freshman.Adapter.MyPagerAdapter;
import com.mredrock.cyxbs.freshman.R;


public class MienActivity extends BaseActivity implements View.OnClickListener{
    private View paddingView;
    private ImageView titleBackImv;
    private TextView titleTv;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private String[] titles = new String[]{"学生组织", "大型活动"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freshman_mien_h_activity);
        init();
        fusionStatusBar(paddingView);
    }

    private void init() {

        mViewPager =findViewById(R.id.cyfc_view_pager);
        mTabLayout =findViewById(R.id.cyfc_tab_layout);


        paddingView = findViewById(R.id.cyfc_padding_view);
        titleBackImv = findViewById(R.id.cyfc_title_back);
        titleTv = findViewById(R.id.fengcai_title_tv);
        TextPaint tp = titleTv.getPaint();
        tp.setFakeBoldText(true);
        titleBackImv.setOnClickListener(this);


        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), titles, MyPagerAdapter.CYFC_TABLAYOUT);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

        // 通过自定义的TabLayout条目样式来修改条目字体大小（16dp）
        for (int i = 0; i < adapter.getCount(); i++) {
            View view1 = LayoutInflater.from(this).inflate(R.layout.freshman_h_custom_tab, null);
            TextView textView = view1.findViewById(R.id.tab_item_tv);
            TextPaint tp1 = titleTv.getPaint();
            tp1.setFakeBoldText(true);
            textView.setText(adapter.getPageTitle(i));
            mTabLayout.getTabAt(i).setCustomView(view1);
        }


//        mTabLayout.post(() -> setIndicator(mTabLayout, 40, 40));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cyfc_title_back:
                finish();
                break;
        }
    }
}
