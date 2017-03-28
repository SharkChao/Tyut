package com.lenovohit.administrator.tyut.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.fragment.one.AcademicFragment;
import com.lenovohit.administrator.tyut.fragment.one.NoticeFragment;
import com.lenovohit.administrator.tyut.fragment.one.SchhoolNewsFragment;
import com.lenovohit.administrator.tyut.fragment.one.SchoolPlayFragment;
import com.viewpagerindicator.TabPageIndicator;


/**
 * Created by SharkChao on 2017/1/13.
 */

public class NewsFragment extends BaseFragment {

    private TabPageIndicator indicator;
    private ViewPager pager;
    private String title[] = {"学校新闻","校园活动","通知公告","学术动态"};
    private Context context;

    public NewsFragment(Context context) {
        this.context = context;
    }

    @Override
    public View initView() {
        View v = View.inflate(context, R.layout.fragment_news, null);
        indicator=(TabPageIndicator)v.findViewById(R.id.tab);
        pager=(ViewPager) v.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment=null;
                switch (position){
                    case 0:
                        fragment=new SchhoolNewsFragment(context);
                        break;
                    case 1:
                        fragment=new SchoolPlayFragment(context);
                        break;
                    case 2:
                        fragment = new NoticeFragment(context);
                        break;
                    case 3:
                        fragment=new AcademicFragment(context);
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return title.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return title[position % title.length];
            }
        });
        indicator.setViewPager(pager);
        return v;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initEvent() {
    }

    @Override
    public void loadData() {

    }

}
