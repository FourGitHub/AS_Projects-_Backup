package com.mredrock.cyxbs.freshman.MVP.View;

import android.content.Context;

import com.mredrock.cyxbs.freshman.DataBean.SexRatioBean;
import com.mredrock.cyxbs.freshman.DataBean.SubjectBean;

/**
 * Created by FengHaHa on2018/8/18 0018 14:11
 */
public interface DataDetailView extends BaseView {
  void showData(SexRatioBean ratioBean, SubjectBean subjectBean);
}
