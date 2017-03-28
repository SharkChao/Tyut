package com.lenovohit.administrator.tyut.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13.
 */

public abstract class COBaseAdapter<T> extends BaseAdapter {
    private List<T> dataList;
    private BaseViewHolder baseViewHolder;

    public COBaseAdapter(List<T>dataList){
       if (dataList.size()==0){
           this.dataList=new ArrayList<>();
       }else {
           this.dataList=dataList;
       }
    }
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public T  getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

    //拿到BaseViewHolder
    public void initViewHolder(View convertView,int ResId){
        this.baseViewHolder=BaseViewHolder.getViewHolder(convertView,ResId);
    }
    //拿到convertView
    public View getConvertView(){
        return baseViewHolder.getConvertView();
    }
    //public
    public <T extends View> T getView(int var1) {
        return this.baseViewHolder.getView(var1);
    }
}
