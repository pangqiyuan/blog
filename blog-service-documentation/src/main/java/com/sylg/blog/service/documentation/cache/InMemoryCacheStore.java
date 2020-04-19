package com.sylg.blog.service.documentation.cache;


import com.sylg.blog.service.documentation.factory.ScheduledThreadFactory;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.Assert;

import javax.annotation.PreDestroy;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * In-memory cache store.
 *
 * @author 忆地球往事
 */
@Slf4j
public abstract class InMemoryCacheStore extends AbstractCacheStore<String, Integer> {

    /**
     * Cleaner schedule period. (ms)
     */
    private final static long PERIOD = 60 * 1000;

    /**
     * Blog Views Cache container.
     */
    private final ConcurrentHashMap<String, CacheWrapper<Integer>> BlogViewsCache = new ConcurrentHashMap<>(16);


    private final ScheduledExecutorService scheduledExecutorService;

    /**
     * Lock.
     */
    private Lock lock = new ReentrantLock();

    public InMemoryCacheStore() {
        scheduledExecutorService = new ScheduledThreadPoolExecutor(2 , new ScheduledThreadFactory("VC-schedule"));
        scheduledExecutorService.scheduleWithFixedDelay(new CacheExpiryCleaner(), 0, PERIOD , TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleWithFixedDelay(new SyncDataToDatabaseTask(),0,PERIOD ,TimeUnit.MILLISECONDS);
    }

    @Override
    Optional<CacheWrapper<Integer>> getInternal(String key) {
        Assert.hasText(key, "Cache key must not be blank");

        return Optional.ofNullable(BlogViewsCache.get(key));
    }

    @Override
    void putInternal(String key, CacheWrapper<Integer> cacheWrapper) {
        Assert.hasText(key, "Cache key must not be blank");
        Assert.notNull(cacheWrapper, "Cache wrapper must not be null");

        // Put the cache wrapper
        CacheWrapper<Integer> putCacheWrapper = BlogViewsCache.put(key, cacheWrapper);

        log.debug("Put [{}] cache result: [{}], original cache wrapper: [{}]", key, putCacheWrapper, cacheWrapper);
    }

    @Override
    Boolean putInternalIfAbsent(String key, CacheWrapper<Integer> cacheWrapper) {
        Assert.hasText(key, "Cache key must not be blank");
        Assert.notNull(cacheWrapper, "Cache wrapper must not be null");

        log.debug("Preparing to put key: [{}], value: [{}]", key, cacheWrapper);

        lock.lock();
        try {
            // Get the value before
            Optional<Integer> valueOptional = get(key,false);

            if (valueOptional.isPresent()) {
                log.warn("Failed to put the cache, because the key: [{}] has been present already", key);
                return false;
            }

            // Put the cache wrapper
            putInternal(key, cacheWrapper);
            log.debug("Put successfully");
            return true;
        } finally {
            lock.unlock();
        }
    }



    @Override
    public void delete(String key) {
        Assert.hasText(key, "Cache key must not be blank");

        BlogViewsCache.remove(key);
        log.debug("Removed key: [{}]", key);
    }

    @PreDestroy
    public void preDestroy() {
        log.debug("Cancelling all timer tasks");
        scheduledExecutorService.shutdown();
        clear();
    }

    private void clear() {
        BlogViewsCache.clear();
    }

    /**
     * Cache cleaner.
     *
     * @author 忆地球往事
     * @date 2020-04-11 17:08
     */
    private class CacheExpiryCleaner implements Runnable {

        @Override
        public void run() {
            BlogViewsCache.keySet().forEach(key -> {
                if (!InMemoryCacheStore.this.get(key,true).isPresent()) {
                    log.debug("Deleted the cache: [{}] for expiration", key);
                }
            });
        }
    }

    /**
     * Cache cleaner.
     *
     * @author 忆地球往事
     * @date 2020-04-11 17:08
     */
    private class SyncDataToDatabaseTask implements Runnable {


        @Override
        public void run() {
            BlogViewsCache.keySet().forEach(key -> {
                syncDataToDatabase(key,BlogViewsCache.get(key).getData());
                log.debug("Sync Data :[{}] To Database", key);
            });
            if(!BlogViewsCache.isEmpty()){
                log.debug("Clear data after synchronization");
                clear();
            }
        }
    }
}
