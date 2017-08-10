package com.lenovohit.administrator.tyut.fragment.two;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.constant.Constant;
import com.lenovohit.administrator.tyut.data.KeBiaoData;
import com.lenovohit.administrator.tyut.greendao.DaoManager;
import com.lenovohit.administrator.tyut.greendao.DaoSession;
import com.lenovohit.administrator.tyut.greendao.KeBiaoEntity;
import com.lenovohit.administrator.tyut.greendao.KeBiaoEntityDao;
import com.lenovohit.administrator.tyut.net.service.UserService;
import com.lenovohit.administrator.tyut.utils.EventUtil;
import com.lenovohit.administrator.tyut.utils.KeBiaoUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;



/**
 * Created by Administrator on 2017/3/17.
 * 本学期课表
 */

public class   KeBiaoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Inject
    UserService service;
    private LinearLayout ll1;
    private LinearLayout ll2;
    private LinearLayout ll3;
    private LinearLayout ll4;
    private LinearLayout ll5;
    private LinearLayout ll6;
    private LinearLayout ll7;
    private LinearLayout ll8;
    private LinearLayout ll9;
    private LinearLayout ll10;
    private LinearLayout ll11;
    private SwipeRefreshLayout swipeLayout;
    private ScrollView scrollview;

    private int colors[] = {
            Color.rgb(0xee,0xff,0xff),
            Color.rgb(0xf0,0x96,0x09),
            Color.rgb(0x8c,0xbf,0x26),
            Color.rgb(0x00,0xab,0xa9),
            Color.rgb(0x99,0x6c,0x33),
            Color.rgb(0x3b,0x92,0xbc),
            Color.rgb(0xd5,0x4d,0x34),
            Color.rgb(0xcc,0xcc,0xcc)
    };
    private LinearLayout[] ll;
    private KeBiaoEntityDao keBiaoEntityDao;

    public void setClass(LinearLayout ll,String title,int classes,int color) {
        View view = View.inflate(this, R.layout.item, null);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dip2px(this,50),1.0f);
        view.setLayoutParams(params);
        if (title.length()==3){
            view.setBackgroundColor(colors[0]);
        }else {
            view.setBackgroundColor(colors[color]);
        }
        ((TextView)view.findViewById(R.id.title)).setText(title);
        ll.addView(view);
    }
    public void setNoClass(LinearLayout ll, int classes, int color)
    {
        TextView blank = new TextView(this);
        if(color == 0)
            blank.setMinHeight(dip2px(this,classes * 50));
        blank.setBackgroundColor(colors[color]);
        ll.addView(blank);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_kebiao);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        ll5 = (LinearLayout) findViewById(R.id.ll5);
        ll6 = (LinearLayout) findViewById(R.id.ll6);
        ll7 = (LinearLayout) findViewById(R.id.ll7);
        ll8 = (LinearLayout) findViewById(R.id.ll8);
        ll9 = (LinearLayout) findViewById(R.id.ll9);
        ll10 = (LinearLayout) findViewById(R.id.ll10);
        ll11= (LinearLayout) findViewById(R.id.ll11);
        ll = new LinearLayout[]{ll1,ll2,ll3,ll4,ll5,ll6,ll7,ll8,ll9,ll10,ll11};
        if (scrollview != null) {
            scrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if (swipeLayout != null) {
                        swipeLayout.setEnabled(scrollview.getScrollY() == 0);
                    }
                }
            });
        }
    }

    @Override
    public void initDate() {
        ((MyApp) getApplicationContext()).getActivityComponent().inject(this);
        EventUtil.register(this);
        DaoManager instance = DaoManager.getInstance(this);
        DaoSession session = instance.getSession();
        keBiaoEntityDao = session.getKeBiaoEntityDao();
        List<KeBiaoEntity> keBiaoEntities = queryData();
        if (keBiaoEntities!=null&&keBiaoEntities.size()!=0){
            //从本地数据库中获取
            for (KeBiaoEntity entity:keBiaoEntities){
                int v = (int) (Math.random() * 6)+1;
                setClass(ll[Integer.parseInt(entity.getState())-1],entity.getValue(),1,v);
            }
        }else {
            //需要从网络获取
            KeBiaoUtil.getKeBiao(service,this);
        }
    }

    @Override
    public void initEvent() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receiverData(String code){
        if (code.equals(Constant.KeBiaoQuery)){
            if (swipeLayout.isRefreshing()){
                swipeLayout.setRefreshing(false);
            }
            List<KeBiaoEntity> list = KeBiaoData.getList();
            if (list!=null&&list.size()!=0) {
                for (KeBiaoEntity entity : list) {
                    int v = (int) (Math.random() * 6) + 1;
                    setClass(ll[Integer.parseInt(entity.getState()) - 1], entity.getValue(), 1, v);
                }
                //从网络获取到数据后，放入到数据库中
                insertData(list);
            }
        }
    }

    /**
     * 插入数据到数据库中,如果有数据的话就得更新数据
     */
    public void insertData(List<KeBiaoEntity>entitys){
        List<KeBiaoEntity> list = queryData();
        if (list!=null&&list.size()!=0){
            //更新数据
            keBiaoEntityDao.deleteInTx(list);
            keBiaoEntityDao.insertInTx(entitys);
        }else {
            //直接插入数据
            keBiaoEntityDao.insertInTx(entitys);
        }
    }

    /**
     *
     * 查询数据库中的课表数据
     */
    public List<KeBiaoEntity> queryData(){
        List<KeBiaoEntity> list = keBiaoEntityDao.queryBuilder().build().list();
        if (list.size()==0){
            return null;
        }
        return list;
    }

    public static void startKeBiaoActivity(Context context){
        context.startActivity(new Intent(context,KeBiaoActivity.class));
    }

    @Override
    public void onRefresh() {
        KeBiaoUtil.getKeBiao(service,this);
    }
}