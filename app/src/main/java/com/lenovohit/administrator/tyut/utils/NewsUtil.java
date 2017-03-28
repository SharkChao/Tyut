package com.lenovohit.administrator.tyut.utils;

import android.content.Context;
import android.widget.Toast;

import com.lenovohit.administrator.tyut.data.NewsData;
import com.lenovohit.administrator.tyut.domain.News;
import com.lenovohit.administrator.tyut.domain.Object1;
import com.lenovohit.administrator.tyut.net.service.UserService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/6.
 */

public class NewsUtil {
    public  void setNewsData(final Context context, UserService service, String url, final int index){
        Observable<ResponseBody> schoolNews = service.getSchoolNews(url);
        schoolNews.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "网络有问题，请重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            if (index==1){
//                                System.out.println(responseBody.string());
                                parseHtml1(responseBody.string(),index);
                            }else {
                                parseHtml(responseBody.string(),index);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    /**
     * 解析html，并且把数据封装到newsdata类中,但不包括通知公告界面
     * @param html
     */
    private  void parseHtml(String html,int index) {
        Document document = Jsoup.parse(html);
        Elements elements = document
                .getElementsByAttributeValue("class", "list-card");
        List<News> list = new ArrayList<>();
        for (Element element : elements) {
            News news = new News();
            // Toast.makeText(application, html, 1).show();
            // 拿到图片地址
            Element a = element.getElementsByTag("a").first();
            Element img = a.getElementsByTag("img").first();
            String imgUrl = img.attr("src");

            // 拿到标题和新闻地址
            Element div2 = element.getElementsByAttributeValue(
                    "class", "list-tit").first();
            Element a1 = div2.getElementsByAttributeValue(
                    "class", "mainTitle am-text-left").first();
            String newsUrl = a1.attr("href");
            String title = a1.text();
            // 拿到时间
            Element times = element.getElementsByAttributeValue(
                    "class", "am-fl").first();
            String time = times.text();
            news.setTitle(title);
            news.setDate(time);
            news.setImageUrl(imgUrl);
            news.setUrl(newsUrl);
            list.add(news);
        }
        switch (index){
            case 0:
                NewsData.setList1(list);
                break;
            case 1:
                NewsData.setList2(list);
                break;
            case 2:
                NewsData.setList3(list);
                break;
            case 3:
                NewsData.setList4(list);
                break;
        }
        Object1 object=new Object1();
        object.setIndex(index+"");
        EventUtil.postSticky(object);
    }

    /**
     * 解析通知公告界面
     * @param html
     * @param index
     */
    private  void parseHtml1(String html,int index) {
        Document document = Jsoup.parse(html);
        List<News> list = new ArrayList<>();
        Elements lis = document
                .getElementsByAttributeValue("class", "am-g am-g-collapse am-scrollspy-init am-scrollspy-inview am-animation-slide-bottom");
       for (Element li:lis){
           News news=new News();
           //拿到新闻地址
           Element a = li.getElementsByTag("a").first();
           String href = a.attr("href");
           System.out.println(href+"..........................");

           news.setUrl(href);
           //拿到时间
           Element div = li.getElementsByAttributeValue("class", "date am-u-sm-2").first();
           Element h3 = div.getElementsByTag("h3").first();
           Element span = div.getElementsByTag("span").first();
           String time=span.text()+""+h3.text();
           System.out.println(time+"..........................");

           news.setDate(time);
           //拿到新闻标题和内容
           Element div2 = li.getElementsByAttributeValue("class", "box am-u-sm-10").first();
           Element h2 = div2.getElementsByTag("h2").first();
           Element p = div2.getElementsByTag("p").first();
           news.setTitle(h2.text());
           news.setImageUrl(p.text());
           System.out.println(h2.text()+".........................."+p.text());

           list.add(news);
       }
       NewsData.setList2(list);
        Object1 object=new Object1();
        object.setIndex(index+"");
        EventUtil.postSticky(object);
    }
}
