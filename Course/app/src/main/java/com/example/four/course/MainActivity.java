package com.example.four.course;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private final int CAMERA_REQUEST_CODE = 3;

    private Uri imageUri;
    LinearLayout courseTop;

    private BottomNavigationView mBottomNavigationView;
    private SwipeRefreshLayout kebiaoSwipeRefreshLayout;
    private SwipeRefreshLayout youwenSwipeRefreshLayout;

    private TabLayout KeBiaoTabLayout;
    private TabLayout YouWenTabLayout;

    private ViewPager KeBiaoViewPager;
    private ViewPager YouWenViewPager;
    private FrameLayout FaXianLayout;
    private FrameLayout WoDeLayout;

    private ImageView headerImageUpDowm;
    private ImageView headerImageLeft;
    private ImageView headerImageRight;
    private LinearLayout bgEmptyCourse;

    private CustomCircleHead circleHead;

    private TextView headerTextView;
    private List<View> viewList;

    private static int flag = 0;
    private static boolean isKeBiaoTabLayoutVisible = false;
    private static String currentWeek = "第一周";
    private static ViewPager currentViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        courseTop = (LinearLayout) findViewById(R.id.course_top);

        bgEmptyCourse = (LinearLayout) findViewById(R.id.bg_empty_course);
        circleHead = (CustomCircleHead) findViewById(R.id.image_head);
        // 标题栏
        headerImageLeft = (ImageView) findViewById(R.id.header_image_left);
        headerImageUpDowm = (ImageView) findViewById(R.id.header_image_up_down);
        headerImageRight = (ImageView) findViewById(R.id.header_image_right);
        headerTextView = (TextView) findViewById(R.id.header_tv_which_week);
        headerTextView.setText(currentWeek);

        // 往ViewPager里添加7个标签(这里把它写死了，仅展示效果)
        KeBiaoViewPager = (ViewPager) findViewById(R.id.kebiao_view_pager);
        KeBiaoTabLayout = (TabLayout) findViewById(R.id.ke_biao_tab_layout);
        KeBiaoTabLayout.setupWithViewPager(KeBiaoViewPager);
        KeBiaoTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        KeBiaoTabLayout.setVisibility(View.GONE);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view_0 = inflater.inflate(R.layout.course_frag, null);
        View view_1 = inflater.inflate(R.layout.course_frag, null);
        View view_2 = inflater.inflate(R.layout.course_frag, null);
        View view_3 = inflater.inflate(R.layout.course_frag, null);
        View view_4 = inflater.inflate(R.layout.course_frag, null);
        View view_5 = inflater.inflate(R.layout.course_frag, null);
        View view_6 = inflater.inflate(R.layout.course_frag, null);
        viewList = new ArrayList<>();
        viewList.add(view_0);
        viewList.add(view_1);
        viewList.add(view_2);
        viewList.add(view_3);
        viewList.add(view_4);
        viewList.add(view_5);
        viewList.add(view_6);
        KeBiaoPagerAdapter adapter = new KeBiaoPagerAdapter(viewList);
        KeBiaoViewPager.setAdapter(adapter);
        KeBiaoViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentWeek = (String) adapter.getPageTitle(position);
                headerTextView.setText(currentWeek);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 邮问ViewPager
        YouWenViewPager = (ViewPager) findViewById(R.id.you_wen_view_pager);
        YouWenTabLayout = (TabLayout) findViewById(R.id.you_wen_tab_layout);
        YouWenPagerAdapter adapter1 = new YouWenPagerAdapter();

        // 发现ViewPager
        FaXianLayout = (FrameLayout) findViewById(R.id.fa_xian_layout);

        // 我的ViewPager
        WoDeLayout = (FrameLayout) findViewById(R.id.wo_de_layout);

        // 底部菜单栏
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        int[][] states = new int[][]{new int[]{-android.R.attr.state_checked}, new int[]{android.R.attr.state_checked}};
        int[] colors = new int[]{getResources().getColor(R.color.home_bottom_normal), getResources().getColor(R.color
                .home_bottom_checked)};
        ColorStateList csl = new ColorStateList(states, colors);
        mBottomNavigationView.setItemTextColor(csl);
        mBottomNavigationView.setItemIconTintList(csl);
        mBottomNavigationView.setSelectedItemId(0);
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.ke_biao:
                    // 选择课表ViewPager可见
