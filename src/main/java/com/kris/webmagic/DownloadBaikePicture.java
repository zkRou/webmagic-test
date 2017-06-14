package com.kris.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

/**
 * Created by Kairou Zeng on 2017/6/14.
 */
public class DownloadBaikePicture implements PageProcessor{

    private Site site = Site.me();

    @Override
    public void process(Page page) {
        String urlSring = page.getHtml().xpath("//div[@class='summary-pic']//img/@src").get();
        try {
            URL url = new URL(urlSring);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            String name = page.getHtml().xpath("//dd[@class='lemmaWgt-lemmaTitle-title']/h1/text()").get() + ".png";
            File file = new File("F:\\pic\\");
            if(!file.isDirectory()){
                file.mkdir();
            }
            FileOutputStream fileOutputStream = new FileOutputStream("F:\\pic\\" + name);
            byte[] buffer = new byte[1024];
            int length;
            while((length = dataInputStream.read(buffer)) > 0){
                fileOutputStream.write(buffer, 0, length);
            }
            dataInputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
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
