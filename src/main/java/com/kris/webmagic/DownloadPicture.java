package com.kris.webmagic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 下载百度图片
 *
 * @author Kairou Zeng
 */
public class DownloadPicture implements PageProcessor{

    private static List<String> urls;
    private static List<String> names;

    public static void setUrls(List<String> urls) {
        DownloadPicture.urls = urls;
    }

    public static void setNames(List<String> names) {
        DownloadPicture.names = names;
    }

    private Site site = Site.me();

    @Override
    public void process(Page page) {
        List<String> urlList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        JSONObject jsonObject = (JSONObject) JSONObject.parse(page.getRawText());
        JSONArray data = (JSONArray) jsonObject.get("data");
        for(int i = 0; i < data.size(); i++){
            String url = (String)data.getJSONObject(i).get("thumbURL");
            String name = (String)data.getJSONObject(i).get("fromPageTitleEnc");
            if(url!=null){
                urlList.add(url);
                nameList.add(name);
            }
        }
        setUrls(urlList);
        setNames(nameList);
    }

    @Override
    public Site getSite() {
        return site;
    }

    private void downloadPicture(List<String> urlList, List<String> nameList){
        URL url = null;
        for(int i = 0; i < urlList.size(); i++){
            try {
                url = new URL(urlList.get(i));
                DataInputStream dataInputStream = new DataInputStream(url.openStream());
                String imageName = nameList.get(i) + ".jpg";
                File file = new File("E:\\pic");
                if(!file.isDirectory()){
                    file.mkdir();
                }
                FileOutputStream fileOutputStream = new FileOutputStream("E:\\pic\\" + imageName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = dataInputStream.read(buffer)) > 0){
                    fileOutputStream.write(buffer, 0, length);
                }
                dataInputStream.close();
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String key = "陈信宏";
        DownloadPicture downloadPicture = new DownloadPicture();
        List<String> urlList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            String url = "https://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&queryWord="+key+"&word="+key+"&pn="+i*3+"0";
            Spider.create(new DownloadPicture())
                    .addUrl(url)
                    .run();
            urlList.addAll(urls);
            nameList.addAll(names);
        }
        downloadPicture.downloadPicture(urlList, nameList);
    }
}
