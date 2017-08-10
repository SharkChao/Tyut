package com.lenovohit.administrator.tyut.fragment.two;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.views.Alert;
import com.lenovohit.administrator.tyut.views.CircleImageView;

import butterknife.Bind;


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
    private int imgRes[]=new int[]{R.mipmap.head1,R.mipmap.head2,R.mipmap.head3,R.mipmap.head4,R.mipmap.head5};
    private String names[]=new String[]{"程兴申","邓稼先","郭沫若","钱学森","胡启明"};
    private String types[]=new String[]{"博士生导师","博士后","博士生导师","博士后","博士生导师"};
    private String xis[]=new String[]{"计算机科学与技术学院","软件学院","计算机科学与技术学院","数学学院","软件学院"};
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
        lvList.setAdapter(new MyAdapter());
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new Alert(XuanKeGuanLiActivity.this).builder().setMsg("您确定要预约此门课程吗?")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(XuanKeGuanLiActivity.this, "恭喜你，成功预约此门课程", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
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
    class  MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return imgRes.length;
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
            viewHolder.imageView.setImageResource(imgRes[position]);
            viewHolder.tvName.setText(names[position]);
            viewHolder.tvType.setText(types[position]);
            viewHolder.tvXi.setText(xis[position]);
            viewHolder.tvExpert.setText(getResources().getTextArray(R.array.expert)[position]);
            return convertView;
        }
    }

    public static void startXuanKeGuanliActivity(Context context) {
        context.startActivity(new Intent(context, XuanKeGuanLiActivity.class));
    }
}
