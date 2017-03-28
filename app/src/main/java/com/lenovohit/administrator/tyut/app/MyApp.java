package com.lenovohit.administrator.tyut.app;

import android.app.Application;

import com.lenovohit.administrator.tyut.constant.Constant;
import com.lenovohit.administrator.tyut.greendao.User;
import com.lenovohit.administrator.tyut.net.module.ActivityComponent;
import com.lenovohit.administrator.tyut.net.module.ActivityModule;
import com.lenovohit.administrator.tyut.net.module.AppComponent;
import com.lenovohit.administrator.tyut.net.module.AppModule;
import com.lenovohit.administrator.tyut.net.module.DaggerActivityComponent;
import com.lenovohit.administrator.tyut.net.module.DaggerAppComponent;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2017/2/20.
 */

public class MyApp extends Application {
    private ActivityComponent activityComponent;
    private static User user;
   private  static MyApp app=new MyApp();
    private boolean isCooection=false;

    public MyApp(){

    }
    public static MyApp getInstance(){
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
//        SkinCompatManager.init(this)                          // 基础控件换肤初始化
//                .addInflater(new SkinMaterialViewInflater())  // material design 控件换肤初始化[可选]
//                .addInflater(new SkinCardViewInflater())      // CardView 控件换肤初始化[可选]
//                .loadSkin();
        AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();
        activityComponent=  DaggerActivityComponent.builder().appComponent(appComponent).activityModule(new ActivityModule(Constant.LoginUrl,this)).build();
        RongIM.init(this);
    }
    public ActivityComponent getActivityComponent(){
        return activityComponent;
    }
    public static void setUser(User user){
        MyApp.user=user;
    }

    public static User getUser() {
        return user;
    }
}
