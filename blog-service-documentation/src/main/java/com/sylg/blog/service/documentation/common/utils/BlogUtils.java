package com.sylg.blog.service.documentation.common.utils;

import com.sylg.blog.service.documentation.exception.BlogScoreException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @program: blog
 * @description: 博客工具类
 * @author: 忆地球往事
 * @create: 2020-04-15 18:10
 **/
public class BlogUtils {

    /**
    * @Description: 通过反射给update进行赋值，若为空跳过
    * @Param: [update, o]
    * @return: org.springframework.data.mongodb.core.query.Update
    * @Author: 忆地球往事
    * @Date: 2020/4/22
    */
    public static Update createUpdate(Update update,Object o){
        Class<?> oClass = o.getClass();
        Field[] fields = oClass.getDeclaredFields();
        for (Field declaredField : fields) {
            if("id".equals(declaredField.getName())){
                continue;
            }
            String field = "get" + declaredField.getName().substring(0,1).toUpperCase() + declaredField.getName().substring(1);
            try {
                Method method = oClass.getMethod(field);
                Object value = method.invoke(o);
                if (!Objects.isNull(value)) {
                    update.set(declaredField.getName(),value);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return update;
    }
    /**
     * Gets random uuid without dash.
     *n
     * @return random uuid without dash
     */
    @NonNull
    public static String randomUUIDWithoutDash() {
        return StringUtils.remove(UUID.randomUUID().toString(), '-');
    }

    /**
    * @Description: 获取指定包含关键字的Pattern
    * @Param: [keyword] 关键字
    * @return: java.util.regex.Pattern
    * @Author: 忆地球往事
    * @Date: 2020/4/17
    */
    public static Pattern getPattern(String keyword){

        return Pattern.compile("^.*"+ keyword +".*$", Pattern.CASE_INSENSITIVE);
    }

    /**
     * Calculate blog score
     * @param views 观看量
     * @param createTime 博客创建时间
     * @return blog score
     */
    public static Double blogScore(Integer views ,String createTime){
        LocalDateTime create = LocalDateTime.parse(createTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Duration between = Duration.between(create, DateUtils.now());
        long days = between.toDays();

        if(days < 0){
            throw new BlogScoreException("时间差溢出");
        } else {
            long minutes = between.toMinutes();
            return computeScore(views,minutes);
        }
    }

    /**
    * @Description: 计算得分
    * @Param: [views 观看量, minutes 时间差]
    * @return: java.lang.Double
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    private static Double computeScore(Integer views ,Long minutes ){
        return   wilsonScore((double)views,(double)minutes,2.00);
    }

    /**
    * @Description: 使用威尔逊得分算法
    * @Param: [u, n, zP]
    * @return: double
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    private static double wilsonScore(double u, double n, double zP) {
        // 好评率
        try {
            double p = u / n;
            //System.out.println("好评率为：" + p);
            // 威尔逊得分
            double s = (p + (Math.pow(zP, 2) / (2. * n)) - ((zP / (2. * n)) * Math.sqrt(4. * n * (1. - p) * p + Math.pow(zP, 2)))) / (1. + Math.pow(zP, 2) / n);
            //System.out.println("威尔逊得分为：" + s);
            return s;
        }catch (Exception e){
            return 0;
        }

    }
}
