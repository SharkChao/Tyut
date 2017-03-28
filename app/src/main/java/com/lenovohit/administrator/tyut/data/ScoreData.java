package com.lenovohit.administrator.tyut.data;

import com.lenovohit.administrator.tyut.greendao.Score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/6.
 */

public class ScoreData {
    //学分绩点的json字符串
    public static String json;
    //本学期
   public static  List<Score>list1=new ArrayList<>();
    //所有学期
    public static  List<Score>list2=new ArrayList<>();
    //尚不及格
    public static  List<Score>list31=new ArrayList<>();
    //曾不及格
    public static  List<Score>list32=new ArrayList<>();
    //上学期
    public static  List<Score>list4=new ArrayList<>();
    //所有学期成绩
    public static Map<String,List<Score>>map=new HashMap<>();

    public static Map<String, List<Score>> getMap() {
        return map;
    }

    public static void setMap(Map<String, List<Score>> map) {
        ScoreData.map = map;
    }

    public static List<Score> getList1() {
        return list1;
    }

    public static void setList1(List<Score> list) {
        ScoreData.list1.clear();
        ScoreData.list1.addAll(list);
    }

    public static List<Score> getList2() {
        return list2;
    }

    public static void setList2(List<Score> list2) {
        ScoreData.list2.clear();
        ScoreData.list2.addAll(list2);
    }


    public static void setList31(List<Score> list3) {
        ScoreData.list31.clear();
        ScoreData.list31.addAll(list3);
    }

    public static List<Score> getList31() {
        return list31;
    }

    public static List<Score> getList32() {
        return list32;
    }

    public static void setList32(List<Score> list32) {
        ScoreData.list32.clear();
        ScoreData.list32.addAll(list32);
    }

    public static List<Score> getList4() {
        return list4;
    }

    public static void setList4(List<Score> list4) {
        ScoreData.list4.clear();
        ScoreData.list4.addAll(list4);
    }

    public static String getJson() {
        return json;
    }

    public static void setJson(String json) {
        ScoreData.json = json;
    }
}
