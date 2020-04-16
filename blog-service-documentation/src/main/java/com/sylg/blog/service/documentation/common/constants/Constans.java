package com.sylg.blog.service.documentation.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量
 * 创建者 忆地球往事
 * 创建时间	2020年4月16日
 *
 */
public class Constans {
     public static  Map<String,String>  mapOptions = new HashMap<>();
     public static final String CURRENT_USER = "blogUser";

    /**
     *  文件分隔符
     */
 	 public static final String SF_FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * 行分隔符
     */
 	 public static final String SF_LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * 路径分隔符
     */
 	 public static final String SF_PATH_SEPARATOR = System.getProperty("path.separator");
 	 
     //public static final String AVATAR_PATH = ClassUtils.getDefaultClassLoader().getResource("static").getPath()+SF_FILE_SEPARATOR+"images"+SF_FILE_SEPARATOR+"avatar";

    /**
     * 头像存放
     */
     public static final String AVATAR_PATH  = "avatar";
}
