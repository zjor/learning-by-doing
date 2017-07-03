package com.github.zjor;

import com.github.zjor.service.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan({"com.github.zjor.entity"})
public class App {

    @Bean
    public CustomerService customerService() {
        return new CustomerService();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class);
        CustomerService service = context.getBean(CustomerService.class);

        service.create("Bruce", "Brown");

        System.out.println(service.getAll());

    }
}
