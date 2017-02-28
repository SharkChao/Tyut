package com.lenovohit.administrator.tyut.activity;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.utils.NetworkUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

public class SplashActivity extends BaseActivity {


    @Override
    public void initView() {
        setContentView(R.layout.activity_splash);

    }

    @Override
    public void initDate() {

    }

    @Override
    public void initEvent() {
        Observable.timer(3, TimeUnit.SECONDS)

                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //在此判断是否有网络
                        NetworkUtil.isConnected(SplashActivity.this);
                    }
                })
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        LoginActivity.startLoginActivity(SplashActivity.this);
                        finish();
                    }
                });
    }
}
