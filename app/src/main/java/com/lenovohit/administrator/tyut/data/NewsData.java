package com.lenovohit.administrator.tyut.data;

import com.lenovohit.administrator.tyut.domain.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */

public class NewsData {
   public static  List<News>list1=new ArrayList<>();
    public static  List<News>list2=new ArrayList<>();
    public static  List<News>list3=new ArrayList<>();
    public static  List<News>list4=new ArrayList<>();
    public static List<News> getList1() {
        return list1;
    }

    public static void setList1(List<News> list) {
        NewsData.list1.clear();
        NewsData.list1.addAll(list);
    }

    public static List<News> getList2() {
        return list2;
    }

    public static void setList2(List<News> list2) {
        NewsData.list2.clear();
        NewsData.list2.addAll(list2);
    }

    public static List<News> getList3() {
        return list3;
    }

    public static void setList3(List<News> list3) {
        NewsData.list3.clear();
        NewsData.list3.addAll(list3);
    }

    public static List<News> getList4() {
        return list4;
    }

    public static void setList4(List<News> list4) {
        NewsData.list4.clear();
        NewsData.list4.addAll(list4);
    }
}
