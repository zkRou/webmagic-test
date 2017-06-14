package com.kris.webmagic;

import com.alibaba.fastjson.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Kairou Zeng on 2017/6/14.
 */
public class DownloadBaikePicture implements PageProcessor{

    private Site site = Site.me();

    @Override
    public void process(Page page) {

        System.out.println(page.getHtml().xpath("//div[@class='summary-pic']//img/@src").get());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String key = "中国石油化工股份有限公司";
        String url = "http://baike.baidu.com/item/" + key;
        Spider.create(new DownloadBaikePicture())
                .addUrl(url)
                .run();

    }
}
