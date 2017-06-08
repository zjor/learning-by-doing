package com.github.zjor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Random;

@Configuration
@PropertySource("classpath:application.properties")
public class App {

    @Value("${prop}")
    private String property;

    @Autowired
    private ConfigurableEnvironment env;

    @Bean
    public RandomPropertySource propertySource() {
        Random random = new Random(0L);
        RandomPropertySource propertySource = new RandomPropertySource("random", random);
        env.getPropertySources().addFirst(propertySource);
        return propertySource;
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        System.out.println(context.getEnvironment().getProperty("prop"));
        System.out.println(context.getEnvironment().getProperty("randomString"));

    }
}
