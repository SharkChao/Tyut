package com.lenovohit.administrator.tyut.fragment.four;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.BaseActivity;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.domain.BmobUser;
import com.lenovohit.administrator.tyut.utils.SpUtil;
import com.lenovohit.administrator.tyut.utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.lenovohit.administrator.tyut.utils.SpUtil.getParam;

/**
 * Created by Administrator on 2017/4/12.
 * 学生信息设置页面
 */

public class StudentInfoSetActivity extends BaseActivity{
    @Bind(R.id.tvName)
    EditText tvName;
    @Bind(R.id.tvNickname)
    EditText tvNickname;
    @Bind(R.id.rgSex)
    RadioGroup radioGroup;
    @Bind(R.id.btn0)
    RadioButton btn0;
    @Bind(R.id.btn1)
    RadioButton btn1;
    @Bind(R.id.tvHappy)
    EditText tvHappy;
    @Bind(R.id.tvZhuan)
    EditText tvZhuan;
    @Bind(R.id.tvBan)
    EditText tvBan;
    @Bind(R.id.tvCommit)
    Button btnCommit;
    private String sex="男";
    private String objectId;

    @Override
    public void Update(Boolean isConnection) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_studentinfoset);
        tvName.setText(getParam(StudentInfoSetActivity.this,"tvName","")+"");
        tvNickname.setText(getParam(StudentInfoSetActivity.this,"tvNickname","")+"");
        tvHappy.setText(getParam(StudentInfoSetActivity.this,"tvHappy","")+"");
        tvZhuan.setText(getParam(StudentInfoSetActivity.this,"tvZhuan","")+"");
        tvBan.setText(getParam(StudentInfoSetActivity.this,"tvBan","")+"");
        String sex = (String) SpUtil.getParam(StudentInfoSetActivity.this,"tvSex","男");
        if (sex.equals("男")){
            radioGroup.check(R.id.btn0);
        }else {
            radioGroup.check(R.id.btn1);
        }
    }

    @Override
    public void initDate() {

    }

    @Override
    public void initEvent() {
        radioGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=tvName.getText().toString();
                final String nickname=tvNickname.getText().toString();
                final String happy=tvHappy.getText().toString();
                final String zhuanye=tvZhuan.getText().toString();
                final String banji=tvBan.getText().toString();

                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId==R.id.btn0){
                    sex="男";
                }else if (checkedRadioButtonId==R.id.btn1){
                    sex="女";
                }
                BmobQuery query=new BmobQuery("_User");
                query.addWhereEqualTo("username", MyApp.getUser().getAccount());
                query.findObjectsByTable(new QueryListener<JSONArray>() {
                    @Override
                    public void done(JSONArray jsonArray, BmobException e) {
                        if (e==null&&jsonArray.length()>0){
                            try {
                                objectId = jsonArray.getJSONObject(0).getString("objectId");
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                            BmobUser user=new BmobUser();
                            if (!StringUtil.isStrEmpty(name))
                            user.setName(name);
                            if (!StringUtil.isStrEmpty(nickname))
                                user.setNickname(nickname);
                            if (!StringUtil.isStrEmpty(happy))
                                user.setHappy(happy);
                            if (!StringUtil.isStrEmpty(zhuanye))
                                user.setZhuanye(zhuanye);
                            if (!StringUtil.isStrEmpty(banji))
                                user.setBanji(banji);
                            if (!StringUtil.isStrEmpty(objectId))
                            user.update(objectId, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                        Toast.makeText(StudentInfoSetActivity.this,"数据更新完成！",Toast.LENGTH_LONG).show();
                                        finish();
                                    }else {
                                        Toast.makeText(StudentInfoSetActivity.this,"数据更新失败",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });
                SpUtil.setParam(StudentInfoSetActivity.this,"tvName",name);
                SpUtil.setParam(StudentInfoSetActivity.this,"tvNickname",nickname);
                SpUtil.setParam(StudentInfoSetActivity.this,"tvHappy",happy);
                SpUtil.setParam(StudentInfoSetActivity.this,"tvZhuan",zhuanye);
                SpUtil.setParam(StudentInfoSetActivity.this,"tvBan",banji);
                SpUtil.setParam(StudentInfoSetActivity.this,"tvSex",sex);
            }
        });
    }

    public static void startStudentInfoSetActivity(Context context){
        context.startActivity(new Intent(context,StudentInfoSetActivity.class));
    }
}
