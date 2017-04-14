package com.lenovohit.administrator.tyut.fragment.two;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.adapter.CommenAdapter;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.constant.Constant;
import com.lenovohit.administrator.tyut.data.ScoreData;
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

public class CurrentScoreActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @Inject
    UserService service;
    @Bind(R.id.lvScoreList)
    ListView listView;
    @Bind(R.id.id_swipe_ly)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.title)
            TextView textView;
    List<Score>list=new ArrayList<>();
    private CommenAdapter<Score> adapter;
    private User user;
    private ScoreDao scoreDao;
    //和数据库交互的list
    private List<Score>list8=new ArrayList<>();
    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_currentscore);
        ((MyApp) getApplicationContext()).getActivityComponent().inject(this);
        EventUtil.register(this);
        adapter = new CommenAdapter<Score>(list) {
            @Override
            public void convertView(int position, Object var) {
                TextView tv1= (TextView) getView(R.id.tvName);
                TextView tv2= (TextView) getView(R.id.tvXuefen);
                TextView tv3= (TextView) getView(R.id.tvChengji);
                Score score=(Score)var;
                if (score!=null){
                    tv1.setText(score.getTitle());
                    tv2.setText(score.getXuefen()+"");
                    tv3.setText(score.getChengji()+"");
                }
            }

            @Override
            public int inflateView() {
                return R.layout.score_list_item;
            }
        };
        listView.setAdapter(adapter);
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
        getCurrentScore();
    }

    /**
     * 发送请求到学校接口，获取本学期成绩
     */
    public void getCurrentScore(){
        List<Score> scores = queryData();
        if (scores.size()==0){
            ScoreUtil.getCurrentScore(service,this);
        }else {
            listView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.INVISIBLE);
            list.clear();
            list.addAll(scores);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 拿到成绩后回调过来
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getScore(String code){
        switch (code){
            case Constant.CurrentScoreUrl:
                list= ScoreData.getList1();
                list8.clear();
               if (list.size()==0){
                   listView.setVisibility(View.INVISIBLE);
                   textView.setVisibility(View.VISIBLE);
               }else {
                   textView.setVisibility(View.INVISIBLE);
                   listView.setVisibility(View.VISIBLE);
                   for (Score score:list){
                       list8.add(new Score(null,user.getAccount(),score.getTitle(),score.getXuefen(),score.getChengji(),"ben",false));
                   }
                   insertData(list8);
               }
               if (adapter!=null){
                   adapter.notifyDataSetChanged();
               }
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                break;
        }
    }

    public static void StartCurrentScoreActivity(Context context){
        Intent intent=new Intent(context,CurrentScoreActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        ScoreUtil.getCurrentScore(service,this);
    }
    /**
     * 查询数据库中的本学期成绩
     */
    public List<Score> queryData(){

        QueryBuilder<Score> qb = scoreDao.queryBuilder();
        qb.where(qb.and(ScoreDao.Properties.Username.eq(user.getAccount()),ScoreDao.Properties.Flag.eq("ben")));
        List<Score> list = qb.list();
        return list;
    }
    /**
     * 把本学期成绩插入到数据库中
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

