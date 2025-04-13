package com.baedal.rider.application.service;

import com.baedal.rider.adapter.presentation.response.LoginResponse;
import com.baedal.rider.application.port.in.RiderAuthenticationUsecase;
import com.baedal.rider.domain.entity.Rider;
import com.baedal.rider.domain.repository.RiderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderAuthenticationService implements RiderAuthenticationUsecase {

  private final PasswordEncoder passwordEncoder;
  private final RiderRepository repository;

  @Transactional(readOnly = true)
  public LoginResponse authenticate(String email, String password) {
    // TODO: Authentication Exception 정의
    Rider rider = repository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Wrong email or password"));

    if (!passwordEncoder.matches(password, rider.getPassword())) {
      throw new RuntimeException("Wrong email or password");
    }

    return new LoginResponse(
        rider.getId(),
        rider.getEmail()
    );
  }
}
