package com.mredrock.cyxbs.freshman.UI.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.mredrock.cyxbs.freshman.DataBean.SexRatioBean;
import com.mredrock.cyxbs.freshman.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/17 0017.
 */
public class ProportionFrg extends BaseFragment {

    private PieChart pieChart;
    // 学院名字
    private TextView nameTv;
    private String name;
    private SexRatioBean ratioBean;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getContentViewId() {
        return R.layout.freshman_h_proportion_frg;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pieChart = view.findViewById(R.id.spread_pie_chart);
        nameTv = view.findViewById(R.id.college_name);
        nameTv.setText(name + "男女比例");
    }

    private void showPieChart(PieChart pieChart, PieData pieData) {

        pieChart.setHoleRadius(40f); //半径
        pieChart.setTransparentCircleRadius(50f); //半透明圈
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(90); //初始角度
        pieChart.setRotationEnabled(true); //可以手动旋转
        pieChart.setUsePercentValues(true); //显示百分比
        pieChart.setDrawCenterText(true); //饼状图中间可以添加文字
        pieChart.setCenterText("男女比例");
        pieChart.setCenterTextColor(Color.GRAY);
        pieChart.setData(pieData);
        Legend mLegend = pieChart.getLegend(); //设置比例图
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER); //坐右边显示
        mLegend.setXEntrySpace(10f);
        mLegend.setYEntrySpace(5f);
        mLegend.setTextColor(Color.GRAY);
        pieChart.animateXY(1000, 1000);
    }

    // 这里设置数据
    public void setPieData(SexRatioBean sexRatioBean) {
        List<PieEntry> entries = new ArrayList<>();
        int total = sexRatioBean.getFemaleAmount() + sexRatioBean.getMaleAmount();
        float femaleRatio = new BigDecimal(sexRatioBean.getFemaleAmount() / (float) total).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

        /////////////////////////////////////////
        entries.add(new PieEntry(femaleRatio, "女"));
        entries.add(new PieEntry(1 - femaleRatio, "男"));
        /////////////////////////////////////////////
        PieDataSet set = new PieDataSet(entries, "颜色标注");
        set.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.valueOf(value).substring(0, 2) + "%";
            }
        });
        set.setColors(Color.parseColor("#B2FF2B98"), Color.parseColor("#B200a9ff"));
        set.setValueTextSize(18f);
        set.setValueTextColor(Color.WHITE);
        PieData mPieData = new PieData(set);
        showPieChart(pieChart, mPieData);
    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }

    public void show() {
//        pieChart.notifyDataSetChanged();
        pieChart.animateXY(1000, 1000);
    }

    public static ProportionFrg getInstance() {
        return new ProportionFrg();
    }
}
