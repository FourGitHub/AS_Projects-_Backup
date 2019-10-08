package com.example.four.recyclerviewtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VerticalActivity extends BasicActivity {
    private static final String TAG = "VerticalActivity";
    private List<Fruit> fruitList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FruitAdapter adapter;
    private Handler mHandler;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_in_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.horizontal:
                Intent intent = new Intent(VerticalActivity.this, HorizontalActivity.class);
                startActivity(intent);
                break;
            case R.id.staggered:
                Intent intent1 = new Intent(VerticalActivity.this, StaggeredActivity.class);
                startActivity(intent1);
                break;
            case R.id.GridLayout:
                Intent intent2 = new Intent(VerticalActivity.this, GridLayoutActivity.class);
                startActivity(intent2);
                break;
            case R.id.harvest:
                Intent intent3 = new Intent(VerticalActivity.this, SummaryActivity.class);
                startActivity(intent3);
                break;
            case R.id.quit:
                ActivityCollector.removeAll();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vertical_layout);
        initFruits();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            /**
             * 一旦RecyclerView的滚动状态改变，就会回调此方法
             * @param newState
             * SCROLL_STATE_IDLE （0）- RecyclerView当前不在滚动（拖住不滚，或者确实没有滑动屏幕）
             * SCROLL_STATE_DRAGGING （1）- RecyclerView当前正在被手指拖住
             * SCROLL_STATE_SETTLING （2）- RecyclerView目前正滚动，但手指已经离开屏幕
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case 0:
                        Log.i(TAG, "newState ->> SCROLL_STATE_IDLE");
                        break;
                    case 1:
                        Log.i(TAG, "newState ->> SCROLL_STATE_DRAGGING");
                        break;
                    case 2:
                        Log.i(TAG, "newState ->> SCROLL_STATE_SETTLING");
                        break;
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            /**
             * 一旦recyclerVirew发送滑动动作，就会回调此方法，只要列表在滚动，不管手指有没有离开屏幕
             * @param dx 水平滚动的数量
             * @param dy 垂直滚动的数量
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.i(TAG, "onScrolled >> dx = " + dx + "    dy = " + dy);
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        // 创建LinearLayoutManager的对象，用于指定 RecyclerView的布局方式
//         LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
                                               int position) {
                LinearSmoothScroller linearSmoothScroller =
                    new LinearSmoothScroller(recyclerView.getContext()) {
                        /* 此方法定义，滚动到指定位置的Item时，Item的顶部 | 底部边缘与父RecyclerView对齐 */
                        @Override
                        protected int getVerticalSnapPreference() {
                            return SNAP_TO_START;
                        }
                    };
                linearSmoothScroller.setTargetPosition(position);
                startSmoothScroll(linearSmoothScroller);
            }
        };

        recyclerView.setLayoutManager(layoutManager);

        adapter = new FruitAdapter(fruitList, R.layout.subitem_vertical_layout);// 将要适配的数据源传入到适配器
        recyclerView.setAdapter(adapter);// 数据和 RecyclerView 关联
        refresh();
    }

    private void refresh() {
        mHandler = new Handler();
        mHandler.postDelayed(() -> {
            fruitList.remove(0);
            adapter.notifyItemRemoved(0);
        }, 2000);

        mHandler.postDelayed(() -> {
            fruitList.add(0, new Fruit("微笑", R.drawable.expression_1));
            adapter.notifyItemInserted(0);

            // 重写LinearLayoutManager，再使用 smoothScrollToPosition 时，就能滑动置顶
            recyclerView.smoothScrollToPosition(0);

            adapter.notifyDataSetChanged();
        }, 3500);

        mHandler.postDelayed(() -> {
            fruitList.set(1, new Fruit("流泪", R.drawable.expression_6));
            adapter.notifyItemChanged(1);
        }, 5000);

        mHandler.postDelayed(() -> {
            Fruit fruit = fruitList.get(16);
            fruitList.remove(16);
            fruitList.add(2, fruit);
            adapter.notifyItemMoved(16, 2);
        }, 6500);

    }


    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Fruit smile = new Fruit("微笑", R.drawable.expression_1);
            fruitList.add(smile);
            Fruit weiqu = new Fruit("委屈", R.drawable.expression_2);
            fruitList.add(weiqu);
            Fruit se = new Fruit("花痴", R.drawable.expression_3);
            fruitList.add(se);
            Fruit zhengzha = new Fruit("挣扎", R.drawable.expression_4);
            fruitList.add(zhengzha);
            Fruit taoqi = new Fruit("淘气", R.drawable.expression_5);
            fruitList.add(taoqi);
            Fruit liulei = new Fruit("流泪", R.drawable.expression_6);
            fruitList.add(liulei);
            Fruit haixiu = new Fruit("害羞", R.drawable.expression_7);
            fruitList.add(haixiu);
            Fruit siluo = new Fruit("失落", R.drawable.expression_8);
            fruitList.add(siluo);
            Fruit nanguo = new Fruit("难过", R.drawable.expression_9);
            fruitList.add(nanguo);
            Fruit jiujie = new Fruit("脸红", R.drawable.expression_13);
            fruitList.add(jiujie);
            Fruit aoman = new Fruit("傲慢", R.drawable.expression_15);
            fruitList.add(aoman);
            Fruit zayan = new Fruit("蜜汁微笑", R.drawable.expression_17);
            fruitList.add(zayan);
            Fruit weixiao = new Fruit("微笑", R.drawable.expression_19);
            fruitList.add(weixiao);
            Fruit tongyi = new Fruit("开心", R.drawable.expression_20);
            fruitList.add(tongyi);
            Fruit zhishi = new Fruit("知识", R.drawable.expression_21);
            fruitList.add(zhishi);
            Fruit menguan = new Fruit("懵圈儿", R.drawable.expression_25);
            fruitList.add(menguan);
            Fruit feiwen = new Fruit("飞吻", R.drawable.expression_26);
            fruitList.add(feiwen);
            Fruit xiaoku = new Fruit("笑哭", R.drawable.expression_27);
            fruitList.add(xiaoku);
            Fruit anjing = new Fruit("闭嘴", R.drawable.expression_29);
            fruitList.add(anjing);
            Fruit ganga = new Fruit("尴尬", R.drawable.expression_33);
            fruitList.add(ganga);
            Fruit buman = new Fruit("不满", R.drawable.expression_35);
            fruitList.add(buman);
            Fruit tiaodou = new Fruit("挑逗", R.drawable.expression_36);
            fruitList.add(tiaodou);
        }
    }
}
