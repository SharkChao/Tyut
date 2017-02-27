package com.lenovohit.administrator.tyut.views;

/**
 * Created by Administrator on 2017/2/22.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;


/**
 * 基类BaseActivity用到的TitleBar，用来返回页面和展示当前页内容的Title信息
 *
 * @author mingwei
 *
 */
public class TitleBar extends RelativeLayout implements View.OnClickListener {

    private ImageView mBack;
    private TextView mTitle;
    private TextView mRTBtn;
    BaseActivity mContext;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = (BaseActivity) getContext();// <span style="color:#ff0000;">注释1</span>
    }

    /**
     * 初始化控件
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContext = (BaseActivity) getContext();
        mBack = (ImageView) findViewById(R.id.base_titlebar_back);
        mTitle = (TextView) findViewById(R.id.base_titlebar_title);
        mRTBtn = (TextView) findViewById(R.id.base_titlebar_rtbtn);
        mBack.setOnClickListener(this);
        mTitle.setOnClickListener(this);
        mRTBtn.setOnClickListener(this);
        mTitle.setClickable(false);
    }

    /**
     * 右边的控件是否可见
     */
    public void setRTBtnVisiable(int visiable) {
        mRTBtn.setVisibility(visiable);
    }

    public void setLFBtnVisiable(int visiable) {
        mBack.setVisibility(visiable);
    }
    /**
     * 右边控件的文版
     */
    public void setRTBtnText(String title) {
        mRTBtn.setText(title);
    }


    /**
     * 返回右边控件
     */
    public TextView getRTBtnTextView() {
        return mRTBtn;
    }

    /**
     * 中间控件的点击事件
     */
    public void setTitleClick(boolean bool) {
        mTitle.setClickable(bool);
    }

    /**
     * 中间控件文本</span>
     */
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    /**
     * 中间控件图标</span>
     */
    public void setCompoundDrawables(Drawable drawable) {
        mTitle.setCompoundDrawables(null, null, drawable, null);
    }


    /**
     * 右边控件图标</span>
     */
    public void setTitleRightDrawable(Drawable drawable) {
        mTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }
    public void setTitleLeftDrawable(int sourceId) {
        mBack.setImageResource(sourceId);
    }

    /**
     * 控件的点击情况，分别去调用BaseActivity的方法，
     * 基类被重写后将在子类中调用这些方法</span>
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_titlebar_back:
                mContext.finish();
                break;
            case R.id.base_titlebar_title:
                break;
            case R.id.base_titlebar_rtbtn:
                break;
            default:
                break;
        }
    }
}