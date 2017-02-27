package com.lenovohit.administrator.tyut.fragment;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bannerlayout.widget.BannerLayout;
import com.lenovohit.administrator.tyutclient.CityListActivity;
import com.lenovohit.administrator.tyutclient.R;
import com.lenovohit.administrator.tyutclient.app.MyApp;
import com.lenovohit.administrator.tyutclient.domain.City;
import com.lenovohit.administrator.tyutclient.utils.EventUtil;
import com.lenovohit.administrator.tyutclient.view.AlphaListView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;


/**
 * Created by SharkChao on 2017/1/13.
 */

public class NewsFragment extends BaseFragment {

    @Bind(R.id.tvZhuan)
    TextView tvZhuan;
    @Bind(R.id.lvList)
    AlphaListView lvList;
    @Bind(R.id.banner)
    BannerLayout bannerLayout;//轮播条
    @Bind(R.id.tvLocal)
    TextView tvLocal;
    @Bind(R.id.ivPic1)
    ImageView imageView1;
    private Context context;
    private int index;
    private List<String> lists;

    public NewsFragment(Context context) {
        this.context = context;
    }

    @Override
    public View initView() {
        View v = View.inflate(context, R.layout.fragment_news, null);
        ButterKnife.bind(this, v);

        tvZhuan.setText(Html.fromHtml("转<font color='#ff0000'>多久</font>？，怎么转<font color='#ff0000'>更快</font>?"));
        EventUtil.register(this);
        final RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        imageView1.startAnimation(animation);
        return v;
    }

    @Override
    public void initData() {
        //轮播条的初始化
        int[] mImage = new int[]{R.mipmap.news1, R.mipmap.news2, R.mipmap.news3, R.mipmap.news4};
        bannerLayout
                .initImageArrayResources(mImage)
                .initAdapter()
                .initRound(false, false, false)
                .start(true);

        //转让信息列表在此初始化
        lists = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            lists.add("吴先生的餐馆成功转让" + i);
        }
        lvList.setAdapter(new MyBaseAdapter());
    }

    @Override
    public void initEvent() {
        //跳转到城市列表
        tvLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityListActivity.startCityListActivity(context);
            }
        });
        index = 0;
        //转让信息每次自动向上跳转一次
        Observable
                .just("1")
                .interval(1, 1, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        index++;
                        if (index <= lists.size() - 1) {
                            lvList.smoothScrollToPosition(index);
                        } else {
                            index = 0;
                            lvList.smoothScrollToPosition(0);
                        }
                    }
                });
    }

    //百度地图api拿到信息以后在此地拿到消息
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 0, sticky = true)
    public void handleEvent(City local) {
        tvLocal.setText(local.getCityName());
        MyApp.getInstance().setLocalCity(local);
    }
    class  MyBaseAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return lists.size();
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
            View v=View.inflate(context,R.layout.listview_item,null);
            TextView textView = (TextView) v.findViewById(R.id.text);
            textView.setText(lists.get(position));
            return v;
        }
    }
}
