package com.example.app.utils;


import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 缓存管理类
 *
 * @author Fururur
 * @create 2019-12-03-14:12
 */
@Component
public class CaffeineCacheManager {
    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>(16);

    /**
     * 缓存创建
     *
     * @param cacheName
     * @param cache
     */
    public void createCache(String cacheName, Cache cache) {
        cacheMap.put(cacheName, cache);
    }

    /**
     * 缓存获取
     *
     * @param name
     * @return
     */
    public synchronized Cache getCache(String name) {
        Cache cache = this.cacheMap.get(name);
        if (cache == null) {
            throw new IllegalArgumentException("No this cache.");
        }
        return cache;
    }

}