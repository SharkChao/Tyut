package com.lenovohit.administrator.tyut.net.module;

import android.content.Context;

import com.lenovohit.administrator.tyut.app.MyApp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/2/13.
 */
@Module
public class AppModule {
    @Provides
    Context provideAppContext(){
        return MyApp.getInstance();
    }
}
