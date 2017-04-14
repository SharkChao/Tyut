package com.lenovohit.administrator.tyut.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/3/30.
 */
@Entity
public class Token {

    /**
     * code : 200
     * userId : 2013006238
     * token : pBZoiCpC3fHC4yubOP1FBvPUGMaftX9sGs6/td50hRLXvH/odweYtfwxcnd8TKTb9VawXmn3x6k5YhksKNeVte9XegDe1nV1
     */

    @Transient
    private int code;
    @Id
    private String userId;
    private String token;
    @Generated(hash = 326596530)
    public Token(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }
    @Generated(hash = 79808889)
    public Token() {
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }

}
