package com.lenovohit.administrator.tyut.fragment.three;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.domain.BmobUser;
import com.lenovohit.administrator.tyut.fragment.four.StudentInfoSetActivity;
import com.lenovohit.administrator.tyut.utils.StringUtil;
import com.lenovohit.administrator.tyut.views.CircleImageView;
import com.lenovohit.administrator.tyut.views.PullScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2017/4/12.
 学生信息主界面
 */

public class StudentInfoActivity extends BaseActivity{
    @Bind(R.id.pullScrollview)
    PullScrollView pullScrollView;
    @Bind(R.id.ivBack)
    ImageView imageView;
    @Bind(R.id.ivPic)
    CircleImageView head;
    @Bind(R.id.tvNickname)
    TextView tvNickName;
    @Bind(R.id.tvQian)
    TextView tvQian;
    @Bind(R.id.tvAccount)
    TextView tvAccount;
    @Bind(R.id.tvSex)
    TextView tvSex;
    @Bind(R.id.tvZhuan)
    TextView tvZhuan;
    @Bind(R.id.tvBan)
    TextView tvBan;
    @Bind(R.id.rigBtn)
    ImageButton rigBtn;
    @Bind(R.id.addFriend)
    Button addFridend;
    @Bind(R.id.senMsg)
    Button sendMsg;
    //state用来标识是自己的个人信息，还是查询的同学的个人信息  0标识他本身，1表示查询到的
   static  String picUrl,nickname,qianming,account,sex,zhuanye,banji,state;
    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_studentinfo);
        pullScrollView.setOnTurnListener(new PullScrollView.OnTurnListener() {
            @Override
            public void onTurn() {

            }
        });
        pullScrollView.setHeader(imageView);
    }

    @Override
    public void initDate() {
        if (!StringUtil.isStrEmpty(picUrl)){
            Glide.with(this).load(picUrl).asBitmap().into(head);
        }
        tvNickName.setText(StringUtil.isStrEmpty(nickname)?"这家伙没有昵称":nickname);
        tvQian.setText(StringUtil.isStrEmpty(qianming)?"这家伙很懒，还没有设置个人签名":qianming);
        tvAccount.setText(StringUtil.isStrEmpty(account)?"查无此人":account);
       tvSex.setText(StringUtil.isStrEmpty(sex)?"这家伙是外星人":sex);
        tvZhuan.setText(StringUtil.isStrEmpty(zhuanye)?"暂无信息":zhuanye);
        tvBan.setText(StringUtil.isStrEmpty(banji)?"暂无信息":banji);
        if(!StringUtil.isStrEmpty(sex)&&sex.equals("男")){
            imageView.setImageResource(R.mipmap.back4);
        }
        if (state.equals("0")){
            //本人信息
            rigBtn.setVisibility(View.VISIBLE);
            sendMsg.setVisibility(View.GONE);
            addFridend.setVisibility(View.GONE);
        }else if (state.equals("1")){
            //查询到的信息
            rigBtn.setVisibility(View.GONE);
            sendMsg.setVisibility(View.VISIBLE);
            addFridend.setVisibility(View.VISIBLE);
        }
        if (account.equals(MyApp.getUser().getAccount())){
            //本人信息
            rigBtn.setVisibility(View.VISIBLE);
            sendMsg.setVisibility(View.GONE);
            addFridend.setVisibility(View.GONE);
        }
    }

    @Override
    public void initEvent() {
        rigBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(StudentInfoActivity.this, StudentInfoSetActivity.class),0);
            }
        });
        addFridend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery query=new BmobQuery("_User");
                query.addWhereEqualTo("username",MyApp.getUser().getAccount());
                query.findObjectsByTable(new QueryListener<JSONArray>() {
                    @Override
                    public void done(JSONArray jsonArray, BmobException e) {
                        if (e==null&&jsonArray.length()!=0){
                            try {

                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                               String objectId = (String) jsonObject.get("objectId");
                                BmobUser bmobUser=new BmobUser();
                                List<String>list=new ArrayList<String>();
                                list.add(StringUtil.isStrEmpty(account)?"":account);
                                bmobUser.setLists(list);
                                bmobUser.update(objectId, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e==null){
                                            Toast.makeText(StudentInfoActivity.this, "添加好友成功!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }else {
                            Toast.makeText(StudentInfoActivity.this, "添加好友失败，请重试！！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtil.isStrEmpty(account)){
                    RongIM.getInstance().startPrivateChat(StudentInfoActivity.this,account,"聊天");
                }
            }
        });
    }
    public static void startStudentInfoActivity(Context context,String picUrl,String nickname,String qianming,String account,String sex,String zhuanye,String banji,String state){
        StudentInfoActivity.picUrl=picUrl;
        StudentInfoActivity.nickname=nickname;
        StudentInfoActivity.qianming=qianming;
        StudentInfoActivity.account=account;
        StudentInfoActivity.sex=sex;
        StudentInfoActivity.zhuanye=zhuanye;
        StudentInfoActivity.banji=banji;
        StudentInfoActivity.state=state;
        context.startActivity(new Intent(context, StudentInfoActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0&&resultCode==1){
            data.getStringExtra("name");
           nickname =  data.getStringExtra("nickname");
            qianming = data.getStringExtra("happy");
            zhuanye = data.getStringExtra("zhuanye");
            banji =  data.getStringExtra("banji");
            sex = data.getStringExtra("sex");
        }
    }
}
