package com.lenovohit.administrator.tyut.fragment;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.fragment.three.LianxirenFragment;
import com.lenovohit.administrator.tyut.greendao.TokenDao;
import com.lenovohit.administrator.tyut.views.SwitchButton;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;


/**
 * Created by SharkChao on 2017/1/13.
 * 聊天界面
 */

public class LiaoTianFragment extends BaseFragment {
    //回话列表
    private ConversationListFragment mConversationFragment = null;
    private Context context;
    private TokenDao tokenDao;
    private SwitchButton switchButton;
    private Fragment fragment;

    public LiaoTianFragment(Context context){
        this.context=context;
    }
    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_liaotians, null);
        switchButton= (SwitchButton) view.findViewById(R.id.switchButton);
        return view;
    }

    @Override
    public void initData() {
        Fragment fragment = initConversationList();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment,fragment).commit();
    }

    @Override
    public void initEvent() {
        switchButton.setOnChangeListener(new SwitchButton.OnChangeListener() {
            @Override
            public void onChange(int position) {
                if (position==0){
                    Fragment fragment = initConversationList();
                    FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment,fragment).commit();
                }else if (position==1){
                    if (fragment==null) fragment = new LianxirenFragment(getActivity());
                    FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment,fragment).commit();
                }
            }
        });
    }

    @Override
    public void loadData() {
    }
    //会话列表的初始化
    private Fragment  initConversationList(){
        if (mConversationFragment == null) {
            ConversationListFragment listFragment = new ConversationListFragment();
            Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//讨论组
                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                    .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//系统
                    .build();
            listFragment.setUri(uri);
            return  listFragment;
        } else {
            return  mConversationFragment;
        }
    }

}
