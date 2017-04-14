package com.lenovohit.administrator.tyut.fragment.two;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.adapter.ScoreAdapter;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.constant.Constant;
import com.lenovohit.administrator.tyut.data.ScoreData;
import com.lenovohit.administrator.tyut.domain.MySection;
import com.lenovohit.administrator.tyut.greendao.DaoManager;
import com.lenovohit.administrator.tyut.greendao.DaoSession;
import com.lenovohit.administrator.tyut.greendao.Score;
import com.lenovohit.administrator.tyut.greendao.ScoreDao;
import com.lenovohit.administrator.tyut.greendao.User;
import com.lenovohit.administrator.tyut.net.service.UserService;
import com.lenovohit.administrator.tyut.utils.EventUtil;
import com.lenovohit.administrator.tyut.utils.ScoreUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.QueryBuilder;

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
    //用于和listview绑定
    List<MySection> list2 = new ArrayList<>();
    //用于保存到数据库中
    List<Score>list8=new ArrayList<>();
    private ScoreAdapter adapter;
    private ScoreDao scoreDao;
    private User user;

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
        user = MyApp.getUser();
        DaoManager instance = DaoManager.getInstance(this);
        DaoSession session = instance.getSession();
        scoreDao = session.getScoreDao();
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
        List<Score> scores = queryData();
        if (scores.size()==0){
            ScoreUtil.getBuScore(service,this);
        }else {
            list2.clear();
            for (Score score:scores){
                if (score.getIsHead()){
                    list2.add(new MySection(true,score.getTitle()));
                }else {
                    list2.add(new MySection(score));
                }
            }
            adapter.notifyDataSetChanged();
        }

    }
    /**
     * 拿到成绩后回调过来
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getScore(String code) {
        switch (code) {
            case Constant.BuScoreUrl:
                User user=MyApp.getUser();
                list2.clear();
                list8.clear();
                list31 = ScoreData.getList31();
                list32 = ScoreData.getList32();
                MySection mySection = new MySection(true, "尚不及格");
                list2.add(mySection);
                list8.add(new Score(null,user.getAccount(),"尚不及格",null,null,"bu",true));
                if (list31 .size()!= 0) {
                    for (Score score : list31) {
                        list2.add(new MySection(score));
                        list8.add(new Score(null,user.getAccount(),score.getTitle(),score.getXuefen(),score.getChengji(),"bu",false));
                    }
                } else {
                    Score score=new Score();
                    score.setTitle("已经通过所有课程");
                    list2.add(new MySection(score));
                    list8.add(new Score(null,user.getAccount(),score.getTitle(),null,null,"bu",false));
                }
                MySection mySection1 = new MySection(true, "曾不及格");
                list2.add(mySection1);
                list8.add(new Score(null,user.getAccount(),"曾不及格",null,null,"bu",true));
                if (list32 != null)
                    for (Score score : list32) {
                        list2.add(new MySection(score));
                        list8.add(new Score(null,user.getAccount(),score.getTitle(),score.getXuefen(),score.getChengji(),"bu",false));
                    }
                    insertData(list8);
                adapter.notifyDataSetChanged();
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(this, "刷新成功！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public static void StartCurrentScoreActivity(Context context) {
        Intent intent = new Intent(context, BuScoreActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        ScoreUtil.getBuScore(service,this);
    }
    /**
     * 查询数据库中的不及格成绩
     */
    public List<Score> queryData(){

        QueryBuilder<Score> qb = scoreDao.queryBuilder();
        qb.where(qb.and(ScoreDao.Properties.Username.eq(user.getAccount()),ScoreDao.Properties.Flag.eq("bu")));
        List<Score> list = qb.list();
        return list;
    }
    /**
     * 把不及格成绩插入到数据库中
     */
    public void insertData(List<Score>list){
        List<Score> scores = queryData();
        if (scores.size()==0){
            scoreDao.insertInTx(list);
        }else {
            scoreDao.deleteInTx(scores);
            scoreDao.insertInTx(list);
        }
    }
}
