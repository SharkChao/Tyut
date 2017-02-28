package com.lenovohit.administrator.tyut.app;

import android.app.Application;

/**
 * Created by Administrator on 2017/2/20.
 */

public class MyApp extends Application {
   private  static MyApp app=new MyApp();
    private MyApp(){

    }
    public static MyApp getInstance(){
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

}
