package com.learn.four.tablayoutdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_Pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initViewPager() {
        int numsOfPager = 4;
        List<View> pagers = new ArrayList<>(numsOfPager);
        LayoutInflater layoutInflater = getLayoutInflater();
        pagers.add(layoutInflater.inflate(R.layout.pager_item0, null));
        pagers.add(layoutInflater.inflate(R.layout.pager_item1, null));
        pagers.add(layoutInflater.inflate(R.layout.pager_item2, null));
        pagers.add(layoutInflater.inflate(R.layout.pager_item3, null));

        viewPager.setAdapter(new MyPagerAdapter(pagers));

    }

}
