package com.baedal.rider.adapter.cache;

import com.baedal.rider.adapter.cache.store.RedisRosterStore;
import com.baedal.rider.application.port.out.RiderRosterPort;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RiderRosterAdapter implements RiderRosterPort {

  @Value("${rider.roster.key}")
  private String key;

  private final RedisRosterStore store;

  public void makeOnDuty(Long riderId) {
    store.add(key, riderId);
  }

  public void makeOffDuty(Long riderId) {
    store.remove(key, riderId);
  }

  public Set<Long> findAllOnDuty() {
    return store.members(key);
  }
}
