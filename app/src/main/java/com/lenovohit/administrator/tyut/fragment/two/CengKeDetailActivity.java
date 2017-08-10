package com.lenovohit.administrator.tyut.fragment.two;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.data.CengKeData;
import com.lenovohit.administrator.tyut.domain.Courses;
import com.lenovohit.administrator.tyut.greendao.KeBiaoEntity;
import com.lenovohit.administrator.tyut.net.service.UserService;
import com.lenovohit.administrator.tyut.utils.CengKeUtil;
import com.lenovohit.administrator.tyut.utils.EventUtil;
import com.lenovohit.administrator.tyut.utils.StringUtil;
import com.lenovohit.administrator.tyut.views.LoadingDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Administrator on 2017-04-26.
 * 蹭课课程详情页
 */

public class CengKeDetailActivity extends BaseActivity{

    @Bind(R.id.lvList)
    ListView lvList;
    @Bind(R.id.tvCourseName)
    TextView tvCourseName;
    @Bind(R.id.tvNum)
    TextView tvNum;
    @Bind(R.id.tvTeacherName)
    TextView tvTeacherName;
    @Bind(R.id.tvXuefen)
    TextView tvXueFen;
    @Inject
    UserService service;
    List<KeBiaoEntity>list=new ArrayList<>();
    private MyAdapter adapter;
    private LoadingDialog loadingDialog;
    private static Courses courses;

    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_cengkedetail);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setMessage("正在加载中....");
    }

    @Override
    public void initDate() {
        ((MyApp) getApplicationContext()).getActivityComponent().inject(this);
        EventUtil.register(this);
        adapter = new MyAdapter();
        if (!StringUtil.isStrEmpty(courses.getUrl())){
            CengKeUtil.getCengKeTime(service,this,courses.getUrl());
        }
        tvCourseName.setText(StringUtil.isStrEmpty(courses.getCourseName())?"暂无课程":courses.getCourseName());
        tvTeacherName.setText(StringUtil.isStrEmpty(courses.getTeacherName())?"暂无老师":courses.getTeacherName());
        tvXueFen.setText(StringUtil.isStrEmpty(courses.getCourseName())?"无学分":courses.getXueFen()+"学分");
    }

    @Override
    public void initEvent() {
        lvList.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void receiveData(String code) {
     if (code.equals("蹭课课程详情页")) {
            List<KeBiaoEntity> list2 = CengKeData.getList2();
            if (list2 != null && list2.size() != 0) {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                list.clear();
                for (KeBiaoEntity kebiao:list2){
                    if (!kebiao.getValue().equals(" ")){
                        list.add(kebiao);
                    }
                }
                Spanned spanned = Html.fromHtml("<font color='red'>" + list.size() + "" + "</font>");
                tvNum.setText("共"+spanned+"小节");
                adapter.notifyDataSetChanged();
            }
        }
    }

    class ViewHolder {
        public TextView tvTime;
        public TextView tvLocal;
    }
    class  MyAdapter extends BaseAdapter {
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
            ViewHolder viewHolder=null;
            if (convertView==null){
                View view=View.inflate(CengKeDetailActivity.this,R.layout.cengke_course_item,null);
                viewHolder=new ViewHolder();
                convertView=view;
                viewHolder.tvTime= (TextView) view.findViewById(R.id.tvTime);
                viewHolder.tvLocal= (TextView) view.findViewById(R.id.tvLocal);

                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            KeBiaoEntity course = list.get(position);
            viewHolder.tvTime.setText("礼拜"+StringUtil.numToUpper(Integer.parseInt(course.getXingqi()))+"\n"+course.getState());
            viewHolder.tvLocal.setText(course.getValue());
            return convertView;
        }
    }
    public static void startCengkeDetailActivity(Context context, Courses courses){
        CengKeDetailActivity.courses=courses;
        context.startActivity(new Intent(context,CengKeDetailActivity.class));
    }
}
