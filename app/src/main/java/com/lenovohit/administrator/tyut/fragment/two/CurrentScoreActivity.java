package com.lenovohit.administrator.tyut.fragment.two;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.adapter.CommenAdapter;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.constant.Constant;
import com.lenovohit.administrator.tyut.data.ScoreData;
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
        getCurrentScore();
    }

    @Override
    public void initEvent() {

    }

    /**
     * 发送请求到学校接口，获取本学期成绩
     */
    public void getCurrentScore(){

        Gson gson = new Gson();
        String cache = (String) SpUtil.getParam(this, "currentscore", "");
        if (TextUtils.isEmpty(cache)) {
            ScoreUtil.getCurrentScore(service,this);
            textView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        } else {
            List<Score> score = gson.fromJson(cache, new TypeToken<List<Score>>() {
            }.getType());
            if (score.size() != 0) {
                listView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.INVISIBLE);
                list.clear();
                list.addAll(score);
                adapter.notifyDataSetChanged();
            }
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
               if (list.size()==0){
                   listView.setVisibility(View.INVISIBLE);
                   textView.setVisibility(View.VISIBLE);
               }else {
                   SpUtil.setParam(this,"currentscore",list);
                   textView.setVisibility(View.INVISIBLE);
                   listView.setVisibility(View.VISIBLE);
               }
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

    public static void StartCurrentScoreActivity(Context context){
        Intent intent=new Intent(context,CurrentScoreActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        getCurrentScore();
    }
}
