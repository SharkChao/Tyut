package com.lenovohit.administrator.tyut.domain;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017-04-25.
 */

public class feedback extends BmobObject {
    String message;
    String account;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
