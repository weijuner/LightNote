package com.rainbow.lightnote.app;

import android.app.Activity;
import android.app.Application;

import com.rainbow.lightnote.ui.activity.BaseActivity;

import java.util.ArrayList;

/**
 * Created by weijuner on 2015/9/6.
 */
public class BaseApplication extends Application {

    /**
     * Activity集合
     */
    private static ArrayList<BaseActivity> activitys = new ArrayList<BaseActivity>();

    @Override
    public void onCreate() {

    }

    /**
     * 添加Activity到ArrayList<Activity>管理集合
     * @param activity
     */
    public void addActivity(BaseActivity activity) {
        String className = activity.getClass().getName();
        for (Activity at : activitys) {
            if (className.equals(at.getClass().getName())) {
                activitys.remove(at);
                break;
            }
        }
        activitys.add(activity);
    }

    /**
     * 退出应用程序的时候，手动调用
     */
    @Override
    public void onTerminate() {
        for (BaseActivity activity : activitys) {
            activity.defaultFinish();
        }
    }

}