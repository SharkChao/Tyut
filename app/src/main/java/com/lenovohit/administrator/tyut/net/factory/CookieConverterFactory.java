package com.lenovohit.administrator.tyut.net.factory;

import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/3/1.
 */

public class CookieConverterFactory extends Converter.Factory {
    public static CookieConverterFactory create() {
        return new CookieConverterFactory();
    }

    private CookieConverterFactory() {

    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {

        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        return new CookieResponseBodyConverter();
    }

    class CookieResponseBodyConverter implements Converter<ResponseBody, String> {
        @Override
        public String convert(ResponseBody value) throws IOException {
            try {
                Log.d("tag",value.string());
                return value.string();
            } finally {
                value.close();
            }
        }
    }

//    class CookieRequestBodyConverter implements Converter<String, RequestBody> {
//
//        CookieRequestBodyConverter() {
//
//        }
//
//        @Override
//        public RequestBody convert(String value) throws IOException {
//
//            return value;
//        }
//    }
}