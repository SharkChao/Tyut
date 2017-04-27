package com.lenovohit.administrator.tyut.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.fragment.two.CengKeActivity;
import com.lenovohit.administrator.tyut.fragment.two.KeBiaoActivity;
import com.lenovohit.administrator.tyut.fragment.two.PingJiaoActivity;
import com.lenovohit.administrator.tyut.fragment.two.SchoolDetailActivity;
import com.lenovohit.administrator.tyut.fragment.two.ScoreMenuActivity;
import com.lenovohit.administrator.tyut.fragment.two.SiLiuJiFindActivity;
import com.lenovohit.administrator.tyut.fragment.two.XuanKeGuanLiActivity;
import com.lenovohit.administrator.tyut.fragment.two.XueFenActivity;
import com.lenovohit.administrator.tyut.views.MyGridView;

import butterknife.Bind;


/**
 * Created by SharkChao on 2017/1/13.
 */

public class JiaoWuFragment extends BaseFragment {
    @Bind(R.id.ivPhone)
    ImageView ivPhone;
    @Bind(R.id.gvGuide)
    MyGridView gridView;
    @Bind(R.id.rl_pic)
    RelativeLayout relativeLayout;
    private Context context;
    private String[]titles={"成绩","课表","学分绩点","选课管理","一键评教","四六级查询","我要蹭课"};
    private String[] contents={"随时了解最新成绩","一键查询课表","学分绩点排名一键查询","选课管理","这个是一键评教","这个是四六级查询","要学就学喜欢的课程"};
    private int []picId={R.mipmap.p6, R.mipmap.p2, R.mipmap.p3, R.mipmap.p6, R.mipmap.p6, R.mipmap.p2,R.mipmap.p2,R.mipmap.p2,R.mipmap.p3};
    public JiaoWuFragment(Context context){
        this.context=context;
    }
    @Override
    public View initView() {
        View v=View.inflate(context, R.layout.fragment_jiaowu,null);
        return v;
    }
    @Override
    public void initData() {
        gridView.setAdapter(new MyAdapter());

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SchoolDetailActivity.startSchoolDetailActivity(context);
            }
        });
       gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               switch (position){
                   case 0:
                       //成绩模块
                       ScoreMenuActivity.startScoreMenuActivity(context);
                       break;
                   case 1:
                       //课表模块
                       KeBiaoActivity.startKeBiaoActivity(context);
                       break;
                   case 2:
                       //学分绩点模块
                       XueFenActivity.startXueFenActivity(context);
                       break;
                   case 3:
                       //选课管理模块
                       //选课管理
                       XuanKeGuanLiActivity.startXuanKeGuanliActivity(context);
                       break;
                   case 4:
                       //一键评教
                       PingJiaoActivity.startPingJiaoActivity(context);
                       break;
                   case 5:
                       SiLiuJiFindActivity.startSiLiuActivity(context);
                       //四六级查询
                       break;
                   case 6:
                       //蹭一节课
                       CengKeActivity.startCengKeQueryActivity(context);
                       break;
                   case 7:
                       //空闲教室查询
                       break;
                   default:
                       break;
               }
           }
       });
    }

    @Override
    public void initEvent() {
        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "0351-6010300"));//跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);
            }
        });
    }

    @Override
    public void loadData() {

    }
    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return titles.length;
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
            View view=View.inflate(context,R.layout.gride_item,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_guide);
            TextView title= (TextView) view.findViewById(R.id.tv_guide);
            TextView content= (TextView) view.findViewById(R.id.tv_content);
            imageView.setImageResource(picId[position]);
            title.setText(titles[position]);
            content.setText(contents[position]);
            return view;
        }
    }
}
