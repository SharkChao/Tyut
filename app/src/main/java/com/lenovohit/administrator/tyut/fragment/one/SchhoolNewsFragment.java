package com.lenovohit.administrator.tyut.fragment.one;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bannerlayout.Interface.OnBannerClickListener;
import com.bannerlayout.model.BannerModel;
import com.bannerlayout.widget.BannerLayout;
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
import com.mingle.widget.LoadingView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;


/**
 * Created by Administrator on 2017/3/6.
 * 学校新闻页，在第一个页面
 */

public class SchhoolNewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private Context context;
    Alert alert;
    @Inject
    UserService service;
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    ListView lvList;
    BannerLayout bannerLayout;
    @Bind(R.id.loadView)
    LoadingView loadingView;
    List<News> list = new ArrayList<>();
    private MyAdapter adapter;


    public SchhoolNewsFragment(Context context) {
        this.context = context;
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_schoolnews, null);
        lvList = (ListView) view.findViewById(R.id.rv_list);
        View view1 = View.inflate(context, R.layout.bannder_layout, null);
        bannerLayout = (BannerLayout) view1.findViewById(R.id.bannder);
        return view;
    }

    @Override
    public void initData() {
        EventUtil.register(this);
        adapter = new MyAdapter();
        lvList.setAdapter(adapter);
        lvList.addHeaderView(bannerLayout);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list != null)
                    NewsDetailActivity.startNewsDetailActivity(context, list.get(position).getUrl());
            }
        });
        lvList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                lvList.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    @Override
    public void initEvent() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        NewsUtil newsUtil = new NewsUtil();
        newsUtil.setNewsData(this.context, service, Constant.BASE_URL + Constant.IMPORT_URL, 0);
    }

    /**
     * 加载数据，三级缓存可以写在这里
     */
    @Override
    public void loadData() {
        Context context1 = context.getApplicationContext();
        ((MyApp) context1).getActivityComponent().inject(this);
//        alert = new Alert(context);
//        alert.builder().setMsg("加载").setTitle("正在加载中，请稍等").setCancelable(false).show();

        Gson gson = new Gson();
        String cache = (String) SpUtil.getParam(context, "cache", "");
        if (TextUtils.isEmpty(cache)) {
            NewsUtil newsUtil = new NewsUtil();
            newsUtil.setNewsData(this.context, service, Constant.BASE_URL + Constant.IMPORT_URL, 0);
        } else {
            List<News> newss = gson.fromJson(cache, new TypeToken<List<News>>() {
            }.getType());
            if (newss.size() != 0) {
                setBannerLayout(newss);
                setLvList(newss);
            }
        }
        loadingView.setVisibility(View.GONE);

    }

    //数据回调，从网络返回的数据
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getData(Object1 b) {
        if (b.getIndex().equals("0")) {
            setBannerLayout(NewsData.getList1());
            setLvList(NewsData.getList1());
            Gson gson = new Gson();
            String json = gson.toJson(NewsData.getList1());
            SpUtil.setParam(context, "cache", json);
            //下拉刷新完成
            if (!(swipeRefreshLayout==null)&&swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(false);
            }
        }
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

            if (list.size() != 0) {
                viewHolder.title.setText(list.get(position).getTitle());
                viewHolder.date.setText(list.get(position).getDate());
                Glide.with(context).load(list.get(position).getImageUrl()).into(viewHolder.image);
            }
            return convertView;
        }
    }

    class ViewHolder {
        TextView title;
        TextView date;
        ImageView image;
    }

    public void setLvList(List<News> newsList) {
        //listview的数据加载
        list.clear();
        list.addAll(newsList);
        adapter.notifyDataSetChanged();
        Toast.makeText(context, "数据更新成功", Toast.LENGTH_SHORT).show();
    }

    public void setBannerLayout(List<News> newsList) {
         final List<News> newses = new ArrayList<>(newsList.subList(0,3));
        //截取前三项，放入bannderLayout中
        List<BannerModel> models = new ArrayList<>();
        for (int i = 0; i < newses.size(); i++) {
            models.add(new BannerModel(newses.get(i).getImageUrl(), newses.get(i).getTitle()));
        }
        bannerLayout
                .initListResources(models)
                .initTips(true,true,true)
                .start(true);
        bannerLayout.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onBannerClick(View view, int position, Object model) {
                NewsDetailActivity.startNewsDetailActivity(context, newses.get(position).getUrl());
            }
        });
    }
}
