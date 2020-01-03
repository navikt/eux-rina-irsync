package no.nav.eux.rina.admin.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

  public static final String CPI_SESSION_CACHE = "CPI_SESSION_CACHE";

  @Bean
  public CacheManager cacheManager() {
    SimpleCacheManager cacheManager = new SimpleCacheManager();
    cacheManager.setCaches(Arrays.asList(
        new CaffeineCache(CPI_SESSION_CACHE, Caffeine.newBuilder()
            //default RINA cpiSession is 30 minutes
            .expireAfterWrite(29, TimeUnit.MINUTES)
            .recordStats()
            .build())
    ));

    return cacheManager;
  }
}