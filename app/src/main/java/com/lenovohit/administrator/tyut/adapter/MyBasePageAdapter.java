package com.lenovohit.administrator.tyut.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lenovohit.administrator.tyut.fragment.LiaoTianFragment;
import com.lenovohit.administrator.tyut.fragment.MeFragment;
import com.lenovohit.administrator.tyut.fragment.JiaoWuFragment;
import com.lenovohit.administrator.tyut.fragment.NewsFragment;

/**
 * Created by SharkChao on 2017/1/13.
 */

public class MyBasePageAdapter extends FragmentPagerAdapter{
    private Context context;
    public MyBasePageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
       switch(position){
           case 0:
               fragment=new NewsFragment(context);
               break;
           case 1:
               fragment=new JiaoWuFragment(context);
               break;
           case 2:
               fragment=new LiaoTianFragment(context);
               break;
           case 3:
               fragment=new MeFragment(context);
               break;
           default:
               break;
       }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
