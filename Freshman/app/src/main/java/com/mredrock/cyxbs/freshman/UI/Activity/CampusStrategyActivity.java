package com.mredrock.cyxbs.freshman.UI.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mredrock.cyxbs.freshman.MVP.Presenter.CampusStrategyPresenter;
import com.mredrock.cyxbs.freshman.MVP.View.CampusStrategyView;
import com.mredrock.cyxbs.freshman.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CampusStrategyActivity extends BaseActivity implements CampusStrategyView, View.OnClickListener {

    @BindView(R.id.card_dining_hall)
    CardView cardDiningHall;
    @BindView(R.id.card_environment)
    CardView cardEnvironment;
    @BindView(R.id.card_data)
    CardView cardData;
    @BindView(R.id.card_bank)
    CardView cardBank;
    @BindView(R.id.card_bus)
    CardView cardBus;
    @BindView(R.id.card_dormitory)
    CardView cardDormitory;
    @BindView(R.id.card_express)
    CardView cardExpress;
    @BindView(R.id.card_view_spot)
    CardView cardViewSpot;
    @BindView(R.id.card_food)
    CardView cardFood;
    @BindView(R.id.campus_title_tv)
    TextView campusTitleTv;
    @BindView(R.id.iv_campus_back)
    ImageView mBack;
    @BindView(R.id.base_view)
    RelativeLayout baseView;
    @BindView(R.id.campus_page_padding_view)
    View campusPagePaddingView;

    private CampusStrategyPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freshman_campus_strategy_f_activity);
        ButterKnife.bind(this);
        mPresenter = new CampusStrategyPresenter();
        mPresenter.attachView(this);
        TextPaint tp = campusTitleTv.getPaint();
        tp.setFakeBoldText(true);
        initListener();
        fusionStatusBar(campusPagePaddingView);
    }

    private void initListener() {
        cardBank.setOnClickListener(this);
        cardBus.setOnClickListener(this);
        cardData.setOnClickListener(this);
        cardDiningHall.setOnClickListener(this);
        cardViewSpot.setOnClickListener(this);
        cardDormitory.setOnClickListener(this);
        cardEnvironment.setOnClickListener(this);
        cardExpress.setOnClickListener(this);
        cardFood.setOnClickListener(this);
        mBack.setOnClickListener(v -> onBack());


    }

    @Override
    public void onItemClick(View view) {
        //mPresenter.onItemClick(view.getId());
        Log.d("onItemClick", "id = " + view.getId());
    }

    @Override
    public void enterDetails(Fragment fragment) {
        baseView.setVisibility(View.GONE);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_wrapper, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void setTitle(String title) {
        TextPaint tp = campusTitleTv.getPaint();
        tp.setFakeBoldText(true);
        campusTitleTv.setText(title);
    }

    @Override
    public void onBack() {
        if (baseView.getVisibility() == View.VISIBLE) finish();
        else {
            getSupportFragmentManager().popBackStack();
            setTitle("校园攻略");
            baseView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onBack();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        mPresenter.onItemClick(v.getId());
    }
}
