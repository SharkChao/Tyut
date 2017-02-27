package com.lenovohit.administrator.tyut.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lenovohit.administrator.tyutclient.utils.NetworkUtil;


/**
 * Created by Administrator on 2017/2/20.
 */

public class NetStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isNet = NetworkUtil.isConnected(context);
        if (isNet) {
//            EventBus.getDefault().post(0, ConstantValue.Net_open);
        } else {
//            EventBus.getDefault().post(1, ConstantValue.Net_open);
        }
    }
}