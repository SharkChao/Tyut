package com.lenovohit.administrator.tyut.fragment.two;

import android.content.Context;
import android.content.Intent;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;

/**
 * Created by Administrator on 2017/3/17.
 * 特殊服务
 */

public class TeShuActivity extends BaseActivity {
    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.layout_teshu);
    }

    @Override
    public void initDate() {

    }

    @Override
    public void initEvent() {

    }
    public static void startTeShuActivity(Context context){
        context.startActivity(new Intent(context,TeShuActivity.class));
    }
}
