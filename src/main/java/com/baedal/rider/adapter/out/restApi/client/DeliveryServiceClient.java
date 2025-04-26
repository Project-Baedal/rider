package com.baedal.rider.adapter.out.restApi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "delivery", url = "${servers.delivery.url}")
public interface DeliveryServiceClient {

  @GetMapping("/pending/{deliveryId}")
  boolean isPendingDelivery(@PathVariable Long deliveryId);

}
