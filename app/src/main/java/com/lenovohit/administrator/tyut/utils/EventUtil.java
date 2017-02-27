package com.lenovohit.administrator.tyut.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/2/23.
 */

public class EventUtil {
    public static void register(Object context) {
        if (!EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().register(context);
        }
    }
    public static void unregister(Object context) {
        if (EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().unregister(context);
        }
    }
    public static void post(Object object) {
        EventBus.getDefault().post(object);
    }
    public static void postSticky(Object object) {
        EventBus.getDefault().postSticky(object);
    }
}