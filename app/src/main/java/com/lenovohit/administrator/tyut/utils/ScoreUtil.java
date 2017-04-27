package com.lenovohit.administrator.tyut.utils;

import android.content.Context;
import android.widget.Toast;

import com.lenovohit.administrator.tyut.activity.LoginActivity;
import com.lenovohit.administrator.tyut.app.AppManager;
import com.lenovohit.administrator.tyut.constant.Constant;
import com.lenovohit.administrator.tyut.data.ScoreData;
import com.lenovohit.administrator.tyut.greendao.Score;
import com.lenovohit.administrator.tyut.fragment.two.CurrentScoreActivity;
import com.lenovohit.administrator.tyut.net.service.UserService;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/14.
 * 获取成绩工具类
 */

public class ScoreUtil {


    //拿到本學期成绩
    public static void getCurrentScore(UserService service, final Context context) {
        Observable<ResponseBody> currentScore = service.getCurrentScore();
        currentScore.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "身份信息过期，请重新登录" + e, Toast.LENGTH_SHORT).show();
                        AppManager appManager = AppManager.getAppManager();
                        appManager.finishAllActivity();
                        LoginActivity.startLoginActivity(context);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            parseCurrentScoreHtml(responseBody.string(), context);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //解析本学期成绩
    public static void parseCurrentScoreHtml(String html, Context context) {
        Document document = Jsoup.parse(html);
        String title = document.title();
        if (title.equals("错误信息")) {
            LoginActivity.startLoginActivity(context);
            ((CurrentScoreActivity) context).finish();
        } else {
            Elements elements = document.getElementsByClass("odd");
            List<Score> list = new ArrayList<Score>();
            for (int i = 0; i < elements.size(); i++) {
                Score score = new Score();
                Elements tds = elements.get(i).getElementsByTag("td");
                Map<String, String> map = new HashMap<String, String>();
                for (int j = 0; j < tds.size(); j++) {
                    String text = tds.get(j).text();
                    map.put(j + "", text);
                }
                score.setTitle(map.get("2"));
                score.setXuefen(map.get("4"));
                score.setChengji(map.get("6"));
                list.add(score);
            }
            ScoreData.setList1(list);
            EventUtil.postSticky(Constant.CurrentScoreUrl);
        }

    }

    //拿到所有學期成绩
    public static void getAllScore(UserService service, final Context context) {
        Observable<ResponseBody> currentScore = service.getAllScore();
        currentScore.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "身份信息过期，请重新登录" + e, Toast.LENGTH_SHORT).show();
                        AppManager appManager = AppManager.getAppManager();
                        appManager.finishAllActivity();
                        LoginActivity.startLoginActivity(context);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            parseAllScoreHtml(responseBody.string(), context);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //解析所有成绩
    public static void parseAllScoreHtml(String html, Context context) {
        Document document = Jsoup.parse(html);
        String title = document.title();
        if (title.equals("错误信息")) {
            LoginActivity.startLoginActivity(context);
            ((CurrentScoreActivity) context).finish();
        } else {
            //这个list用来存放标题
            //解析标题12个
            List<Score> list2 = new ArrayList<>();
            Elements as = document.getElementsByTag("a");
            for (Element a : as) {
                String titles = a.attr("name");
                Score score = new Score();
                score.setTitle(titles);
                list2.add(score);
            }

            //这两个用来存放成绩
            Elements tables = document.getElementsByAttributeValue("id", "user");
            Map<String, List<Score>> map = new HashMap<>();
            List<Score> list;
            for (int k = 0; k < tables.size(); k++) {
                Elements tbodyss = tables.get(k).getElementsByTag("tbody");
                if (tbodyss.size() != 0) {
                    Element tbodys = tbodyss.get(0);
                    if (tbodys != null) {
                        Elements trs = tbodys.getElementsByTag("tr");
                        list = new ArrayList<>();
                        list.clear();
                        for (int i = 0; i < trs.size(); i++) {
                            Score score = new Score();
                            Elements tds = trs.get(i).getElementsByAttributeValue("align", "center");
                            for (int j = 0; j < tds.size(); j++) {
                                if (j == 2) {
                                    String titles = tds.get(2).text();
                                    score.setTitle(titles);
                                }
                                if (j == 4) {
                                    String xuefen = tds.get(4).text();
                                    score.setXuefen(xuefen);
                                }
                                if (j == 6) {
                                    String chengji = tds.get(6).getElementsByTag("p").get(0).text();
                                    score.setChengji(chengji);
                                }
                            }
                            list.add(score);//将一个表格中所有数据都添加完成
                        }
                        map.put(k + "", list);

                    }
                }

            }
            ScoreData.setList2(list2);
            ScoreData.setMap(map);
            EventUtil.postSticky(Constant.AllScoreUrl);
        }

    }

