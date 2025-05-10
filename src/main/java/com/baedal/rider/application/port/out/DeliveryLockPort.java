package com.baedal.rider.application.port.out;

public interface DeliveryLockPort {

  boolean lockApproveDelivery(Long deliveryId);
  void unLockApproveDelivery(Long deliveryId);

}
