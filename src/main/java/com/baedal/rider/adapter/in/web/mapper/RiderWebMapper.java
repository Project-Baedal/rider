package com.baedal.rider.adapter.in.web.mapper;

import com.baedal.rider.application.command.ApproveDeliveryCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RiderWebMapper {

  // 배달 승인
  ApproveDeliveryCommand.Request approveDeliveryToCommand(Long riderId, Long deliveryId);
}
