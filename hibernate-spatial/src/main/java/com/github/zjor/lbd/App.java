package com.github.zjor.lbd;

import com.github.zjor.lbd.service.EventService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:spring-context-**.xml"
        );
        EventService eventService = ctx.getBean(EventService.class);
        eventService.create("Hello world", 35.4, 56.4);
    }
}
