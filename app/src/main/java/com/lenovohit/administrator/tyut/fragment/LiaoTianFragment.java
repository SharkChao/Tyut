package com.lenovohit.administrator.tyut.fragment;

import android.content.Context;
import android.view.View;

import com.lenovohit.administrator.tyut.R;


/**
 * Created by SharkChao on 2017/1/13.
 * 查看学习资料
 */

public class LiaoTianFragment extends BaseFragment {

    private Context context;
    public LiaoTianFragment(Context context){
        this.context=context;
    }
    @Override
    public View initView() {
        View v=View.inflate(context, R.layout.fragment_liaotian,null);
        return v;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initEvent() {
    }

    @Override
    public void loadData() {
    }
}
