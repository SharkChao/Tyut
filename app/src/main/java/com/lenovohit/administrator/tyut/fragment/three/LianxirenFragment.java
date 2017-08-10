package com.lenovohit.administrator.tyut.fragment.three;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.domain.BmobUser;
import com.lenovohit.administrator.tyut.fragment.BaseFragment;
import com.lenovohit.administrator.tyut.views.CircleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by Administrator on 2017/4/11.
 * 常用联系人界面fragment
 */

public class LianxirenFragment extends BaseFragment {
    @Bind(R.id.tvGroup)
    LinearLayout tvGroup;
    @Bind(R.id.tvGongZhong)
    LinearLayout tvGongZhong;
    private Context context;
    private LinearLayout tvSearch;
    @Bind(R.id.lvList)
    ListView lvList;
    private MyAdapter adapter;
    private List<JSONObject> list = new ArrayList<>();

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_lianxiren, null);
        tvSearch = (LinearLayout) view.findViewById(R.id.tvFindFriend);
        return view;
    }

    @Override
    public void initData() {
        if (list.size() <= 0) {
            adapter = new MyAdapter();
            lvList.setAdapter(adapter);
            requestData();
        }
    }

    @Override
    public void initEvent() {
        //添加新朋友
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindFriendActivity.startFindFriendActivity(getActivity());
            }
        });
        //群聊
        tvGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //聊天室
        tvGongZhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().joinChatRoom("110", 0, new RongIMClient.OperationCallback() {
                    @Override
                    public void onSuccess() {
                        //进入聊天室成功
                        Toast.makeText(context, "进入聊天室成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
            }
        });
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String account = list.get(position).optString("username", "");
                if (!account.equals("")) {
                    RongIM.getInstance().startPrivateChat(context, account, "聊天");
                } else {
                    Toast.makeText(context, "融云聊天功能初始化失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void loadData() {

    }

    public LianxirenFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class MyAdapter extends BaseAdapter implements Serializable {

        @Override
        public int getCount() {
            return list.size();
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
            View view = View.inflate(context, R.layout.friend_item, null);
            JSONObject jsonObject = list.get(position);
            String nickname = jsonObject.optString("nickname", "暂无昵称");
            String url = jsonObject.optString("picture", "");
            String account = jsonObject.optString("username", "");
            TextView tvNickname = (TextView) view.findViewById(R.id.tvNickname);
            TextView tvAccount = (TextView) view.findViewById(R.id.tvAccount);
            CircleImageView img = (CircleImageView) view.findViewById(R.id.ivPic);
            tvAccount.setText(account);
            tvNickname.setText(nickname);
            if (!url.equals(""))
                Glide.with(context).load(url).asBitmap().into(img);
            return view;
        }
    }


    public void requestData() {
        BmobQuery<BmobUser> query = new BmobQuery("_User");
        query.addWhereEqualTo("username", MyApp.getUser().getAccount());
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null && jsonArray.length() > 0) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        JSONArray array = (JSONArray) jsonObject.opt("friend");
                        if (array.length() != 0) {
                            list.clear();
                            for (int i = 0; i < array.length(); i++) {
                                String account = (String) array.get(i);
                                BmobQuery query1 = new BmobQuery("_User");
                                query1.addWhereEqualTo("username", account);
                                query1.findObjectsByTable(new QueryListener<JSONArray>() {
                                    @Override
                                    public void done(JSONArray jsonArray, BmobException e) {
                                        if (e == null && jsonArray.length() > 0) {
                                            try {
                                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                                list.add(jsonObject1);
                                            } catch (JSONException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                    }
                                });
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    Toast.makeText(context, "错误" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
