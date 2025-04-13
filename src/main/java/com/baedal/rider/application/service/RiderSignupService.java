package com.baedal.rider.application.service;

import com.baedal.rider.application.port.in.RiderSignupUsecase;
import com.baedal.rider.domain.entity.Rider;
import com.baedal.rider.domain.repository.RiderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RiderSignupService implements RiderSignupUsecase {

  private final RiderRepository repository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void signup(String email, String nickname, String rawPassword) {
    Rider rider = new Rider(email, nickname, passwordEncoder.encode(rawPassword));
    repository.save(rider);
  }
}
