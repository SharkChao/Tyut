package com.lenovohit.administrator.tyut.data;

import com.lenovohit.administrator.tyut.domain.BmobUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/15.
 */

public class UserData {
 public static List<BmobUser>list=new ArrayList<>();
    public static List<BmobUser>findList=new ArrayList<>();

    public static List<BmobUser> getList() {
        return list;
    }

    public static void setList(List<BmobUser> list) {
        UserData.list = list;
    }

    public static List<BmobUser> getFindList() {
        return findList;
    }

    public static void setFindList(List<BmobUser> findList) {
        UserData.findList = findList;
    }
}
