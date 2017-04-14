package com.lenovohit.administrator.tyut.fragment.three;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.utils.StringUtil;
import com.lenovohit.administrator.tyut.views.CircleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Administrator on 2017/4/12.
 * 添加好友列表
 */

public class FindFriendActivity extends BaseActivity {

    @Bind(R.id.tvNickname)
    TextView tvNickname;
    @Bind(R.id.tvAccount)
    TextView tvAccount;
    @Bind(R.id.ivPic)
    CircleImageView ivPic;
    @Bind(R.id.persion)
    RelativeLayout persion;
    @Bind(R.id.tvShow)
    TextView tvshow;
    private EditText editText;
    private ListView listView;

    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_findfriend);
        editText = (EditText) findViewById(R.id.search_et);
        listView= (ListView) findViewById(R.id.lvList);
    }

    @Override
    public void initDate() {

//        listView.setAdapter(new );
    }

    @Override
    public void initEvent() {
        persion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentInfoActivity.startStudentInfoActivity(FindFriendActivity.this);
            }
        });
    }

    //点击查询，从bmob后端云中获取信息，并且展示出来
    public void search(View v){
        String account = editText.getText().toString();
        if (account.length()!=10){
            Toast.makeText(this,"请输入正确的学号格式",Toast.LENGTH_SHORT).show();
        }else {
            try{
                Integer integer = Integer.valueOf(account);
                BmobUser user=new BmobUser();
                user.setUsername(integer+"");
                BmobQuery query=new BmobQuery("_User");
                query.addWhereEqualTo("username",account);
                query.findObjectsByTable(new QueryListener<JSONArray>() {
                    @Override
                    public void done(JSONArray jsonArray, BmobException e) {
                        if (e==null){
                            if (jsonArray.length()!=0){
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String nickname = jsonObject.getString("nickname");
                                    String username = jsonObject.getString("username");
                                    String url = jsonObject.getString("picture");
                                    persion.setVisibility(View.VISIBLE);
                                    tvshow.setVisibility(View.GONE);
                                    tvNickname.setText(StringUtil.isStrEmpty(nickname)?"该用户暂无昵称":nickname);
                                    tvAccount.setText(username);
                                    if (!StringUtil.isStrEmpty(url)){
                                        Glide.with(FindFriendActivity.this).load(url).asBitmap().into(ivPic);
                                    }
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            }else {
                                Toast.makeText(FindFriendActivity.this, "该用户还未在本应用登陆过", Toast.LENGTH_SHORT).show();
                                persion.setVisibility(View.GONE);
                                tvshow.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            }catch (Exception e){
                Toast.makeText(this, "请输入正确的学号格式", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static void startFindFriendActivity(Context context){
        context.startActivity(new Intent(context,FindFriendActivity.class));
    }
}
