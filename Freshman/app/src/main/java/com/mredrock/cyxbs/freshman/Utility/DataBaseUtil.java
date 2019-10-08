package com.mredrock.cyxbs.freshman.Utility;

import com.mredrock.cyxbs.freshman.DataBean.SaveBean.BaseBean;

import org.litepal.LitePal;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/18 0018 16:09
 */
public class DataBaseUtil {
    public static <T extends BaseBean> List<T> getData(Class<T> clazz) {
        return LitePal.findAll(clazz);
    }

    public static <T extends BaseBean> void saveData(Class<T> clazz, List<T> data) {
        new Thread(() -> {
            LitePal.deleteAll(clazz);
            for (int i = 0; i < data.size(); i++) {
                data.get(i).getBean().save();
            }
        }
        ).start();
    }
}

