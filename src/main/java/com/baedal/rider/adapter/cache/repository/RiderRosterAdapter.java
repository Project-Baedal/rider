package com.baedal.rider.adapter.cache.repository;

import com.baedal.rider.application.port.out.RiderRosterPort;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RiderRosterAdapter implements RiderRosterPort {

  @Value("${rider.roster.key}")
  private String key;

  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public void makeOnDuty(Long riderId) {
    log.debug("make on:[{}]", riderId);
    ops().add(key, riderId);
  }

  @Override
  public void makeOffDuty(Long riderId) {
    log.debug("make off:[{}]", riderId);
    ops().remove(key, riderId);
  }

  @Override
  public Set<Long> findAllOnDuty() {
    Set<Object> set = ops().members(key);
    return set == null ?
        Collections.EMPTY_SET :
        set.stream()
            .map(o -> Long.parseLong(o.toString()))
            .collect(Collectors.toSet());
  }

  private SetOperations<String, Object> ops() {
    return redisTemplate.opsForSet();
  }
}
