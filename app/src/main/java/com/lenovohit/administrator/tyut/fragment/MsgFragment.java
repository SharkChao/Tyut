package com.lenovohit.administrator.tyut.fragment;

import android.content.Context;
import android.view.View;

import com.lenovohit.administrator.tyutclient.R;
import com.lenovohit.administrator.tyutclient.view.TitleBar;


/**
 * Created by SharkChao on 2017/1/13.
 */

public class MsgFragment extends BaseFragment {

    private Context context;
    private TitleBar titleBar;

    public MsgFragment(Context context){
        this.context=context;
    }
    @Override
    public View initView() {
        View v=View.inflate(context, R.layout.fragment_msg,null);
        titleBar = (TitleBar) v.findViewById(R.id.titlebar);
        titleBar.setRTBtnVisiable(View.GONE);
        titleBar.setTitle("公司介绍");
        titleBar.setLFBtnVisiable(View.GONE);
        return v;
    }
    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
