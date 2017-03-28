package com.lenovohit.administrator.tyut.fragment.three;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/23.
 */

public class StudyZLActivity extends BaseActivity {
    @Bind(R.id.webview)
    WebView webView;
    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_studyzl);
        webView.loadUrl("http://pan.baidu.com/s/1bpBwe5p#list/path=%2F%E8%87%B4%E8%A1%8C%E5%AA%92%E4%BD%93%E5%B9%B3%E5%8F%B0%E9%83%A8%2F%E5%AD%A6%E4%B9%A0%E8%B5%84%E6%96%99%2F%E5%A4%A7%E4%B8%80&parentPath=%2F%E8%87%B4%E8%A1%8C%E5%AA%92%E4%BD%93%E5%B9%B3%E5%8F%B0%E9%83%A8");
    }

    @Override
    public void initDate() {

    }

    @Override
    public void initEvent() {

    }
    public static void startStudyZLActivity(Context context){
        context.startActivity(new Intent(context,StudyZLActivity.class));
    }
}
