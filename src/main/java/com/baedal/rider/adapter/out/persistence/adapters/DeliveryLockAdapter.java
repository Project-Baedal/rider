package com.baedal.rider.adapter.out.persistence.adapters;

import com.baedal.rider.adapter.out.persistence.manager.ApproveDeliveryLockManager;
import com.baedal.rider.application.port.out.DeliveryLockPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryLockAdapter implements DeliveryLockPort {

  private final ApproveDeliveryLockManager approveDeliveryLockManager;

  @Override
  public boolean lockApproveDelivery(Long deliveryId) {
    return approveDeliveryLockManager.getLock(deliveryId);
  }

  @Override
  public void unLockApproveDelivery(Long deliveryId) {
    approveDeliveryLockManager.unLock(deliveryId);
  }
}
