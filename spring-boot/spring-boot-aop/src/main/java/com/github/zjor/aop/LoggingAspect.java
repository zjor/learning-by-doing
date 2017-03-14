package com.github.zjor.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Aspect
public class LoggingAspect {

    @Around("@annotation(logAnnotation)")
    public Object log(ProceedingJoinPoint joinPoint, Log logAnnotation) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Stream<Object> argStream = Arrays.stream(joinPoint.getArgs());
        String args = Optional.ofNullable(signature.getParameterNames())
                .map(names ->
                        zip(
                                Arrays.stream(signature.getParameterNames()),
                                argStream,
                                (name, value) ->
                                        name + ": " + stringify(value))
                                .collect(Collectors.joining(", ")))
                .orElseGet(() ->
                        argStream.map(LoggingAspect::stringify)
                                .collect(Collectors.joining(", ")));

        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        try {
            Object result = joinPoint.proceed();

            String message;
            if (signature.getReturnType() == Void.TYPE) {
                message = MessageFormat.format("void {0}({1})", signature.getName(), args);
            } else {
                message = MessageFormat.format("{0}({1}): {2}", signature.getName(), args, result);
            }

            log(logger, message, logAnnotation.level());

            return result;
        } catch (Throwable t) {
            String message = MessageFormat.format("{0}({1}) threw {2}", signature.getName(), args, t);
            log(logger, message, Log.Level.ERROR);
            throw t;
        }
    }

    private void log(Logger log, String message, Log.Level level) {
        switch (level) {
            case TRACE:
                log.trace(message);
                return;
            case DEBUG:
                log.debug(message);
                return;
            case INFO:
                log.info(message);
                return;
            case WARN:
                log.warn(message);
                return;
            case ERROR:
                log.error(message);
                return;
        }
    }

    private static <A, B, C> Stream<C> zip(Stream<A> a, Stream<B> b, BiFunction<A, B, C> zipper) {
        Iterator<A> i = a.iterator();
        return b.filter(y -> i.hasNext()).map(y -> zipper.apply(i.next(), y));
    }

    private static String stringify(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Object[]) {
            return Arrays.toString((Object[]) obj);
        } else {
            return obj.toString();
        }
    }

}
