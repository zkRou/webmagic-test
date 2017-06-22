package com.kris.webmagic;

import com.kris.webmagic.service.DownloadBaikePictureService;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SpringBootApplication
public class Application implements ApplicationContextAware{

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        DownloadBaikePictureService downloadBaikePictureService = applicationContext.getBean(DownloadBaikePictureService.class);
        downloadBaikePictureService.downloadBaikePicture();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Application.applicationContext = applicationContext;
    }
}