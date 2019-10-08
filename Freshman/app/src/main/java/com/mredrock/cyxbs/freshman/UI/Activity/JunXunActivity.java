package com.mredrock.cyxbs.freshman.UI.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mredrock.cyxbs.freshman.Adapter.MyJunXunPagerAdapter;
import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.UI.Fragment.JunXunFengCaiFrg;
import com.mredrock.cyxbs.freshman.UI.Fragment.JunXunTieShiFrg;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JunXunActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "JunXunActivity";
    @BindView(R.id.padding_view)
    View mPaddingView;
    @BindView(R.id.title_back)
    ImageView mTitleBackImv;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.title_tv)
    TextView titleTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freshman_h_activity_jun_xun);
        ButterKnife.bind(this);
        init();
        fusionStatusBar(mPaddingView);
        mTitleBackImv.setOnClickListener(this);
    }

    private void init() {
        TextPaint tp1 = titleTv.getPaint();
        tp1.setFakeBoldText(true);

        Fragment[] fragments = new Fragment[]{
                new JunXunFengCaiFrg(), new JunXunTieShiFrg()
        };
        MyJunXunPagerAdapter mAdapter = new MyJunXunPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        // 通过自定义的TabLayout条目样式来修改条目字体大小（16dp）
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.freshman_h_custom_tab, null);
            TextView textView = view.findViewById(R.id.tab_item_tv);
            textView.setText(mAdapter.getPageTitle(i));
            mTabLayout.getTabAt(i).setCustomView(view);
        }
//        mTabLayout.post(() -> setIndicator(mTabLayout, 40, 40));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }
}
