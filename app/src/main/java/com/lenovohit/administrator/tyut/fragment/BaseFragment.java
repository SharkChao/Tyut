package com.lenovohit.administrator.tyut.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by SharkChao on 2017/1/13.
 */

public abstract class BaseFragment extends Fragment {
    public View view;
    private boolean isViewCreated;
    private boolean isUiCreated;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=initView();
        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvent();
    }
    public abstract View initView();
    public abstract void initData();
    public abstract void initEvent();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated=true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            isUiCreated=true;
            lazyLoad();
        }else {
            isUiCreated=false;
        }
    }
    private void lazyLoad(){
        if (isViewCreated&&isUiCreated){
            loadData();
            isUiCreated=false;
            isViewCreated=false;
        }
    }
    public abstract  void loadData();
}
