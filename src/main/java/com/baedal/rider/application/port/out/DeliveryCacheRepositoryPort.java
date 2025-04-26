package com.baedal.rider.application.port.out;

public interface DeliveryCacheRepositoryPort {

  boolean isDeliveryAvailable(Long deliveryId);

  void saveDelivering(Long deliveryId);
}
