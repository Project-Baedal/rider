package com.baedal.rider.domain;

import org.springframework.stereotype.Component;

@Component
public class DeliveryValidator {

  public void validateLockAcquired(boolean locked) {
    if (!locked) {
      throw new IllegalStateException("배달 승인 락 획득 실패");
    }
  }
}
