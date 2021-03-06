package com.sylg.blog.service.documentation.cache;


import lombok.extern.slf4j.Slf4j;


/**
 * String cache store.
 *
 * @author 忆地球往事
 */
@Slf4j
public abstract class StringCacheStore extends AbstractCacheStore<String, String> {

    //public <T> void putAny(String key, T value) {
    //    try {
    //        put(key, JsonUtils.objectToJson(value));
    //    } catch (JsonProcessingException e) {
    //        throw new ServiceException("Failed to convert " + value + " to json", e);
    //    }
    //}
    //
    //public <T> void putAny(@NonNull String key, @NonNull T value, long timeout, @NonNull TimeUnit timeUnit) {
    //    try {
    //        put(key, JsonUtils.objectToJson(value), timeout, timeUnit);
    //    } catch (JsonProcessingException e) {
    //        throw new ServiceException("Failed to convert " + value + " to json", e);
    //    }
    //}
    //
    //public <T> Optional<T> getAny(String key, Class<T> type) {
    //    Assert.notNull(type, "Type must not be null");
    //
    //    return get(key).map(value -> {
    //        try {
    //            return JsonUtils.jsonToObject(value, type);
    //        } catch (IOException e) {
    //            log.error("Failed to convert json to type: " + type.getName(), e);
    //            return null;
    //        }
    //    });
    //}
}
