package com.lenovohit.administrator.tyut.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import com.lenovohit.administrator.tyut.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/27.
 */

public class LoginActivity extends BaseActivity {
    @Bind(R.id.etName)
    EditText tvAccount;
    @Bind(R.id.etPassword)
    EditText tvPassword;
    @Bind(R.id.etYan)
    EditText tvYan;
    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public void initDate() {
        String account=tvAccount.getText().toString();
        String password=tvPassword.getText().toString();
        String yan=tvYan.getText().toString();
    }

    @Override
    public void initEvent() {

    }
    public static void startLoginActivity(Context context){
        Intent intent=new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }
}
