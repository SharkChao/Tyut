package com.lenovohit.administrator.tyut.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

/**
 * Created by Administrator on 2017/2/21.
 */

public abstract class BaseActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTheme(android.R.style.Theme_Light_NoTitleBar);
        //判断当前SDK版本号，如果是4.4以上，就是支持沉浸式状态栏的
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        initView();
        initDate();
        initEvent();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initBaseView();
    }

    public void initBaseView() {
    }

    public abstract void initView();

    public abstract void initDate();

    public abstract void initEvent();
}