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
import com.lenovohit.administrator.tyut.data.UserData;
import com.lenovohit.administrator.tyut.utils.StringUtil;
import com.lenovohit.administrator.tyut.views.CircleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        persion.setVisibility(View.GONE);
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
                List<com.lenovohit.administrator.tyut.domain.BmobUser> findList = UserData.getFindList();
                if (findList.size()>0){
                    com.lenovohit.administrator.tyut.domain.BmobUser user = findList.get(0);
                    StudentInfoActivity.startStudentInfoActivity(FindFriendActivity.this,user.getPicture(),user.getNickname(),user.getHappy(),user.getUsername(),user.getSex(),user.getZhuanye(),user.getBanji(),"1");
                }else {
                    Toast.makeText(FindFriendActivity.this,"暂无用户信息",Toast.LENGTH_SHORT).show();
                }
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
                                    persion.setVisibility(View.VISIBLE);
                                    tvshow.setVisibility(View.GONE);

                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    //设置用户昵称
                                    String nickname = jsonObject.optString("nickname","");
                                    //设置用户账号
                                    String account = jsonObject.optString("username","");
                                    tvNickname.setText(StringUtil.isStrEmpty(nickname)?"暂无昵称":nickname);
                                    tvAccount.setText(StringUtil.isStrEmpty(account)?"学号为空":account);
                                    //设置用户头像
                                    String  url = (String) jsonObject.optString("picture","");
                                    com.lenovohit.administrator.tyut.domain.BmobUser user=new com.lenovohit.administrator.tyut.domain.BmobUser();
                                    user.setName(StringUtil.isStrEmpty(jsonObject.optString("name",""))?"":jsonObject.optString("name",""));
                                    user.setNickname(StringUtil.isStrEmpty(jsonObject.optString("nickname",""))?"":jsonObject.optString("nickname",""));
                                    user.setPicture(StringUtil.isStrEmpty(url)?"":url);
                                    user.setHappy(StringUtil.isStrEmpty(jsonObject.optString("happy",""))?"":jsonObject.optString("happy",""));
                                    user.setZhuanye(StringUtil.isStrEmpty(jsonObject.optString("zhuanye",""))?"":jsonObject.optString("zhuanye",""));
                                    user.setBanji(StringUtil.isStrEmpty(jsonObject.optString("banji",""))?"":jsonObject.optString("banji",""));
                                    user.setUsername(StringUtil.isStrEmpty(account)?"":account);
                                    List<com.lenovohit.administrator.tyut.domain.BmobUser> list=new ArrayList<com.lenovohit.administrator.tyut.domain.BmobUser>();
                                    list.clear();
                                    list.add(user);
                                    UserData.setFindList(list);
                                    if (StringUtil.isStrEmpty(url)){
                                        Toast.makeText(FindFriendActivity.this, "还没有头像，请先设置头像！", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(FindFriendActivity.this,url,Toast.LENGTH_LONG).show();
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
