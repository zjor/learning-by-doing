package com.github.zjor;

import com.github.zjor.model.Person;
import com.github.zjor.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.LockModeType;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
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

                em.lock(local, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
                log.info("Lock acquired for John");
                local.setName("John");
                log.info("Name set to John; {}", person);

                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return null;
        });

        exec.submit(() -> {
            service.tx(em -> {
                Person local = service.get(person.getId());
                log.info("Seeing name: {}", person.getName());

                em.lock(local, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("Lock acquired for Steve");
                local.setName("Steve");
                log.info("Name set to Steve; {}", person);
            });
            return null;
        });

        exec.shutdown();
        exec.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println(service.get(person.getId()));

    }
}
