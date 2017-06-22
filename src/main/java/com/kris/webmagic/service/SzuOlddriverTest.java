package com.kris.webmagic.service;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Kris on 2017/6/11.
 */
public class SzuOlddriverTest implements PageProcessor{

    private Site site = Site.me();

    @Override
    public void process(Page page) {
        System.out.println(page.getHtml().xpath("//div[@class='movie-info']/span/text()").all());
        System.out.println(page.getRawText());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new SzuOlddriverTest())
                .addUrl("https://www.szuolddriver.com/rank/imdbdouban")
                .thread(1)
                .run();
    }
}
