package com.learn.four.banner;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.learn.four.banner.Utils.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        initViewPager();
    }

    private void initViewPager() {
        //            List<MyPagerFrag> frags = new ArrayList<>(4);
        //            for (int pos = 0; pos < 4; pos++) {
        //                frags.add(MyPagerFrag.getInstance(pos));
        //            }
        //        MyPagerFragAdapter adapter = new MyPagerFragAdapter(getSupportFragmentManager(),frags);

       /* List<View> frags = new ArrayList<>(4);
        View view = getLayoutInflater().inflate(R.layout.frg_pager, null);
        RelativeLayout layout = view.findViewById(R.id.frg_pager_layout);
        layout.setBackgroundColor(Color.BLACK);
        TextView tv = view.findViewById(R.id.frg_pager_tv);
        tv.setText("One");
        frags.add(view);

        view = getLayoutInflater().inflate(R.layout.frg_pager, null);
        layout = view.findViewById(R.id.frg_pager_layout);
        layout.setBackgroundColor(Color.CYAN);
        tv = view.findViewById(R.id.frg_pager_tv);
        tv.setText("Two");
        frags.add(view);

        view = getLayoutInflater().inflate(R.layout.frg_pager, null);
         layout = view.findViewById(R.id.frg_pager_layout);
        layout.setBackgroundColor(Color.GREEN);
         tv = view.findViewById(R.id.frg_pager_tv);
        tv.setText("Three");
        frags.add(view);

        view = getLayoutInflater().inflate(R.layout.frg_pager, null);
        layout = view.findViewById(R.id.frg_pager_layout);
        layout.setBackgroundColor(Color.RED);
        tv = view.findViewById(R.id.frg_pager_tv);
        tv.setText("Four");
        frags.add(view);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(frags); */

        MyPagerFragAdapter adapter = new MyPagerFragAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new MyPagerFrgTransformer());
        viewPager.setOffscreenPageLimit(2);
        // 把它设为负数会有另外的效果 (*^__^*)
        viewPager.setPageMargin(DensityUtil.dip2px(getApplicationContext(), 5));
        viewPager.setCurrentItem(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.to_recycler_banner:
                Intent intent = new Intent(this, RecyclerBannerActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}


