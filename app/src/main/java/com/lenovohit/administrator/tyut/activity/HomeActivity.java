package com.lenovohit.administrator.tyut.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.adapter.MyBasePageAdapter;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/2/27.
 */

public class HomeActivity extends BaseActivity {
    @Bind(R.id.tvConn)
    TextView tvConn;
    @Bind(R.id.rg_bottom)
     RadioGroup rgBottom;
    @Bind(R.id.vp_home)
     ViewPager viewPager;
    @Bind(R.id.tvHomeTitle)
     TextView tvTitle;
    @Bind(R.id.rl_top)
    RelativeLayout relativeLayout;
    @Override
    public void initView() {
        setContentView(R.layout.activity_home);
        viewPager.setAdapter(new MyBasePageAdapter(getSupportFragmentManager(), this));
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void initDate() {

    }

    @Override
    public void initEvent() {
        rgBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbHome:
                        viewPager.setCurrentItem(0);
                        tvTitle.setText("新闻");
                        break;
                    case R.id.rbHospal:
                        viewPager.setCurrentItem(1);
                        tvTitle.setText("教务管理");
                        break;
                    case R.id.rbUse:
                        viewPager.setCurrentItem(2);
                        tvTitle.setText("学习资料");
                        break;
                    case R.id.rbMy:
                        viewPager.setCurrentItem(3);
                        tvTitle.setText("我的");
                        break;
                }
            }
        });
    }

    @Override
    public void Update(Boolean isConnection) {
        if (isConnection){
            tvConn.setText("请连接网络！");
            tvConn.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.INVISIBLE);
        }else {
            tvConn.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }
    public static  void StartHomeActivity(Context context){
        context.startActivity(new Intent(context,HomeActivity.class));
    }
}
