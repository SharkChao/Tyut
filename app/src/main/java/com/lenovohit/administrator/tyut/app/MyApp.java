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
import com.lenovohit.administrator.tyut.utils.GlideImageLoader;
import com.orhanobut.logger.LogLevel;

import cn.bmob.v3.Bmob;
import cn.feng.skin.manager.loader.SkinManager;
import cn.finalteam.galleryfinal.BuildConfig;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.toolsfinal.Logger;
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
//      SkinManager.getInstance().init(this);
//        SkinManager.getInstance().load();
        initLogger();
        AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();
        activityComponent=  DaggerActivityComponent.builder().appComponent(appComponent).activityModule(new ActivityModule(Constant.LoginUrl,this)).build();
        //第一：默认初始化
        Bmob.initialize(this, "2b6a4b2e8c3caf4a759cd1f29178964d");
        RongIM.init(this);
        //配置主题
//ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
        .build();
//配置功能
//        SkinManager.getInstance().init(this);
        SkinManager.getInstance().init(this);
        SkinManager.getInstance().load();
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
        .build();

//配置imageloader
        ImageLoader imageloader = new GlideImageLoader();
//设置核心配置信息
        CoreConfig coreConfig = new CoreConfig.Builder(getApplicationContext(), imageloader, theme)
                .setDebug(BuildConfig.DEBUG)
                .setFunctionConfig(functionConfig)
        .build();
        GalleryFinal.init(coreConfig);
    }
    private void initLogger(){
        LogLevel logLevel;
        logLevel = LogLevel.FULL;
        Logger.init("tag",true);                 // default PRETTYLOGGER or use just init()
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
