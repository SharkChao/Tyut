package com.lenovohit.administrator.tyut.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

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
    @ToOne(joinProperty = "account")
    private XueFen xueFen;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;
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
    @Generated(hash = 1044308562)
    private transient String xueFen__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1051239080)
    public XueFen getXueFen() {
        String __key = this.account;
        if (xueFen__resolvedKey == null || xueFen__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            XueFenDao targetDao = daoSession.getXueFenDao();
            XueFen xueFenNew = targetDao.load(__key);
            synchronized (this) {
                xueFen = xueFenNew;
                xueFen__resolvedKey = __key;
            }
        }
        return xueFen;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 118057604)
    public void setXueFen(XueFen xueFen) {
        synchronized (this) {
            this.xueFen = xueFen;
            account = xueFen == null ? null : xueFen.getXh();
            xueFen__resolvedKey = account;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }
}
