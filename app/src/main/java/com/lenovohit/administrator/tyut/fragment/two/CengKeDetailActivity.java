package com.lenovohit.administrator.tyut.fragment.two;

import android.content.Context;
import android.content.Intent;

import com.lenovohit.administrator.tyut.activity.BaseActivity;

/**
 * Created by Administrator on 2017-04-26.
 * 蹭课课程详情页
 */

public class CengKeDetailActivity extends BaseActivity{
    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initDate() {

    }

    @Override
    public void initEvent() {

    }
    public static void startCengkeDetailActivity(Context context){
        context.startActivity(new Intent(context,CengKeDetailActivity.class));
    }
}
