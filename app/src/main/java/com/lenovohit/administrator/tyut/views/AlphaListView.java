package com.lenovohit.administrator.tyut.views;

/**
 * Created by Administrator on 2017/2/22.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by ccwxf on 2016/4/7.
 */
public class AlphaListView extends ListView implements AbsListView.OnScrollListener {

    private int height;
    private int itemHeight;

    public AlphaListView(Context context) {
        super(context);
        init();
    }

    public AlphaListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AlphaListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //将所有View的透明度设置为1
        for(int i = 0; i < getChildCount(); i++){
            getChildAt(i).setAlpha(1);
        }
        //得到第一个可见的View
        View v = getChildAt(0);
        if(v != null){
            //得到这个v的高度
            itemHeight = v.getHeight();
            //得到可见部分
            int visiableLength = v.getBottom();
            //得到可见不分部分比例
            float ratio = visiableLength * 1.0f / itemHeight;
            v.setAlpha(ratio);
        }
        //得到最后一个可见的View
        v = getChildAt(visibleItemCount - 1);
        if(v != null){
            //得到这个v的高度
            itemHeight = v.getHeight();
            //得到可见部分
            int visiableLength = height - v.getTop();
            //得到可见不分部分比例
            float ratio = visiableLength * 1.0f / itemHeight;
            v.setAlpha(ratio);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        height = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
