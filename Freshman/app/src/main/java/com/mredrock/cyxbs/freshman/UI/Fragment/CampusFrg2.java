package com.mredrock.cyxbs.freshman.UI.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mredrock.cyxbs.freshman.Adapter.CampusAdapter2;
import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.R;

import java.util.List;

/**
 * Created by Administrator on 2018/8/15 0015.
 */

public class CampusFrg2 extends BaseFragment{

    private RecyclerView recyclerView;
    private List<CampusBean.DataBean> campusBeanList;

    public CampusFrg2() {
    }

    @SuppressLint("ValidFragment")
    public CampusFrg2(List<CampusBean.DataBean> campusBeanList) {
        this.campusBeanList = campusBeanList;
    }

    @Override
    public int getContentViewId() {
        return R.layout.freshman_h_bank_frg;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.xygn_recycler);
    }
    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        CampusAdapter2 adapter = new CampusAdapter2(getActivity(), campusBeanList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    /*
    一定要调用这个方法设置数据
    */
    public void setCampusBeanList(List<CampusBean.DataBean> campusBeanList) {
        this.campusBeanList = campusBeanList;
    }

    // 方便获取 Fragment 实例
    public static CampusFrg2 getInstance() {
        return new CampusFrg2();
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }
}