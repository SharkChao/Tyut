package com.lenovohit.administrator.tyut.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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
import com.lenovohit.administrator.tyut.utils.MD5Util;
import com.lenovohit.administrator.tyut.utils.NetworkUtil;
import com.lenovohit.administrator.tyut.utils.SpUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
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
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    View[] imageViews = new View[3];
    int imgRes[] = new int[]{R.mipmap.vp1, R.mipmap.vp2, R.mipmap.vp3};
    @Bind(R.id.ivPic)
    ImageView ivPic;

    @Override
    public void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ((MyApp) getApplicationContext()).getActivityComponent().inject(this);
    }

    @Override
    public void initDate() {
        for (int i = 0; i < imgRes.length; i++) {
            View view=View.inflate(this,R.layout.fragment_splash,null);
            imageViews[i] = view;
          ImageView  ivPic = (ImageView) view.findViewById(R.id.ivPic);
            Bitmap bitmap = readBitMap(this, imgRes[i]);
            ivPic.setImageBitmap(bitmap);
            if (i==2){
                Button btn = (Button) view.findViewById(R.id.splashBtn);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoginActivity.startLoginActivity(SplashActivity.this);
                        finish();
                    }
                });
            }
        }
        boolean b = (boolean) SpUtil.getParam(this, "isFirstOne", true);
        if (b) {
            viewpager.setVisibility(View.VISIBLE);
            ivPic.setVisibility(View.GONE);
        } else {
            ivPic.setVisibility(View.VISIBLE);
            viewpager.setVisibility(View.GONE);
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
    }

    @Override
    public void initEvent() {

        viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(imageViews[position], 0);
                return imageViews[position];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView(imageViews[position % imgRes.length]);
            }
        });
    }

    @Override
    public void Update(Boolean isConnection) {

    }

    /**
     * 我们需要在登录页写一个方法判断用户是否登录过了
     */
    public void isLogin() {
        boolean connected = NetworkUtil.isConnected(this);
        if (connected) {
            boolean isUser = false;
            isUser = (boolean) SpUtil.getParam(SplashActivity.this, "isUser", false);
            if (isUser == true) {
                Observable<ResponseBody> login = service.isLogin();
                login.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<ResponseBody>() {
                            @Override
                            public void call(ResponseBody responseBody) {
                                try {
                                    String s = parseHtml(responseBody.string());
                                    if (s.equals("学生选课结果")) {
                                        String username1 = (String) SpUtil.getParam(SplashActivity.this, "name", "");
                                        String password1 = (String) SpUtil.getParam(SplashActivity.this, "password", "");
                                        DaoManager instance = DaoManager.getInstance(SplashActivity.this);
                                        final DaoSession session = instance.getSession();
                                        final List<User> list = session.getUserDao().queryBuilder().where(UserDao.Properties.Account.eq(username1)).build().list();
                                        if (list.size() != 0) {
                                            MyApp.setUser(list.get(0));
                                            SpUtil.setParam(SplashActivity.this, "isUser", true);
                                            BmobUser bmobUser = new BmobUser();
                                            bmobUser.setUsername(username1);
                                            bmobUser.setPassword(MD5Util.encrypt(password1));
                                            bmobUser.login(new SaveListener<BmobUser>() {
                                                @Override
                                                public void done(BmobUser bmobUser, BmobException e) {
                                                    if (e == null) {
//                                                        Toast.makeText(SplashActivity.this, "Bmob后端云登陆成功！", Toast.LENGTH_LONG).show();
                                                        List<Token> tokens = queryToken(list.get(0));
                                                        if (tokens.size() == 0) {
                                                            LoginActivity.startLoginActivity(SplashActivity.this);
                                                            finish();
                                                            Toast.makeText(SplashActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            LoginRongIM(tokens.get(0).getToken());
                                                            HomeActivity.StartHomeActivity(SplashActivity.this);
                                                            finish();
                                                        }
                                                    } else {
                                                        Toast.makeText(SplashActivity.this, "Bmob后端云登陆失败，请重新登陆"+e.getMessage(), Toast.LENGTH_LONG).show();
                                                        LoginActivity.startLoginActivity(SplashActivity.this);
                                                        finish();
                                                    }
                                                }
                                            });
                                        } else {
                                            LoginActivity.startLoginActivity(SplashActivity.this);
                                            finish();
                                            Toast.makeText(SplashActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        LoginActivity.startLoginActivity(SplashActivity.this);
                                        finish();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            } else {
                LoginActivity.startLoginActivity(SplashActivity.this);
                finish();
            }
        } else {
            LoginActivity.startLoginActivity(SplashActivity.this);
            finish();
        }

    }

    public String parseHtml(String html) {
        Document document = Jsoup.parse(html);
        String title = document.title();
        return title;
    }

    public void LoginRongIM(String token) {
        if (TextUtils.isEmpty(token)) {
            Toast.makeText(SplashActivity.this, "获取token失败", Toast.LENGTH_SHORT).show();
        } else {
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                //token已经过期
                @Override
                public void onTokenIncorrect() {

                }

                @Override
                public void onSuccess(String s) {
//                    Toast.makeText(SplashActivity.this, "当前用户登陆成功,已经成功连接到融云服务器", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
//                    Toast.makeText(SplashActivity.this, "当前用户登录失败,聊天服务未开启", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public List<Token> queryToken(User user) {
        DaoManager instance = DaoManager.getInstance(this);
        DaoSession session = instance.getSession();
        TokenDao tokenDao = session.getTokenDao();
        List<Token> list = tokenDao.queryBuilder().where(TokenDao.Properties.UserId.eq(user.getAccount())).build().list();
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    public Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
}
