package com.github.zjor.messaging;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class Receiver {

    @Getter
    private CountDownLatch latch;

    public Receiver(int count) {
        log.info("Receiving...");
        latch = new CountDownLatch(count);
    }

    public void handleMessage(String message, String channel) {
        latch.countDown();
        log.info("{}: {}", channel, message);
    }

}
