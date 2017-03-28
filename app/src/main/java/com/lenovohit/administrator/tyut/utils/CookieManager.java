package com.lenovohit.administrator.tyut.utils;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookieManager implements CookieJar{
	Context context;
	 private  PersistentCookieStore cookieStore ;
	 public CookieManager(Context context) {
		// TODO Auto-generated constructor stub
		 this.context=context;
		 cookieStore= new PersistentCookieStore(context);
	}
	@Override
	public List<Cookie> loadForRequest(HttpUrl url) {
		List<Cookie> cookies = cookieStore.get(url);
        return cookies;
	}

	@Override
	public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
		// TODO Auto-generated method stub
		if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
	}

}
