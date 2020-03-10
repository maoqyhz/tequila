package com.example.mq;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis configuration
 *
 * @author Fururur
 * @create 2019-12-10-14:19
 */
@EnableCaching
@Configuration
public class RedisConfig  {

    /**
     * 自定义 redisTemplate 配置序列化相关
     *
     * @param factory
     * @return
     */
    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 配置序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());

        return template;
    }

    @Primary
    @Bean("redisTemplateJson")
    public RedisTemplate<String, Object> redisTemplateJson(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // 使用 fastjson 作为缓存序列化器
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(fastJsonRedisSerializer);

        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(stringRedisSerializer);

        // template.setHashValueSerializer(fastJsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }


    // 下面是配置 pub/sub

    @Bean
    Receiver receiver1() {
        return new Receiver(1L);
    }

    @Bean
    Receiver receiver2() {
        return new Receiver(2L);
    }

    @Bean
    MessageListenerAdapter listenerAdapter1(Receiver receiver1) {
        return new MessageListenerAdapter(receiver1, "receiveMessage");
    }

    @Bean
    MessageListenerAdapter listenerAdapter2(Receiver receiver2) {
        return new MessageListenerAdapter(receiver2, "receiveMessage");
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter1,
                                            MessageListenerAdapter listenerAdapter2) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter1, new PatternTopic("chat"));
        container.addMessageListener(listenerAdapter2, new PatternTopic("chat"));
        return container;
    }
}
