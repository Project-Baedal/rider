package com.baedal.rider.application.command;

import lombok.Builder;
import lombok.Getter;

public class ApproveDeliveryCommand {

  @Getter
  @Builder
  public static class Request {
    private Long riderId;
    private Long deliveryId;
  }
}
