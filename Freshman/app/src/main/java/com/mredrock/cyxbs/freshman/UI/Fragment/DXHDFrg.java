package com.mredrock.cyxbs.freshman.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mredrock.cyxbs.freshman.Adapter.DXHDRecyclerAdapter;
import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.MVP.Presenter.MienPresenter;
import com.mredrock.cyxbs.freshman.MVP.View.MienView;
import com.mredrock.cyxbs.freshman.R;

import java.util.List;

/**
 * Created by Administrator on 2018/8/15 0015.
 * 大型活动Fragment
 */

public class DXHDFrg extends BaseFragment implements MienView {

    private RecyclerView recyclerView;
    private MienPresenter mPresenter;


    @Override
    public int getContentViewId() {
        return R.layout.freshman_h_dxhd_frg;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.dxhd_recycler);

        mPresenter = new MienPresenter();
        mPresenter.attachView(this);
        mPresenter.getData("大型活动");
    }

    @Override
    public void showData(List<CampusBean.DataBean> dataBeanList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        DXHDRecyclerAdapter adapter = new DXHDRecyclerAdapter(getActivity(), dataBeanList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
