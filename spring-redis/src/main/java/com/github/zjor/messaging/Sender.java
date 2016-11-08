package com.github.zjor.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import javax.inject.Inject;

@Slf4j
public class Sender {

    @Inject
    private RedisTemplate redisTemplate;

    public void send(final String channel, final String message) {
        redisTemplate.convertAndSend(channel, message);
//        redisTemplate.execute(new RedisCallback() {
//            public Object doInRedis(RedisConnection connection) throws DataAccessException {
//                try {
//                    return connection.publish(message.getBytes(), channel.getBytes());
//                } finally {
//                    log.info("Published {}: {}", channel, message);
//                }
//            }
//        });
    }
}
