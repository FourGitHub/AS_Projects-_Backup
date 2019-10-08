package com.mredrock.cyxbs.freshman.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mredrock.cyxbs.freshman.Adapter.MyPagerAdapter;
import com.mredrock.cyxbs.freshman.DataBean.SexRatioBean;
import com.mredrock.cyxbs.freshman.DataBean.SubjectBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.DataDetailPresenter;
import com.mredrock.cyxbs.freshman.MVP.View.DataDetailView;
import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.UI.Fragment.BaseFragment;
import com.mredrock.cyxbs.freshman.UI.Fragment.DifficultestFrg;
import com.mredrock.cyxbs.freshman.UI.Fragment.ProportionFrg;
import com.mredrock.cyxbs.freshman.Utility.TabLayoutIndicatorHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


// 这
public class DataActivity extends BaseActivity implements DataDetailView, View.OnClickListener {

    private static final String TAG = "Data";
    @BindView(R.id.padding_view)
    View paddingView;
    @BindView(R.id.title_back)
    ImageView titleBackImv;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    // 根据不同学院，应该讲标题栏设为对应学院名字
    private MyPagerAdapter adapter;
    private String[] titles = new String[]{"男女比例", "最难科目"};
    private String collegeName;
    private DataDetailPresenter mPresenter;

    private DifficultestFrg difficultestFrg;
    private ProportionFrg proportionFrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 和军训的布局一毛一样，所以直接复用了。
        setContentView(R.layout.freshman_h_activity_jun_xun);
        ButterKnife.bind(this);
        init();
        mPresenter = new DataDetailPresenter();
        mPresenter.attachView(this);
        mPresenter.requestData(collegeName);

    }

    @Override
    public void showData(SexRatioBean ratioBean, SubjectBean subjectBean) {
        if (ratioBean != null)proportionFrg.setPieData(ratioBean);
         if (subjectBean!=null)difficultestFrg.showData(subjectBean);
    }

    private void init() {
        collegeName = getIntent().getStringExtra("collegeName");

        mTitleTv.setText(collegeName);
        TextPaint tp = mTitleTv.getPaint();
        tp.setFakeBoldText(true);


        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(proportionFrg = new ProportionFrg());
        fragments.add(difficultestFrg = new DifficultestFrg());
        proportionFrg.setName(collegeName);
        adapter = new MyPagerAdapter(getSupportFragmentManager(), titles, fragments);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);


        titleBackImv.setOnClickListener(this);
        // 通过自定义的TabLayout条目样式来修改条目字体大小（16dp）
        for (int i = 0; i < titles.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.freshman_h_custom_tab, null);
            TextView textView = view.findViewById(R.id.tab_item_tv);
            TextPaint tp1 = textView.getPaint();
            textView.setText(adapter.getPageTitle(i));
            tp1.setFakeBoldText(true);
            mTabLayout.getTabAt(i).setCustomView(view);
        }
        mTabLayout.post(() -> TabLayoutIndicatorHelper.setIndicator(this,mTabLayout, 40, 40));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.w(TAG, "-->> position = " + position);
                if (adapter.getCurrentFragment() instanceof DifficultestFrg) {
                    ((DifficultestFrg) adapter.getCurrentFragment()).show();
                } else {
                    ((ProportionFrg) adapter.getCurrentFragment()).show();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fusionStatusBar(paddingView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }


    @Override
    public Context getContext() {
        return this;
    }

    public static void actionStart(Context context, String name) {
        Intent intent = new Intent(context, DataActivity.class);
        intent.putExtra("collegeName", name);
        context.startActivity(intent);
    }
}