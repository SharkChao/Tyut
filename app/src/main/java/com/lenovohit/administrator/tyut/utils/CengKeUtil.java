package com.lenovohit.administrator.tyut.utils;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lenovohit.administrator.tyut.activity.LoginActivity;
import com.lenovohit.administrator.tyut.app.AppManager;
import com.lenovohit.administrator.tyut.constant.Constant;
import com.lenovohit.administrator.tyut.data.CengKeData;
import com.lenovohit.administrator.tyut.domain.CengKeQuery;
import com.lenovohit.administrator.tyut.domain.CourseCeng;
import com.lenovohit.administrator.tyut.domain.Courses;
import com.lenovohit.administrator.tyut.fragment.two.CengKeActivity;
import com.lenovohit.administrator.tyut.greendao.KeBiaoEntity;
import com.lenovohit.administrator.tyut.net.service.UserService;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017-04-21.
 */

public class CengKeUtil {

    private static String state;

    //拿到本學期成绩
    public static void getCengKeQuery(UserService service, final Context context) {
        Observable<ResponseBody> currentScore = service.getCengKeQuery();
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
                            String string = responseBody.string();
                            parseHtml(string,context);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    public static void parseHtml(String html,Context context){
        Logger.d(html);
        Document document = Jsoup.parse(html);
        String title = document.title();
        if (title.equals("错误信息")){
            Toast.makeText(context, "身份信息过期，请重新登陆", Toast.LENGTH_SHORT).show();
            LoginActivity.startLoginActivity(context);
            ((CengKeActivity)context).finish();
        }else {
            CengKeQuery query = new CengKeQuery();
            //拿到本学期所在的时间
            Element xueqi = document.getElementsByAttributeValue("name", "kcxnxq").get(0);
            Element benxueqi = xueqi.getElementsByAttributeValue("selected", "selected").get(0);
            String xueqiText = benxueqi.attr("value");
            query.setXueqi(xueqiText);
            //拿到所在的院系
            Element yuanxi = document.getElementsByAttributeValue("name", "xsh").get(0);
            Elements options = yuanxi.select("option");
            List<CourseCeng> yuanList = new ArrayList<>();
            for (Element element : options) {
                String yuanxiText = element.text();
                String value = element.attr("value");
                CourseCeng ceng = new CourseCeng();
                ceng.setName(yuanxiText);
                ceng.setValue(value);
                yuanList.add(ceng);
            }
            query.setYuanxis(yuanList);
            //将时间发送给activity
            CengKeData.setCengKeQuery(query);
            EventUtil.postSticky(Constant.CengKeQuery);
        }
    }
    //拿到本學期成绩
    public static void getCengKeQueryResult(UserService service, final Context context,String xueqi,String yuanxi,String page,String name) {
        Observable<ResponseBody> currentScore = service.getCengKeQueryResult(xueqi,yuanxi,"50",page,page,name,"","","");
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
                            String string = responseBody.string();
                            parseHtml2(string,context,"1");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    //拿到本學期成绩
    public static void getCengKeQueryResult3(final Context context, final String xueqi, final String yuanxi, final String page, final String name) {
        RequestQueue mQueue = Volley.newRequestQueue(context);
        HttpUrl httpUrl = HttpUrl.parse("http://202.207.247.49/kckbcxAction.do?oper=kbtjcx");
        final Cookie cookie = new CookieManager(context).loadForRequest(httpUrl).get(0);
        Logger.d(new CookieManager(context).loadForRequest(httpUrl).size());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://202.207.247.49/kckbcxAction.do?oper=kbtjcx",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
//                parseHtml2(s,context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap localHashMap = new HashMap();
                localHashMap.put("Cookie", "JSESSIONID="+cookie.value());//向请求头部添加Cookie
                localHashMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");//向请求头部添加Cookie
                localHashMap.put("Accept-Encoding", "gzip, deflate");//向请求头部添加Cookie
                localHashMap.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");//向请求头部添加Cookie
                localHashMap.put("Content-Type", "application/x-www-form-urlencoded");//向请求头部添加Cookie
                return localHashMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("kcxnxq", xueqi);
                map.put("xsh", yuanxi);
                map.put("pageSize", "50");
                map.put("page", page);
                map.put("currentPage", page);
                map.put("kckcm", name);
                map.put("kckch", "");
                map.put("kckxh", "");
                map.put("pageNo", "");
                return map;
            }
        };
        mQueue.add(stringRequest);
    }
    public static void parseHtml2(String html,Context context,String page){
        Document parse = Jsoup.parse(html);
        String title = parse.title();
        Element nume = parse.getElementsByAttributeValue("align", "right").get(0);
        String num = nume.text().trim();
        String substring = num.substring(1, num.length() - 1);
        CengKeData.setNum(substring);
        if (title.equals("错误信息")){
            Toast.makeText(context, "身份信息过期，请重新登陆!", Toast.LENGTH_SHORT).show();
            AppManager appManager = AppManager.getAppManager();
            appManager.finishAllActivity();
            LoginActivity.startLoginActivity(context);
        }else {
            Elements trs = parse.getElementsByAttributeValue("class", "odd");
            List<Courses>list=new ArrayList<>();
            for (int i=0;i<trs.size();i++){
                Courses course=new Courses();
                Element tr = trs.get(i);
                Elements tds = tr.select("td");
                course.setId(StringUtil.isStrEmpty(tds.get(0).text())?"":Integer.parseInt(tds.get(0).text())+(Integer.parseInt(page)-1)*50+"");
                course.setCourseCode(StringUtil.isStrEmpty(tds.get(1).text())?"":tds.get(1).text());
                course.setCourseName(StringUtil.isStrEmpty(tds.get(2).text())?"":tds.get(2).text());
                course.setXueFen(StringUtil.isStrEmpty(tds.get(4).text())?"":tds.get(4).text());
                course.setKaoshiType(StringUtil.isStrEmpty(tds.get(5).text())?"":tds.get(5).text());
                course.setXiSuo(StringUtil.isStrEmpty(tds.get(6).text())?"":tds.get(6).text());
                course.setTeacherName(StringUtil.isStrEmpty(tds.get(7).text())?"":tds.get(7).text());
                //第八个需要拿到网址，这个稍微有些复杂
                Element img = tds.get(8).select("img").get(0);
                String onclick = img.attr("onclick");
                String url = onclick.substring(10, onclick.length() - 3);
                course.setUrl(StringUtil.isStrEmpty(url)?"":url);

                list.add(course);
            }
            CengKeData.setList(list);
        }
        if (page.equals("1")){
            EventUtil.postSticky(Constant.CengKeQueryResult);
        }else {
            EventUtil.postSticky(Constant.CengKeQueryResult+"1");
        }
    }
    public static void getCengKeQueryResult1(final Context context, String xueqi, String yuanxi, final String page, String name){
        HttpUtils httputils = new HttpUtils();
        HttpUrl httpUrl = HttpUrl.parse("http://202.207.247.49/kckbcxAction.do?oper=kbtjcx");
        Cookie cookie = new CookieManager(context).loadForRequest(httpUrl).get(0);

        RequestParams params = new RequestParams();
        params.addHeader("Cookie", "JSESSIONID="+cookie.value());
        params.addBodyParameter("kcxnxq", xueqi);
        params.addBodyParameter("xsh", yuanxi);
        params.addBodyParameter("pageSize", "50");
        params.addBodyParameter("page", page);
        params.addBodyParameter("currentPage", page);
        params.addBodyParameter("kckcm", name);
        params.addBodyParameter("kckch", "");
        params.addBodyParameter("kckxh", "");
        params.addBodyParameter("pageNo", "");
        httputils.send(HttpRequest.HttpMethod.POST, "http://202.207.247.49/kckbcxAction.do?oper=kbtjcx", params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                parseHtml2(result,context,page);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    public static void getCengKeTime(UserService service, final Context context, String url){
        Observable<ResponseBody> currentScore = service.getCengkeTime(Constant.LoginUrl+url);
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
                            String string = responseBody.string();
                            parseHtml(string);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     *
     * @param html
     */
    public static void parseHtml(String html){
        Document document = Jsoup.parse(html);
        Element table = document.getElementsByAttributeValue("id", "user").get(0);
        //0-13 共十四个tr  先把0,5,10排除，
        Elements trs = table.getElementsByAttributeValue("bgcolor", "#FFFFFF");
        List<KeBiaoEntity>list=new ArrayList<>();
        for(int i=0;i<trs.size();i++){
            if (i!=0&&i!=5&&i!=10){
                if (i!=1&&i!=6&&i!=11){
                    Element tr = trs.get(i);
                    Elements tds = tr.select("td");
                    for (int j=0;j<tds.size();j++){
                        if (j==0){
                            state = tds.get(0).text();
                        }else {
                            KeBiaoEntity kebiao=new KeBiaoEntity();
                            String text = tds.get(j).text();
                            kebiao.setState(state);
                            Logger.d(text);
                            kebiao.setValue(text);
                            kebiao.setXingqi(j+"");
                            list.add(kebiao);
                        }
                    }
                }else {
                    Element tr = trs.get(i);
                    Elements tds = tr.select("td");
                    for (int j=0;j<tds.size();j++){
                        if (j==1){
                            state = tds.get(j).text();
                        }else if (j>1){
                            KeBiaoEntity kebiao=new KeBiaoEntity();
                            String text = tds.get(j).text();
                            kebiao.setState(state);
                            Logger.d(text);
                            kebiao.setValue(text);
                            kebiao.setXingqi(j-1+"");
                            list.add(kebiao);
                        }
                    }
                }
            }
        }
        CengKeData.setList2(list);
        EventUtil.postSticky("蹭课课程详情页");
    }
    public static void getCengKeQueryResultTwo(UserService service, final Context context, String num, final String page) {
        Observable<ResponseBody> currentScore = service.getCengKeReslutTwo("http://202.207.247.49/kckbcxAction.do?totalrows="+num+"&page="+page+"&pageSize=50");
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
                            String string = responseBody.string();
                            parseHtml2(string,context,page);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
