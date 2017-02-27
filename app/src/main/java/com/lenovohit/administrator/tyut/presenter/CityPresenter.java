package com.lenovohit.administrator.tyut.presenter;

import android.content.Context;

import com.lenovohit.administrator.tyutclient.domain.Citys;
import com.lenovohit.administrator.tyutclient.model.imp.CityModelImp;
import com.lenovohit.administrator.tyutclient.view.imp.CityView;

/**
 * Created by Administrator on 2017/2/23.
 */

public class CityPresenter {
    CityModelImp cityModel;
    CityView cityView;
    public CityPresenter(CityView cityView){
        this.cityView=cityView;
        cityModel=new CityModelImp((Context)cityView);
    }
    public void saveCity(){

    }
    public Citys getCity(){
        return null;
    }
    public Citys getAllCity(){
       return cityModel.getAllCity();
    }
}
