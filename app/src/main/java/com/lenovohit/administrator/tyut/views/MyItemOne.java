package com.lenovohit.administrator.tyut.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lenovohit.administrator.tyut.R;

/**
 * Created by Administrator on 2017/3/29.
 */

public class MyItemOne extends LinearLayout {

    private ImageView ivRowOneICO;
    private TextView txRowOneContent;
    private TextView txRowOneSelect;

    public MyItemOne(Context context) {
        super(context);
    }

    public MyItemOne(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.lx_list_row_one, this);
        ivRowOneICO = (ImageView) findViewById(R.id.ivRowOneICO);
        txRowOneContent = (TextView) findViewById(R.id.txRowOneContent);
        txRowOneSelect = (TextView) findViewById(R.id.txRowOneSelect);
    }

    /**
     * 设置每一条的数据
     */
    public void setItemInfo(int resid,String content,String selectName){
        if (resid == 0){
            ivRowOneICO.setVisibility(GONE);
        }else{
            ivRowOneICO.setVisibility(VISIBLE);
            ivRowOneICO.setImageResource(resid);
        }

        if (content.equals("")){
            txRowOneContent.setVisibility(GONE);
        }else{
            txRowOneContent.setVisibility(VISIBLE);
            txRowOneContent.setText(content);
        }

        if (selectName.equals("")){
            txRowOneSelect.setVisibility(GONE);
        }else{
            txRowOneSelect.setVisibility(VISIBLE);
            txRowOneSelect.setText(selectName);
        }
    }
}
