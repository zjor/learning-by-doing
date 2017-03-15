package com.github.zjor;

import com.github.zjor.aop.Log;

public class GreetingServiceImpl implements GreetingService {

    @Log
    @Override
    public String greet(String name) {
        return "Hello " + name;
    }
}
