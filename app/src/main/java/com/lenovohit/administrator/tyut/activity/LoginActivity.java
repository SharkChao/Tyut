package com.lenovohit.administrator.tyut.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.model.GlideUrl;
import com.jock.lib.HighLight;
import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.data.TokenData;
import com.lenovohit.administrator.tyut.greendao.DaoManager;
import com.lenovohit.administrator.tyut.greendao.DaoSession;
import com.lenovohit.administrator.tyut.greendao.Token;
import com.lenovohit.administrator.tyut.greendao.TokenDao;
import com.lenovohit.administrator.tyut.greendao.User;
import com.lenovohit.administrator.tyut.greendao.UserDao;
import com.lenovohit.administrator.tyut.net.service.UserService;
import com.lenovohit.administrator.tyut.utils.EventUtil;
import com.lenovohit.administrator.tyut.utils.MD5Util;
import com.lenovohit.administrator.tyut.utils.NetworkUtil;
import com.lenovohit.administrator.tyut.utils.SpUtil;
import com.lenovohit.administrator.tyut.utils.TokenUtil;
import com.lenovohit.administrator.tyut.views.Alert;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import okhttp3.Cookie;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

import static com.lenovohit.administrator.tyut.utils.SpUtil.getParam;
import static com.lenovohit.administrator.tyut.utils.SpUtil.setParam;


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
    @Bind(R.id.ivYan)
    ImageView ivYan;
    @Bind(R.id.btnLogin)
    Button button;
    @Bind(R.id.tvConn)
    TextView tvConn;
    @Bind(R.id.checkbox)
    CheckBox checkBox;
    @Inject
    UserService service;
    Cookie cookie;
    private GlideUrl url;
    private UserDao userDao;
    private User user1;
    private TokenDao tokenDao;
    private String token;
    private Alert alert;
    private UserInfo userInfo;

    @Override
    public void initView() {
        //解决键盘遮挡布局
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_login);
        ((MyApp)getApplicationContext()).getActivityComponent().inject(this);
        EventUtil.register(this);
        //判断有没有网络
        boolean connected = NetworkUtil.isConnected(this);
        if (!connected){
            tvConn.setText("请连接网络！");
            tvConn.setVisibility(View.VISIBLE);
        }else {
            tvConn.setVisibility(View.GONE);
        }
         boolean checked = (boolean) SpUtil.getParam(this,"isChecked",false);
        boolean isfirst = (boolean) SpUtil.getParam(this,"isFirstOne",true);
        if (isfirst==true){
            showMengBan();
        }
        //是否保存账号密码
        if (checked){
            tvAccount.setText(getParam(LoginActivity.this,"name","1")+"");
           tvPassword.setText(SpUtil.getParam(this,"password","1")+"");
            checkBox.setChecked(checked);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                    SpUtil.setParam(LoginActivity.this,"name",tvAccount.getText().toString()+"");
                    SpUtil.setParam(LoginActivity.this,"password",tvPassword.getText().toString()+"");
                    SpUtil.setParam(LoginActivity.this,"isChecked",isChecked);
                }
            }
        });
        SpUtil.setParam(this,"isFirstOne",false);
    }

   @Override
    public void initDate() {
       getYan();
       //展示验证码
//       reflashYan(ivYan);
       DaoSession session = DaoManager.getInstance(this).getSession();
       userDao = session.getUserDao();
       tokenDao = session.getTokenDao();
   }

    @Override
    public void initEvent() {
        ivYan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getYan();
            }
        });
    }

    //展示蒙版
    public void showMengBan() {
                HighLight highLight = new HighLight(LoginActivity.this)
                        .anchor(findViewById(R.id.screen))
                        .addHighLight(R.id.ivYan, R.layout.info_down, new HighLight.OnPosCallback() {

                            @Override
                            public void getPos(float v, float v1, RectF rectF, HighLight.MarginInfo marginInfo) {
                                marginInfo.leftMargin = v - rectF.width() / 2;
                                marginInfo.bottomMargin = v1+200;
                            }
                        });
                highLight.show();
    }
    /**
     * 拿到验证码图片，得放到第一步之后,因为要获得cookie
     */
    public void getYan(){
        Observable<ResponseBody>getImage=service.getImage();
        getImage.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(LoginActivity.this, "验证码刷新失败，请检查网络！", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNext(ResponseBody responseBody) {
                         InputStream is=null;
                            try {
                                is = responseBody.byteStream();
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    if (is != null) {
                                        is.close();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (is != null){
                                Bitmap bitmap = BitmapFactory.decodeStream(is);
                                ivYan.setImageBitmap(bitmap);
                            }
                    }
                });
    }

    /**
     * 填写好账号密码后，登录教务处
     */
    @OnClick(R.id.btnLogin)
    public void Login() {
//        List<Cookie> list = new PersistentCookieStore(LoginActivity.this).get(HttpUrl.parse(Constant.LoginUrl));
//        Toast.makeText(this, list.get(0).value()+"", Toast.LENGTH_SHORT).show();
        final String account=tvAccount.getText().toString();
        final String password=tvPassword.getText().toString();
        String yan=tvYan.getText().toString();
        alert = new Alert(LoginActivity.this);
        service.Login(account,password,yan)
             .subscribeOn(Schedulers.newThread())
             .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        alert.builder().setCancelable(false).setMsg("正在登陆中，请稍等").setTitle("登录").show();
                    }
                })
             .subscribe(new Subscriber<ResponseBody>() {
                 @Override
                 public void onCompleted() {
                 }
                 @Override
                 public void onError(Throwable e) {
                     //如果登录教务处失败，弹出失败原因
                     Log.d("tag1",e.toString());
                     Toast.makeText(LoginActivity.this, "请连接您的网络！"+e, Toast.LENGTH_SHORT).show();
                     alert.dismiss();
                 }
                 @Override
                 public void onNext(ResponseBody responseBody) {
                     //首先通过responseBody拿到标题，如果为教务管理系统则说明登陆成功
                     String title = null;
                     try {
                         title = parseHtml(responseBody.string());
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                     if (title!=null){
                         if (title.equals("学分制综合教务")){
                                 final String account=tvAccount.getText().toString();
                                 String password=tvPassword.getText().toString();
                                 SpUtil.setParam(LoginActivity.this,"name",account);
                                 setParam(LoginActivity.this,"password", password);
                                 //吧用户插入到数据库中
                                 final User user=new User(null,account,MD5Util.encrypt(password));
                                 insertUser(user);
                                //给当前应用设置用户
                                 User user2 = queryUser(user);
                                 Toast.makeText(LoginActivity.this, user2.getAccount(), Toast.LENGTH_SHORT).show();
                                 MyApp.setUser(user2);
                                 SpUtil.setParam(LoginActivity.this,"isUser",true);
                                //有个先后步骤，现在教务处登录成功，在bmob后端云登录成功，最后在融云登录成功，如果有一个失败了，则登陆失败。

                             final BmobUser bmobUser=new BmobUser();
                             bmobUser.setUsername(account);
                             bmobUser.setPassword(MD5Util.encrypt(password));
                             BmobQuery query=new BmobQuery("_User");
                             query.addWhereEqualTo("username",account);
                             query.findObjectsByTable(new QueryListener<JSONArray>() {
                                 @Override
                                 public void done(JSONArray jsonArray, BmobException e) {
                                     if (e==null&&jsonArray.length()>0){
                                         Toast.makeText(LoginActivity.this, "Bmob后端云由当前用户，可以直接登录", Toast.LENGTH_SHORT).show();
                                         bmobUser.login(new SaveListener<BmobUser>() {

                                             @Override
                                             public void done(BmobUser bmobUser, BmobException e) {
                                                 if (e==null){
                                                     Toast.makeText(LoginActivity.this,"Bmob后端云登陆成功！",Toast.LENGTH_LONG).show();
                                                     //判断有没有token，有的话连接上才可以跳转
                                                     List<Token> tokens1 = queryToken(user);
                                                     if (tokens1.size()==0){
                                                         TokenUtil.getToken(service, user.getAccount());
                                                     }else {
                                                         System.out.println(tokens1.get(0).getUserId()+"-------------------"+tokens1.get(0).getToken());
                                                         LoginRongIM(tokens1.get(0).getToken());
                                                     }
                                                 }else {
                                                     Toast.makeText(LoginActivity.this,"Bmob后端云登陆失败，请重新登陆"+e.getMessage(),Toast.LENGTH_LONG).show();
                                                 }
                                             }
                                         });
                                     }else if (e==null&&jsonArray.length()<=0){
                                         Toast.makeText(LoginActivity.this, "Bmob后端云还没有当前用户，先登录", Toast.LENGTH_SHORT).show();
                                         bmobUser.signUp(new SaveListener<BmobUser>() {
                                             @Override
                                             public void done(BmobUser bmobUser, BmobException e) {
                                                 if (e==null){
                                                     Toast.makeText(LoginActivity.this,"第一次进入bmob后端云注册成功！",Toast.LENGTH_LONG).show();
                                                     //判断有没有token，有的话连接上才可以跳转
                                                     List<Token> tokens1 = queryToken(user);
                                                     if (tokens1.size()==0){
                                                         TokenUtil.getToken(service, user.getAccount());
                                                     }else {
                                                         System.out.println(tokens1.get(0).getUserId()+"-------------------"+tokens1.get(0).getToken());
                                                         LoginRongIM(tokens1.get(0).getToken());
                                                     }

                                                 }else {
                                                     Toast.makeText(LoginActivity.this,"第一次进入bmob后端云注册失败，请重试！",Toast.LENGTH_LONG).show();
                                                 }
                                             }
                                         });
                                     }
                                     else if (e!=null){
                                         Toast.makeText(LoginActivity.this, "登录bmob后端云失败，请重新登陆", Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             });
                       }else {
                             alert.dismiss();
                             Toast.makeText(LoginActivity.this, "如账号密码无误，请再次刷新验证码", Toast.LENGTH_SHORT).show();
                         }
                     }
                 }
             });
    }
    /**
     * 显示蒙版新手引导，点击验证码刷新
     */
    public void reflashYan(ImageView view){
        new MaterialIntroView.Builder(this)
                .enableDotAnimation(true)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(500)
                .enableFadeAnimation(true)
                .performClick(true)
                .setInfoText("图片看不清？点击换一张")
                .setShape(ShapeType.RECTANGLE)
                .setTarget(view)
                .setUsageId("intro_card") //THIS SHOULD BE UNIQUE ID
                .show();
    }


    /**
     * 如果网络状态发生变化，提示给activity
     * @param isConnection
     */
    @Override
    public void Update(Boolean isConnection) {

        if (!isConnection&&BaseActivity.currentActivity==this){
            tvConn.setText("请连接网络！");
            tvConn.setVisibility(View.VISIBLE);
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("失败")
                    .setMessage("检测网络未打开,现在去开吗?")
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    arg0.cancel();
                                }
                            })
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    // 跳转到设置界面
                                    Intent intent = new Intent(
                                            "android.settings.WIRELESS_SETTINGS");
                                    startActivity(intent);
                                    finish();
                                }
                            }).show();

        }else {
            tvConn.setVisibility(View.GONE);
            getYan();
        }
    }

    /**
     * 登录成功后，需要我们解析返回的html的标题
     */
    public String  parseHtml(String html){
        Document document = Jsoup.parse(html);
        String title = document.title();
        return title;
    }

    public static void startLoginActivity(Context context){
        Intent intent=new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * 将user插入到user表中，我们需要先判断有没有account=“”的数据，
     * 有的话就不需要插入了。
     * @param user
     */
    public void insertUser(User user){

        List<User> list = userDao.queryBuilder()
                .where(UserDao.Properties.Account.eq(user.getAccount())).build().list();

        if (list.size()==0){
            userDao.insert(user);
            Toast.makeText(this, "插入数据库成功！", Toast.LENGTH_LONG).show();
        }else {
            //说明数据库中有当前用户的实例
            Toast.makeText(this, "数据库中已有数据", Toast.LENGTH_LONG).show();
            User user2 = queryUser(user);
            userDao.update(user2);
        }
    }
    public User queryUser(User user){
        List<User> list = userDao.queryBuilder()
                .where(UserDao.Properties.Account.eq(user.getAccount())).build().list();
        if (list.size()==0){
            Toast.makeText(this,"数据库中没有当前用户",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "数据库中有当前用户，直接拿来用", Toast.LENGTH_SHORT).show();
            user1 = list.get(0);
        }
        return user1;
    }
    public void insertAndUpdateToken(User user,Token token){
        List<Token> tokens = queryToken(user);
        if (tokens.size()==0){
            tokenDao.insert(token);
        }else {
            tokenDao.update(tokens.get(0));
        }
    }
    public List<Token> queryToken(User user){
        List<Token> list = tokenDao.queryBuilder().where(TokenDao.Properties.UserId.eq(user.getAccount())).build().list();
        return list;
    }

    public void LoginRongIM(String token) {
        if (TextUtils.isEmpty(token)){
            Toast.makeText(LoginActivity.this,"token为空",Toast.LENGTH_SHORT).show();
        }else {
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                //token已经过期
                @Override
                public void onTokenIncorrect() {

                }

                @Override
                public void onSuccess(String s) {
                    Toast.makeText(LoginActivity.this, "当前用户登陆成功,已经成功连接到融云服务器", Toast.LENGTH_SHORT).show();
                    if (alert!=null){
                        alert.dismiss();
                    }
                    RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                        @Override
                        public UserInfo getUserInfo(String s) {
                            return findUserById(s);
                        }
                    },true);
                    HomeActivity.StartHomeActivity(LoginActivity.this);
                    finish();
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    if (alert!=null){
                        Toast.makeText(LoginActivity.this, "当前用户登录失败,请重新登陆！", Toast.LENGTH_SHORT).show();
                        alert.dismiss();
                    }
                }
            });
        }
    }

    /**
     * 接收到token以后，判断此用户在数据库中有没有数据，没有的话插入
     * @param value
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getToken(String value){
        if (value.equals("http://api.cn.ronghub.com/user/getToken.json")){
            token= TokenData.getToken();
            User user=MyApp.getUser();
            if (TextUtils.isEmpty(token)){
                Toast.makeText(LoginActivity.this,"获取token失败，请重新连接",Toast.LENGTH_SHORT).show();
            }else {
                System.out.println("loginActivity"+token);
                //获取token,存入数据库
                Token tokens=new Token(user.getAccount(),token);
                insertAndUpdateToken(user,tokens);
                LoginRongIM(tokens.getToken());
            }
        }
    }
    public UserInfo findUserById(final String userId){
        BmobQuery query=new BmobQuery("_User");
        query.addWhereEqualTo("username", userId);
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e==null&&jsonArray.length()>0){
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String nickname = jsonObject.optString("nickname", "");
                        String url=jsonObject.optString("picture","");
                        Uri uri=Uri.parse(url);
                        userInfo = new UserInfo(userId, nickname, uri);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        if (userInfo==null){
            return null;
        }
        return userInfo;
    }
}