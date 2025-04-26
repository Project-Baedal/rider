package com.baedal.rider.adapter.out.messaging;

import com.baedal.rider.application.port.out.MessageSendPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSenderAdapter implements MessageSendPort {

  private final KafkaSender kafkaSender;

  @Override
  public void updateToDeliveringStatus(Long deliveryId) {
    String key = deliveryId.toString();
    kafkaSender.sendMessage("delivery.updateDeliveryStatus", key, "Delivering");
  }
}
