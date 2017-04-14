package com.lenovohit.administrator.tyut.fragment.three.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lenovohit.administrator.tyut.greendao.User;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 *查询学生信息 适配器
 */

public class searchStudentAdapter extends BaseAdapter {
    private Context context;
    private List<User>users;
    public searchStudentAdapter(Context context){

    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
