package com.baedal.rider.application.port.out;

public interface MessageSendPort {

  void updateToDeliveringStatus(Long deliveryId);
}
