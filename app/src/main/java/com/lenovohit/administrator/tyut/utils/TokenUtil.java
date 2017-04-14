package com.lenovohit.administrator.tyut.utils;

import com.google.gson.Gson;
import com.lenovohit.administrator.tyut.data.TokenData;
import com.lenovohit.administrator.tyut.greendao.Token;
import com.lenovohit.administrator.tyut.net.service.UserService;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/30.
 * 通过传入用户名，服务器给我返回一个token
 */

public class TokenUtil {
    //获取token
    public static void   getToken(UserService service,String userId){
        String App_Key = "vnroth0kvfpho"; //开发者平台分配的 App Key。
        String App_Secret = "Wi4qqgyLipqXhC";
        String Timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳，从 1970 年 1 月 1 日 0 点 0 分 0 秒开始到现在的秒数。
        String Nonce = String.valueOf(Math.floor(Math.random() * 1000000));//随机数，无长度限制。
        String Signature = sha1(App_Secret + Nonce + Timestamp);//数据签名。

        HashMap<String,String>map=new HashMap<>();
        map.put("App-Key", App_Key);
        map.put("Timestamp", Timestamp);
        map.put("Nonce", Nonce);
        map.put("Signature", Signature);
        map.put("Content-Type", "application/x-www-form-urlencoded");

        Observable<ResponseBody> userToken = service.getUserToken("http://api.cn.ronghub.com/user/getToken.json",map, userId, null, null);
        userToken.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                             String token = responseBody.string();
                            System.out.println("在获取token的工具类中"+token);
                            Gson gson=new Gson();
                            Token token1 = gson.fromJson(token, Token.class);
                            TokenData.setToken(token1.getToken());
                            EventUtil.postSticky("http://api.cn.ronghub.com/user/getToken.json");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    //SHA1加密//http://www.rongcloud.cn/docs/server.html#通用_API_接口签名规则
    private static String sha1(String data){
        StringBuffer buf = new StringBuffer();
        try{
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes());
            byte[] bits = md.digest();
            for(int i = 0 ; i < bits.length;i++){
                int a = bits[i];
                if(a<0) a+=256;
                if(a<16) buf.append("0");
                buf.append(Integer.toHexString(a));
            }
        }catch(Exception e){

        }
        return buf.toString();
    }
}
