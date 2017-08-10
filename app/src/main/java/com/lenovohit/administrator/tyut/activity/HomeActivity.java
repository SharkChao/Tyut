package com.lenovohit.administrator.tyut.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.adapter.MyBasePageAdapter;
import com.lenovohit.administrator.tyut.views.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.Bind;
import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * Created by Administrator on 2017/2/27.
 */

public class HomeActivity extends BaseActivity {
    @Bind(R.id.tvConn)
    TextView tvConn;
    @Bind(R.id.rg_bottom)
     RadioGroup rgBottom;
    @Bind(R.id.vp_home)
     ViewPager viewPager;
    @Bind(R.id.tvHomeTitle)
     TextView tvTitle;
    @Bind(R.id.rl_top)
    RelativeLayout relativeLayout;
    @Override
    public void initView() {
        setContentView(R.layout.activity_home);
        viewPager.setAdapter(new MyBasePageAdapter(getSupportFragmentManager(), this));
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void initDate() {
        //检测我的应用是否需要更新
        BmobUpdateAgent.setUpdateOnlyWifi(true);
        BmobUpdateAgent.update(this);
        //将皮肤包放置到sd卡的根目录下
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            CopyAssets("BlackFantacy.skin","");

        }
    }

    @Override
    public void initEvent() {
        rgBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbHome:
                        viewPager.setCurrentItem(0);
                        relativeLayout.setVisibility(View.VISIBLE);
                        tvTitle.setText("新闻");
                        break;
                    case R.id.rbHospal:
                        viewPager.setCurrentItem(1);
                        relativeLayout.setVisibility(View.VISIBLE);
                        tvTitle.setText("教务管理");
                        break;
                    case R.id.rbUse:
                        viewPager.setCurrentItem(2);
                        relativeLayout.setVisibility(View.VISIBLE);
                        tvTitle.setText("聊天");
                        relativeLayout.setVisibility(View.GONE);
                        break;
                    case R.id.rbMy:
                        viewPager.setCurrentItem(3);
                        tvTitle.setText("我的");
                        relativeLayout.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    @Override
    public void Update(Boolean isConnection) {
        if (isConnection){
            tvConn.setText("请连接网络！");
            tvConn.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.INVISIBLE);
        }else {
            tvConn.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }
    public static  void StartHomeActivity(Context context){
        context.startActivity(new Intent(context,HomeActivity.class));
    }

    @Override
    public void onBackPressed() {
        final Alert alert=new Alert(this);
        alert.builder().setTitle("退出").setMsg("确认退出吗?").setPositiveButton("确认",new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                finish();
                cn.bmob.v3.BmobUser.logOut();
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        }).show();
    }
    private void CopyAssets(String assetDir,String dir) {
        String[] files;
        try {
            files = this.getResources().getAssets().list(assetDir);
        } catch (IOException e1) {
            return;
        }
        File mWorkingPath = new File(dir);
        File sdCardDir = Environment.getExternalStorageDirectory();//获取SDCard目录
        File saveFile = new File(sdCardDir, "BlackFantacy.skin");
        //if this directory does not exists, make one.
        if (!mWorkingPath.exists()) {
            if (!mWorkingPath.mkdirs()) {

            }
        }

        for (int i = 0; i < files.length; i++) {
            try {
                String fileName = files[i];
                //we make sure file name not contains '.' to be a folder.
                if (!fileName.contains(".")) {
                    if (0 == assetDir.length()) {
                        CopyAssets(fileName, dir + fileName + "/");
                    } else {
                        CopyAssets(assetDir + "/" + fileName, dir + fileName + "/");
                    }
                    continue;
                }
                File outFile = new File(mWorkingPath, fileName);
                if (saveFile.exists())
                    saveFile.delete();
                InputStream in = null;
                if (0 != assetDir.length())
                    in = getAssets().open(assetDir + "/" + fileName);
                else
                    in = getAssets().open(fileName);
                OutputStream out = new FileOutputStream(saveFile);

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                in.close();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
