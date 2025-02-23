package com.teamfresh.store.global.redis.aop;

import com.teamfresh.store.global.redis.annotation.RedisLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RedissonLockAspect {
    private final RedissonClient redissonClient;

    @Around("@annotation(com.teamfresh.store.global.redis.annotation.RedisLock)")
    public Object redissonLock(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedisLock annotation = method.getAnnotation(RedisLock.class);


        String lockKey = method.getName();
        RLock lock = redissonClient.getLock(lockKey);

        try {
            boolean lockable = lock.tryLock(annotation.waitTime(), annotation.leaseTime(), TimeUnit.MILLISECONDS);
            if(!lockable) {
                log.info("Lock 획득 실패 = {}", lockKey);
                return null;
            }
            log.info("로직 수행");
            return joinPoint.proceed();
        } catch (InterruptedException e) {
            log.warn("Lock획득 중 에러발생  = {}", lockKey);
            throw e;
        } finally {
            log.info("락 해제");
            lock.unlock();
        }

    }
}
