package com.lenovohit.administrator.tyut.app;

import android.app.Application;

import com.lenovohit.administrator.tyutclient.domain.City;

/**
 * Created by Administrator on 2017/2/20.
 */

public class MyApp extends Application {
    private City city;
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
    public City getLocalCity(){
        return  city;
    }
    public void setLocalCity(City city){
        this.city=city;
    }

}
