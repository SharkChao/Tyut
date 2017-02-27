package com.lenovohit.administrator.tyut.fragment;

import android.content.Context;
import android.view.View;

import com.lenovohit.administrator.tyutclient.R;


/**
 * Created by SharkChao on 2017/1/13.
 */

public class BiaoBaiFragment extends BaseFragment {

    private Context context;

    public BiaoBaiFragment(Context context){
        this.context=context;
    }
    @Override
    public View initView() {
        View v=View.inflate(context, R.layout.fragment_baiobai,null);
        return v;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
