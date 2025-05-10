package com.baedal.rider.application.service;

import com.baedal.rider.adapter.presentation.response.LoginResponse;
import com.baedal.rider.adapter.presentation.security.UserDetailsImpl;
import com.baedal.rider.application.command.ApproveDeliveryCommand.Request;
import com.baedal.rider.application.port.out.DeliveryCacheRepositoryPort;
import com.baedal.rider.application.port.out.DeliveryClientPort;
import com.baedal.rider.application.port.out.DeliveryLockPort;
import com.baedal.rider.application.port.out.MessageSendPort;
import com.baedal.rider.domain.DeliveryValidator;
import com.baedal.rider.application.port.out.RiderRosterPort;
import com.baedal.rider.domain.entity.Rider;
import com.baedal.rider.domain.repository.RiderRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderService {

  private final RiderRepository repository;

  private final PasswordEncoder passwordEncoder;

  private final UserDetailsService userDetailsService;

  private final RiderRosterPort riderRosterPort;

  @Transactional
  public void signUp(String email, String nickname, String rawPassword) {
    Rider rider = new Rider(email, nickname, passwordEncoder.encode(rawPassword));
    repository.save(rider);
  }

  @Transactional(readOnly = true)
  public LoginResponse authenticate(String email, String password) {
    UserDetailsImpl user = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);

    if (!passwordEncoder.matches(password, user.getPassword())) {
      // FIXME: Define Exception Class
      throw new IllegalArgumentException("Invalid email or password");
    }

    return new LoginResponse(
        user.rider().getId()
    );
  }

  @Transactional
  public void toggleDuty(Long riderId, boolean onDuty) {
    if (onDuty) {
      makeOnDuty(riderId);
    } else {
      makeOffDuty(riderId);
    }
  }

  private void makeOnDuty(Long riderId) {
    riderRosterPort.makeOnDuty(riderId);
  }

  private void makeOffDuty(Long riderId) {
    riderRosterPort.makeOffDuty(riderId);
  }

  private Set<Long> findAllOnDuty() {
    return riderRosterPort.findAllOnDuty();
  }



  private final DeliveryLockPort deliveryLockPort;
  private final DeliveryClientPort deliveryClient;
  private final DeliveryCacheRepositoryPort deliveryCacheRepositoryPort;
  private final MessageSendPort messageSendPort;
  private final DeliveryValidator deliveryValidator;

  public void approveDelivery(Request req) {

    Long deliveryId = req.getDeliveryId();

    // 락 획득
    boolean locked = deliveryLockPort.lockApproveDelivery(deliveryId);

    // 락 미획득시 예외 처리
    deliveryValidator.validateLockAcquired(locked);

    try {
      // 1. 캐시에서 해당 배달이 대기 중인지 확인
      boolean isDeliveryCache = deliveryCacheRepositoryPort.isDeliveryAvailable(deliveryId);

      // 2. 배달 도메인에 해당 배달 데이터를 가져오고, 배달 대기 중인지 확인
      boolean isDelivery = deliveryClient.isDeliveryAvailable(deliveryId);

      // 3. 배달 가능 상태면 캐시에 이를 저장하고 배달 상태 변경 메세지 큐 전달
      if (isDelivery && isDeliveryCache) {
        deliveryCacheRepositoryPort.saveDelivering(deliveryId);
        messageSendPort.updateToDeliveringStatus(deliveryId);
      }


    } finally {
      // 락 해제
      deliveryLockPort.unLockApproveDelivery(deliveryId);
    }

  }
}
