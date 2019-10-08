package com.mredrock.cyxbs.freshman.UI.Activity;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.mredrock.cyxbs.freshman.Adapter.RuXueAdapter;
import com.mredrock.cyxbs.freshman.CustomView.PopWindow;
import com.mredrock.cyxbs.freshman.DataBean.SaveBean.DescribeBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.RuXuePresenter;
import com.mredrock.cyxbs.freshman.MVP.View.RuXueView;
import com.mredrock.cyxbs.freshman.R;
import com.mredrock.cyxbs.freshman.Utility.DensityUtil;

import java.util.List;

import butterknife.ButterKnife;


public class RuXueActivity extends AppCompatActivity implements RuXueView, View.OnClickListener {
    private static final String TAG = "RuXueActivity";


    private Vibrator mVibrater;
    private View paddingView;

    private ImageView ruxueTitleBackupImv;
    private ImageView ruxueTitleInfoImv;
    private TextView ruxueTitleTv;
    private TextView ruxueTitleRightTv;

    private RecyclerView ruxueRecyclerView;
    private FloatingActionButton fab;


    private RuXueAdapter mAdapter;


    private RuXuePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freshman_h_activity_ru_xue);
        ButterKnife.bind(this);
        init();
        fusionStatusBar();
        mPresenter = new RuXuePresenter();
        mPresenter.attachView(this);
        // 初始化列表数据并显示。
        mPresenter.showList();
    }

    private void init() {
        paddingView = findViewById(R.id.padding_view);
        mVibrater = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        ruxueTitleBackupImv = findViewById(R.id.ruxue_title_backup_img_view);
        ruxueTitleInfoImv = findViewById(R.id.ruxue_title_info);
        ruxueTitleRightTv = findViewById(R.id.ruxue_title_right_tv);
        ruxueTitleTv = findViewById(R.id.ruxue_title_tv);
        TextPaint tp = ruxueTitleRightTv.getPaint();
        tp.setFakeBoldText(true);
        TextPaint tp1 = ruxueTitleTv.getPaint();
        tp1.setFakeBoldText(true);
        ruxueRecyclerView = findViewById(R.id.ruxue_recycler);
        fab = findViewById(R.id.ruxue_fab);

        fab.setOnClickListener(this);

        fab.attachToRecyclerView(ruxueRecyclerView);


        // 点击编辑按钮
        ruxueTitleRightTv.setOnClickListener(this);

        // 点击标题栏上的返回，关闭 入学必备Activity,返回recyclerView中的数据，更新数据库。
        ruxueTitleBackupImv.setOnClickListener(this);

        // 点击问号，弹出Dialog
        ruxueTitleInfoImv.setOnClickListener(this);

    }

    private void fusionStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        ViewGroup.LayoutParams params = paddingView.getLayoutParams();
        params.height = getStatusBarHeight();
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public void onBackPressed() {
        if (mAdapter != null && mAdapter.isEditing()) {
            cancelEdit();
            return;
        }
        super.onBackPressed();
    }


    public void setDelTv(int count) {
        ruxueTitleRightTv.setText("删除" + "(" + count + ")");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ruxue_title_right_tv:
                edit();
                break;
            case R.id.ruxue_fab:
                fab.hide();
                showAddMoreWindow();
                break;
            case R.id.ruxue_title_info:
                showInformation();
                break;
            case R.id.ruxue_title_backup_img_view:
                exit();
                break;
        }
    }

    @Override
    public void exit() {
        finish();
    }

    @Override
    public void vibrate() {
        if (mVibrater.hasVibrator()) {
            mVibrater.vibrate(30);
        }
    }

    @Override
    public void showList(List<DescribeBean> list) {
        int numOfDone = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isChecked()) {
                numOfDone++;
            }
            Log.e(TAG, "init -->> isDone =  " + list.get(i).isChecked());
        }

        // 初始化 RuXue RecyclerView数据
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mAdapter = new RuXueAdapter(this);
        mAdapter.setLastHaveDoneItemPosition(numOfDone);
        ruxueRecyclerView.setLayoutManager(layoutManager);
        mAdapter.setDataList(list);
        ruxueRecyclerView.setAdapter(mAdapter);
        ruxueRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.w(TAG, "-->> onScrollStateChanged: ");
                int totalCount = mAdapter.getItemCount();
                int slast = layoutManager.findLastVisibleItemPosition();
                int sfirst = layoutManager.findFirstVisibleItemPosition();

                if (!mAdapter.isEditing()) {
                    if ((slast - sfirst + 1) == totalCount && !(newState == RecyclerView.SCROLL_STATE_DRAGGING)) {
                        fab.setVisibility(View.VISIBLE);
                        fab.show();
                    }
                }else fab.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public void addNewItem(DescribeBean bean) {
        mAdapter.addNewItem(bean);
    }

    @Override
    public void edit() {
        vibrate();
        // 如果说点击就告诉你，这就是编辑模式，则这次点击表示退出编辑模式（可能会删除数据源中的一些项）
        if (mAdapter.isEditing()) {
            mAdapter.editMode(false);
            mAdapter.notifyDataSetChanged();
            ruxueTitleRightTv.setText("编辑");
            fab.setVisibility(View.VISIBLE);
            fab.show();
        } else {
            mAdapter.editMode(true);
            ruxueTitleRightTv.setText("删除" + "(" + mAdapter.getDelNumber() + ")");
            fab.setVisibility(View.GONE);
        }
    }

    @Override
    public void cancelEdit() {
        mAdapter.cancelEdit();
        ruxueTitleRightTv.setText("编辑");
        fab.setVisibility(View.VISIBLE);
        fab.show();
    }

    @Override
    public void showAddMoreWindow() {
        View mView = LayoutInflater.from(this).inflate(R.layout.freshman_h_custom_additem, null);
        Button addItemBtn = mView.findViewById(R.id.add_btn);
        EditText editText = mView.findViewById(R.id.edit_text);
        PopWindow popWindow = new PopWindow.PopupWindowBuilder(this)
                .setView(mView)
                .setFocusable(true)
                .setOutsideTouchable(true)
                .setAnimationStyle(R.style.pop_add_anim)
                .size(0, DensityUtil.dip2px(this, 55))
                .create();
        addItemBtn.setOnClickListener(v -> {
            String itemName = editText.getText().toString().trim();
            if (!TextUtils.isEmpty(itemName)) {
                addNewItem(new DescribeBean("", mAdapter.getItemCount(),
                        itemName, "非必需", false, false, true,true));
                showToast("添加成功！");
            } else {
                // 提示未添加任何代办事件
                showToast("事件名称不能为空哦！");
            }
            popWindow.dismiss();
            fab.show();
        });
        popWindow.showAtLocation(findViewById(R.id.ruxue_layout), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void showInformation() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(RuXueActivity.this);
        View customAlertDialogLayout = getLayoutInflater().inflate(R.layout.freshman_h_custom_dialog_img, null);
        builder.setCancelable(true);
        builder.setView(customAlertDialogLayout);
        final AlertDialog dialog = builder.create();
        ImageButton button = customAlertDialogLayout.findViewById(R.id.close_dialog);
        button.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    @Override
    public void saveData() {
        if (mAdapter != null) {
            mPresenter.saveData(mAdapter.getDataList());
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}


