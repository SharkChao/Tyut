package com.lenovohit.administrator.tyut.fragment;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.lenovohit.administrator.tyutclient.R;
import com.lenovohit.administrator.tyutclient.TestBombActivity;
import com.lenovohit.administrator.tyutclient.view.TitleBar;


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
        titleBar = (TitleBar) v.findViewById(R.id.titlebar);
        rlZhuan= (RelativeLayout) v.findViewById(R.id.rlZhuan);
        titleBar.setRTBtnVisiable(View.GONE);
        titleBar.setTitle("快速发布");
        titleBar.setLFBtnVisiable(View.GONE);
        return v;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        rlZhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestBombActivity.startTestBmobActivity(context);
            }
        });
    }
}
