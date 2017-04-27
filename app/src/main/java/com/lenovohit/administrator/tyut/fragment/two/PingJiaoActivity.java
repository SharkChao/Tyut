package com.lenovohit.administrator.tyut.fragment.two;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.greendao.DaoManager;
import com.lenovohit.administrator.tyut.greendao.DaoSession;
import com.lenovohit.administrator.tyut.greendao.PingJiao;
import com.lenovohit.administrator.tyut.greendao.PingJiaoDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017-04-16.
 */

public class PingJiaoActivity extends BaseActivity {

    @Bind(R.id.lvList)
    ListView listView;
    private List<PingJiao> list;

    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_pingjiao);

    }

    @Override
    public void initDate() {

        PingJiao pingjiao1=new PingJiao(null,"系统分析与设计","董步武","3",false,"女","");
        PingJiao pingjiao2=new PingJiao(null,"数据结构B","刘云","3",true,"男","");
        PingJiao pingjiao3=new PingJiao(null,"硬件技术基础","杜牧","2",false,"女","");
        PingJiao pingjiao4=new PingJiao(null,"操作系统B","苏轼","2",true,"男","");
        PingJiao pingjiao5=new PingJiao(null,"计算机网络B","楼天城","3",false,"女","");
        PingJiao pingjiao6=new PingJiao(null,"信息技术安全B","王痕","2",true,"男","");
        PingJiao pingjiao7=new PingJiao(null,"软件测试技术","张亚红","3",false,"女","");
        list = new ArrayList<>();
        list.add(pingjiao1);
        list.add(pingjiao2);
        list.add(pingjiao3);
        list.add(pingjiao4);
        list.add(pingjiao5);
        list.add(pingjiao6);
        list.add(pingjiao7);
        DaoManager instance = DaoManager.getInstance(this);
        DaoSession session = instance.getSession();
        PingJiaoDao pingJiaoDao = session.getPingJiaoDao();
        pingJiaoDao.insertInTx(list);
    }

    @Override
    public void initEvent() {
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view=View.inflate(PingJiaoActivity.this,R.layout.pingjiao_item,null);
                TextView tvCoursename= (TextView) view.findViewById(R.id.coursename);
                TextView teachername= (TextView) view.findViewById(R.id.teachername);
                TextView sex= (TextView) view.findViewById(R.id.sex);
                TextView xuefen= (TextView) view.findViewById(R.id.xuefen);
                TextView btnPingjiao= (Button) view.findViewById(R.id.btnPing);
                PingJiao pingJiao = list.get(position);
                tvCoursename.setText(pingJiao.getCoursename());
                teachername.setText("教师:"+pingJiao.getTeachername());
                sex.setText(pingJiao.getSex());
                xuefen.setText(pingJiao.getXuefen()+"学分");
                if (pingJiao.getIsdo()){
                    btnPingjiao.setText("已评价");
                    btnPingjiao.setBackgroundColor(Color.GREEN);
                }else {
                    btnPingjiao.setText("暂未评价");
                    btnPingjiao.setBackgroundColor(Color.RED);
                }
                return view;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!list.get(position).getIsdo()){
                    PingJiaoDetailActivity.startPingJiaoDetailActivity(PingJiaoActivity.this,list.get(position));
                }
            }
        });
    }
    public static void startPingJiaoActivity(Context context){
        context.startActivity(new Intent(context,PingJiaoActivity.class));
    }
}
