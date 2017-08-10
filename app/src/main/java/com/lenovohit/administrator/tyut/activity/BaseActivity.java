package com.lenovohit.administrator.tyut.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.lenovohit.administrator.tyut.app.AppManager;
import com.lenovohit.administrator.tyut.receiver.NetStateReceiver;
import com.lenovohit.administrator.tyut.receiver.Observer;

import butterknife.ButterKnife;
import cn.feng.skin.manager.base.BaseFragmentActivity;

/**
 * Created by Administrator on 2017/2/21.
 */

public abstract class BaseActivity extends BaseFragmentActivity implements Observer{

    public static Activity currentActivity;
    private NetStateReceiver receiver;
    private AppManager appManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTheme(android.R.style.Theme_Light_NoTitleBar);
        receiver = new NetStateReceiver();
        receiver.registerObserver(this);
        currentActivity=this;
        appManager = AppManager.getAppManager();
        appManager.addActivity(this);
        initView();
        initDate();
        initEvent();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        initBaseView();
    }

    public void initBaseView() {
    }

    public abstract void initView();

    public abstract void initDate();

    public abstract void initEvent();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        receiver.UnRegisterObserver(this);
    }
}
