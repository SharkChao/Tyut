package com.lenovohit.administrator.tyut.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

import com.lenovohit.administrator.tyut.activity.BaseActivity;

/**
 * Created by Administrator on 2017/3/13.
 * 我们需要了解到ViewHolder的作用。
 * 提供view中的各个控件，并且只需要findviewById一次就可以了
 * 所以  viewholder需要我们提供 view，以及一个arraylist包含每个小控件。
 */

public class BaseViewHolder {
    private SparseArray<View> mViews = new SparseArray();
    private View mConvertView;

    private BaseViewHolder(int ResId) {
        this.mConvertView = LayoutInflater.from(BaseActivity.currentActivity).inflate(ResId, null, false);
        this.mConvertView.setTag(this);
    }

    public static BaseViewHolder getViewHolder(View convertView,int ResId) {
        return convertView==null?new BaseViewHolder(ResId):(BaseViewHolder) convertView.getTag();
    }

    public <T extends View> T getView(int var1) {
        View var2;
        if ((var2 = (View) this.mViews.get(var1)) == null) {
            var2 = this.mConvertView.findViewById(var1);
            this.mViews.put(var1, var2);
        }else {
            var2=this.mViews.get(var1);
        }
        return (T) var2;
    }

    public View getConvertView() {
        return this.mConvertView;
    }
}

