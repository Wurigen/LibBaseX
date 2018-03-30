package com.wurigen.libwrgbase.LibUtils;

import android.app.Activity;

import java.util.Stack;

/**
 * @author Wurigen
 * @Createby 2017/10/16
 * @Ps 存放继承过BaseActivity的打开过的Activity
 */

public class ActiivtyStack {
    private static Stack<Activity> mActivityStack = new Stack<Activity>();
    private static ActiivtyStack instance = new ActiivtyStack();

    private ActiivtyStack() {
    }

    public static ActiivtyStack getScreenManager() {
        return instance;
    }

    /**
     * 弹出当前activity并销毁
     *
     * @param activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            mActivityStack.remove(activity);
        }
    }

    /**
     * 将当前Activity添加到栈中
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        mActivityStack.add(activity);
    }

    /**
     * 退出栈中所有Activity
     */
    public void clearAllActivity() {
        while (!mActivityStack.isEmpty()) {
            Activity activity = mActivityStack.pop();
            if (activity != null) {
                activity.finish();
            }
        }
    }

}