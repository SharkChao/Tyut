package com.lenovohit.administrator.tyut.fragment;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.views.TitleBar;



/**
 * Created by SharkChao on 2017/1/13.
 */

public class MeFragment extends BaseFragment {

    private TitleBar titleBar;
    private Context context;
    private RelativeLayout rlZhuan;
    public MeFragment(Context context){
        this.context=context;
    }
    @Override
    public View initView() {
        View v=View.inflate(context, R.layout.fragment_me,null);
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
