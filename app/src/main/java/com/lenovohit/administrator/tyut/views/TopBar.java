package com.lenovohit.administrator.tyut.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenovohit.administrator.tyut.R;

/**
 * Created by Administrator on 2017-04-25.
 */

public class TopBar extends RelativeLayout{

    private TextView textView;
    private Context context;
    public TopBar(Context context) {
        super(context);
        this.context=context;
        initView();
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }
    //初始化布局
    public void initView(){
        View view=View.inflate(context, R.layout.layout_topbar,null);
        textView = (TextView) view.findViewById(R.id.tvHomeTitle);
    }
    public void setTopText(String text){
        textView.setText(text);
    }
}
