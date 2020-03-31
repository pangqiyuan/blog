package com.sylg.blog.service.documentation.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SessionCache {

    private static final HashMap<String,Object> SESSION_CACHE = new HashMap<>(256);

    public void put (String cacheName ,Object cache){
        SESSION_CACHE.put(cacheName,cache);
    }

    public Object get(String cacheName){
        return SESSION_CACHE.get(cacheName);
    }

    public void remove(String cacheName){
        SESSION_CACHE.remove(cacheName);
    }

}
