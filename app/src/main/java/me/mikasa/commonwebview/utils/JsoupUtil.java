package me.mikasa.commonwebview.utils;

import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import me.mikasa.commonwebview.bean.GuoKe;


/**
 * Created by mikasacos on 2018/9/8.
 */

public class JsoupUtil {
    private static JsoupUtil sJsoupUtil;
    public static JsoupUtil getInstance(){
        if (sJsoupUtil ==null){
            synchronized (JsoupUtil.class){
                if (sJsoupUtil ==null){
                    sJsoupUtil =new JsoupUtil();
                }
            }
        }
        return sJsoupUtil;
    }

    public List<GuoKe>getGuoKe(String s){
        List<GuoKe>beanList=new ArrayList<>();
        Document doc=Jsoup.parse(s);
        Element e=doc.getElementById("waterfall");
        Elements es=e.select("div.article");
        Log.i("oputil",String.valueOf(es.size()));
        for (Element ee:es){
            GuoKe bean=new GuoKe();
            if (!TextUtils.isEmpty(ee.child(0).attr("href"))){
                //存在label
                bean.setLabel(ee.child(0).text());
                bean.setTitle(ee.child(1).text());
                bean.setAuthor(ee.child(2).child(0).text());
                bean.setComment(ee.child(2).child(2).text());
                bean.setImgUrl(ee.child(3).child(0).attr("src"));
                bean.setDesc(ee.child(4).text());
                bean.setLink(ee.child(1).child(0).attr("href"));
            }else {
                bean.setTitle(ee.child(0).text());
                bean.setAuthor(ee.child(1).child(0).text());
                bean.setComment(ee.child(1).child(2).text());
                bean.setImgUrl(ee.child(2).child(0).attr("src"));
                bean.setDesc(ee.child(3).text());
                bean.setLink(ee.child(0).child(0).attr("href"));
            }
            beanList.add(bean);
        }
        return beanList;
    }
}
