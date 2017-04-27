package com.lenovohit.administrator.tyut.fragment.two;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/27.
 */

public class SiLiuJiFindActivity extends BaseActivity{
    @Bind(R.id.webview)
    WebView webview;
    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_siliujifind);
        setWebSeting(webview);
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }
        });
        webview.loadUrl("file:///android_asset/siliuji.html");
    }

    @Override
    public void initDate() {

    }

    @Override
    public void initEvent() {

    }
    public void setWebSeting(WebView webview){
        WebSettings webSettings = webview.getSettings();

        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setDomStorageEnabled(true);
//设置加载进来的页面自适应手机屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(false);  //将图片调整到适合webview的大小
        webSettings.setTextZoom(4);
        webSettings.setSupportZoom(true);  //支持缩放

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局

        webSettings.supportMultipleWindows();  //多窗口

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存

        webSettings.setAllowFileAccess(true);  //设置可以访问文件

        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点


        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口

        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片

    }
    public static  void  startSiLiuActivity(Context context){
        context.startActivity(new Intent(context,SiLiuJiFindActivity.class));
    }
}
