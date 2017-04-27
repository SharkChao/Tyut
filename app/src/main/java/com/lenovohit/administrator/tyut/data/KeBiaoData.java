package com.lenovohit.administrator.tyut.data;


import com.lenovohit.administrator.tyut.greendao.KeBiaoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-04-21.
 */

public class KeBiaoData {
    public static  String html="";
    public static List<KeBiaoEntity>list=new ArrayList<>();
    public static String getHtml() {
        return html;
    }

    public static void setHtml(String html) {
        KeBiaoData.html = html;
    }

    public static List<KeBiaoEntity> getList() {
        return list;
    }

    public static void setList(List<KeBiaoEntity> list) {
        KeBiaoData.list = list;
    }
}
