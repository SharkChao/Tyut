package com.lenovohit.administrator.tyut.net.module;

import android.content.Context;

import dagger.Component;

/**
 * Created by Administrator on 2017/2/13.
 */
@Component(modules = AppModule.class)
public interface AppComponent {
    Context getApp();
}
