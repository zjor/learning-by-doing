package com.github.zjor.aop;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ExampleService {

    @Log
    public void greetingService(String name) {
        System.out.println("Hello " + name);
    }

    @Log(level = Log.Level.WARN)
    public void varArgs(String param, String... params) {
        System.out.println(param + ": " + Arrays.toString(params));
    }

    @Log(level = Log.Level.ERROR)
    public String throwError(boolean shouldThrow) {
        if (shouldThrow) {
            throw new RuntimeException("Some error");
        } else {
            return "finishing calmly";
        }
    }

}
