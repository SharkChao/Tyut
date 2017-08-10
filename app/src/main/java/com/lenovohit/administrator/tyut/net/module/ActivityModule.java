package com.lenovohit.administrator.tyut.net.module;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.lenovohit.administrator.tyut.net.scope.ActivityScope;
import com.lenovohit.administrator.tyut.net.service.UserService;
import com.lenovohit.administrator.tyut.utils.CookieManager;
import com.lenovohit.administrator.tyut.utils.FileUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2017/2/16.
 */
@Module
public class ActivityModule {
    Retrofit retrofit;
    Context context;
    String BASE_URL;
    public ActivityModule(String BASE_URL,Context context){
        this.BASE_URL=BASE_URL;
        this.context=context;
    }
    @Provides
    String provideString(){
        return new String();

    }
    @ActivityScope
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient){
        Interceptor interceptor = new Interceptor() {

            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.i("logcat","request="+request);
                okhttp3.Response response = chain.proceed(request);
                Log.i("logcat","response="+response);

                String cacheControl = request.cacheControl().toString();
                if (TextUtils.isEmpty(cacheControl)) {
                    cacheControl = "public, max-age=6000000";
                }
                cacheControl = "public, max-age=6000000";
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }
        };


        retrofit=new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .build();
        return retrofit;
    }
    @ActivityScope
    @Provides
    UserService provideService(Retrofit retrofit){
        UserService service = retrofit.create(UserService.class);
        return service;
    }


    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;
    @ActivityScope
    @Provides
    Cache cache(Context context) {
        //设置缓存路径
        final File baseDir = FileUtils.getCacheDirectory(context,"");
        final File cacheDir = new File(baseDir, "HttpResponseCache");
        //设置缓存 10M
        return new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
    @ActivityScope
    @Provides
    OkHttpClient getOKhttpClient(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //打印日志

                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder requestBuilder = request.newBuilder();
                        request = requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=GBK"),
                                URLDecoder.decode(bodyToString(request.body()), "UTF-8")))
                                .build();
                        return chain.proceed(request);
                    }
                })

                //设置Cache目录
//                .cache(cache(context))

                //失败重连
//                .retryOnConnectionFailure(true)

                //time out
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)

                //缓存cookie
                .cookieJar(new CookieManager(context))
                .build();
        return  okHttpClient;
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
    class LoggingInterceptor implements Interceptor {

        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            for (int i = 0; i < request.headers().size(); i++) {
                Logger.d(request.headers().value(i));
            }
            return response;
        }
    }
}
