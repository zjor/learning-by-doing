package com.github.zjor.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("@annotation(Log)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object result = joinPoint.proceed();

            log(joinPoint, result);

            return result;
        } catch (Throwable t) {
            System.out.println(joinPoint.getSignature() + " threw " + t.getMessage());
            throw t;
        }
    }

    private void log(ProceedingJoinPoint joinPoint, Object result) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String args = zip(
                Arrays.stream(signature.getParameterNames()),
                Arrays.stream(joinPoint.getArgs()),
                (name, value) -> name + ": " + value
        ).collect(Collectors.joining(", "));

        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        if (signature.getReturnType() == Void.TYPE) {
            logger.info("void {}({})", signature.getName(), args);
        } else {
            logger.info("{}({}): {}", signature.getName(), args, result);
        }
    }

    private static <A, B, C> Stream<C> zip(Stream<A> a, Stream<B> b, BiFunction<A, B, C> zipper) {
        Iterator<A> i = a.iterator();
        return b.filter(y -> i.hasNext()).map(y -> zipper.apply(i.next(), y));
    }

}
