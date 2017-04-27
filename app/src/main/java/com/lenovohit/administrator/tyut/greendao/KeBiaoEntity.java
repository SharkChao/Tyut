package com.lenovohit.administrator.tyut.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2017-04-25.
 * 课表实体类
 */
@Entity
public class KeBiaoEntity {
    @Id
    Long id;
    //详细的课表内容
     String value;
    //课表信息对应的节数
     String  state;
    //课表对应的星期数
     String xingqi;
    @Generated(hash = 1996468739)
    public KeBiaoEntity(Long id, String value, String state, String xingqi) {
        this.id = id;
        this.value = value;
        this.state = state;
        this.xingqi = xingqi;
    }
    @Generated(hash = 1628808789)
    public KeBiaoEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getXingqi() {
        return this.xingqi;
    }
    public void setXingqi(String xingqi) {
        this.xingqi = xingqi;
    }

}
