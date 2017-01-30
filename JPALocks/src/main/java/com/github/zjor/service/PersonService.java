package com.github.zjor.service;

import com.github.zjor.model.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.function.Consumer;

@Service
public class PersonService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Person create(String name) {
        Person person = new Person(name);
        em.persist(person);
        return person;
    }

    public Person get(Long id) {
        return em.find(Person.class, id);
    }

    @Transactional
    public void tx(Runnable runnable) {
        runnable.run();
    }

    @Transactional
    public void tx(Consumer<EntityManager> consumer) {
        consumer.accept(em);
    }


}
