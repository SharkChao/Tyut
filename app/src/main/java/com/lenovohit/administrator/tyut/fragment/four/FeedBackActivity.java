package com.lenovohit.administrator.tyut.fragment.four;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.domain.feedback;
import com.lenovohit.administrator.tyut.utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/4/15.
 * 意见反馈功能，对应bmob后端云中 feedbaxk表
 */

public class FeedBackActivity extends BaseActivity {
    private EditText feedText;
    private Button btnFeed;
    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_feedback);
        feedText= (EditText) findViewById(R.id.editfeed);
        btnFeed= (Button) findViewById(R.id.btnFeed);
    }

    @Override
    public void initDate() {

    }

    @Override
    public void initEvent() {
        btnFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feed = feedText.getText().toString();
                if (!StringUtil.isStrEmpty(feed)){
                    sendFeedToBmob(feed,MyApp.getUser().getAccount());
                }else {
                    Toast.makeText(FeedBackActivity.this, "请先填写意见反馈!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 发送消息到bmob后端云
     */
    public void sendFeedToBmob(final String message, final String account){
        BmobQuery query=new BmobQuery("feedback");
        query.addWhereEqualTo("account", MyApp.getUser().getAccount());
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                feedback feedback=new feedback();
                feedback.setMessage(message);
                feedback.setAccount(account);
                if (e==null&&jsonArray.length()>0){
                //说明在数据库中已经有此用户的意见反馈，需要更新。此处不完善，应该是个列表，后续改进
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String objectId = jsonObject.getString("objectId");
                        feedback.update(objectId,new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    Toast.makeText(FeedBackActivity.this,"提交反馈成功!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }else if (e==null&&jsonArray.length()==0){
                    //说明数据库中还没有本人的意见反馈，直接插入一条数据

                    feedback.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                Toast.makeText(FeedBackActivity.this,"提交反馈成功!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    public static void startFeddBackActivity(Context context){
        context.startActivity(new Intent( context,FeedBackActivity.class));
    }
}
