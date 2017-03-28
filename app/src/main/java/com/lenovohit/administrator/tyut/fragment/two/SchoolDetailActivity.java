package com.lenovohit.administrator.tyut.fragment.two;

import android.content.Context;
import android.content.Intent;

import com.bannerlayout.widget.BannerLayout;
import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/9.
 */

public class SchoolDetailActivity extends BaseActivity{
    @Bind(R.id.bannder)
    BannerLayout bannerLayout;
    private Object picId[]={R.mipmap.school1,R.mipmap.school2,R.mipmap.school3,R.mipmap.school4};
    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_schooldetail);
    }

    @Override
    public void initDate() {
        //学校主页图片轮播条
        bannerLayout
                .initArrayResources(picId)
                .start(true);
    }

    @Override
    public void initEvent() {

    }
    public static void startSchoolDetailActivity(Context context){
        context.startActivity(new Intent(context,SchoolDetailActivity.class));
    }
}
