package com.sylg.blog.service.documentation.cache;

import com.sylg.blog.service.documentation.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Abstract cache store.
 *
 * @author 忆地球往事
 * @date 2020-04-10 15:28
*/
@Slf4j
public abstract class AbstractCacheStore<K, V> implements CacheStore<K, V> {

    /**
     * Get cache wrapper by key.
     *
     * @param key key must not be null
     * @return an optional cache wrapper
     */
    @NonNull
    abstract Optional<CacheWrapper<V>> getInternal(@NonNull K key);

    /**
     * Puts the cache wrapper.
     *
     * @param key          key must not be null
     * @param cacheWrapper cache wrapper must not be null
     */
    abstract void putInternal(@NonNull K key, @NonNull CacheWrapper<V> cacheWrapper);

    /**
     * Puts the cache wrapper if the key is absent.
     *
     * @param key          key must not be null
     * @param cacheWrapper cache wrapper must not be null
     * @return true if the key is absent and the value is set, false if the key is present before, or null if any other reason
     */
    abstract Boolean putInternalIfAbsent(@NonNull K key, @NonNull CacheWrapper<V> cacheWrapper);

    /**
    * @Description: 同步数据到数据库
    * @Param: [key, value]
    * @return: void
    * @Author: 忆地球往事
    * @Date: 2020/4/10
    */
     protected abstract void syncDataToDatabase(@NonNull K key,@NonNull V value);

    @Override
    public Optional<V> get(K key , boolean isSyncDataToDatabase) {
        Assert.notNull(key, "Cache key must not be blank");

        return getInternal(key).map(cacheWrapper -> {

            // Check expiration
            if (cacheWrapper.getExpireAt() != null && cacheWrapper.getExpireAt().isBefore(DateUtils.now())) {

                    if(isSyncDataToDatabase){
                        //同步数据到数据库
                        syncDataToDatabase(key,cacheWrapper.getData());
                    }

                    // Expired then delete it
                    log.warn("Cache key: [{}] has been expired", key);

                    // Delete the key
                    delete(key);

                    return null;
            }

            return cacheWrapper.getData();
        });
    }

    @Override
    public void put(K key, V value, long timeout, TimeUnit timeUnit) {
        putInternal(key, buildCacheWrapper(value, timeout, timeUnit));
    }

    @Override
    public Boolean putIfAbsent(K key, V value, long timeout, TimeUnit timeUnit) {
        return putInternalIfAbsent(key, buildCacheWrapper(value, timeout, timeUnit));
    }

    @Override
    public void put(K key, V value) {
        putInternal(key, buildCacheWrapper(value, 0, null));
    }

    /**
     * Builds cache wrapper.
     *
     * @param value    cache value must not be null
     * @param timeout  the key expiry time, if the expiry time is less than 1, the cache won't be expired
     * @param timeUnit timeout unit must
     * @return cache wrapper
     */
    @NonNull
    private CacheWrapper<V> buildCacheWrapper(@NonNull V value, long timeout, @Nullable TimeUnit timeUnit) {

        Assert.notNull(value, "Cache value must not be null");
        Assert.isTrue(timeout >= 0, "Cache expiration timeout must not be less than 1");

        LocalDateTime now = DateUtils.now();

        LocalDateTime expireAt = null;
        if (timeout > 0 && timeUnit != null) {
            expireAt = DateUtils.add(now, timeout, timeUnit);
        }

        // Build cache wrapper
        CacheWrapper<V> cacheWrapper = new CacheWrapper<>();
        cacheWrapper.setCreateAt(now);
        cacheWrapper.setExpireAt(expireAt);
        cacheWrapper.setData(value);

        return cacheWrapper;
    }
}
