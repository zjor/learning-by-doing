package com.github.zjor.lbd;

import com.github.zjor.lbd.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Random;
import java.util.UUID;

@Slf4j
public class App {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:spring-context-**.xml"
        );
        EventService eventService = ctx.getBean(EventService.class);
//        createSampleEvents(eventService, 50, 10.0, 15.0, 50.0);
        log.info("{}", eventService.withinRadius(10.0, 15.0, 50.0));
    }

    private static void createSampleEvents(EventService service, int count, double centerX, double centerY, double r) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            double x = r * (random.nextDouble() * 2.0 - 1.0) + centerX;
            double y = r * (random.nextDouble() * 2.0 - 1.0) + centerY;
            service.create(UUID.randomUUID().toString(), x, y);
        }
    }


}
