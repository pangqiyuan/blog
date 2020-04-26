package com.sylg.blog.service.documentation.cache.lock;

import com.sylg.blog.service.documentation.cache.ViewsCacheStore;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Interceptor for cache lock annotation.
 *
 * @author 忆地球往事
 * @date 2020-04-11 11:28
 */
@Slf4j
@Aspect
@Configuration
public class CacheLockInterceptor {

    private final static String CACHE_LOCK_PREFOX = "cache_lock_";

    private final static String CACHE_LOCK_VALUE = "locked";

    private ViewsCacheStore viewsCacheStore;

    /**
     * Lock.
     */
    private Lock lock = new ReentrantLock();

    public CacheLockInterceptor(ViewsCacheStore viewsCacheStore) {
        this.viewsCacheStore = viewsCacheStore;
    }

    @After("execution(* com.sylg.blog.service.documentation.controller.web.BlogController.*(..)) && @annotation(CacheLock)")
    @Async("viewTaskExecutor")
    public void incrViews(JoinPoint joinPoint){
        // Get cache lock
        CacheLock cacheLock = get(joinPoint);

        Object[] args = joinPoint.getArgs();

        String id = (String) args[0];

        log.debug("Starting blog views id : [{}]",id);
        Integer view;

        lock.lock();
        try {
            Optional<Integer> views = viewsCacheStore.get(id, true);

            //Get the latest data from the database
            view = views.orElseGet(() ->{
                Integer data = viewsCacheStore.getDataFromDatabase(id);
                log.debug("Database views count:{}",data);
                return data;
            } );

            log.debug("Cache views count:{}",view);
            viewsCacheStore.put(id ,++view,cacheLock.expired(),cacheLock.timeUnit());

        }finally {
            lock.unlock();
        }

    }




    //@Around("@annotation(CacheLock)")
    public Object interceptCacheLock(ProceedingJoinPoint joinPoint) throws Throwable {

        // Get cache lock
        CacheLock cacheLock = get(joinPoint);

        // Build cache lock key
        String cacheLockKey = buildCacheLockKey(cacheLock, joinPoint);

        log.debug("Built lock key: [{}]", cacheLockKey);


        try {
            // Get from cache
            Boolean cacheResult = viewsCacheStore.putIfAbsent(cacheLockKey, 1, cacheLock.expired(), cacheLock.timeUnit());

            //if (cacheResult == null) {
            //    throw new ServiceException("Unknown reason of cache " + cacheLockKey).setErrorData(cacheLockKey);
            //}
            //
            //if (!cacheResult) {
            //    throw new FrequentAccessException("访问过于频繁，请稍后再试！").setErrorData(cacheLockKey);
            //}

            // Proceed the method
            return joinPoint.proceed();
        } finally {
            // Delete the cache
            if (cacheLock.autoDelete()) {
                viewsCacheStore.delete(cacheLockKey);
                log.debug("Deleted the cache lock: [{}]", cacheLock);
            }
        }
    }

    private CacheLock get(JoinPoint joinPoint){
        // Get method signature
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        log.debug("Starting locking: [{}]", methodSignature.toString());

        return methodSignature.getMethod().getAnnotation(CacheLock.class);
    }

    private String buildCacheLockKey(@NonNull CacheLock cacheLock, @NonNull ProceedingJoinPoint joinPoint) {
        Assert.notNull(cacheLock, "Cache lock must not be null");
        Assert.notNull(joinPoint, "Proceeding join point must not be null");

        // Get the method
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // Build the cache lock key
        StringBuilder cacheKeyBuilder = new StringBuilder(CACHE_LOCK_PREFOX);

        String delimiter = cacheLock.delimiter();

        if (StringUtils.isNotBlank(cacheLock.prefix())) {
            cacheKeyBuilder.append(cacheLock.prefix());
        } else {
            cacheKeyBuilder.append(methodSignature.getMethod().toString());
        }

        // Handle cache lock key building
        Annotation[][] parameterAnnotations = methodSignature.getMethod().getParameterAnnotations();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            log.debug("Parameter annotation[{}] = {}", i, parameterAnnotations[i]);

            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                Annotation annotation = parameterAnnotations[i][j];
                log.debug("Parameter annotation[{}][{}]: {}", i, j, annotation);
                if (annotation instanceof CacheParam) {
                    // Get current argument
                    Object arg = joinPoint.getArgs()[i];
                    log.debug("Cache param args: [{}]", arg);

                    // Append to the cache key
                    cacheKeyBuilder.append(delimiter).append(arg.toString());
                }
            }
        }

        //if (cacheLock.traceRequest()) {
        //    // Append http request info
        //    cacheKeyBuilder.append(delimiter).append(ServletUtils.getRequestIp());
        //}

        return cacheKeyBuilder.toString();
    }
}
