package com.kris.webmagic.util;

/**
 * 文件格式判断工具类
 *
 * @author Kairou Zeng
 */
public class FileTypeUtil {

    private static final String DEFAULT_TYPE = "png";

    public static String getFileType(String imgUrl){
        String[] urlSplit = imgUrl.split("\\.");
        return urlSplit.length > 0 ? urlSplit[urlSplit.length - 1] : DEFAULT_TYPE;
    }
}
