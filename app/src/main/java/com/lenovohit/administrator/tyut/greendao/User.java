package com.lenovohit.administrator.tyut.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by Administrator on 2017/2/28.
 */
@Entity
public class User {
    @Id
    private Long id;//主键
    @Property(nameInDb = "account")//外键，查找其他表都是通过账号来关联的
    private String account;//账号
    @Property(nameInDb = "password")
    private String password;//密码
    @Transient
    @ToOne(joinProperty = "account")
    private XueFen xueFen;
    @Generated(hash = 602562219)
    public User(Long id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAccount() {
        return this.account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
   
}
