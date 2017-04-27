package com.lenovohit.administrator.tyut.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.activity.HomeActivity;
import com.lenovohit.administrator.tyut.activity.LoginActivity;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.data.UserData;
import com.lenovohit.administrator.tyut.domain.BmobUser;
import com.lenovohit.administrator.tyut.fragment.four.FeedBackActivity;
import com.lenovohit.administrator.tyut.fragment.three.StudentInfoActivity;
import com.lenovohit.administrator.tyut.fragment.three.StudyZLActivity;
import com.lenovohit.administrator.tyut.utils.SpUtil;
import com.lenovohit.administrator.tyut.utils.StringUtil;
import com.lenovohit.administrator.tyut.views.Alert;
import com.lenovohit.administrator.tyut.views.CircleImageView;
import com.lenovohit.administrator.tyut.views.MyItemOne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;


/**
 * Created by SharkChao on 2017/1/13.
 * 个人主页，包括了其他设置选项，为此，我们需要加一个个人信息页，再添加好友时显示。
 */

public class MeFragment extends BaseFragment {


    private Context context;
    @Bind(R.id.item1)
    MyItemOne myItemOne1;
    @Bind(R.id.item2)
    MyItemOne myItemOne2;
    @Bind(R.id.item3)
    MyItemOne myItemOne3;
    @Bind(R.id.item4)
    MyItemOne myItemOne4;
    @Bind(R.id.item5)
    MyItemOne myItemOne5;
    @Bind(R.id.item6)
    MyItemOne myItemOne6;
    @Bind(R.id.btnUnLogin)
    Button button;
    @Bind(R.id.ivPic)
    CircleImageView imageView;
    @Bind(R.id.name)
    TextView tvName;
    @Bind(R.id.account)
    TextView tvAccount;

    public MeFragment(Context context) {
        this.context = context;
    }
    @Override
    public View initView() {
        View v = View.inflate(context, R.layout.fragment_me, null);
        return v;
    }

    @Override
    public void initData() {
        myItemOne1.setItemInfo(R.mipmap.i_my_switch_patient,"个人信息","");
        myItemOne2.setItemInfo(R.mipmap.i_my_appointment,"学习资料","");
        myItemOne3.setItemInfo(R.mipmap.i_mobile_treatment_history,"信息设置","");
        myItemOne4.setItemInfo(R.mipmap.i_my_address,"换肤","");
        myItemOne5.setItemInfo(R.mipmap.i_my_opinion,"意见反馈","");
        myItemOne6.setItemInfo(R.mipmap.i_my_setting,"设置","");
        //从bmob获取用户头像
        //从bmob获取用户昵称，二者合并到一块

        getNickNameAndPicFromBmob();
        //从本地获取学号
        tvAccount.setText(MyApp.getUser().getAccount());
    }

    @Override
    public void initEvent() {
        //传递参数，进入个人信息界面
        myItemOne1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<com.lenovohit.administrator.tyut.domain.BmobUser> list = new ArrayList<BmobUser>();
                list.clear();
                list.addAll(UserData.getList());
                if (list.size()>0) {
                    com.lenovohit.administrator.tyut.domain.BmobUser user = list.get(0);
                    StudentInfoActivity.startStudentInfoActivity(getActivity(),user.getPicture(),user.getNickname(),user.getHappy(),user.getUsername(),user.getSex(),user.getZhuanye(),user.getBanji(),"0");
                }else {
                    Toast.makeText(context, "暂无用户信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
        myItemOne2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudyZLActivity.startStudyZLActivity(context);
            }
        });
        myItemOne5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedBackActivity.startFeddBackActivity(context);
            }
        });
        //退出当前账户
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Alert alert=new Alert(context);
                alert.builder().setTitle("退出").setTitle("确认退出当前账户吗？")
                        .setCancelable(false)
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SpUtil.setParam(context,"isUser",false);
                                LoginActivity.startLoginActivity(context);
                                ((HomeActivity)context).finish();
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alert.dismiss();
                            }
                        }).show();
            }
        });
        //设置头像，还需要提交到Bmob后端云中。
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryFinal.openGallerySingle(1, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resust) {
                        Bitmap bitmap = BitmapFactory.decodeFile(resust.get(0).getPhotoPath());
                        imageView.setImageBitmap(bitmap);
                        pushPicToBmob(resust.get(0).getPhotoPath());
                    }
                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {
                    }
                });

            }
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getNickNameAndPicFromBmob();
    }

    /**
     * 从bmob获取用户所有信息
     * @return
     */
    public void  getNickNameAndPicFromBmob(){
        BmobQuery query=new BmobQuery("_User");
        query.addWhereEqualTo("username",MyApp.getUser().getAccount());
        query.findObjectsByTable(new QueryListener<JSONArray>() {

            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e==null&&jsonArray.length()!=0){
                    try {

                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        //设置用户昵称
                        String nickname = jsonObject.optString("nickname","");
                        tvName.setText(StringUtil.isStrEmpty(nickname)?"暂无昵称":nickname);
                        //设置用户头像
                        String  url = (String) jsonObject.optString("picture","");
                        com.lenovohit.administrator.tyut.domain.BmobUser user=new com.lenovohit.administrator.tyut.domain.BmobUser();
                        user.setName(StringUtil.isStrEmpty(jsonObject.optString("name",""))?"":jsonObject.optString("name",""));
                        user.setNickname(StringUtil.isStrEmpty(jsonObject.optString("nickname",""))?"":jsonObject.optString("nickname",""));
                        user.setPicture(StringUtil.isStrEmpty(url)?"":url);
                        user.setHappy(StringUtil.isStrEmpty(jsonObject.optString("happy",""))?"":jsonObject.optString("happy",""));
                        user.setZhuanye(StringUtil.isStrEmpty(jsonObject.optString("zhuanye",""))?"":jsonObject.optString("zhuanye",""));
                        user.setBanji(StringUtil.isStrEmpty(jsonObject.optString("banji",""))?"":jsonObject.optString("banji",""));
                       user.setUsername(MyApp.getUser().getAccount());
                        List<com.lenovohit.administrator.tyut.domain.BmobUser>list=new ArrayList<com.lenovohit.administrator.tyut.domain.BmobUser>();
                        list.clear();
                        list.add(user);
                        UserData.setList(list);
                        if (StringUtil.isStrEmpty(url)){
                            Toast.makeText(getActivity(), "还没有头像，请先设置头像！", Toast.LENGTH_SHORT).show();
                        }else {
                            Glide.with(context).load(url).asBitmap().into(imageView);
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }else {
                    Toast.makeText(context, "获取用户信息失败，请重试！！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * 将用户个人头像上传到bmob后端云
     */
    public void pushPicToBmob(final String filename){
        final BmobQuery query=new BmobQuery("_User");
        query.addWhereEqualTo("username",MyApp.getUser().getAccount());
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e==null&&jsonArray.length()>0){
                    try {
                        final String objectid = (String) jsonArray.getJSONObject(0).get("objectId");
                        File file=new File(filename);
                        final BmobFile bmobfile=new BmobFile(file);
                        bmobfile.upload(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    Toast.makeText(getActivity(),"头像上传成功", Toast.LENGTH_SHORT).show();
                                    com.lenovohit.administrator.tyut.domain.BmobUser user=new com.lenovohit.administrator.tyut.domain.BmobUser();
                                    user.setPicture(bmobfile.getFileUrl());
                                    user.update(objectid, new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e==null){
                                                Toast.makeText(getActivity(),"服务器端更新头像成功！", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(getActivity(),e.toString()+",请重试！",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }
}
