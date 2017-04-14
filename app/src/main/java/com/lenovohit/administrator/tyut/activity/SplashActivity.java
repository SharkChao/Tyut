package com.lenovohit.administrator.tyut.activity;

import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.greendao.DaoManager;
import com.lenovohit.administrator.tyut.greendao.DaoSession;
import com.lenovohit.administrator.tyut.greendao.Token;
import com.lenovohit.administrator.tyut.greendao.TokenDao;
import com.lenovohit.administrator.tyut.greendao.User;
import com.lenovohit.administrator.tyut.greendao.UserDao;
import com.lenovohit.administrator.tyut.net.service.UserService;
import com.lenovohit.administrator.tyut.utils.NetworkUtil;
import com.lenovohit.administrator.tyut.utils.SpUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    @Inject
    UserService service;
    @Override
    public void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ((MyApp)getApplicationContext()).getActivityComponent().inject(this);
    }

    @Override
    public void initDate() {

    }

    @Override
    public void initEvent() {
        Observable.timer(3, TimeUnit.SECONDS)

                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        isLogin();
                    }
                });
    }

    @Override
    public void Update(Boolean isConnection) {

    }
    /**
     * 我们需要在登录页写一个方法判断用户是否登录过了
     */
    public void isLogin(){
        boolean connected = NetworkUtil.isConnected(this);
        if (connected){
            boolean isUser=false;
            isUser = (boolean) SpUtil.getParam(SplashActivity.this,"isUser",false);
            if (isUser==true){
            Observable<ResponseBody> login = service.isLogin();
            login.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<ResponseBody>() {
                        @Override
                        public void call(ResponseBody responseBody) {
                            try {
                                String s = parseHtml(responseBody.string());
                                if (s.equals("学生选课结果")){
                                    String username1 = (String) SpUtil.getParam(SplashActivity.this, "name", "");
                                    String password1 = (String) SpUtil.getParam(SplashActivity.this, "password", "");
                                    DaoManager instance = DaoManager.getInstance(SplashActivity.this);
                                    final DaoSession session = instance.getSession();
                                    final List<User> list = session.getUserDao().queryBuilder().where(UserDao.Properties.Account.eq(username1)).build().list();
                                   if (list.size()!=0){
                                       MyApp.setUser(list.get(0));
                                       SpUtil.setParam(SplashActivity.this,"isUser",true);
                                       BmobUser bmobUser=new BmobUser();
                                       bmobUser.setUsername(username1);
                                       bmobUser.setPassword(password1);
                                       bmobUser.login(new SaveListener<BmobUser>() {
                                           @Override
                                           public void done(BmobUser bmobUser, BmobException e) {
                                               if (e==null){
                                                   Toast.makeText(SplashActivity.this,"Bmob后端云登陆成功！",Toast.LENGTH_LONG).show();
                                                   List<Token> tokens = queryToken(list.get(0));
                                                   if (tokens.size()==0){
                                                       LoginActivity.startLoginActivity(SplashActivity.this);
                                                       finish();
                                                       Toast.makeText(SplashActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                                                   }else {
                                                       LoginRongIM(tokens.get(0).getToken());
                                                       HomeActivity.StartHomeActivity(SplashActivity.this);
                                                       finish();
                                                   }
                                               }else {
                                                   Toast.makeText(SplashActivity.this,"Bmob后端云登陆失败，请重新登陆",Toast.LENGTH_LONG).show();
                                                   LoginActivity.startLoginActivity(SplashActivity.this);
                                                   finish();
                                               }
                                           }
                                       });
                                   }else {
                                       LoginActivity.startLoginActivity(SplashActivity.this);
                                       finish();
                                       Toast.makeText(SplashActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                                   }

                                }else {
                                    LoginActivity.startLoginActivity(SplashActivity.this);
                                    finish();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });}
            else {
                LoginActivity.startLoginActivity(SplashActivity.this);
                finish();
            }
        }else {
            LoginActivity.startLoginActivity(SplashActivity.this);
            finish();
        }

    }
    public String  parseHtml(String html){
        Document document = Jsoup.parse(html);
        String title = document.title();
        return title;
    }
    public void LoginRongIM(String token) {
        if (TextUtils.isEmpty(token)){
            Toast.makeText(SplashActivity.this,"获取token失败",Toast.LENGTH_SHORT).show();
        }else {
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                //token已经过期
                @Override
                public void onTokenIncorrect() {

                }

                @Override
                public void onSuccess(String s) {
                    Toast.makeText(SplashActivity.this, "当前用户登陆成功,已经成功连接到融云服务器", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Toast.makeText(SplashActivity.this, "当前用户登录失败,聊天服务未开启", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public List<Token> queryToken(User user){
        DaoManager instance = DaoManager.getInstance(this);
        DaoSession session = instance.getSession();
        TokenDao tokenDao = session.getTokenDao();
        List<Token> list = tokenDao.queryBuilder().where(TokenDao.Properties.UserId.eq(user.getAccount())).build().list();
        return list;
    }
}
