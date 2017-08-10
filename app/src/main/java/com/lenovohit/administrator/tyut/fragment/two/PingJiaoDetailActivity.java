package com.lenovohit.administrator.tyut.fragment.two;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.greendao.DaoManager;
import com.lenovohit.administrator.tyut.greendao.DaoSession;
import com.lenovohit.administrator.tyut.greendao.PingJiao;
import com.lenovohit.administrator.tyut.greendao.PingJiaoDao;

import butterknife.Bind;

/**
 * Created by Administrator on 2017-04-17.
 */

public class PingJiaoDetailActivity extends BaseActivity {
    private static PingJiao pingJiao;
    @Bind(R.id.coursename)
    TextView tvCoursename;
    @Bind(R.id.teachername)
    TextView tvTeachername;
    @Bind(R.id.sex)
    TextView tvSex;
    @Bind(R.id.xuefen)
    TextView tvXuefen;
    @Bind(R.id.pingjia)
    EditText etPingjia;
    @Bind(R.id.commit)
    Button btnCommit;
    private PingJiaoDao pingJiaoDao;

    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_pingjiaodetail);
        tvCoursename.setText(pingJiao.getCoursename());
        tvTeachername.setText(pingJiao.getTeachername());
        tvSex.setText(pingJiao.getSex());
        tvXuefen.setText(pingJiao.getXuefen()+" 学分");
    }

    @Override
    public void initDate() {
        DaoManager instance = DaoManager.getInstance(this);
        DaoSession session = instance.getSession();
        pingJiaoDao = session.getPingJiaoDao();
    }

    @Override
    public void initEvent() {
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pingjias = etPingjia.getText().toString();
                PingJiao jiao = pingJiaoDao.queryBuilder().where(PingJiaoDao.Properties.Coursename.eq(pingJiao.getCoursename())).build().list().get(0);
                jiao.setIsdo(true);
                jiao.setPingjiao(pingjias);
                pingJiaoDao.update(jiao);
                Toast.makeText(PingJiaoDetailActivity.this, "评价成功！", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static void startPingJiaoDetailActivity(Context context, PingJiao pingJiao){
        context.startActivity(new Intent(context,PingJiaoDetailActivity.class));
        PingJiaoDetailActivity.pingJiao=pingJiao;
    }
}
