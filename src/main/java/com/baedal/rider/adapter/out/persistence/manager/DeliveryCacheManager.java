package com.baedal.rider.adapter.out.persistence.manager;

import com.baedal.rider.adapter.out.persistence.repository.RiderRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryCacheManager {

  private final String PREFIX = "Delivering:";

  private final RiderRedisRepository riderRedisRepository;

  private String getKey(Long key) {
    return PREFIX + key;
  }

  public void save(Long deliveryId) {
    String key = getKey(deliveryId);
    String value = deliveryId.toString();
    riderRedisRepository.save(key, value);
  }

  public String findByDeliveryId(Long deliveryId) {
    String key = getKey(deliveryId);
    return riderRedisRepository.get(key);
  }
}
