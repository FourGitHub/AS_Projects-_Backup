package com.example.four.materialtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefresh;
    private DrawerLayout mDrawerLayout;
    private RecyclerView fruitView;
    private List<Fruit> fruitList = new ArrayList<>();
    private FruitAdaptor adapter;
    private GridLayoutManager manager;
    private static Fruit[] fruits = {new Fruit("菠萝", R.drawable.boluo), new Fruit("草莓", R.drawable.caomei), new Fruit("车厘子", R.drawable.chelizi), new Fruit("鲜橙", R.drawable.juzi), new Fruit("蓝莓", R.drawable.lanmei), new Fruit("柠檬", R.drawable.linmeng), new Fruit("荔枝", R.drawable.lizhi), new Fruit("芒果", R.drawable.mangguo), new Fruit("毛丹", R.drawable.maodan), new Fruit("猕猴桃", R.drawable.mihoutao), new Fruit("苹果", R.drawable.pingguo), new Fruit("葡萄", R.drawable.putao), new Fruit("山竹", R.drawable.sanzu), new Fruit("石榴", R.drawable.shiliu), new Fruit("树莓", R.drawable.shumei), new Fruit("杨桃", R.drawable.yangtao), new Fruit("椰子", R.drawable.yezi), new Fruit("樱桃", R.drawable.yintao), new Fruit("桑葚", R.drawable.sanseng), new Fruit("无花果", R.drawable.wuhuaguo), new Fruit("木瓜", R.drawable.mugua), new Fruit("哈密瓜", R.drawable.hamigua), new Fruit("血橙", R.drawable.xuecheng), new Fruit("榴莲", R.drawable.liulian), new Fruit("香蕉", R.drawable.xiangjiao_1)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.tool_bar);

        toolbar.setLogo(R.drawable.heart);

        toolbar.setTitle(" ");

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        final CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator_layout);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // 启用 HomeAsUp 按钮
            actionBar.setHomeAsUpIndicator(R.drawable.icon_home);
        }


        // 侧滑菜单栏中的控件点击事件，默认选中 R.id.nav_call ，点击任意一项关闭侧滑菜单
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.nav_call);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(getApplicationContext(), "关闭侧滑菜单！\nmDrawerLayout.closeDrawer();", Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        // 悬浮式按钮监听点击事件。使用自定义snackbar工具类弹出交互框。
        // 这里讲匿名类用lambda表达式替换了 鼠标光标指向匿名类的地方快键键 alt + Enter
        fab.setOnClickListener(view -> snackBarUtils.showSnackbar(coordinatorLayout, "Snackbar.make().setAction();\n下拉刷新、上拉加载、长按删除！"));

        // 初始化水果数据
        initFruitList();

        // 初始化recyclerView 并 注册RecyclerView.onScrollListener，实现下拉加载
        initRecyclerView();

        // 初始化SwipeRefreshLayout 并 注册SwipeRefreshLayout.OnRefreshListener，实现上拉刷新
        initSwipeRefresh();

    }

    // （重新）初始化fruitList的数据
    public void initFruitList() {
        fruitList.clear();
        int i = 0;
        while (i < 50) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
            i++;
        }
        //        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //        Environment.getExternalStorageDirectory();
        //        Environment.getDownloadCacheDirectory();
        //        Environment.getExternalStorageState();
    }

    public void initRecyclerView() {
        // recyclerView的标准用法
        fruitView = (RecyclerView) findViewById(R.id.recycler_view);
        fruitView.setItemAnimator(new DefaultItemAnimator());
        manager = new GridLayoutManager(this, 2);
        fruitView.setLayoutManager(manager);
        adapter = new FruitAdaptor(fruitList);
        fruitView.setAdapter(adapter);

        //上拉加载：自定义监听器 EndLessOnScrollListener implements RecyclerView.onScrollListener
        fruitView.addOnScrollListener(new EndLessOnScrollListener(manager) {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });


    }

    public void initSwipeRefresh() {
        // 下拉刷新，只需将需要刷新的控件放入SwipeRefreshLayout中
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refesh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruit();
            }
        });

    }

    //每次上拉加载的时候，给RecyclerView的后面随机添加了20条数据
    private void loadMoreData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 切回主线程UI更新
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for (int i = 0; i < 20; i++) {
                            Random random = new Random();
                            int index = random.nextInt(fruits.length);
                            fruitList.add(fruits[index]);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }).start();
    }


    // 下拉刷新：模拟两秒刷新recyclerView中的数据
    public void refreshFruit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 切回主线程UI更新
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruitList();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    // toolbar 上的的菜单栏加载
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    // toolbar 上的按钮点击事件。( HomeAsUp导航按钮 和 Toolbar 上的菜单项)

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START); // 弹出菜单
                break;

            case R.id.backup:
                //                new Handler().postDelayed(new Runnable() {
                //                    @Override
                //                    public void run() {
                //                        runOnUiThread(new Runnable() {
                //                            @Override
                //                            public void run() {
                //                                initFruitList();
                //                                adapter.notifyDataSetChanged();
                //                                swipeRefresh.setRefreshing(false);
                //                            }
                //                        });
                //                    }
                //                },3000);
                Person person = new Person();
                person.setName("F_our");
                person.setSex("Man");
                person.setSingle("single");
                //                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(this, FruitActivity.class);
                intent.putExtra("post_msg", person);
                //                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
                //                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME,SystemClock.elapsedRealtime(),pi);
                startActivity(intent);
                finish();
                break;
            case R.id.setting:
                ToastUtil.showToast(getApplicationContext(), "app:showAsAction= \"never\"", Toast.LENGTH_SHORT);
                break;
            case R.id.delete:
                ToastUtil.showToast(getApplicationContext(), "app:showAsAction = \"ifRoom\"", Toast.LENGTH_SHORT);
                break;
            default:
                break;
        }
        return true;
    }

}


