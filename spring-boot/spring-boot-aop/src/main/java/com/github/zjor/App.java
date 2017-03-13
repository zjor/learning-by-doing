package com.github.zjor;

import com.github.zjor.aop.ExampleService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan({"com.github.zjor.aop"})
@EnableAspectJAutoProxy
public class App {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App.class, args);
        ExampleService service = context.getBean(ExampleService.class);

        service.greetingService("zjor");

        service.varArgs("Train", "Van1", "Van2", "Van3");

        service.varArgs("Empty train");

        service.throwError(false);
        service.throwError(true);
    }
}
