package com.lenovohit.administrator.tyut.net.service;

import com.lenovohit.administrator.tyut.constant.Constant;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/13.
 */
public interface UserService {

    //登录
    @FormUrlEncoded
    @POST(Constant.MainUrl)
    Observable<ResponseBody> Login(@Field("zjh") String account, @Field("mm") String password, @Field("v_yzm") String yzm);
    //获取验证码
    @GET(Constant.YZMurl)
    Observable<ResponseBody>getImage();
    //获取学校新闻
    @GET
    Observable<ResponseBody>getSchoolNews(@Url String url);
    //当再次进入应用时，需要验证是否已经登录过了
    @GET(Constant.YZurl)
    Observable<ResponseBody>isLogin();
    //获取本学期成绩
    @GET(Constant.CurrentScoreUrl)
    Observable<ResponseBody>getCurrentScore();
    //获取所有成绩
    @GET(Constant.AllScoreUrl)
    Observable<ResponseBody>getAllScore();
    //不及格成绩
    @GET(Constant.BuScoreUrl)
    Observable<ResponseBody>getBuScore();
    //上学期成绩
    @GET(Constant.ShangUrl)
    Observable<ResponseBody>getShangScore();
    //获取学分绩点得先登录
    @FormUrlEncoded
    @POST
    Observable<ResponseBody>LoginXueFen(@Url String url,@Field("__EVENTVALIDATION") String account, @Field("__VIEWSTATE") String password, @Field("__VIEWSTATEGENERATOR") String yzm, @Field("btn_login") String a, @Field("chkRememberme") String b, @Field("txt_password") String c, @Field("txt_username") String d);
    //登陆完成之后获取学分绩点排名
    @FormUrlEncoded
    @POST
    Observable<ResponseBody>getXueFen(@Url String url,@Field("do")String a,@Field("limit")String b,@Field("offset")String c,@Field("order")String d,@Field("sort")String e,@Field("xh")String f);
    @FormUrlEncoded
    @POST
    Observable<ResponseBody>getAllData(@Url String url,@Field("reportParamsId")String id,@Field("report1_currPage") String page,@Field("report1_cachedId")String cache);
}