package com.lenovohit.administrator.tyut.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/3/28.
 */
@Entity
public class ScoreHead {
    @Property(nameInDb = "title")
    public String title;

    @Generated(hash = 750832000)
    public ScoreHead(String title) {
        this.title = title;
    }

    @Generated(hash = 1941897101)
    public ScoreHead() {
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
