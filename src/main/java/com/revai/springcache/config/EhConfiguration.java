package com.revai.springcache.config;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 *
 * Caching is a temporary storage near to the client helps to store
 * frequently accessed data, there by reducing need to fetch info
 * from the database for repetitive request.
 */

@Configuration
@EnableCaching
public class EhConfiguration {

    public CacheManager cacheManager() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("myCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                String.class,
                                String.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .heap(10) // 10 entries in heap
                                        .offheap(50, MemoryUnit.MB) // 50 MB off-heap
                                        .disk(500, MemoryUnit.MB, true) // 500 MB on disk, persistent
                                )
                                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(5)))
                )
                .build(true);

        return cacheManager;

    }
}
