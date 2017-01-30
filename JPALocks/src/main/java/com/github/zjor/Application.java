package com.github.zjor;

import com.github.zjor.model.Person;
import com.github.zjor.service.PersonService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.LockModeType;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Application {

    public static void main(String[] args) throws InterruptedException {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-context-**.xml");

        PersonService service = ctx.getBean(PersonService.class);

        Person person = service.create("Mike");

        ExecutorService exec = Executors.newFixedThreadPool(2);
        CyclicBarrier barrier = new CyclicBarrier(2);

        exec.submit(() -> {
            service.tx(em -> {
                Person local = service.get(person.getId());

                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                em.lock(local, LockModeType.PESSIMISTIC_WRITE);

                local.setName("John");

            });
            return null;
        });

        exec.submit(() -> {
            service.tx(em -> {
                Person local = service.get(person.getId());

                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                em.lock(local, LockModeType.PESSIMISTIC_WRITE);

                local.setName("Steve");
            });
            return null;
        });

        exec.shutdown();
        exec.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println(service.get(person.getId()).getName());

    }
}
