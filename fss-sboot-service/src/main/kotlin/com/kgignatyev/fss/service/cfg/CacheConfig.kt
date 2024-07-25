package com.kgignatyev.fss.service.cfg

import com.hazelcast.cache.HazelcastCacheManager
import com.hazelcast.config.CacheConfig
import com.hazelcast.config.CacheConfiguration
import com.hazelcast.config.InMemoryFormat
import com.hazelcast.config.MapConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.util.*

@Component
@ConfigurationProperties(prefix = "spring.cache")
class HazelcastProperties {
    lateinit var properties: Properties
}

@Component
class HazelcastCustomizer(): CacheManagerCustomizer<CacheManager> {
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    override fun customize(cacheManager: CacheManager) {
        logger.info("customizing cache manager $cacheManager")
//        if (cacheManager is HazelcastCacheManager) {
//            val hzCm:HazelcastCacheManager = cacheManager
//            hzCm.createCache("enforcers", enforcersConfig())
//            logger.info("cacheNames: ${hzCm.cacheNames}")
//        }
    }

    private fun enforcersConfig(): CacheConfiguration<String,Any> {
        val c = CacheConfig<String,Any>("enforcers")
        c.inMemoryFormat = InMemoryFormat.OBJECT
        return c
    }


}

@Configuration
class CacheConfig {
//    @Bean
//    fun hazelcastInstance(): HazelcastInstance {
//        val instance = Hazelcast.newHazelcastInstance()
//        instance.config.addMapConfig(MapConfig("enforcers").setTimeToLiveSeconds(50))
//        return instance
//    }
//
//    @Bean
//    fun hzConfig( hzProperties: HazelcastProperties):Config {
//        val config = ClasspathXmlConfig("hazelcast.xml",hzProperties.properties)
//        config.properties
//        return config
//    }



//    @Bean
//    fun cacheManager(): CacheManager {
//        val hzCp = HazelcastCachingProvider()
//        return hzCp.cacheManager
//    }



}
