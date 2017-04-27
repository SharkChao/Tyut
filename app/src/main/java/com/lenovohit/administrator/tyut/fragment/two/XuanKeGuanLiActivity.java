package com.lenovohit.administrator.tyut.fragment.two;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.views.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * Created by Administrator on 2017-04-24.
 */

public class XuanKeGuanLiActivity extends BaseActivity {
    @Bind(R.id.tvHomeTitle)
    TextView tvHomeTitle;
    @Bind(R.id.rl_top)
    RelativeLayout rlTop;
    @Bind(R.id.tvTop)
    TextView tvTop;
    @Bind(R.id.lvList)
    ListView lvList;

    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_xuankeguanli);
    }

    @Override
    public void initDate() {

    }

    @Override
    public void initEvent() {
        lvList.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 5;
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
                ViewHolder viewHolder=null;
                if (convertView==null){
                    View view=View.inflate(XuanKeGuanLiActivity.this,R.layout.teacher_info,null);
                    convertView=view;
                    viewHolder=new ViewHolder();
                    viewHolder. imageView = (CircleImageView) view.findViewById(R.id.imgDoctor);
                    viewHolder. tvName = (TextView) view.findViewById(R.id.tvDoctorName);
                    viewHolder. tvType = (TextView) view.findViewById(R.id.tvDoctorType);
                    viewHolder. tvXi= (TextView) view.findViewById(R.id.tvHospitalName);
                    viewHolder. tvExpert = (TextView) view.findViewById(R.id.tvExpert);
                    viewHolder. tvBtn= (TextView) view.findViewById(R.id.tvStatus);
                    convertView.setTag(viewHolder);
                }else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                return convertView;
            }
        });
    }
    class ViewHolder{
        CircleImageView imageView ;
        TextView tvName ;
        TextView tvType ;
        TextView tvXi;
        TextView tvExpert;
        TextView tvBtn;
    }

    public static void startXuanKeGuanliActivity(Context context) {
        context.startActivity(new Intent(context, XuanKeGuanLiActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
