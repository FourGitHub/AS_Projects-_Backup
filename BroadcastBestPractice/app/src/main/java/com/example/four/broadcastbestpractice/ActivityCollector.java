package com.example.four.broadcastbestpractice;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/9 0009.
 */

public class ActivityCollector {
    private static List<Activity> activityList = new ArrayList<Activity>();

   public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void finishAllActivity() {
        for (Activity activity : activityList) {
                if(!activity.isFinishing()){
                    activity.finish();
                }
        }
        activityList.clear();
    }
}
