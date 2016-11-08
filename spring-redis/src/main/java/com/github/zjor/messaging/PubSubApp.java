package com.github.zjor.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
public class PubSubApp {

    public static final String TOPIC = "chatroom";

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        Receiver receiver = context.getBean(Receiver.class);
        final Sender sender = context.getBean(Sender.class);
        Thread t = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sender.send(TOPIC, "Hello world: " + i);
                }
            }
        });
        t.start();
        t.join();
        receiver.getLatch().await();

        ((ConfigurableApplicationContext) context).close();


    }
}
