package com.learn.four.banner;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

public class RecyclerBannerActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private RecyclerBannerAdapter adapter;
    private PagerSnapHelper snapHelper;
    private LinearLayoutManager layoutManager;
    private static final int AUTO_PLAY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_banner);
        ButterKnife.bind(this);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        init();

        // 自动轮播
        Observable.interval(3,TimeUnit.SECONDS)
                  .subscribe(aLong -> autoPlay.sendEmptyMessage(AUTO_PLAY));

    }

    private void init() {
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        adapter = new RecyclerBannerAdapter();
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(RecyclerBannerAdapter.pageCount * 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycler_banner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scroll_vertical:
                layoutManager =
                        new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                break;
            case R.id.scroll_horizontal:
                layoutManager =
                        new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                break;
        }
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(RecyclerBannerAdapter.pageCount * 1000);
        return true;
    }

    private Handler autoPlay = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case AUTO_PLAY:
                    recyclerView.smoothScrollToPosition(
                            recyclerView.getChildLayoutPosition(snapHelper.findSnapView(layoutManager)) + 1
                    );
                    break;
            }
            return true;
        }
    });
}
