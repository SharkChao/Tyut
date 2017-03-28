package com.lenovohit.administrator.tyut.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lenovohit.administrator.tyut.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/2/20.
 */

public class NetStateReceiver extends BroadcastReceiver implements Observable{
    static List<Observer>lists=new ArrayList<>();
    private boolean isNet;

    @Override
    public void onReceive(Context context, Intent intent) {
        isNet = NetworkUtil.isConnected(context);
        notifyObserver();
    }

    @Override
    public void notifyObserver() {
        for (Observer observer:lists){
            observer.Update(isNet);
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        lists.add(observer);
    }

    @Override
    public void UnRegisterObserver(Observer observer) {
        lists.remove(observer);
    }
}
