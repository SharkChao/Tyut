package com.lenovohit.administrator.tyut.fragment.one;

import android.content.Context;
import android.view.View;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.fragment.BaseFragment;

/**
 * Created by Administrator on 2017-05-01.
 */

public class ViewPagerFragment extends BaseFragment {
    Context context;
    public ViewPagerFragment(Context context){
        this.context=context;
    }
    @Override
    public View initView() {
        View view=View.inflate(context, R.layout.fragment_splash,null);
        return view;
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
