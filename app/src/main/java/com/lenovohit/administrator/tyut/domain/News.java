package com.lenovohit.administrator.tyut.domain;

/**
 * Created by Administrator on 2017/2/16.
 * 新闻实体
 */

public class News {
    //标题
    private String Title;
    //时间
    private String date;
    //网址
    private String url;
    //图片网址
    private String imageUrl;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
