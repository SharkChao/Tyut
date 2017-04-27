package com.lenovohit.administrator.tyut.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017-04-17.
 * 一键评教实体类
 */
@Entity
public class PingJiao {
    @Id
    public Long id;
    @Property(nameInDb = "coursename")
    public String coursename;
    @Property(nameInDb = "teachername")
    public String teachername;
    @Property(nameInDb = "xuefen")
    public String xuefen;
    @Property(nameInDb = "isdo")
    public Boolean isdo;
    @Property(nameInDb = "sex")
    public String sex;
    @Property(nameInDb = "pingjiao")
    public String pingjiao;
    @Generated(hash = 1875124009)
    public PingJiao(Long id, String coursename, String teachername, String xuefen,
            Boolean isdo, String sex, String pingjiao) {
        this.id = id;
        this.coursename = coursename;
        this.teachername = teachername;
        this.xuefen = xuefen;
        this.isdo = isdo;
        this.sex = sex;
        this.pingjiao = pingjiao;
    }
    @Generated(hash = 376954894)
    public PingJiao() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCoursename() {
        return this.coursename;
    }
    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }
    public String getTeachername() {
        return this.teachername;
    }
    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }
    public String getXuefen() {
        return this.xuefen;
    }
    public void setXuefen(String xuefen) {
        this.xuefen = xuefen;
    }
    public Boolean getIsdo() {
        return this.isdo;
    }
    public void setIsdo(Boolean isdo) {
        this.isdo = isdo;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getPingjiao() {
        return this.pingjiao;
    }
    public void setPingjiao(String pingjiao) {
        this.pingjiao = pingjiao;
    }
}
