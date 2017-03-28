package com.lenovohit.administrator.tyut.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.lenovohit.administrator.tyut.R;
import com.lenovohit.administrator.tyut.app.MyApp;
import com.lenovohit.administrator.tyut.greendao.DaoManager;
import com.lenovohit.administrator.tyut.greendao.DaoSession;
import com.lenovohit.administrator.tyut.greendao.User;
import com.lenovohit.administrator.tyut.greendao.UserDao;
import com.lenovohit.administrator.tyut.net.service.UserService;
import com.lenovohit.administrator.tyut.utils.NetworkUtil;
import com.lenovohit.administrator.tyut.utils.SpUtil;
import com.lenovohit.administrator.tyut.views.Alert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;
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

    @Override
    public void initView() {
        //解决键盘遮挡布局
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_login);
        ((MyApp)getApplicationContext()).getActivityComponent().inject(this);
        //判断有没有网络
        boolean connected = NetworkUtil.isConnected(this);
        if (!connected){
            tvConn.setText("请连接网络！");
            tvConn.setVisibility(View.VISIBLE);
        }else {
            tvConn.setVisibility(View.GONE);
        }
         boolean checked = (boolean) SpUtil.getParam(this,"isChecked",false);
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
    }

   @Override
    public void initDate() {
      getYan();
       //展示验证码
       reflashYan(ivYan);
       DaoSession session = DaoManager.getInstance(this).getSession();
       userDao = session.getUserDao();
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
        final Alert alert=new Alert(LoginActivity.this);
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
                     Toast.makeText(LoginActivity.this, "请连接您的网络！", Toast.LENGTH_SHORT).show();
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
                             if (alert != null) {
                                 alert.dismiss();
                                 HomeActivity.StartHomeActivity(LoginActivity.this);
                                 SpUtil.setParam(LoginActivity.this,"username1",account);
                                 setParam(LoginActivity.this,"password1",password);
                                 User user=new User(null,account,password);
                                 MyApp.setUser(user);
                                 insertUser(user);
                                 finish();
                             }
                       }else {
                             alert.dismiss();
                             Toast.makeText(LoginActivity.this, "请先核对您的账号密码,以及验证码", Toast.LENGTH_SHORT).show();
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

        User user1 = userDao.queryBuilder()
                .where(UserDao.Properties.Account.eq(user.getAccount())).build().list().get(0);
        if (user1==null){
            userDao.insert(user);
        }else {
            //说明数据库中有当前用户的实例
        }
    }
}