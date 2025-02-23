package com.teamfresh.store.global.redis.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {
    String value() default "";
    long waitTime() default 5000L;
    long leaseTime() default 20000L;
}