    //拿到不及格成绩
    public static void getBuScore(UserService service, final Context context) {
        Observable<ResponseBody> currentScore = service.getBuScore();
        currentScore.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "身份信息过期，请重新登录" + e, Toast.LENGTH_SHORT).show();
                        AppManager appManager = AppManager.getAppManager();
                        appManager.finishAllActivity();
                        LoginActivity.startLoginActivity(context);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            parseBuScoreHtml(responseBody.string(), context);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //解析不及格成绩  尚不及格1  曾不及格2
    public static void parseBuScoreHtml(String html, Context context) {
        Document document = Jsoup.parse(html);
        String title = document.title();
        if (title.equals("错误信息")) {
            LoginActivity.startLoginActivity(context);
            ((CurrentScoreActivity) context).finish();
        } else {
            Elements tables = document.getElementsByAttributeValue("id", "user");
            List<Score> list1 = new ArrayList<>();
            List<Score> list2 = new ArrayList<>();
            for (int k = 0; k < tables.size(); k++) {
                Elements tbodyss = tables.get(k).getElementsByTag("tbody");
                if (tbodyss.size() != 0) {
                    Element tbodys = tbodyss.get(0);
                    if (tbodys != null) {
                        Elements trs = tbodys.getElementsByTag("tr");
                        for (int i = 0; i < trs.size(); i++) {
                            Score score = new Score();
                            Elements tds = trs.get(i).getElementsByAttributeValue("align", "center");
//                        Toast.makeText(context, "tds"+tds, Toast.LENGTH_LONG).show();
                            for (int j = 0; j < tds.size(); j++) {
                                if (j == 2) {
                                    String titles = tds.get(2).text();
                                    score.setTitle(titles);
                                }
                                if (j == 4) {
                                    String xuefen = tds.get(4).text();
                                    score.setXuefen(xuefen);
                                }
                                if (j == 6) {
                                    String chengji = tds.get(6).getElementsByTag("p").get(0).text();
                                    score.setChengji(chengji);
                                }
                            }
                            if (k == 0) {
                                list1.add(score);
                            }
                            if (k == 1) {
                                list2.add(score);
                            }
                        }
                    }
                }
            }
            if (list1 != null) {
                ScoreData.setList31(list1);
            }
            if (list2 != null) {
                ScoreData.setList32(list2);
                EventUtil.postSticky(Constant.BuScoreUrl);
            }

        }
    }

