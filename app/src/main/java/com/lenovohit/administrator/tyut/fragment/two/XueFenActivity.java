package com.lenovohit.administrator.tyut.fragment.two;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.constant.Constant;
import com.lenovohit.administrator.tyut.data.ScoreData;
import com.lenovohit.administrator.tyut.greendao.DaoManager;
import com.lenovohit.administrator.tyut.greendao.DaoSession;
import com.lenovohit.administrator.tyut.greendao.User;
import com.lenovohit.administrator.tyut.greendao.XueFen;
import com.lenovohit.administrator.tyut.greendao.XueFenDao;
import com.lenovohit.administrator.tyut.net.service.UserService;
import com.lenovohit.administrator.tyut.utils.EventUtil;
import com.lenovohit.administrator.tyut.utils.ScoreUtil;
import com.lenovohit.administrator.tyut.utils.StringUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;


/**
 * Created by Administrator on 2017/3/17.
 */

public class XueFenActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.tv11)
    TextView tv11;
    @Bind(R.id.tv12)
    TextView tv12;
    @Bind(R.id.tv13)
    TextView tv13;
    @Bind(R.id.tv21)
    TextView tv21;
    @Bind(R.id.tv22)
    TextView tv22;
    @Bind(R.id.tv23)
    TextView tv23;
    @Bind(R.id.tv31)
    TextView tv31;
    @Bind(R.id.tv32)
    TextView tv32;
    @Bind(R.id.tv33)
    TextView tv33;
    @Bind(R.id.tv41)
    TextView tv41;
    @Bind(R.id.tv42)
    TextView tv42;
    @Bind(R.id.tv43)
    TextView tv43;
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Inject
    UserService service;
    private XueFenDao xueFenDao;

    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.layout_xuefen);
        EventUtil.register(this);
        ((MyApp) getApplicationContext()).getActivityComponent().inject(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
    }

    @Override
    public void initDate() {

        DaoManager instance = DaoManager.getInstance(this);
        DaoSession session = instance.getSession();
        xueFenDao = session.getXueFenDao();
        List<XueFen>list= queryXueFen();
        if (list.size()==0){
            ScoreUtil.getXueFenLogin(service,getApplicationContext());
            Toast.makeText(XueFenActivity.this,"第一次进入时，数据库中没有数据",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(XueFenActivity.this,"直接从本地数据库中拿的数据",Toast.LENGTH_SHORT).show();
            setTextValues(list.get(0));
        }
    }

    @Override
    public void initEvent() {


    }
    public static void startXueFenActivity(Context context){
        context.startActivity(new Intent(context,XueFenActivity.class));
    }
    //数据回调，从网络返回的数据
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getData(String value) {
        if (Constant.XueFenQuery.equals(value)){
            if (!TextUtils.isEmpty(value)&&value.length()>1){
                String s=ScoreData.getJson();
                String s1 = StringUtil.trimFirstAndLastChar(s, '[');
                String s2 = StringUtil.trimFirstAndLastChar(s1, ']');
                Gson gson=new Gson();
                XueFen xuefen = gson.fromJson(s2,XueFen.class);
                setTextValues(xuefen);
                //先展示到街面上，再插入到数据库中
                insertAndUpdateXueFen(xuefen);
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }else {
                Toast.makeText(XueFenActivity.this,"获取学分绩点失败，请刷新重试",Toast.LENGTH_SHORT).show();
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
       }
    }
    public void setTextValues(XueFen xueFen){
        tv11.setText(xueFen.getYqzxf());
        tv12.setText(xueFen.getZxf());
        tv13.setText(xueFen.getYxzzsjxf());

        tv21.setText(xueFen.getPjxfjd());
        tv22.setText(xueFen.getGpabjpm());
        tv23.setText(xueFen.getGpazypm());

        tv31.setText(xueFen.getJqxfcj());
        tv32.setText(xueFen.getJqbjpm());
        tv33.setText(xueFen.getJqzypm());

        tv41.setText(xueFen.getPjcj());
        tv42.setText(xueFen.getPjcjbjpm());
        tv43.setText(xueFen.getPjcjzypm());
    }

    @Override
    public void onRefresh() {
        ScoreUtil.getXueFenLogin(service,this);
    }

    /**
     * 插入学分排名，如果有当前用户的学分排名，不需要插入，
     * 每次从网络获取到学分排名后，都需要在数据库更新一次
     * @param xueFen
     */
    public void insertAndUpdateXueFen(XueFen xueFen){
        List<XueFen> list = xueFenDao.queryBuilder().where(XueFenDao.Properties.Xh.eq(xueFen.getXh())).build().list();
        if (list.size()==0){
            xueFenDao.insert(xueFen);
        }else {
            xueFenDao.update(xueFen);
        }
    }
    public List<XueFen> queryXueFen(){
        User user = MyApp.getUser();
        List<XueFen> list = xueFenDao.queryBuilder().where(XueFenDao.Properties.Xh.eq(user.getAccount())).build().list();
        return list;
    }
}
