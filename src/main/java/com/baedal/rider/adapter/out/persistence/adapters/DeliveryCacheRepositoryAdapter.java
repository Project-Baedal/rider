package com.baedal.rider.adapter.out.persistence.adapters;

import com.baedal.rider.adapter.out.persistence.manager.DeliveryCacheManager;
import com.baedal.rider.application.port.out.DeliveryCacheRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryCacheRepositoryAdapter implements DeliveryCacheRepositoryPort {

  private final DeliveryCacheManager deliveryCacheManager;

  @Override
  public boolean isDeliveryAvailable(Long deliveryId) {
    String value = deliveryCacheManager.findByDeliveryId(deliveryId);
    return value == null;
  }

  @Override
  public void saveDelivering(Long deliveryId) {
    deliveryCacheManager.save(deliveryId);
  }
}
