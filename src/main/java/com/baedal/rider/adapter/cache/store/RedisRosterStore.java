package com.baedal.rider.adapter.cache.store;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisRosterStore {

  private final RedisTemplate<String, Object> redisTemplate;

  public void add(String key, Long riderId) {
    ops().add(key, riderId);
  }

  public void remove(String key, Long riderId) {
    ops().remove(key, riderId);
  }

  public Set<Long> members(String key) {
    Set<Object> rawSet = ops().members(key);
    return rawSet == null ?
        Collections.emptySet()
        : rawSet.stream()
            .map(o -> Long.parseLong(o.toString()))
            .collect(Collectors.toSet());
  }

  private SetOperations<String, Object> ops() {
    return redisTemplate.opsForSet();
  }
}
