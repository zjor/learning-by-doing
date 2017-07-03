package com.github.zjor.service;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class CustomerServiceTest {

    @Autowired
    private CustomerService service;

    @Value("${my-property}")
    private String property;

    @Test
    public void shouldCreateAndFind() {
        service.create("Alice", "Test");
        Assert.assertTrue(service.getAll().size() == 1);
    }

    @Test
    public void shouldInjectProperty() {
        Assert.assertEquals("value", property);
    }


}
