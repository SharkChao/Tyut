package com.lenovohit.administrator.tyut.domain;

import java.util.List;

/**
 * Created by Administrator on 2017-04-26.
 * 蹭课查询界面，可以选择的条件列表
 * 包括所在学期，所在系的列表
 */

public class CengKeQuery {
    //当前学期
    private String xueqi;
    //可以选择的院系
    private List<CourseCeng>yuanxis;

    public String getXueqi() {
        return xueqi;
    }

    public void setXueqi(String xueqi) {
        this.xueqi = xueqi;
    }

    public List<CourseCeng> getYuanxis() {
        return yuanxis;
    }

    public void setYuanxis(List<CourseCeng> yuanxis) {
        this.yuanxis = yuanxis;
    }
}
