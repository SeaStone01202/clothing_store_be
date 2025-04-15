package com.java6.asm.clothing_store.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

    /**
     * ✅ Cấu hình RedisTemplate để thao tác với Redis
     * - Sử dụng RedisTemplate để lưu và truy xuất dữ liệu từ Redis
     * - Key và Value sẽ được lưu dưới dạng String
     *
     * @param redisConnectionFactory Kết nối Redis được Spring Boot quản lý
     * @return RedisTemplate<String, String> để thao tác với Redis
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }
}
