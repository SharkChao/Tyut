package com.lenovohit.administrator.tyut.fragment.two;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.adapter.ScoreAdapter;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.constant.Constant;
import com.lenovohit.administrator.tyut.data.ScoreData;
import com.lenovohit.administrator.tyut.domain.MySection;
import com.lenovohit.administrator.tyut.greendao.Score;
import com.lenovohit.administrator.tyut.net.service.UserService;
import com.lenovohit.administrator.tyut.utils.EventUtil;
import com.lenovohit.administrator.tyut.utils.ScoreUtil;
import com.lenovohit.administrator.tyut.utils.SpUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;


/**
 * Created by Administrator on 2017/3/13.
 * 本学期成绩
 */

public class LastScoreActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Inject
    UserService service;
    @Bind(R.id.lvScoreList)
    RecyclerView recyclerView;
    @Bind(R.id.id_swipe_ly)
    SwipeRefreshLayout swipeRefreshLayout;
    //用来放置recycleview的集合
    List<MySection> list2 = new ArrayList<>();
    //标题
    List<Score> list1 = new ArrayList<>();
    //成绩
    Map<String, List<Score>> map = new HashMap<>();
    private ScoreAdapter adapter;

    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_lastscore);
        ((MyApp) getApplicationContext()).getActivityComponent().inject(this);
        EventUtil.register(this);

    }

    @Override
    public void initDate() {

    }

    @Override
    public void initEvent() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        adapter = new ScoreAdapter(R.layout.score_list_item, R.layout.score_title_item, list2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getCurrentScore();
    }

    /**
     * 发送请求到学校接口，获取本学期成绩
     */
    public void getCurrentScore() {
        Gson gson = new Gson();
        String cache = (String) SpUtil.getParam(this, "lastscore", "");
        if (TextUtils.isEmpty(cache)) {
            ScoreUtil.getAllScore(service, this);
        } else {
            List<MySection> score = gson.fromJson(cache, new TypeToken<List<MySection>>() {
            }.getType());
            if (score.size() != 0) {
                list2.clear();
                list2.addAll(score);
                adapter.notifyDataSetChanged();
            }
        }

    }

    /**
     * 拿到成绩后回调过来
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getScore(String code) {
        switch (code) {
            case Constant.AllScoreUrl:
                list2.clear();
                list1 = ScoreData.getList2();
                map = ScoreData.getMap();
                int k = list1.size() - 1;
                for (int i=0;i<2;i++){
                    k--;
                    if (k >= 0) {
                        MySection mySection = new MySection(true, list1.get(k).getTitle());
                        list2.add(mySection);
                        List<Score> list3 = map.get(k + "");
                        for (int j = 0; j < list3.size(); j++) {
                            list2.add(new MySection(list3.get(j)));
                        }
                    }
                }
                SpUtil.setParam(this, "lastscore", list2);
                adapter.notifyDataSetChanged();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(this, "刷新成功！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public static void StartCurrentScoreActivity(Context context) {
        Intent intent = new Intent(context, LastScoreActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        getCurrentScore();
    }
}
