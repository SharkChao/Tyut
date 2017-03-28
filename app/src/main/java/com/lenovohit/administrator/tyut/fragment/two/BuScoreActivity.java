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
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/13.
 * 本学期成绩
 */

public class BuScoreActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Inject
    UserService service;
    @Bind(R.id.lvScoreList)
    RecyclerView recyclerView;
    @Bind(R.id.id_swipe_ly)
    SwipeRefreshLayout swipeRefreshLayout;
    //尚不及格
    List<Score> list31 = new ArrayList<>();
    //曾不及格
    List<Score> list32 = new ArrayList<>();
    List<MySection> list2 = new ArrayList<>();
    private ScoreAdapter adapter;

    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_buscore);
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
        String cache = (String) SpUtil.getParam(this, "buscore", "");
        if (TextUtils.isEmpty(cache)) {
            ScoreUtil.getBuScore(service, this);
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
            case Constant.BuScoreUrl:
                list2.clear();
                list31 = ScoreData.getList31();
                list32 = ScoreData.getList32();
                MySection mySection = new MySection(true, "尚不及格");
                list2.add(mySection);
                if (list31 .size()!= 0) {
                    for (Score score : list31) {
                        list2.add(new MySection(score));
                    }
                } else {
                    Score score=new Score();
                    score.setTitle("已经通过所有课程");
                    list2.add(new MySection(score));
                }
                MySection mySection1 = new MySection(true, "曾不及格");
                list2.add(mySection1);
                if (list32 != null)
                    for (Score score : list32) {
                        list2.add(new MySection(score));
                    }
                adapter.notifyDataSetChanged();
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(this, "刷新成功！", Toast.LENGTH_SHORT).show();
                }
                SpUtil.setParam(this,"buscore",list2);
                break;
        }
    }

    public static void StartCurrentScoreActivity(Context context) {
        Intent intent = new Intent(context, BuScoreActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        getCurrentScore();
    }
}
