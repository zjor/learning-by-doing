package com.github.zjor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import redis.clients.jedis.Jedis;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        RedisTemplate template = context.getBean(RedisTemplate.class);

        ValueOperations ops = template.opsForValue();
        ops.set("my_key", "value");

        System.out.println(ops.get("my_key"));

        System.out.println(((Jedis)template.getConnectionFactory().getConnection().getNativeConnection()).get("user:101"));
        template.execute(new RedisCallback() {
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.execute("save");
                return null;
            }
        });
    }
}
