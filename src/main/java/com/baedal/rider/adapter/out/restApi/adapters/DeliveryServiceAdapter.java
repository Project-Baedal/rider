package com.baedal.rider.adapter.out.restApi.adapters;

import com.baedal.rider.adapter.out.restApi.client.DeliveryServiceClient;
import com.baedal.rider.application.port.out.DeliveryClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryServiceAdapter implements DeliveryClientPort {

  private final DeliveryServiceClient deliveryServiceClient;

  @Override
  public boolean isDeliveryAvailable(Long deliveryId) {
    return deliveryServiceClient.isPendingDelivery(deliveryId);
  }
}
