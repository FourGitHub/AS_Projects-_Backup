package com.mredrock.cyxbs.freshman.UI.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mredrock.cyxbs.freshman.Adapter.CampusAdapter3;
import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.R;

import java.util.List;

/**
 * Created by Administrator on 2018/8/15 0015.
 */

public class CampusFrg3 extends BaseFragment{

    private RecyclerView recyclerView;

    public CampusFrg3() {
    }

    @Override
    public int getContentViewId() {
        return R.layout.freshman_h_jiemi_frg;
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
        CampusAdapter3 adapter = new CampusAdapter3(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }



    // 方便获取 Fragment 实例
    public static CampusFrg3 getInstance() {
        return new CampusFrg3();
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }
}
