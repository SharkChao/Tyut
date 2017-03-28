package com.lenovohit.administrator.tyut.adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/3/13.
 */

public abstract class CommenAdapter<T> extends COBaseAdapter{


    public CommenAdapter(List dataList) {
        super(dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        initViewHolder(convertView,inflateView());
        convertView(position,getItem(position));
        return getConvertView();
    }
    public abstract void convertView(int position,Object var);
    public abstract  int inflateView();


}
