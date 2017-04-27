package com.lenovohit.administrator.tyut.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.views.Alert;
import com.mingle.widget.LoadingView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/8.
 */

public class NewsDetailActivity extends BaseActivity {
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.tvConn)
    TextView tvConn;
    @Bind(R.id.loadView)
    LoadingView loadingView;
    private Alert alert;


    @Override
    public void initView() {
        setContentView(R.layout.activity_newsdetail);
        loadingView.setVisibility(View.VISIBLE);
        loadingView.setLoadingText("正在加载中，请稍等");
        loadingView.upThrow();
        String url = getIntent().getStringExtra("url");
        ButterKnife.bind(this);
        alert = new Alert(this);
//        Toast.makeText(this,"url"+url,Toast.LENGTH_LONG).show();
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
                loadingView.setVisibility(View.GONE);
                webview.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }
        });
        webview.loadUrl(url);
    }

    public static void startNewsDetailActivity(Context context, String url) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

        webSettings.setUseWideViewPort(false);  //将图片调整到适合webview的大小

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
    @Override
    public void Update(Boolean isConnection) {
        if (isConnection){
            tvConn.setText("请连接网络！");
            tvConn.setVisibility(View.VISIBLE);
        }else {
            tvConn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        webview.destroy();
    }
}
