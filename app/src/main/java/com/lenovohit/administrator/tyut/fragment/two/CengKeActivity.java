package com.lenovohit.administrator.tyut.fragment.two;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.constant.Constant;
import com.lenovohit.administrator.tyut.data.CengKeData;
import com.lenovohit.administrator.tyut.domain.CengKeQuery;
import com.lenovohit.administrator.tyut.domain.CourseCeng;
import com.lenovohit.administrator.tyut.domain.Courses;
import com.lenovohit.administrator.tyut.net.service.UserService;
import com.lenovohit.administrator.tyut.utils.CengKeUtil;
import com.lenovohit.administrator.tyut.utils.EventUtil;
import com.lenovohit.administrator.tyut.utils.StringUtil;
import com.lenovohit.administrator.tyut.views.LoadingDialog;
import com.lenovohit.administrator.tyut.views.NiceSpinner;
import com.lenovohit.administrator.tyut.views.RefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Administrator on 2017-04-25.
 * 蹭课查询页面，可以查询我们想听的课程
 */

public class CengKeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {
    @Inject
    UserService service;
    @Bind(R.id.spinner)
    NiceSpinner spinner;
    @Bind(R.id.etKe)
    EditText editText;
    @Bind(R.id.btnFind)
    Button btnFind;
    @Bind(R.id.lvList)
    ListView lvList;
    @Bind(R.id.swipeLayout)
    RefreshLayout swipeLayout;
    List<CourseCeng>kexiList=new ArrayList<>();
    List<String>listString=new ArrayList<>();
    List<Courses>courseList=new ArrayList<>();
    private ArrayAdapter<String> arr_adapter;
    private String couserCode;
    private MyAdapter adapter;
    private LoadingDialog loadingDialog;

    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_cengke);
        swipeLayout= (RefreshLayout) findViewById(R.id.swipeLayout);
        // 加载颜色是循环播放的，只要没有完成刷新就会一直循环，color1>color2>color3>color4
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setOnLoadListener(this);
    }

    @Override
    public void initDate() {
        ((MyApp) getApplicationContext()).getActivityComponent().inject(this);
        EventUtil.register(this);
        CengKeUtil.getCengKeQuery(service,this);
    }
    @Override
    public void initEvent() {
        loadingDialog = new LoadingDialog(CengKeActivity.this);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
             public  void onClick(View v) {
                loadingDialog.setMessage("正在加载中...");
                loadingDialog.show();
                String s = editText.getText().toString().trim();
                try {
                    String strGBK = URLEncoder.encode(s, "gbk");
                        CengKeUtil.getCengKeQueryResult(service,CengKeActivity.this, StringUtil.isStrEmpty(CengKeData.getCengKeQuery().getXueqi())?"":CengKeData.getCengKeQuery().getXueqi(),StringUtil.isStrEmpty(couserCode)?"":couserCode,"1",strGBK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CourseCeng courseCeng = CengKeData.getCengKeQuery().getYuanxis().get(position);
                    spinner.setText(courseCeng.getName());
                    couserCode=courseCeng.getValue();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        View mListViewFooter=View.inflate(this,R.layout.listview_footer,null);
        adapter = new MyAdapter();
        if(lvList.getFooterViewsCount()==0){
            lvList.addFooterView(mListViewFooter);
        }
        lvList.setAdapter(adapter);
        lvList.removeFooterView(mListViewFooter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Courses courses = courseList.get(position);
                CengKeDetailActivity.startCengkeDetailActivity(CengKeActivity.this, courses);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void receiveData(String code){
        if (code.equals(Constant.CengKeQuery)){
            CengKeQuery cengKeQuery = CengKeData.getCengKeQuery();
            if (cengKeQuery!=null){
                if (cengKeQuery.getYuanxis().size()!=0){
                    kexiList.clear();
                    kexiList.addAll(cengKeQuery.getYuanxis());
                    for (CourseCeng courseCeng:cengKeQuery.getYuanxis()){
                        if (courseCeng.getName().equals("请选择")){
                            listString.add("请选择院系，非必选");
                        }else {
                            listString.add(courseCeng.getName());
                        }
                    }
                    spinner.attachDataSource(listString);
                }
            }
        }
        else if (code.equals(Constant.CengKeQueryResult)){
            if (swipeLayout.isRefreshing()){
                swipeLayout.setRefreshing(false);
                swipeLayout.setLoading(false);
            }
            List<Courses> list = CengKeData.getList();
            if (list!=null&&list.size()!=0){
                courseList.clear();
                courseList.addAll(list);
                adapter.notifyDataSetChanged();
            }else if(list!=null&&list.size()==0){
                courseList.clear();
                Toast.makeText(this, "查不到数据,请换个关键字!", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
            if (loadingDialog!=null)
            if (loadingDialog.isShowing()){
                loadingDialog.dismiss();
            }
        }
        else if (code.equals(Constant.CengKeQueryResult+"1")){
            if (swipeLayout.isRefreshing()){
                swipeLayout.setRefreshing(false);
                swipeLayout.setLoading(false);
            }
            List<Courses> list = CengKeData.getList();
            if (list!=null&&list.size()!=0){
                courseList.addAll(list);
                adapter.notifyDataSetChanged();
            }else if(list!=null&&list.size()==0){
                courseList.clear();
                Toast.makeText(this, "查不到数据,请换个关键字!", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
            if (loadingDialog!=null)
                if (loadingDialog.isShowing()){
                    loadingDialog.dismiss();
                }
        }
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        String s = editText.getText().toString().trim();
        String strGBK = null;
        try {
            strGBK = URLEncoder.encode(s, "gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        CengKeUtil.getCengKeQueryResult(service,CengKeActivity.this, StringUtil.isStrEmpty(CengKeData.getCengKeQuery().getXueqi())?"":CengKeData.getCengKeQuery().getXueqi(),StringUtil.isStrEmpty(couserCode)?"":couserCode,"1",strGBK);
//        CengKeUtil.getCengKeQuery(service,this);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoad() {
        if (courseList.size()%50==0){
            CengKeUtil.getCengKeQueryResultTwo(service,CengKeActivity.this,StringUtil.isStrEmpty(CengKeData.getNum())?"":CengKeData.getNum(),courseList.size()/50+1+"");
        }else{
            if (swipeLayout.isRefreshing()){
                swipeLayout.setLoading(false);
            }
        }

    }

    class ViewHolder {
        public TextView tvId;
        public TextView tvCourseName;
        public TextView tvTeacherName;
        public TextView tvXuefen;
        public TextView tvYuanxi;
    }
    class  MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return courseList.size();
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
                View view=View.inflate(CengKeActivity.this,R.layout.course_item,null);
                viewHolder=new ViewHolder();
                convertView=view;
                viewHolder.tvId= (TextView) view.findViewById(R.id.tvId);
                viewHolder.tvCourseName= (TextView) view.findViewById(R.id.tvCourseName);
                viewHolder.tvTeacherName= (TextView) view.findViewById(R.id.tvTeacherName);
                viewHolder.tvXuefen= (TextView) view.findViewById(R.id.tvXuefen);
                viewHolder.tvYuanxi= (TextView) view.findViewById(R.id.tvYuanxi);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Courses courses = courseList.get(position);
                viewHolder.tvId.setText(courses.getId());
            viewHolder.tvCourseName.setText(courses.getCourseName()+"("+courses.getCourseCode()+")");
            viewHolder.tvTeacherName.setText("任课教师:  "+courses.getTeacherName());
            viewHolder.tvXuefen.setText("学分:  "+courses.getXueFen());
            viewHolder.tvYuanxi.setText(courses.getXiSuo());
            return convertView;
        }
    }
    public static void startCengKeQueryActivity(Context context){
        context.startActivity(new Intent(context,CengKeActivity.class));
    }
}
