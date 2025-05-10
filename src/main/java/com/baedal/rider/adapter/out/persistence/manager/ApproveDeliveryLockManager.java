package com.baedal.rider.adapter.out.persistence.manager;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApproveDeliveryLockManager {

  private final RedissonClient redissonClient;

  private final String DELIVERY_LOCK_KEY = "approveDeliveryLock:";

  private String getKey(Long deliveryId) {
    return DELIVERY_LOCK_KEY  + deliveryId;
  }

  public boolean getLock(Long deliveryId) {
    try {
      String key = getKey(deliveryId);
      RLock rLock = redissonClient.getLock(key);
      return rLock.tryLock(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      return false;
    }
  }

  public void unLock(Long deliveryId) {
    String key = getKey(deliveryId);
    RLock rLock = redissonClient.getLock(key);
    rLock.unlock();
  }

}
