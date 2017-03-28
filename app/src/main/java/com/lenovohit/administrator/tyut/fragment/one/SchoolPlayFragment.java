package com.lenovohit.administrator.tyut.fragment.one;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.NewsDetailActivity;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.constant.Constant;
import com.lenovohit.administrator.tyut.data.NewsData;
import com.lenovohit.administrator.tyut.domain.News;
import com.lenovohit.administrator.tyut.domain.Object1;
import com.lenovohit.administrator.tyut.fragment.BaseFragment;
import com.lenovohit.administrator.tyut.net.service.UserService;
import com.lenovohit.administrator.tyut.utils.EventUtil;
import com.lenovohit.administrator.tyut.utils.NewsUtil;
import com.lenovohit.administrator.tyut.utils.SpUtil;
import com.lenovohit.administrator.tyut.views.Alert;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/6.
 * 校园活动页，在第三个页面
 */

public class SchoolPlayFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private Context context;
    Alert alert;
    @Inject
    UserService service;
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.rv_list)
    ListView lvList;
    List<News> list = new ArrayList<>();
    private MyAdapter adapter;

    public SchoolPlayFragment(Context context) {
        this.context = context;
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_schoolplay, null);
        return view;
    }

    @Override
    public void initData() {
        EventUtil.register(this);
        adapter = new MyAdapter();
        lvList.setAdapter(adapter);

    }

    @Override
    public void initEvent() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsDetailActivity.startNewsDetailActivity(context,list.get(position).getUrl());
            }
        });
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        //还没有数据，先从网络加载数据（需要context）
        Context context1 = context.getApplicationContext();
        ((MyApp) context1).getActivityComponent().inject(this);
        NewsUtil newsUtil = new NewsUtil();
        newsUtil.setNewsData(this.context, service, Constant.BASE_URL + Constant.SCHOOL_URL, 2);
    }

    /**
     * 加载数据，三级缓存可以写在这里
     */
    @Override
    public void loadData() {
        Context context1 = context.getApplicationContext();
        ((MyApp) context1).getActivityComponent().inject(this);
        Gson gson = new Gson();
        String cache = (String) SpUtil.getParam(context, "cache2", "");
        if (TextUtils.isEmpty(cache)) {
            NewsUtil newsUtil = new NewsUtil();
            newsUtil.setNewsData(this.context, service, Constant.BASE_URL + Constant.SCHOOL_URL, 2);
        } else {
            List<News> newss = gson.fromJson(cache, new TypeToken<List<News>>() {
            }.getType());
            if (newss.size() != 0) {
                list.clear();
                list.addAll(newss);
                adapter.notifyDataSetChanged();
            }
        }
    }

    //数据回调，从网络获取数据
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getData(Object1 b) {
        if (b.getIndex().equals("2")) {
            List<News> list1 = NewsData.getList3();
            if (list1.size() != 0) {
                list.clear();
                list.addAll(list1);
                adapter.notifyDataSetChanged();
                Gson gson = new Gson();
                String json = gson.toJson(NewsData.getList3());
                SpUtil.setParam(context, "cache2", json);
                Toast.makeText(context, "数据更新成功", Toast.LENGTH_SHORT).show();
                boolean b1 = swipeRefreshLayout.isRefreshing();
                if (b1) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }
    }

    class ViewHolder {
        TextView title;
        TextView date;
        ImageView image;
    }

    class MyAdapter extends BaseAdapter {
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
            ViewHolder viewHolder;
            if (convertView == null) {
                View view = View.inflate(context, R.layout.list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.title = (TextView) view.findViewById(R.id.title);
                viewHolder.date = (TextView) view.findViewById(R.id.date);
                viewHolder.image = (ImageView) view.findViewById(R.id.imageView1);
                convertView = view;
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (list.size()!=0){
                viewHolder.title.setText(list.get(position).getTitle());
                viewHolder.date.setText(list.get(position).getDate());
                Glide.with(context).load(list.get(position).getImageUrl()).into(viewHolder.image);
            }
            return convertView;
        }
    }
}
