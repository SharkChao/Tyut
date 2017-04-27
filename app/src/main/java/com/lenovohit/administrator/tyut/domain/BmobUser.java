package com.lenovohit.administrator.tyut.domain;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/4/13.
 */

public class BmobUser extends BmobObject {
    //昵称
    public String nickname;
    //性别
    public String sex;
    //头像网址
    public String picture;
    //真实姓名
    public String name;
    //个人简介
    public String happy;
    //所在专业
    public String zhuanye;
    //所在班级
    public String banji;
    //学号
    public String username;
    //好友列表
    public List<String>friend;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BmobUser(){
        this.setTableName("_User");
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHappy() {
        return happy;
    }

    public void setHappy(String happy) {
        this.happy = happy;
    }

    public String getZhuanye() {
        return zhuanye;
    }

    public void setZhuanye(String zhuanye) {
        this.zhuanye = zhuanye;
    }

    public String getBanji() {
        return banji;
    }

    public void setBanji(String banji) {
        this.banji = banji;
    }

    public List<String> getLists() {
        return friend;
    }

    public void setLists(List<String> lists) {
        this.friend = lists;
    }

    @Override
    public String toString() {
        return "BmobUser{" +
                "nickname='" + nickname + '\'' +
                ", sex='" + sex + '\'' +
                ", picture='" + picture + '\'' +
                ", name='" + name + '\'' +
                ", happy='" + happy + '\'' +
                ", zhuanye='" + zhuanye + '\'' +
                ", banji='" + banji + '\'' +
                ", account='" + username + '\'' +
                '}';
    }
}
