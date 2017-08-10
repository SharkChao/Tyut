package com.lenovohit.administrator.tyut.fragment.three;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-05-02.
 * 群组功能界面
 */

public class GroupActivity extends BaseActivity {
    @Bind(R.id.tvHomeTitle)
    TextView tvHomeTitle;
    @Bind(R.id.rl_top)
    RelativeLayout rlTop;
    @Bind(R.id.newgroup)
    TextView newgroup;
    @Bind(R.id.findfroup)
    TextView findfroup;
    @Bind(R.id.ivList)
    ListView ivList;

    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_group);
    }

    @Override
    public void initDate() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
