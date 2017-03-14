package com.github.zjor;

import com.github.zjor.aop.LoggingConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"com.github.zjor.aop"})
@Import({LoggingConfig.class})
public class App {

    @Bean
    public GreetingService greetingService() {
        return new GreetingServiceImpl();
    }

    @Bean
    public ExampleService exampleService() {
        return new ExampleService();
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App.class, args);
        ExampleService service = context.getBean(ExampleService.class);

        GreetingService gs = context.getBean(GreetingService.class);

        gs.greet("Mike");

        service.greetingService("zjor");

        service.varArgs("Train", "Van1", "Van2", "Van3");

        service.varArgs("Empty train");

        service.throwError(false);
        service.throwError(true);
    }
}
