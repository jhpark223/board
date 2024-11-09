package com.example.jhboard.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisLockRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public Boolean lock(Long Key){ //setnx 명령어를 활용하여 lock을 구현
        return redisTemplate.opsForValue().setIfAbsent(Key.toString(), "lock", Duration.ofMillis(2_000));
    }

    public Boolean unlock(Long Key){
        return redisTemplate.delete(Key.toString());
    }

}