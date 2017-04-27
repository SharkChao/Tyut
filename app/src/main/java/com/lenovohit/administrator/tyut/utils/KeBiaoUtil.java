package com.lenovohit.administrator.tyut.utils;

import android.content.Context;
import android.widget.Toast;

import com.lenovohit.administrator.tyut.activity.LoginActivity;
import com.lenovohit.administrator.tyut.app.AppManager;
import com.lenovohit.administrator.tyut.constant.Constant;
import com.lenovohit.administrator.tyut.data.KeBiaoData;
import com.lenovohit.administrator.tyut.greendao.KeBiaoEntity;
import com.lenovohit.administrator.tyut.net.service.UserService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017-04-21.
 */

public class KeBiaoUtil {
    //拿到本學期成绩
    public static void getKeBiao(UserService service, final Context context) {
        Observable<ResponseBody> currentScore = service.getKeBiao();
        currentScore.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "身份信息过期，请重新登录" + e, Toast.LENGTH_SHORT).show();
                        AppManager appManager = AppManager.getAppManager();
                        appManager.finishAllActivity();
                        LoginActivity.startLoginActivity(context);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            parseHtml(string);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    public static void parseHtml(String html){
        Document document = Jsoup.parse(html);
        Element table = document.getElementsByAttributeValue("id", "user").get(0);
        //一共是13个，0-12   0,4, 5  9，10有问题   4和9可以不解析，0,5,10,去掉前两个，其他的去掉前一个
        Elements bgcolor = table.getElementsByAttributeValue("bgcolor", "#FFFFFF");
        List<KeBiaoEntity>list=new ArrayList<>();
        for (int i=0;i<bgcolor.size();i++){
                Elements tds = bgcolor.get(i).select("td");
            if (tds.size()!=0){
                if (i!=0&&i!=5&&i!=10){
                    for (int j=0;j<tds.size();j++){
                        if (j!=0){
                            String val = tds.get(j).text().trim();
                            if (!StringUtil.isStrEmpty(val)){
                                KeBiaoEntity entity=new KeBiaoEntity();
                                entity.setValue(val);
                                entity.setXingqi(j+"");
                                if (i>=0&&i<=3){
                                    entity.setState(i+1+"");
                                }else if (i>=5&&i<=8){
                                    entity.setState(i+"");
                                }else if (i>=10&&i<=12){
                                    entity.setState(i-1+"");
                                }
                                list.add(entity);
                            }
                        }
                    }
                }else {
                    for (int j=0;j<tds.size();j++){
                        if (j!=0&&j!=1){
                            String val = tds.get(j).text().trim();
                            if (!StringUtil.isStrEmpty(val)){
                                KeBiaoEntity entity=new KeBiaoEntity();
                                entity.setValue(val);
                                entity.setXingqi(j+"");
                                if (i>=0&&i<=3){
                                    entity.setState(i+1+"");
                                }else if (i>=5&&i<=8){
                                    entity.setState(i+"");
                                }else if (i>=10&&i<=12){
                                    entity.setState(i-1+"");
                                }
                                list.add(entity);
                            }
                        }
                    }
                }
            }
        }
        KeBiaoData.setList(list);
        EventUtil.postSticky(Constant.KeBiaoQuery);
    }
}
