package com.lenovohit.administrator.tyut.fragment.three;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.fragment.BaseFragment;

/**
 * Created by Administrator on 2017/4/11.
 * 常用联系人界面fragment
 */

public class LianxirenFragment extends BaseFragment{
    private Context context;
    private LinearLayout tvSearch;

    @Override
    public View initView() {
        View view=View.inflate(context, R.layout.fragment_lianxiren,null);
        tvSearch = (LinearLayout) view.findViewById(R.id.tvFindFriend);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindFriendActivity.startFindFriendActivity(getActivity());
            }
        });
    }

    @Override
    public void loadData() {

    }
    public LianxirenFragment(Context context){
        this.context=context;
    }
}
