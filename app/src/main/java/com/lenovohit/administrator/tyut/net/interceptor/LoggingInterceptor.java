package com.lenovohit.administrator.tyut.net.interceptor;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017-04-27.
 */

class LoggingInterceptor implements Interceptor {

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
      for (int i=0;i<request.headers().size();i++){
          Logger.d(request.headers().value(i));
      }
        return response;
    }
}