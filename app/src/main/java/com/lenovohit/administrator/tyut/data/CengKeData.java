package com.lenovohit.administrator.tyut.data;

import com.lenovohit.administrator.tyut.domain.CengKeQuery;
import com.lenovohit.administrator.tyut.domain.Courses;
import com.lenovohit.administrator.tyut.greendao.KeBiaoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-04-26.
 * 蹭课的数据都存放在这里
 */

public class CengKeData {
    //所在学期，所有的院系信息
    private static CengKeQuery cengKeQuery;
    //查询到的课程信息
    private static List<Courses>list=new ArrayList<>();
    //查询到的课表详情页面
    public static List<KeBiaoEntity>list2=new ArrayList<>();
    //查询到的总的课程的个数
    public static String num;

    public static String getNum() {
        return num;
    }

    public static void setNum(String num) {
        CengKeData.num = num;
    }

    public static List<KeBiaoEntity> getList2() {
        return list2;
    }

    public static void setList2(List<KeBiaoEntity> list2) {
        CengKeData.list2 = list2;
    }

    public static CengKeQuery getCengKeQuery() {
        return cengKeQuery;
    }

    public static void setCengKeQuery(CengKeQuery cengKeQuery) {
        CengKeData.cengKeQuery = cengKeQuery;
    }

    public static List<Courses> getList() {
        return list;
    }

    public static void setList(List<Courses> list) {
        CengKeData.list = list;
    }
}
