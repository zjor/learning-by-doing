package com.github.zjor.labs;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @RequestMapping("/")
    public String greet(@RequestParam("name") String name) {
        return "Hello " + name;
    }

}
