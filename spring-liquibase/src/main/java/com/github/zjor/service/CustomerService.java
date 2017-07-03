package com.github.zjor.service;

import com.github.zjor.entity.Customer;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CustomerService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Customer create(String firstName, String lastName) {
        Customer c = new Customer(firstName, lastName);
        em.persist(c);
        return c;
    }

    public List<Customer> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> q = cb.createQuery(Customer.class);
        Root<Customer> r = q.from(Customer.class);
        q.select(r);
        return em.createQuery(q).getResultList();
    }
}
