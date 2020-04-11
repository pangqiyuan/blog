package com.sylg.blog.service.documentation.cache.lock;

import java.lang.annotation.*;

/**
 * Cache parameter annotation.
 *
 * @author 忆地球往事
 * @date 2020-04-11 11:28
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheParam {

}