//                    KeBiaoViewPager.setVisibility(View.VISIBLE);
//                    YouWenViewPager.setVisibility(View.GONE);
                    kebiaoSwipeRefreshLayout.setVisibility(View.VISIBLE);
                    youwenSwipeRefreshLayout.setVisibility(View.GONE);
                    FaXianLayout.setVisibility(View.GONE);
                    WoDeLayout.setVisibility(View.GONE);
                    courseTop.setVisibility(View.VISIBLE);

                    // 首先，上/下 ImageView肯定是可见的，然后再根据课表ViewPager的
                    // TabLayout 可见性决定图标是上/下
                    headerImageUpDowm.setVisibility(View.VISIBLE);
                    if (isKeBiaoTabLayoutVisible) {
                        KeBiaoTabLayout.setVisibility(View.VISIBLE);
                        headerImageUpDowm.setImageResource(R.mipmap.icon_up);
                    } else {
                        KeBiaoTabLayout.setVisibility(View.GONE);
                        headerImageUpDowm.setImageResource(R.mipmap.icon_down);
                    }

                    // 修改顶部除了 上/下ImageView 区域的TextView和ImageView
                    headerImageLeft.setVisibility(View.GONE);
                    headerImageRight.setImageResource(R.mipmap.icon_add);
                    headerTextView.setText(currentWeek);

                    break;
                case R.id.you_wen:
                    // 选择邮问ViewPager可见
//                    KeBiaoViewPager.setVisibility(View.GONE);
//                    YouWenViewPager.setVisibility(View.VISIBLE);
                    kebiaoSwipeRefreshLayout.setVisibility(View.GONE);
                    youwenSwipeRefreshLayout.setVisibility(View.VISIBLE);
                    FaXianLayout.setVisibility(View.GONE);
                    WoDeLayout.setVisibility(View.GONE);
                    courseTop.setVisibility(View.GONE);
                    KeBiaoTabLayout.setVisibility(View.GONE);

                    // 修改邮问ViewPager的header
                    headerImageLeft.setImageResource(R.mipmap.icon_notice);
                    headerImageLeft.setVisibility(View.VISIBLE);
                    headerTextView.setText("邮问");
                    headerImageUpDowm.setVisibility(View.GONE);
                    headerImageRight.setVisibility(View.VISIBLE);
                    headerImageRight.setImageResource(R.mipmap.icon_select);
                    bgEmptyCourse.setVisibility(View.GONE);

                    break;
                case R.id.fa_xian:
                    // 选择发现ViewPager可见
//                    KeBiaoViewPager.setVisibility(View.GONE);
//                    YouWenViewPager.setVisibility(View.GONE);
                    kebiaoSwipeRefreshLayout.setVisibility(View.GONE);
                    youwenSwipeRefreshLayout.setVisibility(View.GONE);
                    FaXianLayout.setVisibility(View.VISIBLE);
                    WoDeLayout.setVisibility(View.GONE);
                    courseTop.setVisibility(View.GONE);
                    KeBiaoTabLayout.setVisibility(View.GONE);

                    // 修改发现ViewPager的header
                    headerImageLeft.setVisibility(View.GONE);
                    headerTextView.setText("发现");
                    headerImageUpDowm.setVisibility(View.GONE);
                    headerImageRight.setVisibility(View.GONE);
                    bgEmptyCourse.setVisibility(View.GONE);

                    break;
                case R.id.wo_de:
