package com.example.four.launchmodetest;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/21 0021.
 */

public class ActivityCollector {
    public static final List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity a : activities){
            if(!a.isFinishing())
            {
                a.finish();
            }
        }
        activities.clear();
    }
}
