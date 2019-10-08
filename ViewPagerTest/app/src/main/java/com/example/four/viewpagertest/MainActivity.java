package com.example.four.viewpagertest;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<View> viewList;
    private BottomNavigationView bnv;
    private MyReceiver receiver;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Log.i(TAG, "--> onCreate()");
    }

    private void init() {
        viewPager = findViewById(R.id.my_view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view_0 = inflater.inflate(R.layout.item_vp_0, null);
        Button send =  view_0.findViewById(R.id.button);
        send.setOnClickListener(v -> {
            Intent i = new Intent("android.intent.action.TEST_BROADCAST");
            sendBroadcast(i);
        });
        View view_1 = inflater.inflate(R.layout.item_vp_1, null);
        View view_2 = inflater.inflate(R.layout.item_vp_2, null);
        View view_3 = inflater.inflate(R.layout.item_vp_3, null);
        viewList = new ArrayList<>();
        viewList.add(view_0);
        viewList.add(view_1);
        viewList.add(view_2);
        viewList.add(view_3);
        MyPagerAdapter adapter = new MyPagerAdapter(viewList);
        viewPager.setAdapter(adapter);
        bnv = findViewById(R.id.navigation);
        bnv.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.call:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.massage:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.search:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.delete:
                    viewPager.setCurrentItem(3);
                    break;
            }
            return true;
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bnv.setSelectedItemId(R.id.call);
                        break;
                    case 1:
                        bnv.setSelectedItemId(R.id.massage);
                        break;
                    case 2:
                        bnv.setSelectedItemId(R.id.search);
                        break;
                    case 3:
                        bnv.setSelectedItemId(R.id.delete);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < adapter.getCount(); i++) {
            View customTab = inflater.inflate(R.layout.custom_tab_layout, null);
            TextView tabText = customTab.findViewById(R.id.tab_text);
            tabText.setText(adapter.getPageTitle(i));
            tabLayout.getTabAt(i).setCustomView(customTab);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.TEST_BROADCAST");
        receiver = new MyReceiver();
        registerReceiver(receiver, intentFilter);
        Log.i(TAG, "--> onStart() register");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "--> onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "--> onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
        Log.i(TAG, "--> onStop() unregister");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "--> onDestroy()");
    }
}

