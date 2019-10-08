package com.mredrock.cyxbs.freshman.UI.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.mredrock.cyxbs.freshman.DataBean.SubjectBean;
import com.mredrock.cyxbs.freshman.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/17 0017.
 */
@SuppressLint("SetTextI18n")
public class DifficultestFrg extends BaseFragment {

    private BarChart barChart;
    private BarData barData;
    private XAxis xAxis;


//    //////需要更新数据地方就这三个////////

    //    List<BarEntry> entries; // Y轴的值
   public void showData(SubjectBean subjectBean) {
        List<String> xAxisValue = new ArrayList<>(); // X轴的值
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < subjectBean.getArray().size(); i++) {
            SubjectBean.ArrayBean arrayBean = subjectBean.getArray().get(i);
            xAxisValue.add(arrayBean.getSubject_name());
            entries.add(new BarEntry(i,arrayBean.getBelow_amount()));
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValue));
        BarDataSet set = new BarDataSet(entries, "科目");
        set.setColors(Color.parseColor("#c2a8ff"), Color.parseColor("#feaab7"), Color.parseColor("#a6bdfe"));
        set.setValueTextSize(14f);
        set.setValueTextColor(Color.parseColor("#fe8682"));
        set.setHighlightEnabled(false);
        set.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> {
            int n = (int) value;
            return n + "人";
        });
        barData = new BarData(set);
        barChart.setData(barData);
        barData.setBarWidth(0.35f);
        barChart.getDescription().setEnabled(false);
        barChart.setTouchEnabled(false);
        barChart.setDrawValueAboveBar(true);
        barChart.notifyDataSetChanged();
        barChart.animateXY(1000, 1000);
        // 图表数据设置，这里更新完毕后，需要调用 ：

    }

    @Override
    public int getContentViewId() {
        return R.layout.freshman_h_difficult_frg;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // xAxisValue = new ArrayList<>();
        barChart = view.findViewById(R.id.bar_chart);


        Legend legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(11f);
        legend.setTextColor(Color.GRAY);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);


        //x坐标轴数据设置
        xAxis = barChart.getXAxis();


        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(14f);
        xAxis.setTextColor(Color.parseColor("#DB0083FF"));
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);


        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);
        leftAxis.setTextColor(Color.parseColor("#DB0083FF"));
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridColor(Color.parseColor("#5954acff"));
        leftAxis.setGridDashedLine(new DashPathEffect(new float[]{25f, 25f}, 25f));
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setTextSize(13f);
        leftAxis.setAxisMinimum(0f);


    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }

    public void show() {
        barChart.notifyDataSetChanged();
        barChart.animateXY(1000, 1000);
    }

    public static DifficultestFrg getInstance() {
        return new DifficultestFrg();
    }
}
