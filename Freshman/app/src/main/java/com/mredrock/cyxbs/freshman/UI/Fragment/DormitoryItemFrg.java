package com.mredrock.cyxbs.freshman.UI.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mredrock.cyxbs.freshman.Adapter.CampusAdapter1;
import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.DormitoryPresenter;
import com.mredrock.cyxbs.freshman.MVP.View.DormitoryView;
import com.mredrock.cyxbs.freshman.R;

import java.util.List;

/**
 * Created by Administrator on 2018/8/14 0014.
 * CampusFrg1 的 RecyclerView适配器是 CampusAdapter1
 * 1-3-1、1-3-3、1-3-4、1-3-5、1-3-8
 * （Fragment复用，只需要切换Fragment的时候，传入不同的 List<CampusBean> 就行了）
 */
@SuppressLint("ValidFragment")
public class DormitoryItemFrg extends BaseFragment implements DormitoryView {

    private RecyclerView recyclerView;
    private DormitoryPresenter mPresenter;
    private String mDormitoryName;


    public DormitoryItemFrg() {
    }

    public DormitoryItemFrg(String mDormitoryName) {
        this.mDormitoryName = mDormitoryName;
    }

    @Override
    public int getContentViewId() {
        return R.layout.freshman_h_xygn_canteen_frg;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.xygn_recycler);
        mPresenter = new DormitoryPresenter();
        mPresenter.attachView(this);
        mPresenter.requestData(mDormitoryName);
    }

    @Override
    public void showData(List<CampusBean.DataBean> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        CampusAdapter1 adapter = new CampusAdapter1(getActivity(), data, CampusFrg1.TYPE_OTHER_FRG);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }
}