    //拿到上学期成绩
    public static void getShangScore(UserService service, final Context context) {
        Observable<ResponseBody> currentScore = service.getShangScore();
        currentScore.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "身份信息过期，请重新登录" + e, Toast.LENGTH_SHORT).show();
                        AppManager appManager = AppManager.getAppManager();
                        appManager.finishAllActivity();
                        LoginActivity.startLoginActivity(context);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            parseShangScoreHtml(responseBody.string(), context);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //解析上学期成绩
    public static void parseShangScoreHtml(String html, Context context) {
        Document document = Jsoup.parse(html);
        String title = document.title();
        if (title.equals("错误信息")) {
            LoginActivity.startLoginActivity(context);
            ((CurrentScoreActivity) context).finish();
        } else {
            //这个list用来存放标题
            //解析标题12个
            List<Score> list2 = new ArrayList<>();
            Elements as = document.getElementsByTag("a");
            for (Element a : as) {
                String titles = a.attr("name");
                Score score = new Score();
                score.setTitle(titles);
                list2.add(score);
            }

            //这两个用来存放成绩
            Elements tables = document.getElementsByAttributeValue("id", "user");
            Map<String, List<Score>> map = new HashMap<>();
            List<Score> list;
            for (int k = 0; k < tables.size(); k++) {
                Elements tbodyss = tables.get(k).getElementsByTag("tbody");
                if (tbodyss.size() != 0) {
                    Element tbodys = tbodyss.get(0);
                    if (tbodys != null) {
                        Elements trs = tbodys.getElementsByTag("tr");
                        list = new ArrayList<>();
                        list.clear();
                        for (int i = 0; i < trs.size(); i++) {
                            Score score = new Score();
                            Elements tds = trs.get(i).getElementsByAttributeValue("align", "center");
                            for (int j = 0; j < tds.size(); j++) {
                                if (j == 2) {
                                    String titles = tds.get(2).text();
                                    score.setTitle(titles);
                                }
                                if (j == 4) {
                                    String xuefen = tds.get(4).text();
                                    score.setXuefen(xuefen);
                                }
                                if (j == 6) {
                                    String chengji = tds.get(6).getElementsByTag("p").get(0).text();
                                    score.setChengji(chengji);
                                }
                            }
                            list.add(score);//将一个表格中所有数据都添加完成
                        }
                        map.put(k + "", list);

                    }
                }

            }
            ScoreData.setList2(list2);
            ScoreData.setMap(map);
            EventUtil.postSticky(Constant.AllScoreUrl);
        }

    }

    //先登录另一个有学分绩点的教务处，里边有成绩，学分绩点排名
    public static void getXueFenLogin(final UserService service, final Context context) {
        final String username = (String) SpUtil.getParam(context, "name", "1");
        String password = (String) SpUtil.getParam(context, "password", "1");
        Observable<ResponseBody> observable = service
                .LoginXueFen(Constant.XuefenLogin,
                        "/wEdAAUmMiHpVdXzbs7h/Fjlk54Os6cJ10LSoiuCyULvHjRPuC/yb/VVqFmvGO/9JabxfEt0OWarNtHZvueomBCnXS0aq0ylv1H88AeRYXQDfUknQ1NvvP5How9yic4FW/LO1rEEV0MN3CKL1nX2BAZHHIEd",
                        "/wEPDwUKMTkzODMzMzAxN2QYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFDWNoa1JlbWVtYmVybWUmxiwhWD+RiQDMJawhlwFVLE2M4EJGvC7QSnf5ysk3RQ==",
                        "C2EE9ABB", "登录", "on", password, username);
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context,   "身份信息过期，请重新登录", Toast.LENGTH_SHORT).show();
                        LoginActivity.startLoginActivity(context);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        getXuefen(service, context, username);
                    }
                });

    }

    public static void getXuefen(UserService service, final Context context, String username) {
        Observable<ResponseBody> xueFen = service.getXueFen(Constant.XueFenQuery, "xsgrcj", "40", "0", "asc", "jqzypm,xh", username);
        xueFen.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String s = responseBody.string();
                            System.out.println(s);
                            ScoreData.setJson(s);
                            EventUtil.postSticky(Constant.XueFenQuery);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public static void LoginXueFen() {
        HttpUtils httputils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("__EVENTVALIDATION", "/wEdAAUmMiHpVdXzbs7h/Fjlk54Os6cJ10LSoiuCyULvHjRPuC/yb/VVqFmvGO/9JabxfEt0OWarNtHZvueomBCnXS0aq0ylv1H88AeRYXQDfUknQ1NvvP5How9yic4FW/LO1rEEV0MN3CKL1nX2BAZHHIEd");
        params.addBodyParameter("__VIEWSTATE", "/wEPDwUKMTkzODMzMzAxN2QYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFDWNoa1JlbWVtYmVybWUmxiwhWD+RiQDMJawhlwFVLE2M4EJGvC7QSnf5ysk3RQ==");
        params.addBodyParameter("__VIEWSTATEGENERATOR", "C2EE9ABB");
        params.addBodyParameter("btn_login", "登录");
        params.addBodyParameter("chkRememberme", "on");
        params.addBodyParameter("txt_password", "208219");
        params.addBodyParameter("txt_username", "2013005577");
//        httputils.send(HttpRequest.HttpMethod.POST, "http://202.207.247.60/Login.aspx", params, new RequestCallBack<String>() {
//        });
    }
}