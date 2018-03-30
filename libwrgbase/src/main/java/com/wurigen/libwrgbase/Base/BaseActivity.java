package com.wurigen.libwrgbase.Base;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.wurigen.libwrgbase.LibUtils.ActiivtyStack;
import com.wurigen.libwrgbase.LibUtils.StatusBarUtils;
import com.wurigen.libwrgbase.R;


/**
 * @author Wurigen
 * @Createby 2017/11/17
 * @Ps 所有Activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    private TextView mToolbarTitle;
    private TextView mToolbarSubTitle;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        //设置为无标题模式
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        initToolbar();
        initStatusBar();
        initView();
        initData();
        ActiivtyStack.getScreenManager().pushActivity(this);
        //  initEvent();
        //  initFindViewById();
        registerBroadrecevicer();
    }

    private void registerBroadrecevicer() {

        //创建意图过滤器
        IntentFilter filter = new IntentFilter();

    }

    /**
     * 初始化BaseToolbar
     */
    public void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarSubTitle = (TextView) findViewById(R.id.toolbar_subtitle);
        if (mToolbar != null) {
            //将Toolbar显示到界面
            setSupportActionBar(mToolbar);
        }
        if (mToolbarTitle != null) {
            //getTitle()的值是activity的android:lable属性值
            mToolbarTitle.setText(getTitle());
            //设置默认的标题不显示
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * API>14时设置为沉浸式状态栏
     */
    public void initStatusBar() {
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }
        StatusBarUtils.compat(this, getResources().getColor(R.color.BaseAppColor));
    }


    @Override
    protected void onStart() {
        super.onStart();
        // 判断是否有Toolbar,并默认显示返回按钮
        if (null != getToolbar() && isShowBacking()) {
            showBack();
        }
    }


    /**
     * 获取头部标题的TextView
     *
     * @return
     */
    public TextView getToolbarTitle() {
        return mToolbarTitle;
    }

    /**
     * 获取头部标题的TextView
     *
     * @return
     */
    public TextView getSubTitle() {
        return mToolbarSubTitle;
    }

    /**
     * 设置头部标题
     *
     * @param title
     */
    public void setToolBarTitle(String title) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        } else {
            getToolbar().setTitle(title);
            setSupportActionBar(getToolbar());
        }
    }

    /**
     * this Activity of tool bar.
     * 获取头部.
     *
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * 版本号小于21的后退按钮图片
     */
    private void showBack() {
        getToolbar().setNavigationIcon(R.drawable.icon_back);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return
     */
    protected boolean isShowBacking() {
        return true;
    }

    /**
     * this activity layout res
     * 设置layout布局,在子类重写该方法.
     *
     * @return res layout xml id
     */
    protected abstract int getLayoutId();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //通知系统进行GC回收
        System.gc();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    abstract protected void initView();

    abstract protected void initData();


    /**
     * 关闭所有Activity
     */
    public static void finishAll() {
        ActiivtyStack.getScreenManager().clearAllActivity();
    }


//    protected void initFindViewById() {
//
//    }
//
//    protected void initEvent() {
//
//    }


    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    /**
     * 退出应用
     */
    public void exitApp(final Class clazz) {

        Dialog alertDialog = new AlertDialog.Builder(this).
                setTitle("您确定退出吗？").
                setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAll();
                        startActivity(new Intent(getApplicationContext(), clazz));


                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                }).
                create();
        alertDialog.show();
    }



}
