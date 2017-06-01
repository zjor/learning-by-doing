package com.github.zjor;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(final String... args) throws Exception {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(AmqpConfiguration.class);
        AmqpTemplate template = context.getBean(AmqpTemplate.class);
        template.convertAndSend("myqueue", "foo");
        String foo = (String) template.receiveAndConvert("myqueue");

        System.out.println("Received: " + foo);

    }
}
