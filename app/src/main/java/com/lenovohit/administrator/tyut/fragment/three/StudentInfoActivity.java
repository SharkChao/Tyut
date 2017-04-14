package com.lenovohit.administrator.tyut.fragment.three;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.views.PullScrollView;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/12.
 学生信息主界面
 */

public class StudentInfoActivity extends BaseActivity{
    @Bind(R.id.pullScrollview)
    PullScrollView pullScrollView;
    @Bind(R.id.ivBack)
    ImageView imageView;
    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_studentinfo);
        pullScrollView.setOnTurnListener(new PullScrollView.OnTurnListener() {
            @Override
            public void onTurn() {

            }
        });
        pullScrollView.setHeader(imageView);
    }

    @Override
    public void initDate() {

    }

    @Override
    public void initEvent() {

    }
    public static void startStudentInfoActivity(Context context){
        context.startActivity(new Intent(context, StudentInfoActivity.class));
    }
}