//                    KeBiaoViewPager.setVisibility(View.GONE);
//                    YouWenViewPager.setVisibility(View.GONE);
                    kebiaoSwipeRefreshLayout.setVisibility(View.GONE);
                    youwenSwipeRefreshLayout.setVisibility(View.GONE);
                    FaXianLayout.setVisibility(View.GONE);
                    WoDeLayout.setVisibility(View.VISIBLE);
                    courseTop.setVisibility(View.GONE);
                    KeBiaoTabLayout.setVisibility(View.GONE);
                    bgEmptyCourse.setVisibility(View.GONE);

                    // 修改我的ViewPager的header
                    headerImageLeft.setVisibility(View.GONE);
                    headerTextView.setText("我的");
                    headerImageUpDowm.setVisibility(View.GONE);
                    headerImageRight.setVisibility(View.GONE);
                    bgEmptyCourse.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
            return true;
        });

        // 中间 显示周一至周日课程的容器
        kebiaoSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.kebiao_swipe_refresh_layout);
        kebiaoSwipeRefreshLayout.setColorSchemeResources(R.color.swipe_refresh_color);
        kebiaoSwipeRefreshLayout.setOnRefreshListener(() -> refreshCourse());

        youwenSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.youwen_swipe_refresh_layout);
        youwenSwipeRefreshLayout.setColorSchemeResources(R.color.swipe_refresh_color);
        youwenSwipeRefreshLayout.setOnRefreshListener(() -> refreshQues());

        // 当界面位于 “课表” 界面时，点击切换上下箭头，并 显示/隐藏 TabLayout
        headerImageUpDowm.setOnClickListener(v -> {
            if (flag == 0) {
                KeBiaoTabLayout.setVisibility(View.VISIBLE);
                headerImageUpDowm.setImageResource(R.mipmap.icon_up);
                flag = 1;
                isKeBiaoTabLayoutVisible = true;
            } else {
                KeBiaoTabLayout.setVisibility(View.GONE);
                headerImageUpDowm.setImageResource(R.mipmap.icon_down);
                flag = 0;
                isKeBiaoTabLayoutVisible = false;
            }
        });

        circleHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                makeChoice();
            }
        });

    }

    /**
     * 课表的SwipeRefresh模拟下拉刷新
     */
    public void refreshCourse() {
        KeBiaoViewPager.setVisibility(View.GONE);
        bgEmptyCourse.setVisibility(View.VISIBLE);
        new Thread(() -> {
            // 耗时操作
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 切回主线程UI更新
            runOnUiThread(() -> {
                //切回主线程更新UI
                kebiaoSwipeRefreshLayout.setRefreshing(false);
                KeBiaoViewPager.setVisibility(View.VISIBLE);
                bgEmptyCourse.setVisibility(View.GONE);
            });
        }).start();
    }

    /**
     * 邮问的SwipeRefresh模拟下拉刷新
     */
    public void refreshQues() {
        new Thread(() -> {
            // 耗时操作
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 切回主线程UI更新
            runOnUiThread(() -> {
                //切回主线程更新UI
                youwenSwipeRefreshLayout.setRefreshing(false);
            });
        }).start();
    }

    private void makeChoice() {
        String[] items = {"拍照", "从相册选择"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("头像");
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.icon_ask);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) !=
                                PackageManager.PERMISSION_GRANTED) {
                            // 第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
                            // 向用户解释为什么需要这个权限
                            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest
                                    .permission.CAMERA)) {
                                new AlertDialog.Builder(MainActivity.this).setMessage
                                        ("由于使用该项功能需要获得相机权限，所以建议您同意应用获取相机权限！").setPositiveButton("确定", (dialog1,
                                                                                                       which1) -> {
                                    //申请相机权限
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest
                                            .permission.CAMERA}, CAMERA_REQUEST_CODE);
                                }).setNegativeButton("取消", (dialog12, which12) -> dialog12.dismiss()).show();
                            } else {
                                //申请相机权限
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission
                                        .CAMERA}, CAMERA_REQUEST_CODE);
                            }
                        } else {
                            takePhoto();
                        }
                        break;
                    case 1:
                        chooseFromAlbum();
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();

    }


    public void chooseFromAlbum() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    public void takePhoto() {
        // 为了兼容性  需在AndroidManifest.xml 中声明访问SD卡权限android.permission.WRITE_EXTERNAL_STORAGE
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT < 24) {
            // 如果运行设备系统版本低于7.0 ， 可以直接使用file:///uri,直接将File对象转换成Uri对象
            imageUri = Uri.fromFile(outputImage);
        } else {
            // 否则将File对象转换成一个封装过的Uri对象。核心代码，将file:///uri 转换成content://uri
            imageUri = FileProvider.getUriForFile(MainActivity.this, "com.example.four.course.fileprovider",
                    outputImage);
        }
        openCamera();
    }

    /**
     * 启动相机程序
     */
    public void openCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 打开相册
     */
    public void openAlbum() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "由于您拒绝了此权限，相应操作不能得到执行！", Toast.LENGTH_SHORT).show();
                }
                break;
            case CAMERA_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    //用户勾选了不再询问,提示用户手动打开权限
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                        Toast.makeText(this, "相机权限已被禁止，请到 \"设置 -> 应用管理 -> 应用权限\" 中开启！", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 拍照完成后，把照片的的uri传递给CustomCirclrHead
                        circleHead.setHeadImageUri(imageUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();//返回此意图所针对的数据的URI
        if (DocumentsContract.isDocumentUri(this, uri)) {// 判断uri是否为documents类型
            String docId = DocumentsContract.getDocumentId(uri);

            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {// 判断uri的authority是否为media格式
                String id = docId.split(":")[1];// 1号单元储存的是id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {//
                // 判断uri的authority是否为downloads格式
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long
                        .valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    /**
     * @param data 4.4以下的系统，选取相册中的图片返回的是图片真实的uri
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            circleHead.setHeadImagePath(imagePath);
        } else {
            Toast.makeText(this, "获取相册图片失败", Toast.LENGTH_LONG).show();
        }
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
