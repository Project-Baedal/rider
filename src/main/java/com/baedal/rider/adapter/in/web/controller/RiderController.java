package com.baedal.rider.adapter.in.web.controller;

import com.baedal.rider.adapter.in.web.mapper.RiderWebMapper;
import com.baedal.rider.application.command.ApproveDeliveryCommand;
import com.baedal.rider.application.port.in.RiderUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/rider/v0")
@RequiredArgsConstructor
@Slf4j
public class RiderController {

  private final RiderWebMapper riderMapper;
  private final RiderUsecase riderUsecase;

  @PreAuthorize("hasRole('RIDER')")
  @PostMapping("/approveDelivery")
  public ResponseEntity<Void> approveDelivery(
      @AuthenticationPrincipal Long riderId,
      @RequestBody Long deliveryId
  ) {
    ApproveDeliveryCommand.Request command = riderMapper.approveDeliveryToCommand(
        riderId, deliveryId
    );
    riderUsecase.approveDelivery(command);
    return ResponseEntity.noContent().build();
  }
}
