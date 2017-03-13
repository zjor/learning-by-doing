package com.github.zjor.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Log {

    enum Level {
        TRACE, DEBUG, INFO, WARN, ERROR
    }

    Level level() default Level.INFO;

}
