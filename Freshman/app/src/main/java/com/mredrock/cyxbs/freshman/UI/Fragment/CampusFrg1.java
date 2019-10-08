package com.mredrock.cyxbs.freshman.UI.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mredrock.cyxbs.freshman.Adapter.CampusAdapter1;
import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.R;

import java.util.List;

/**
 * Created by Administrator on 2018/8/14 0014.
 * CampusFrg1 的 RecyclerView适配器是 CampusAdapter1
 * 1-3-1、1-3-3、1-3-4、1-3-5、1-3-8
 * （Fragment复用，只需要切换Fragment的时候，传入不同的 List<CampusBean> 就行了）
 */
@SuppressLint("ValidFragment")
public class CampusFrg1 extends BaseFragment {

    private RecyclerView recyclerView;
    private List<CampusBean.DataBean> campusBeanList;
    public static final int TYPE_FOOD_FRG = 1;
    public static final int TYPE_OTHER_FRG = 2;
    private int type;


    public CampusFrg1() {
    }

    public CampusFrg1(List<CampusBean.DataBean> campusBeanList, int type) {
        this.campusBeanList = campusBeanList;
        this.type = type;
    }



    @Override
    public int getContentViewId() {
        if (type == TYPE_OTHER_FRG) {
            return R.layout.freshman_h_xygn_canteen_frg;
        } else {
            return R.layout.freshman_h_meishi_frg;
        }

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
        CampusAdapter1 adapter= new CampusAdapter1(getActivity(), campusBeanList, type);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }

}
