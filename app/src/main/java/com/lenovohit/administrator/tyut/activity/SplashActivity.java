package com.lenovohit.administrator.tyut.activity;

import android.view.WindowManager;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.net.service.UserService;
import com.lenovohit.administrator.tyut.utils.NetworkUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

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
            Observable<ResponseBody> login = service.isLogin();
            login.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<ResponseBody>() {
                        @Override
                        public void call(ResponseBody responseBody) {
                            try {
                                String s = parseHtml(responseBody.string());
                                if (s.equals("学生选课结果")){
                                    HomeActivity.StartHomeActivity(SplashActivity.this);
                                    finish();
                                }else {
                                    LoginActivity.startLoginActivity(SplashActivity.this);
                                    finish();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }else {
            HomeActivity.StartHomeActivity(SplashActivity.this);
            finish();
        }

    }
    public String  parseHtml(String html){
        Document document = Jsoup.parse(html);
        String title = document.title();
        return title;
    }

}
