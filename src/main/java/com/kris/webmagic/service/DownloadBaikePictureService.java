package com.kris.webmagic.service;

import com.kris.webmagic.config.FileConfig;
import com.kris.webmagic.util.FileTypeUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

@Slf4j
@Service
public class DownloadBaikePictureService implements PageProcessor{

    private static final String LOGO_DIV_XPATH = "//div[@class='summary-pic']/text()";
    private static final String IMG_SRC_XPATH = "//div[@class='summary-pic']//img/@src";
    private static final String NAME_XPATH = "//dd[@class='lemmaWgt-lemmaTitle-title']/h1/text()";

    @Autowired
    private FileConfig fileConfig;
    private Site site = Site.me();

    @Override
    public void process(Page page) {
        String logoDiv = page.getHtml().xpath(LOGO_DIV_XPATH).get();
        String urlSring = page.getHtml().xpath(IMG_SRC_XPATH).get();
        String name = page.getHtml().xpath(NAME_XPATH).get() + "." + FileTypeUtil.getFileType(urlSring);
        if(logoDiv != null){
            try {
                URL url = new URL(urlSring);
                DataInputStream dataInputStream = new DataInputStream(url.openStream());

                File file = new File(fileConfig.getDir());
                if(!file.isDirectory()){
                    file.mkdir();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(fileConfig.getDir() + name);
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
        }else {
            log.info("no logo:" + name);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void downloadBaikePicture(DownloadBaikePictureService downloadBaikePictureService) {
        String key = "中国石油天然气股份有限公司";
        String url = "http://baike.baidu.com/item/" + key;
        /*List<String> list = ExcelUtil.read("E:\\webmagic\\name.xlsx");
        for(String name : list){
            String url = "http://baike.baidu.com/item/" + name;
            Spider.create(new DownloadBaikePicture())
                    .addUrl(url)
                    .run();
        }*/

        Spider.create(downloadBaikePictureService)
                .addUrl(url)
                .run();

    }
}
