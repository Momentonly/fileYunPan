package com.szxy.config.cache;

import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * 自定义一个CacheManager
 * 参考：https://blog.csdn.net/zl_momomo/article/details/80403564
 */
//@Configuration
public class SimpleCacheConf {

    @Bean
    SimpleCacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        // 一个缓存名称
        ConcurrentMapCache cacheManager = new ConcurrentMapCache("user");
        simpleCacheManager.setCaches(Arrays.asList(cacheManager));
        return simpleCacheManager;
    }
}
