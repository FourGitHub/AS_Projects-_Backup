package com.mredrock.cyxbs.freshman.UI.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mredrock.cyxbs.freshman.Adapter.EntranceAdapter;
import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.EntrancePresenter;
import com.mredrock.cyxbs.freshman.MVP.View.EntranceView;
import com.mredrock.cyxbs.freshman.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EntranceActivity extends BaseActivity implements EntranceView, View.OnClickListener {


    @BindView(R.id.padding_view)
    View paddingView;
    @BindView(R.id.title_back)
    ImageView titleBackImv;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.entrance_recycler)
    RecyclerView entranceRecycler;
    private EntranceAdapter adapter;
    private EntrancePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freshman_h_entrance_activity);
        ButterKnife.bind(this);
        initViews();
        initMvp();
    }

    private void initMvp() {
        mPresenter = new EntrancePresenter();
        mPresenter.attachView(this);
        mPresenter.requestData();
    }

    @Override
    public void showData(List<CampusBean.DataBean> beanList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new EntranceAdapter(this,beanList);
        entranceRecycler.setLayoutManager(layoutManager);
        entranceRecycler.setAdapter(adapter);
    }


    private void initViews() {
        titleBackImv.setOnClickListener(this);
        TextPaint tp = mTitleTv.getPaint();
        tp.setFakeBoldText(true);
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
}
