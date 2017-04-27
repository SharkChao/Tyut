package com.lenovohit.administrator.tyut.net.service;

import com.lenovohit.administrator.tyut.constant.Constant;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017-04-26.
 */

public interface CourseService {
    //获取蹭课结果
    @FormUrlEncoded
    @POST(Constant.CengKeQueryResult)
    Observable<ResponseBody> getCengKeQueryResult(@Field("kcxnxq")String xueqi, @Field("xsh")String yuanxi, @Field("pageSize")String size, @Field("page")String page, @Field("currentPage")String currentPage, @Field("kckcm")String courseName, @Field("kckch") String b, @Field("kckxh")String c, @Field("pageNo")String d);
}
